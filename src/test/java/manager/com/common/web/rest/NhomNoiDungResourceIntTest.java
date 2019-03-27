package manager.com.common.web.rest;

import manager.com.common.CommonApp;

import manager.com.common.domain.NhomNoiDung;
import manager.com.common.repository.NhomNoiDungRepository;
import manager.com.common.service.NhomNoiDungService;
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
 * Test class for the NhomNoiDungResource REST controller.
 *
 * @see NhomNoiDungResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommonApp.class)
public class NhomNoiDungResourceIntTest {

    private static final String DEFAULT_NHOM_NOI_DUNG_CODE = "AAAAAAAAAA";
    private static final String UPDATED_NHOM_NOI_DUNG_CODE = "BBBBBBBBBB";

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
    private NhomNoiDungRepository nhomNoiDungRepository;

    @Autowired
    private NhomNoiDungService nhomNoiDungService;

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

    private MockMvc restNhomNoiDungMockMvc;

    private NhomNoiDung nhomNoiDung;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NhomNoiDungResource nhomNoiDungResource = new NhomNoiDungResource(nhomNoiDungService);
        this.restNhomNoiDungMockMvc = MockMvcBuilders.standaloneSetup(nhomNoiDungResource)
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
    public static NhomNoiDung createEntity(EntityManager em) {
        NhomNoiDung nhomNoiDung = new NhomNoiDung()
            .nhomNoiDungCode(DEFAULT_NHOM_NOI_DUNG_CODE)
            .name(DEFAULT_NAME)
            .userName(DEFAULT_USER_NAME)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .status(DEFAULT_STATUS)
            .program(DEFAULT_PROGRAM);
        return nhomNoiDung;
    }

    @Before
    public void initTest() {
        nhomNoiDung = createEntity(em);
    }

