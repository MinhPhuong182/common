package manager.com.common.web.rest;

import manager.com.common.CommonApp;

import manager.com.common.domain.NhomDanhMuc;
import manager.com.common.repository.NhomDanhMucRepository;
import manager.com.common.service.NhomDanhMucService;
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
 * Test class for the NhomDanhMucResource REST controller.
 *
 * @see NhomDanhMucResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommonApp.class)
public class NhomDanhMucResourceIntTest {

    private static final String DEFAULT_NHOM_DANH_MUC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_NHOM_DANH_MUC_CODE = "BBBBBBBBBB";

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
    private NhomDanhMucRepository nhomDanhMucRepository;

    @Autowired
    private NhomDanhMucService nhomDanhMucService;

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

    private MockMvc restNhomDanhMucMockMvc;

    private NhomDanhMuc nhomDanhMuc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NhomDanhMucResource nhomDanhMucResource = new NhomDanhMucResource(nhomDanhMucService);
        this.restNhomDanhMucMockMvc = MockMvcBuilders.standaloneSetup(nhomDanhMucResource)
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
    public static NhomDanhMuc createEntity(EntityManager em) {
        NhomDanhMuc nhomDanhMuc = new NhomDanhMuc()
            .nhomDanhMucCode(DEFAULT_NHOM_DANH_MUC_CODE)
            .name(DEFAULT_NAME)
            .userName(DEFAULT_USER_NAME)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .status(DEFAULT_STATUS)
            .program(DEFAULT_PROGRAM);
        return nhomDanhMuc;
    }

    @Before
    public void initTest() {
        nhomDanhMuc = createEntity(em);
    }

