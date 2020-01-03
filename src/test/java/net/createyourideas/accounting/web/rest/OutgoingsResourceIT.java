package net.createyourideas.accounting.web.rest;

import net.createyourideas.accounting.HomeApp;
import net.createyourideas.accounting.domain.Outgoings;
import net.createyourideas.accounting.repository.OutgoingsRepository;
import net.createyourideas.accounting.service.OutgoingsService;
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
 * Integration tests for the {@link OutgoingsResource} REST controller.
 */
@SpringBootTest(classes = HomeApp.class)
public class OutgoingsResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_VALUE = 1F;
    private static final Float UPDATED_VALUE = 2F;

    @Autowired
    private OutgoingsRepository outgoingsRepository;

    @Autowired
    private OutgoingsService outgoingsService;

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

    private MockMvc restOutgoingsMockMvc;

    private Outgoings outgoings;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OutgoingsResource outgoingsResource = new OutgoingsResource(outgoingsService);
        this.restOutgoingsMockMvc = MockMvcBuilders.standaloneSetup(outgoingsResource)
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
    public static Outgoings createEntity(EntityManager em) {
        Outgoings outgoings = new Outgoings()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .date(DEFAULT_DATE)
            .value(DEFAULT_VALUE);
        return outgoings;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Outgoings createUpdatedEntity(EntityManager em) {
        Outgoings outgoings = new Outgoings()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .value(UPDATED_VALUE);
        return outgoings;
    }

    @BeforeEach
    public void initTest() {
        outgoings = createEntity(em);
    }

    @Test
    @Transactional
    public void createOutgoings() throws Exception {
        int databaseSizeBeforeCreate = outgoingsRepository.findAll().size();

        // Create the Outgoings
        restOutgoingsMockMvc.perform(post("/api/outgoings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(outgoings)))
            .andExpect(status().isCreated());

        // Validate the Outgoings in the database
        List<Outgoings> outgoingsList = outgoingsRepository.findAll();
        assertThat(outgoingsList).hasSize(databaseSizeBeforeCreate + 1);
        Outgoings testOutgoings = outgoingsList.get(outgoingsList.size() - 1);
        assertThat(testOutgoings.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testOutgoings.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOutgoings.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testOutgoings.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createOutgoingsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = outgoingsRepository.findAll().size();

        // Create the Outgoings with an existing ID
        outgoings.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOutgoingsMockMvc.perform(post("/api/outgoings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(outgoings)))
            .andExpect(status().isBadRequest());

        // Validate the Outgoings in the database
        List<Outgoings> outgoingsList = outgoingsRepository.findAll();
        assertThat(outgoingsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = outgoingsRepository.findAll().size();
        // set the field null
        outgoings.setTitle(null);

        // Create the Outgoings, which fails.

        restOutgoingsMockMvc.perform(post("/api/outgoings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(outgoings)))
            .andExpect(status().isBadRequest());

        List<Outgoings> outgoingsList = outgoingsRepository.findAll();
        assertThat(outgoingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = outgoingsRepository.findAll().size();
        // set the field null
        outgoings.setDescription(null);

        // Create the Outgoings, which fails.

        restOutgoingsMockMvc.perform(post("/api/outgoings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(outgoings)))
            .andExpect(status().isBadRequest());

        List<Outgoings> outgoingsList = outgoingsRepository.findAll();
        assertThat(outgoingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = outgoingsRepository.findAll().size();
        // set the field null
        outgoings.setDate(null);

        // Create the Outgoings, which fails.

        restOutgoingsMockMvc.perform(post("/api/outgoings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(outgoings)))
            .andExpect(status().isBadRequest());

        List<Outgoings> outgoingsList = outgoingsRepository.findAll();
        assertThat(outgoingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = outgoingsRepository.findAll().size();
        // set the field null
        outgoings.setValue(null);

        // Create the Outgoings, which fails.

        restOutgoingsMockMvc.perform(post("/api/outgoings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(outgoings)))
            .andExpect(status().isBadRequest());

        List<Outgoings> outgoingsList = outgoingsRepository.findAll();
        assertThat(outgoingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOutgoings() throws Exception {
        // Initialize the database
        outgoingsRepository.saveAndFlush(outgoings);

        // Get all the outgoingsList
        restOutgoingsMockMvc.perform(get("/api/outgoings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(outgoings.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getOutgoings() throws Exception {
        // Initialize the database
        outgoingsRepository.saveAndFlush(outgoings);

        // Get the outgoings
        restOutgoingsMockMvc.perform(get("/api/outgoings/{id}", outgoings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(outgoings.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOutgoings() throws Exception {
        // Get the outgoings
        restOutgoingsMockMvc.perform(get("/api/outgoings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOutgoings() throws Exception {
        // Initialize the database
        outgoingsService.save(outgoings);

        int databaseSizeBeforeUpdate = outgoingsRepository.findAll().size();

        // Update the outgoings
        Outgoings updatedOutgoings = outgoingsRepository.findById(outgoings.getId()).get();
        // Disconnect from session so that the updates on updatedOutgoings are not directly saved in db
        em.detach(updatedOutgoings);
        updatedOutgoings
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .value(UPDATED_VALUE);

        restOutgoingsMockMvc.perform(put("/api/outgoings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOutgoings)))
            .andExpect(status().isOk());

        // Validate the Outgoings in the database
        List<Outgoings> outgoingsList = outgoingsRepository.findAll();
        assertThat(outgoingsList).hasSize(databaseSizeBeforeUpdate);
        Outgoings testOutgoings = outgoingsList.get(outgoingsList.size() - 1);
        assertThat(testOutgoings.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testOutgoings.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOutgoings.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testOutgoings.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingOutgoings() throws Exception {
        int databaseSizeBeforeUpdate = outgoingsRepository.findAll().size();

        // Create the Outgoings

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOutgoingsMockMvc.perform(put("/api/outgoings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(outgoings)))
            .andExpect(status().isBadRequest());

        // Validate the Outgoings in the database
        List<Outgoings> outgoingsList = outgoingsRepository.findAll();
        assertThat(outgoingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOutgoings() throws Exception {
        // Initialize the database
        outgoingsService.save(outgoings);

        int databaseSizeBeforeDelete = outgoingsRepository.findAll().size();

        // Delete the outgoings
        restOutgoingsMockMvc.perform(delete("/api/outgoings/{id}", outgoings.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Outgoings> outgoingsList = outgoingsRepository.findAll();
        assertThat(outgoingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