    @Test
    @Transactional
    public void createNhomNoiDung() throws Exception {
        int databaseSizeBeforeCreate = nhomNoiDungRepository.findAll().size();

        // Create the NhomNoiDung
        restNhomNoiDungMockMvc.perform(post("/api/nhom-noi-dungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhomNoiDung)))
            .andExpect(status().isCreated());

        // Validate the NhomNoiDung in the database
        List<NhomNoiDung> nhomNoiDungList = nhomNoiDungRepository.findAll();
        assertThat(nhomNoiDungList).hasSize(databaseSizeBeforeCreate + 1);
        NhomNoiDung testNhomNoiDung = nhomNoiDungList.get(nhomNoiDungList.size() - 1);
        assertThat(testNhomNoiDung.getNhomNoiDungCode()).isEqualTo(DEFAULT_NHOM_NOI_DUNG_CODE);
        assertThat(testNhomNoiDung.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNhomNoiDung.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testNhomNoiDung.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testNhomNoiDung.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testNhomNoiDung.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testNhomNoiDung.getProgram()).isEqualTo(DEFAULT_PROGRAM);
    }

    @Test
    @Transactional
    public void createNhomNoiDungWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nhomNoiDungRepository.findAll().size();

        // Create the NhomNoiDung with an existing ID
        nhomNoiDung.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNhomNoiDungMockMvc.perform(post("/api/nhom-noi-dungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhomNoiDung)))
            .andExpect(status().isBadRequest());

        // Validate the NhomNoiDung in the database
        List<NhomNoiDung> nhomNoiDungList = nhomNoiDungRepository.findAll();
        assertThat(nhomNoiDungList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNhomNoiDungCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhomNoiDungRepository.findAll().size();
        // set the field null
        nhomNoiDung.setNhomNoiDungCode(null);

        // Create the NhomNoiDung, which fails.

        restNhomNoiDungMockMvc.perform(post("/api/nhom-noi-dungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhomNoiDung)))
            .andExpect(status().isBadRequest());

        List<NhomNoiDung> nhomNoiDungList = nhomNoiDungRepository.findAll();
        assertThat(nhomNoiDungList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNhomNoiDungs() throws Exception {
        // Initialize the database
        nhomNoiDungRepository.saveAndFlush(nhomNoiDung);

        // Get all the nhomNoiDungList
        restNhomNoiDungMockMvc.perform(get("/api/nhom-noi-dungs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhomNoiDung.getId().intValue())))
            .andExpect(jsonPath("$.[*].nhomNoiDungCode").value(hasItem(DEFAULT_NHOM_NOI_DUNG_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].program").value(hasItem(DEFAULT_PROGRAM.toString())));
    }
    
    @Test
    @Transactional
    public void getNhomNoiDung() throws Exception {
        // Initialize the database
        nhomNoiDungRepository.saveAndFlush(nhomNoiDung);

        // Get the nhomNoiDung
        restNhomNoiDungMockMvc.perform(get("/api/nhom-noi-dungs/{id}", nhomNoiDung.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nhomNoiDung.getId().intValue()))
            .andExpect(jsonPath("$.nhomNoiDungCode").value(DEFAULT_NHOM_NOI_DUNG_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.program").value(DEFAULT_PROGRAM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNhomNoiDung() throws Exception {
        // Get the nhomNoiDung
        restNhomNoiDungMockMvc.perform(get("/api/nhom-noi-dungs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNhomNoiDung() throws Exception {
        // Initialize the database
        nhomNoiDungService.save(nhomNoiDung);

        int databaseSizeBeforeUpdate = nhomNoiDungRepository.findAll().size();

        // Update the nhomNoiDung
        NhomNoiDung updatedNhomNoiDung = nhomNoiDungRepository.findById(nhomNoiDung.getId()).get();
        // Disconnect from session so that the updates on updatedNhomNoiDung are not directly saved in db
        em.detach(updatedNhomNoiDung);
        updatedNhomNoiDung
            .nhomNoiDungCode(UPDATED_NHOM_NOI_DUNG_CODE)
            .name(UPDATED_NAME)
            .userName(UPDATED_USER_NAME)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .status(UPDATED_STATUS)
            .program(UPDATED_PROGRAM);

        restNhomNoiDungMockMvc.perform(put("/api/nhom-noi-dungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNhomNoiDung)))
            .andExpect(status().isOk());

        // Validate the NhomNoiDung in the database
        List<NhomNoiDung> nhomNoiDungList = nhomNoiDungRepository.findAll();
        assertThat(nhomNoiDungList).hasSize(databaseSizeBeforeUpdate);
        NhomNoiDung testNhomNoiDung = nhomNoiDungList.get(nhomNoiDungList.size() - 1);
        assertThat(testNhomNoiDung.getNhomNoiDungCode()).isEqualTo(UPDATED_NHOM_NOI_DUNG_CODE);
        assertThat(testNhomNoiDung.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNhomNoiDung.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testNhomNoiDung.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testNhomNoiDung.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testNhomNoiDung.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testNhomNoiDung.getProgram()).isEqualTo(UPDATED_PROGRAM);
    }

    @Test
    @Transactional
    public void updateNonExistingNhomNoiDung() throws Exception {
        int databaseSizeBeforeUpdate = nhomNoiDungRepository.findAll().size();

        // Create the NhomNoiDung

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhomNoiDungMockMvc.perform(put("/api/nhom-noi-dungs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhomNoiDung)))
            .andExpect(status().isBadRequest());

        // Validate the NhomNoiDung in the database
        List<NhomNoiDung> nhomNoiDungList = nhomNoiDungRepository.findAll();
        assertThat(nhomNoiDungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNhomNoiDung() throws Exception {
        // Initialize the database
        nhomNoiDungService.save(nhomNoiDung);

        int databaseSizeBeforeDelete = nhomNoiDungRepository.findAll().size();

        // Delete the nhomNoiDung
        restNhomNoiDungMockMvc.perform(delete("/api/nhom-noi-dungs/{id}", nhomNoiDung.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NhomNoiDung> nhomNoiDungList = nhomNoiDungRepository.findAll();
        assertThat(nhomNoiDungList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NhomNoiDung.class);
        NhomNoiDung nhomNoiDung1 = new NhomNoiDung();
        nhomNoiDung1.setId(1L);
        NhomNoiDung nhomNoiDung2 = new NhomNoiDung();
        nhomNoiDung2.setId(nhomNoiDung1.getId());
        assertThat(nhomNoiDung1).isEqualTo(nhomNoiDung2);
        nhomNoiDung2.setId(2L);
        assertThat(nhomNoiDung1).isNotEqualTo(nhomNoiDung2);
        nhomNoiDung1.setId(null);
        assertThat(nhomNoiDung1).isNotEqualTo(nhomNoiDung2);
    }
}
