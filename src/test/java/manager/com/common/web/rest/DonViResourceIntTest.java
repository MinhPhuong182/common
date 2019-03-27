package manager.com.common.web.rest;

import manager.com.common.CommonApp;

import manager.com.common.domain.DonVi;
import manager.com.common.repository.DonViRepository;
import manager.com.common.service.DonViService;
import manager.com.common.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static manager.com.common.web.rest.TestUtil.sameInstant;
import static manager.com.common.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DonViResource REST controller.
 *
 * @see DonViResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommonApp.class)
public class DonViResourceIntTest {

    private static final String DEFAULT_DON_VI_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DON_VI_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CREATE_TIME = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_TIME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_PROGRAM = "AAAAAAAAAA";
    private static final String UPDATED_PROGRAM = "BBBBBBBBBB";

    @Autowired
    private DonViRepository donViRepository;

    @Autowired
    private DonViService donViService;

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

    private MockMvc restDonViMockMvc;

    private DonVi donVi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DonViResource donViResource = new DonViResource(donViService);
        this.restDonViMockMvc = MockMvcBuilders.standaloneSetup(donViResource)
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
    public static DonVi createEntity(EntityManager em) {
        DonVi donVi = new DonVi()
            .donViCode(DEFAULT_DON_VI_CODE)
            .name(DEFAULT_NAME)
            .userName(DEFAULT_USER_NAME)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .status(DEFAULT_STATUS)
            .program(DEFAULT_PROGRAM);
        return donVi;
    }

    @Before
    public void initTest() {
        donVi = createEntity(em);
    }

    @Test
    @Transactional
    public void createDonVi() throws Exception {
        int databaseSizeBeforeCreate = donViRepository.findAll().size();

        // Create the DonVi
        restDonViMockMvc.perform(post("/api/don-vis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donVi)))
            .andExpect(status().isCreated());

        // Validate the DonVi in the database
        List<DonVi> donViList = donViRepository.findAll();
        assertThat(donViList).hasSize(databaseSizeBeforeCreate + 1);
        DonVi testDonVi = donViList.get(donViList.size() - 1);
        assertThat(testDonVi.getDonViCode()).isEqualTo(DEFAULT_DON_VI_CODE);
        assertThat(testDonVi.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDonVi.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testDonVi.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testDonVi.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testDonVi.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDonVi.getProgram()).isEqualTo(DEFAULT_PROGRAM);
    }

    @Test
    @Transactional
    public void createDonViWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = donViRepository.findAll().size();

        // Create the DonVi with an existing ID
        donVi.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDonViMockMvc.perform(post("/api/don-vis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donVi)))
            .andExpect(status().isBadRequest());

        // Validate the DonVi in the database
        List<DonVi> donViList = donViRepository.findAll();
        assertThat(donViList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDonViCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = donViRepository.findAll().size();
        // set the field null
        donVi.setDonViCode(null);

        // Create the DonVi, which fails.

        restDonViMockMvc.perform(post("/api/don-vis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donVi)))
            .andExpect(status().isBadRequest());

        List<DonVi> donViList = donViRepository.findAll();
        assertThat(donViList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDonVis() throws Exception {
        // Initialize the database
        donViRepository.saveAndFlush(donVi);

        // Get all the donViList
        restDonViMockMvc.perform(get("/api/don-vis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(donVi.getId().intValue())))
            .andExpect(jsonPath("$.[*].donViCode").value(hasItem(DEFAULT_DON_VI_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].program").value(hasItem(DEFAULT_PROGRAM.toString())));
    }
    
    @Test
    @Transactional
    public void getDonVi() throws Exception {
        // Initialize the database
        donViRepository.saveAndFlush(donVi);

        // Get the donVi
        restDonViMockMvc.perform(get("/api/don-vis/{id}", donVi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(donVi.getId().intValue()))
            .andExpect(jsonPath("$.donViCode").value(DEFAULT_DON_VI_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.program").value(DEFAULT_PROGRAM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDonVi() throws Exception {
        // Get the donVi
        restDonViMockMvc.perform(get("/api/don-vis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDonVi() throws Exception {
        // Initialize the database
        donViService.save(donVi);

        int databaseSizeBeforeUpdate = donViRepository.findAll().size();

        // Update the donVi
        DonVi updatedDonVi = donViRepository.findById(donVi.getId()).get();
        // Disconnect from session so that the updates on updatedDonVi are not directly saved in db
        em.detach(updatedDonVi);
        updatedDonVi
            .donViCode(UPDATED_DON_VI_CODE)
            .name(UPDATED_NAME)
            .userName(UPDATED_USER_NAME)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .status(UPDATED_STATUS)
            .program(UPDATED_PROGRAM);

        restDonViMockMvc.perform(put("/api/don-vis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDonVi)))
            .andExpect(status().isOk());

        // Validate the DonVi in the database
        List<DonVi> donViList = donViRepository.findAll();
        assertThat(donViList).hasSize(databaseSizeBeforeUpdate);
        DonVi testDonVi = donViList.get(donViList.size() - 1);
        assertThat(testDonVi.getDonViCode()).isEqualTo(UPDATED_DON_VI_CODE);
        assertThat(testDonVi.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDonVi.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testDonVi.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testDonVi.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testDonVi.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDonVi.getProgram()).isEqualTo(UPDATED_PROGRAM);
    }

    @Test
    @Transactional
    public void updateNonExistingDonVi() throws Exception {
        int databaseSizeBeforeUpdate = donViRepository.findAll().size();

        // Create the DonVi

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonViMockMvc.perform(put("/api/don-vis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donVi)))
            .andExpect(status().isBadRequest());

        // Validate the DonVi in the database
        List<DonVi> donViList = donViRepository.findAll();
        assertThat(donViList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDonVi() throws Exception {
        // Initialize the database
        donViService.save(donVi);

        int databaseSizeBeforeDelete = donViRepository.findAll().size();

        // Delete the donVi
        restDonViMockMvc.perform(delete("/api/don-vis/{id}", donVi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DonVi> donViList = donViRepository.findAll();
        assertThat(donViList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DonVi.class);
        DonVi donVi1 = new DonVi();
        donVi1.setId(1L);
        DonVi donVi2 = new DonVi();
        donVi2.setId(donVi1.getId());
        assertThat(donVi1).isEqualTo(donVi2);
        donVi2.setId(2L);
        assertThat(donVi1).isNotEqualTo(donVi2);
        donVi1.setId(null);
        assertThat(donVi1).isNotEqualTo(donVi2);
    }
}