    @Test
    @Transactional
    public void createNhomDanhMuc() throws Exception {
        int databaseSizeBeforeCreate = nhomDanhMucRepository.findAll().size();

        // Create the NhomDanhMuc
        restNhomDanhMucMockMvc.perform(post("/api/nhom-danh-mucs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhomDanhMuc)))
            .andExpect(status().isCreated());

        // Validate the NhomDanhMuc in the database
        List<NhomDanhMuc> nhomDanhMucList = nhomDanhMucRepository.findAll();
        assertThat(nhomDanhMucList).hasSize(databaseSizeBeforeCreate + 1);
        NhomDanhMuc testNhomDanhMuc = nhomDanhMucList.get(nhomDanhMucList.size() - 1);
        assertThat(testNhomDanhMuc.getNhomDanhMucCode()).isEqualTo(DEFAULT_NHOM_DANH_MUC_CODE);
        assertThat(testNhomDanhMuc.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNhomDanhMuc.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testNhomDanhMuc.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testNhomDanhMuc.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testNhomDanhMuc.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testNhomDanhMuc.getProgram()).isEqualTo(DEFAULT_PROGRAM);
    }

    @Test
    @Transactional
    public void createNhomDanhMucWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nhomDanhMucRepository.findAll().size();

        // Create the NhomDanhMuc with an existing ID
        nhomDanhMuc.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNhomDanhMucMockMvc.perform(post("/api/nhom-danh-mucs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhomDanhMuc)))
            .andExpect(status().isBadRequest());

        // Validate the NhomDanhMuc in the database
        List<NhomDanhMuc> nhomDanhMucList = nhomDanhMucRepository.findAll();
        assertThat(nhomDanhMucList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNhomDanhMucCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhomDanhMucRepository.findAll().size();
        // set the field null
        nhomDanhMuc.setNhomDanhMucCode(null);

        // Create the NhomDanhMuc, which fails.

        restNhomDanhMucMockMvc.perform(post("/api/nhom-danh-mucs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhomDanhMuc)))
            .andExpect(status().isBadRequest());

        List<NhomDanhMuc> nhomDanhMucList = nhomDanhMucRepository.findAll();
        assertThat(nhomDanhMucList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNhomDanhMucs() throws Exception {
        // Initialize the database
        nhomDanhMucRepository.saveAndFlush(nhomDanhMuc);

        // Get all the nhomDanhMucList
        restNhomDanhMucMockMvc.perform(get("/api/nhom-danh-mucs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhomDanhMuc.getId().intValue())))
            .andExpect(jsonPath("$.[*].nhomDanhMucCode").value(hasItem(DEFAULT_NHOM_DANH_MUC_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].program").value(hasItem(DEFAULT_PROGRAM.toString())));
    }
    
    @Test
    @Transactional
    public void getNhomDanhMuc() throws Exception {
        // Initialize the database
        nhomDanhMucRepository.saveAndFlush(nhomDanhMuc);

        // Get the nhomDanhMuc
        restNhomDanhMucMockMvc.perform(get("/api/nhom-danh-mucs/{id}", nhomDanhMuc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nhomDanhMuc.getId().intValue()))
            .andExpect(jsonPath("$.nhomDanhMucCode").value(DEFAULT_NHOM_DANH_MUC_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.program").value(DEFAULT_PROGRAM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNhomDanhMuc() throws Exception {
        // Get the nhomDanhMuc
        restNhomDanhMucMockMvc.perform(get("/api/nhom-danh-mucs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNhomDanhMuc() throws Exception {
        // Initialize the database
        nhomDanhMucService.save(nhomDanhMuc);

        int databaseSizeBeforeUpdate = nhomDanhMucRepository.findAll().size();

        // Update the nhomDanhMuc
        NhomDanhMuc updatedNhomDanhMuc = nhomDanhMucRepository.findById(nhomDanhMuc.getId()).get();
        // Disconnect from session so that the updates on updatedNhomDanhMuc are not directly saved in db
        em.detach(updatedNhomDanhMuc);
        updatedNhomDanhMuc
            .nhomDanhMucCode(UPDATED_NHOM_DANH_MUC_CODE)
            .name(UPDATED_NAME)
            .userName(UPDATED_USER_NAME)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .status(UPDATED_STATUS)
            .program(UPDATED_PROGRAM);

        restNhomDanhMucMockMvc.perform(put("/api/nhom-danh-mucs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNhomDanhMuc)))
            .andExpect(status().isOk());

        // Validate the NhomDanhMuc in the database
        List<NhomDanhMuc> nhomDanhMucList = nhomDanhMucRepository.findAll();
        assertThat(nhomDanhMucList).hasSize(databaseSizeBeforeUpdate);
        NhomDanhMuc testNhomDanhMuc = nhomDanhMucList.get(nhomDanhMucList.size() - 1);
        assertThat(testNhomDanhMuc.getNhomDanhMucCode()).isEqualTo(UPDATED_NHOM_DANH_MUC_CODE);
        assertThat(testNhomDanhMuc.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNhomDanhMuc.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testNhomDanhMuc.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testNhomDanhMuc.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testNhomDanhMuc.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testNhomDanhMuc.getProgram()).isEqualTo(UPDATED_PROGRAM);
    }

    @Test
    @Transactional
    public void updateNonExistingNhomDanhMuc() throws Exception {
        int databaseSizeBeforeUpdate = nhomDanhMucRepository.findAll().size();

        // Create the NhomDanhMuc

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhomDanhMucMockMvc.perform(put("/api/nhom-danh-mucs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhomDanhMuc)))
            .andExpect(status().isBadRequest());

        // Validate the NhomDanhMuc in the database
        List<NhomDanhMuc> nhomDanhMucList = nhomDanhMucRepository.findAll();
        assertThat(nhomDanhMucList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNhomDanhMuc() throws Exception {
        // Initialize the database
        nhomDanhMucService.save(nhomDanhMuc);

        int databaseSizeBeforeDelete = nhomDanhMucRepository.findAll().size();

        // Delete the nhomDanhMuc
        restNhomDanhMucMockMvc.perform(delete("/api/nhom-danh-mucs/{id}", nhomDanhMuc.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NhomDanhMuc> nhomDanhMucList = nhomDanhMucRepository.findAll();
        assertThat(nhomDanhMucList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NhomDanhMuc.class);
        NhomDanhMuc nhomDanhMuc1 = new NhomDanhMuc();
        nhomDanhMuc1.setId(1L);
        NhomDanhMuc nhomDanhMuc2 = new NhomDanhMuc();
        nhomDanhMuc2.setId(nhomDanhMuc1.getId());
        assertThat(nhomDanhMuc1).isEqualTo(nhomDanhMuc2);
        nhomDanhMuc2.setId(2L);
        assertThat(nhomDanhMuc1).isNotEqualTo(nhomDanhMuc2);
        nhomDanhMuc1.setId(null);
        assertThat(nhomDanhMuc1).isNotEqualTo(nhomDanhMuc2);
    }
}
