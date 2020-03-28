package net.createyourideas.accounting.web.rest;

import net.createyourideas.accounting.HomeApp;
import net.createyourideas.accounting.domain.Idea;
import net.createyourideas.accounting.repository.IdeaRepository;
import net.createyourideas.accounting.service.IdeaService;
import net.createyourideas.accounting.service.UserService;
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
import java.util.List;

import static net.createyourideas.accounting.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import net.createyourideas.accounting.domain.enumeration.Ideatype;
/**
 * Integration tests for the {@link IdeaResource} REST controller.
 */
@SpringBootTest(classes = HomeApp.class)
public class IdeaResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Ideatype DEFAULT_IDEATYPE = Ideatype.PRODUCTION;
    private static final Ideatype UPDATED_IDEATYPE = Ideatype.SERVICE;

    private static final Float DEFAULT_INTEREST = 1F;
    private static final Float UPDATED_INTEREST = 2F;

    private static final Float DEFAULT_DISTRIBUTION = 1F;
    private static final Float UPDATED_DISTRIBUTION = 2F;

    private static final Float DEFAULT_INVESTMENT = 1F;
    private static final Float UPDATED_INVESTMENT = 2F;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Integer DEFAULT_NODE_ID = 1;
    private static final Integer UPDATED_NODE_ID = 2;

    @Autowired
    private IdeaRepository ideaRepository;

    @Autowired
    private IdeaService ideaService;

    @Autowired
    private UserService userService;

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

    private MockMvc restIdeaMockMvc;

    private Idea idea;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IdeaResource ideaResource = new IdeaResource(ideaService, userService);
        this.restIdeaMockMvc = MockMvcBuilders.standaloneSetup(ideaResource)
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
    public static Idea createEntity(EntityManager em) {
        Idea idea = new Idea()
            .title(DEFAULT_TITLE)
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .ideatype(DEFAULT_IDEATYPE)
            .interest(DEFAULT_INTEREST)
            .distribution(DEFAULT_DISTRIBUTION)
            .investment(DEFAULT_INVESTMENT)
            .active(DEFAULT_ACTIVE)
            .nodeId(DEFAULT_NODE_ID);
        return idea;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Idea createUpdatedEntity(EntityManager em) {
        Idea idea = new Idea()
            .title(UPDATED_TITLE)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .ideatype(UPDATED_IDEATYPE)
            .interest(UPDATED_INTEREST)
            .distribution(UPDATED_DISTRIBUTION)
            .investment(UPDATED_INVESTMENT)
            .active(UPDATED_ACTIVE)
            .nodeId(UPDATED_NODE_ID);
        return idea;
    }

    @BeforeEach
    public void initTest() {
        idea = createEntity(em);
    }

    @Test
    @Transactional
    public void createIdea() throws Exception {
        int databaseSizeBeforeCreate = ideaRepository.findAll().size();

        // Create the Idea
        restIdeaMockMvc.perform(post("/api/ideas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idea)))
            .andExpect(status().isCreated());

        // Validate the Idea in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeCreate + 1);
        Idea testIdea = ideaList.get(ideaList.size() - 1);
        assertThat(testIdea.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testIdea.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testIdea.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testIdea.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testIdea.getIdeatype()).isEqualTo(DEFAULT_IDEATYPE);
        assertThat(testIdea.getInterest()).isEqualTo(DEFAULT_INTEREST);
        assertThat(testIdea.getDistribution()).isEqualTo(DEFAULT_DISTRIBUTION);
        assertThat(testIdea.getInvestment()).isEqualTo(DEFAULT_INVESTMENT);
        assertThat(testIdea.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testIdea.getNodeId()).isEqualTo(DEFAULT_NODE_ID);
    }

    @Test
    @Transactional
    public void createIdeaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ideaRepository.findAll().size();

        // Create the Idea with an existing ID
        idea.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIdeaMockMvc.perform(post("/api/ideas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idea)))
            .andExpect(status().isBadRequest());

        // Validate the Idea in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = ideaRepository.findAll().size();
        // set the field null
        idea.setTitle(null);

        // Create the Idea, which fails.

        restIdeaMockMvc.perform(post("/api/ideas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idea)))
            .andExpect(status().isBadRequest());

        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdeatypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ideaRepository.findAll().size();
        // set the field null
        idea.setIdeatype(null);

        // Create the Idea, which fails.

        restIdeaMockMvc.perform(post("/api/ideas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idea)))
            .andExpect(status().isBadRequest());

        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInterestIsRequired() throws Exception {
        int databaseSizeBeforeTest = ideaRepository.findAll().size();
        // set the field null
        idea.setInterest(null);

        // Create the Idea, which fails.

        restIdeaMockMvc.perform(post("/api/ideas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idea)))
            .andExpect(status().isBadRequest());

        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDistributionIsRequired() throws Exception {
        int databaseSizeBeforeTest = ideaRepository.findAll().size();
        // set the field null
        idea.setDistribution(null);

        // Create the Idea, which fails.

        restIdeaMockMvc.perform(post("/api/ideas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idea)))
            .andExpect(status().isBadRequest());

        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInvestmentIsRequired() throws Exception {
        int databaseSizeBeforeTest = ideaRepository.findAll().size();
        // set the field null
        idea.setInvestment(null);

        // Create the Idea, which fails.

        restIdeaMockMvc.perform(post("/api/ideas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idea)))
            .andExpect(status().isBadRequest());

        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIdeas() throws Exception {
        // Initialize the database
        ideaRepository.saveAndFlush(idea);

        // Get all the ideaList
        restIdeaMockMvc.perform(get("/api/ideas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(idea.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].ideatype").value(hasItem(DEFAULT_IDEATYPE.toString())))
            .andExpect(jsonPath("$.[*].interest").value(hasItem(DEFAULT_INTEREST.doubleValue())))
            .andExpect(jsonPath("$.[*].distribution").value(hasItem(DEFAULT_DISTRIBUTION.doubleValue())))
            .andExpect(jsonPath("$.[*].investment").value(hasItem(DEFAULT_INVESTMENT.doubleValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].nodeId").value(hasItem(DEFAULT_NODE_ID)));
    }

    @Test
    @Transactional
    public void getIdea() throws Exception {
        // Initialize the database
        ideaRepository.saveAndFlush(idea);

        // Get the idea
        restIdeaMockMvc.perform(get("/api/ideas/{id}", idea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(idea.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.ideatype").value(DEFAULT_IDEATYPE.toString()))
            .andExpect(jsonPath("$.interest").value(DEFAULT_INTEREST.doubleValue()))
            .andExpect(jsonPath("$.distribution").value(DEFAULT_DISTRIBUTION.doubleValue()))
            .andExpect(jsonPath("$.investment").value(DEFAULT_INVESTMENT.doubleValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.nodeId").value(DEFAULT_NODE_ID));
    }

    @Test
    @Transactional
    public void getNonExistingIdea() throws Exception {
        // Get the idea
        restIdeaMockMvc.perform(get("/api/ideas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIdea() throws Exception {
        // Initialize the database
        ideaService.save(idea);

        int databaseSizeBeforeUpdate = ideaRepository.findAll().size();

        // Update the idea
        Idea updatedIdea = ideaRepository.findById(idea.getId()).get();
        // Disconnect from session so that the updates on updatedIdea are not directly saved in db
        em.detach(updatedIdea);
        updatedIdea
            .title(UPDATED_TITLE)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .ideatype(UPDATED_IDEATYPE)
            .interest(UPDATED_INTEREST)
            .distribution(UPDATED_DISTRIBUTION)
            .investment(UPDATED_INVESTMENT)
            .active(UPDATED_ACTIVE)
            .nodeId(UPDATED_NODE_ID);

        restIdeaMockMvc.perform(put("/api/ideas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIdea)))
            .andExpect(status().isOk());

        // Validate the Idea in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeUpdate);
        Idea testIdea = ideaList.get(ideaList.size() - 1);
        assertThat(testIdea.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testIdea.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testIdea.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testIdea.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testIdea.getIdeatype()).isEqualTo(UPDATED_IDEATYPE);
        assertThat(testIdea.getInterest()).isEqualTo(UPDATED_INTEREST);
        assertThat(testIdea.getDistribution()).isEqualTo(UPDATED_DISTRIBUTION);
        assertThat(testIdea.getInvestment()).isEqualTo(UPDATED_INVESTMENT);
        assertThat(testIdea.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testIdea.getNodeId()).isEqualTo(UPDATED_NODE_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingIdea() throws Exception {
        int databaseSizeBeforeUpdate = ideaRepository.findAll().size();

        // Create the Idea

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdeaMockMvc.perform(put("/api/ideas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idea)))
            .andExpect(status().isBadRequest());

        // Validate the Idea in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIdea() throws Exception {
        // Initialize the database
        ideaService.save(idea);

        int databaseSizeBeforeDelete = ideaRepository.findAll().size();

        // Delete the idea
        restIdeaMockMvc.perform(delete("/api/ideas/{id}", idea.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
