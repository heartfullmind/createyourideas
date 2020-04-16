package net.createyourideas.accounting.web.rest;

import net.createyourideas.accounting.HomeApp;
import net.createyourideas.accounting.domain.ProfitBalance;
import net.createyourideas.accounting.repository.ProfitBalanceRepository;
import net.createyourideas.accounting.service.ProfitBalanceService;
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
 * Integration tests for the {@link ProfitBalanceResource} REST controller.
 */
@SpringBootTest(classes = HomeApp.class)
public class ProfitBalanceResourceIT {

    private static final Float DEFAULT_PROFIT = 1F;
    private static final Float UPDATED_PROFIT = 2F;

    private static final Float DEFAULT_PROFIT_TO_SPEND = 1F;
    private static final Float UPDATED_PROFIT_TO_SPEND = 2F;

    private static final Float DEFAULT_NET_PROFIT = 1F;
    private static final Float UPDATED_NET_PROFIT = 2F;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ProfitBalanceRepository profitBalanceRepository;

    @Autowired
    private ProfitBalanceService profitBalanceService;

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

    private MockMvc restProfitBalanceMockMvc;

    private ProfitBalance profitBalance;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfitBalanceResource profitBalanceResource = new ProfitBalanceResource(profitBalanceService);
        this.restProfitBalanceMockMvc = MockMvcBuilders.standaloneSetup(profitBalanceResource)
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
    public static ProfitBalance createEntity(EntityManager em) {
        ProfitBalance profitBalance = new ProfitBalance()
            .profit(DEFAULT_PROFIT)
            .profitToSpend(DEFAULT_PROFIT_TO_SPEND)
            .netProfit(DEFAULT_NET_PROFIT)
            .date(DEFAULT_DATE);
        return profitBalance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProfitBalance createUpdatedEntity(EntityManager em) {
        ProfitBalance profitBalance = new ProfitBalance()
            .profit(UPDATED_PROFIT)
            .profitToSpend(UPDATED_PROFIT_TO_SPEND)
            .netProfit(UPDATED_NET_PROFIT)
            .date(UPDATED_DATE);
        return profitBalance;
    }

    @BeforeEach
    public void initTest() {
        profitBalance = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfitBalance() throws Exception {
        int databaseSizeBeforeCreate = profitBalanceRepository.findAll().size();

        // Create the ProfitBalance
        restProfitBalanceMockMvc.perform(post("/api/profit-balances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profitBalance)))
            .andExpect(status().isCreated());

        // Validate the ProfitBalance in the database
        List<ProfitBalance> profitBalanceList = profitBalanceRepository.findAll();
        assertThat(profitBalanceList).hasSize(databaseSizeBeforeCreate + 1);
        ProfitBalance testProfitBalance = profitBalanceList.get(profitBalanceList.size() - 1);
        assertThat(testProfitBalance.getProfit()).isEqualTo(DEFAULT_PROFIT);
        assertThat(testProfitBalance.getProfitToSpend()).isEqualTo(DEFAULT_PROFIT_TO_SPEND);
        assertThat(testProfitBalance.getNetProfit()).isEqualTo(DEFAULT_NET_PROFIT);
        assertThat(testProfitBalance.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createProfitBalanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profitBalanceRepository.findAll().size();

        // Create the ProfitBalance with an existing ID
        profitBalance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfitBalanceMockMvc.perform(post("/api/profit-balances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profitBalance)))
            .andExpect(status().isBadRequest());

        // Validate the ProfitBalance in the database
        List<ProfitBalance> profitBalanceList = profitBalanceRepository.findAll();
        assertThat(profitBalanceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProfitBalances() throws Exception {
        // Initialize the database
        profitBalanceRepository.saveAndFlush(profitBalance);

        // Get all the profitBalanceList
        restProfitBalanceMockMvc.perform(get("/api/profit-balances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profitBalance.getId().intValue())))
            .andExpect(jsonPath("$.[*].profit").value(hasItem(DEFAULT_PROFIT.doubleValue())))
            .andExpect(jsonPath("$.[*].profitToSpend").value(hasItem(DEFAULT_PROFIT_TO_SPEND.doubleValue())))
            .andExpect(jsonPath("$.[*].netProfit").value(hasItem(DEFAULT_NET_PROFIT.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getProfitBalance() throws Exception {
        // Initialize the database
        profitBalanceRepository.saveAndFlush(profitBalance);

        // Get the profitBalance
        restProfitBalanceMockMvc.perform(get("/api/profit-balances/{id}", profitBalance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profitBalance.getId().intValue()))
            .andExpect(jsonPath("$.profit").value(DEFAULT_PROFIT.doubleValue()))
            .andExpect(jsonPath("$.profitToSpend").value(DEFAULT_PROFIT_TO_SPEND.doubleValue()))
            .andExpect(jsonPath("$.netProfit").value(DEFAULT_NET_PROFIT.doubleValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProfitBalance() throws Exception {
        // Get the profitBalance
        restProfitBalanceMockMvc.perform(get("/api/profit-balances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfitBalance() throws Exception {
        // Initialize the database
        profitBalanceService.save(profitBalance);

        int databaseSizeBeforeUpdate = profitBalanceRepository.findAll().size();

        // Update the profitBalance
        ProfitBalance updatedProfitBalance = profitBalanceRepository.findById(profitBalance.getId()).get();
        // Disconnect from session so that the updates on updatedProfitBalance are not directly saved in db
        em.detach(updatedProfitBalance);
        updatedProfitBalance
            .profit(UPDATED_PROFIT)
            .profitToSpend(UPDATED_PROFIT_TO_SPEND)
            .netProfit(UPDATED_NET_PROFIT)
            .date(UPDATED_DATE);

        restProfitBalanceMockMvc.perform(put("/api/profit-balances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProfitBalance)))
            .andExpect(status().isOk());

        // Validate the ProfitBalance in the database
        List<ProfitBalance> profitBalanceList = profitBalanceRepository.findAll();
        assertThat(profitBalanceList).hasSize(databaseSizeBeforeUpdate);
        ProfitBalance testProfitBalance = profitBalanceList.get(profitBalanceList.size() - 1);
        assertThat(testProfitBalance.getProfit()).isEqualTo(UPDATED_PROFIT);
        assertThat(testProfitBalance.getProfitToSpend()).isEqualTo(UPDATED_PROFIT_TO_SPEND);
        assertThat(testProfitBalance.getNetProfit()).isEqualTo(UPDATED_NET_PROFIT);
        assertThat(testProfitBalance.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingProfitBalance() throws Exception {
        int databaseSizeBeforeUpdate = profitBalanceRepository.findAll().size();

        // Create the ProfitBalance

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfitBalanceMockMvc.perform(put("/api/profit-balances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profitBalance)))
            .andExpect(status().isBadRequest());

        // Validate the ProfitBalance in the database
        List<ProfitBalance> profitBalanceList = profitBalanceRepository.findAll();
        assertThat(profitBalanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProfitBalance() throws Exception {
        // Initialize the database
        profitBalanceService.save(profitBalance);

        int databaseSizeBeforeDelete = profitBalanceRepository.findAll().size();

        // Delete the profitBalance
        restProfitBalanceMockMvc.perform(delete("/api/profit-balances/{id}", profitBalance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProfitBalance> profitBalanceList = profitBalanceRepository.findAll();
        assertThat(profitBalanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
