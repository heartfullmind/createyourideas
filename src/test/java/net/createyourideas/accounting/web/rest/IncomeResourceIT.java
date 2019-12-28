package net.createyourideas.accounting.web.rest;

import net.createyourideas.accounting.HomeApp;
import net.createyourideas.accounting.domain.Income;
import net.createyourideas.accounting.repository.IncomeRepository;
import net.createyourideas.accounting.service.IncomeService;
import net.createyourideas.accounting.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static net.createyourideas.accounting.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link IncomeResource} REST controller.
 */
@SpringBootTest(classes = HomeApp.class)
public class IncomeResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_VALUE = 1F;
    private static final Float UPDATED_VALUE = 2F;

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restIncomeMockMvc;

    private Income income;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IncomeResource incomeResource = new IncomeResource(incomeService);
        this.restIncomeMockMvc = MockMvcBuilders.standaloneSetup(incomeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Income createEntity(EntityManager em) {
        Income income = new Income()
            .description(DEFAULT_DESCRIPTION)
            .date(DEFAULT_DATE)
            .value(DEFAULT_VALUE);
        return income;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Income createUpdatedEntity(EntityManager em) {
        Income income = new Income()
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .value(UPDATED_VALUE);
        return income;
    }

    @BeforeEach
    public void initTest() {
        income = createEntity(em);
    }

    @Test
    @Transactional
    public void createIncome() throws Exception {
        int databaseSizeBeforeCreate = incomeRepository.findAll().size();

        // Create the Income
        restIncomeMockMvc.perform(post("/api/incomes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(income)))
            .andExpect(status().isCreated());

        // Validate the Income in the database
        List<Income> incomeList = incomeRepository.findAll();
        assertThat(incomeList).hasSize(databaseSizeBeforeCreate + 1);
        Income testIncome = incomeList.get(incomeList.size() - 1);
        assertThat(testIncome.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testIncome.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testIncome.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createIncomeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = incomeRepository.findAll().size();

        // Create the Income with an existing ID
        income.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncomeMockMvc.perform(post("/api/incomes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(income)))
            .andExpect(status().isBadRequest());

        // Validate the Income in the database
        List<Income> incomeList = incomeRepository.findAll();
        assertThat(incomeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllIncomes() throws Exception {
        // Initialize the database
        incomeRepository.saveAndFlush(income);

        // Get all the incomeList
        restIncomeMockMvc.perform(get("/api/incomes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(income.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getIncome() throws Exception {
        // Initialize the database
        incomeRepository.saveAndFlush(income);

        // Get the income
        restIncomeMockMvc.perform(get("/api/incomes/{id}", income.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(income.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingIncome() throws Exception {
        // Get the income
        restIncomeMockMvc.perform(get("/api/incomes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIncome() throws Exception {
        // Initialize the database
        incomeService.save(income);

        int databaseSizeBeforeUpdate = incomeRepository.findAll().size();

        // Update the income
        Income updatedIncome = incomeRepository.findById(income.getId()).get();
        // Disconnect from session so that the updates on updatedIncome are not directly saved in db
        em.detach(updatedIncome);
        updatedIncome
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .value(UPDATED_VALUE);

        restIncomeMockMvc.perform(put("/api/incomes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIncome)))
            .andExpect(status().isOk());

        // Validate the Income in the database
        List<Income> incomeList = incomeRepository.findAll();
        assertThat(incomeList).hasSize(databaseSizeBeforeUpdate);
        Income testIncome = incomeList.get(incomeList.size() - 1);
        assertThat(testIncome.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testIncome.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testIncome.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingIncome() throws Exception {
        int databaseSizeBeforeUpdate = incomeRepository.findAll().size();

        // Create the Income

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncomeMockMvc.perform(put("/api/incomes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(income)))
            .andExpect(status().isBadRequest());

        // Validate the Income in the database
        List<Income> incomeList = incomeRepository.findAll();
        assertThat(incomeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIncome() throws Exception {
        // Initialize the database
        incomeService.save(income);

        int databaseSizeBeforeDelete = incomeRepository.findAll().size();

        // Delete the income
        restIncomeMockMvc.perform(delete("/api/incomes/{id}", income.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Income> incomeList = incomeRepository.findAll();
        assertThat(incomeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
