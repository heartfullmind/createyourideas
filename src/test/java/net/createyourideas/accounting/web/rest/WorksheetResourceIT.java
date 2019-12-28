package net.createyourideas.accounting.web.rest;

import net.createyourideas.accounting.HomeApp;
import net.createyourideas.accounting.domain.Worksheet;
import net.createyourideas.accounting.repository.WorksheetRepository;
import net.createyourideas.accounting.service.WorksheetService;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static net.createyourideas.accounting.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link WorksheetResource} REST controller.
 */
@SpringBootTest(classes = HomeApp.class)
public class WorksheetResourceIT {

    private static final String DEFAULT_JOBTITLE = "AAAAAAAAAA";
    private static final String UPDATED_JOBTITLE = "BBBBBBBBBB";

    private static final String DEFAULT_JOBDESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_JOBDESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_COST_HOUR = 1F;
    private static final Float UPDATED_COST_HOUR = 2F;

    private static final Duration DEFAULT_HOURS = Duration.ofHours(6);
    private static final Duration UPDATED_HOURS = Duration.ofHours(12);

    private static final Float DEFAULT_TOTAL = 1F;
    private static final Float UPDATED_TOTAL = 2F;

    @Autowired
    private WorksheetRepository worksheetRepository;

    @Autowired
    private WorksheetService worksheetService;

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

    private MockMvc restWorksheetMockMvc;

    private Worksheet worksheet;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WorksheetResource worksheetResource = new WorksheetResource(worksheetService);
        this.restWorksheetMockMvc = MockMvcBuilders.standaloneSetup(worksheetResource)
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
    public static Worksheet createEntity(EntityManager em) {
        Worksheet worksheet = new Worksheet()
            .jobtitle(DEFAULT_JOBTITLE)
            .jobdescription(DEFAULT_JOBDESCRIPTION)
            .date(DEFAULT_DATE)
            .costHour(DEFAULT_COST_HOUR)
            .hours(DEFAULT_HOURS)
            .total(DEFAULT_TOTAL);
        return worksheet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Worksheet createUpdatedEntity(EntityManager em) {
        Worksheet worksheet = new Worksheet()
            .jobtitle(UPDATED_JOBTITLE)
            .jobdescription(UPDATED_JOBDESCRIPTION)
            .date(UPDATED_DATE)
            .costHour(UPDATED_COST_HOUR)
            .hours(UPDATED_HOURS)
            .total(UPDATED_TOTAL);
        return worksheet;
    }

    @BeforeEach
    public void initTest() {
        worksheet = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorksheet() throws Exception {
        int databaseSizeBeforeCreate = worksheetRepository.findAll().size();

        // Create the Worksheet
        restWorksheetMockMvc.perform(post("/api/worksheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(worksheet)))
            .andExpect(status().isCreated());

        // Validate the Worksheet in the database
        List<Worksheet> worksheetList = worksheetRepository.findAll();
        assertThat(worksheetList).hasSize(databaseSizeBeforeCreate + 1);
        Worksheet testWorksheet = worksheetList.get(worksheetList.size() - 1);
        assertThat(testWorksheet.getJobtitle()).isEqualTo(DEFAULT_JOBTITLE);
        assertThat(testWorksheet.getJobdescription()).isEqualTo(DEFAULT_JOBDESCRIPTION);
        assertThat(testWorksheet.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testWorksheet.getCostHour()).isEqualTo(DEFAULT_COST_HOUR);
        assertThat(testWorksheet.getHours()).isEqualTo(DEFAULT_HOURS);
        assertThat(testWorksheet.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void createWorksheetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = worksheetRepository.findAll().size();

        // Create the Worksheet with an existing ID
        worksheet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorksheetMockMvc.perform(post("/api/worksheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(worksheet)))
            .andExpect(status().isBadRequest());

        // Validate the Worksheet in the database
        List<Worksheet> worksheetList = worksheetRepository.findAll();
        assertThat(worksheetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWorksheets() throws Exception {
        // Initialize the database
        worksheetRepository.saveAndFlush(worksheet);

        // Get all the worksheetList
        restWorksheetMockMvc.perform(get("/api/worksheets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(worksheet.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobtitle").value(hasItem(DEFAULT_JOBTITLE)))
            .andExpect(jsonPath("$.[*].jobdescription").value(hasItem(DEFAULT_JOBDESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].costHour").value(hasItem(DEFAULT_COST_HOUR.doubleValue())))
            .andExpect(jsonPath("$.[*].hours").value(hasItem(DEFAULT_HOURS.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getWorksheet() throws Exception {
        // Initialize the database
        worksheetRepository.saveAndFlush(worksheet);

        // Get the worksheet
        restWorksheetMockMvc.perform(get("/api/worksheets/{id}", worksheet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(worksheet.getId().intValue()))
            .andExpect(jsonPath("$.jobtitle").value(DEFAULT_JOBTITLE))
            .andExpect(jsonPath("$.jobdescription").value(DEFAULT_JOBDESCRIPTION.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.costHour").value(DEFAULT_COST_HOUR.doubleValue()))
            .andExpect(jsonPath("$.hours").value(DEFAULT_HOURS.toString()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingWorksheet() throws Exception {
        // Get the worksheet
        restWorksheetMockMvc.perform(get("/api/worksheets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorksheet() throws Exception {
        // Initialize the database
        worksheetService.save(worksheet);

        int databaseSizeBeforeUpdate = worksheetRepository.findAll().size();

        // Update the worksheet
        Worksheet updatedWorksheet = worksheetRepository.findById(worksheet.getId()).get();
        // Disconnect from session so that the updates on updatedWorksheet are not directly saved in db
        em.detach(updatedWorksheet);
        updatedWorksheet
            .jobtitle(UPDATED_JOBTITLE)
            .jobdescription(UPDATED_JOBDESCRIPTION)
            .date(UPDATED_DATE)
            .costHour(UPDATED_COST_HOUR)
            .hours(UPDATED_HOURS)
            .total(UPDATED_TOTAL);

        restWorksheetMockMvc.perform(put("/api/worksheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorksheet)))
            .andExpect(status().isOk());

        // Validate the Worksheet in the database
        List<Worksheet> worksheetList = worksheetRepository.findAll();
        assertThat(worksheetList).hasSize(databaseSizeBeforeUpdate);
        Worksheet testWorksheet = worksheetList.get(worksheetList.size() - 1);
        assertThat(testWorksheet.getJobtitle()).isEqualTo(UPDATED_JOBTITLE);
        assertThat(testWorksheet.getJobdescription()).isEqualTo(UPDATED_JOBDESCRIPTION);
        assertThat(testWorksheet.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testWorksheet.getCostHour()).isEqualTo(UPDATED_COST_HOUR);
        assertThat(testWorksheet.getHours()).isEqualTo(UPDATED_HOURS);
        assertThat(testWorksheet.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingWorksheet() throws Exception {
        int databaseSizeBeforeUpdate = worksheetRepository.findAll().size();

        // Create the Worksheet

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorksheetMockMvc.perform(put("/api/worksheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(worksheet)))
            .andExpect(status().isBadRequest());

        // Validate the Worksheet in the database
        List<Worksheet> worksheetList = worksheetRepository.findAll();
        assertThat(worksheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorksheet() throws Exception {
        // Initialize the database
        worksheetService.save(worksheet);

        int databaseSizeBeforeDelete = worksheetRepository.findAll().size();

        // Delete the worksheet
        restWorksheetMockMvc.perform(delete("/api/worksheets/{id}", worksheet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Worksheet> worksheetList = worksheetRepository.findAll();
        assertThat(worksheetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
