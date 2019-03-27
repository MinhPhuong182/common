package manager.com.common.web.rest;

import manager.com.common.CommonApp;

import manager.com.common.domain.DanhMuc;
import manager.com.common.repository.DanhMucRepository;
import manager.com.common.service.DanhMucService;
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
 * Test class for the DanhMucResource REST controller.
 *
 * @see DanhMucResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommonApp.class)
public class DanhMucResourceIntTest {

    private static final String DEFAULT_DANH_MUC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DANH_MUC_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NHOM_DANH_MUC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_NHOM_DANH_MUC_CODE = "BBBBBBBBBB";

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
    private DanhMucRepository danhMucRepository;

    @Autowired
    private DanhMucService danhMucService;

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

    private MockMvc restDanhMucMockMvc;

    private DanhMuc danhMuc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DanhMucResource danhMucResource = new DanhMucResource(danhMucService);
        this.restDanhMucMockMvc = MockMvcBuilders.standaloneSetup(danhMucResource)
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
    public static DanhMuc createEntity(EntityManager em) {
        DanhMuc danhMuc = new DanhMuc()
            .danhMucCode(DEFAULT_DANH_MUC_CODE)
            .name(DEFAULT_NAME)
            .nhomDanhMucCode(DEFAULT_NHOM_DANH_MUC_CODE)
            .userName(DEFAULT_USER_NAME)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .status(DEFAULT_STATUS)
            .program(DEFAULT_PROGRAM);
        return danhMuc;
    }

    @Before
    public void initTest() {
        danhMuc = createEntity(em);
    }

    @Test
    @Transactional
    public void createDanhMuc() throws Exception {
        int databaseSizeBeforeCreate = danhMucRepository.findAll().size();

        // Create the DanhMuc
        restDanhMucMockMvc.perform(post("/api/danh-mucs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(danhMuc)))
            .andExpect(status().isCreated());

        // Validate the DanhMuc in the database
        List<DanhMuc> danhMucList = danhMucRepository.findAll();
        assertThat(danhMucList).hasSize(databaseSizeBeforeCreate + 1);
        DanhMuc testDanhMuc = danhMucList.get(danhMucList.size() - 1);
        assertThat(testDanhMuc.getDanhMucCode()).isEqualTo(DEFAULT_DANH_MUC_CODE);
        assertThat(testDanhMuc.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDanhMuc.getNhomDanhMucCode()).isEqualTo(DEFAULT_NHOM_DANH_MUC_CODE);
        assertThat(testDanhMuc.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testDanhMuc.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testDanhMuc.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testDanhMuc.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDanhMuc.getProgram()).isEqualTo(DEFAULT_PROGRAM);
    }

    @Test
    @Transactional
    public void createDanhMucWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = danhMucRepository.findAll().size();

        // Create the DanhMuc with an existing ID
        danhMuc.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDanhMucMockMvc.perform(post("/api/danh-mucs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(danhMuc)))
            .andExpect(status().isBadRequest());

        // Validate the DanhMuc in the database
        List<DanhMuc> danhMucList = danhMucRepository.findAll();
        assertThat(danhMucList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDanhMucCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = danhMucRepository.findAll().size();
        // set the field null
        danhMuc.setDanhMucCode(null);

        // Create the DanhMuc, which fails.

        restDanhMucMockMvc.perform(post("/api/danh-mucs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(danhMuc)))
            .andExpect(status().isBadRequest());

        List<DanhMuc> danhMucList = danhMucRepository.findAll();
        assertThat(danhMucList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDanhMucs() throws Exception {
        // Initialize the database
        danhMucRepository.saveAndFlush(danhMuc);

        // Get all the danhMucList
        restDanhMucMockMvc.perform(get("/api/danh-mucs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(danhMuc.getId().intValue())))
            .andExpect(jsonPath("$.[*].danhMucCode").value(hasItem(DEFAULT_DANH_MUC_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].nhomDanhMucCode").value(hasItem(DEFAULT_NHOM_DANH_MUC_CODE.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].program").value(hasItem(DEFAULT_PROGRAM.toString())));
    }
    
    @Test
    @Transactional
    public void getDanhMuc() throws Exception {
        // Initialize the database
        danhMucRepository.saveAndFlush(danhMuc);

        // Get the danhMuc
        restDanhMucMockMvc.perform(get("/api/danh-mucs/{id}", danhMuc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(danhMuc.getId().intValue()))
            .andExpect(jsonPath("$.danhMucCode").value(DEFAULT_DANH_MUC_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.nhomDanhMucCode").value(DEFAULT_NHOM_DANH_MUC_CODE.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.program").value(DEFAULT_PROGRAM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDanhMuc() throws Exception {
        // Get the danhMuc
        restDanhMucMockMvc.perform(get("/api/danh-mucs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDanhMuc() throws Exception {
        // Initialize the database
        danhMucService.save(danhMuc);

        int databaseSizeBeforeUpdate = danhMucRepository.findAll().size();

        // Update the danhMuc
        DanhMuc updatedDanhMuc = danhMucRepository.findById(danhMuc.getId()).get();
        // Disconnect from session so that the updates on updatedDanhMuc are not directly saved in db
        em.detach(updatedDanhMuc);
        updatedDanhMuc
            .danhMucCode(UPDATED_DANH_MUC_CODE)
            .name(UPDATED_NAME)
            .nhomDanhMucCode(UPDATED_NHOM_DANH_MUC_CODE)
            .userName(UPDATED_USER_NAME)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .status(UPDATED_STATUS)
            .program(UPDATED_PROGRAM);

        restDanhMucMockMvc.perform(put("/api/danh-mucs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDanhMuc)))
            .andExpect(status().isOk());

        // Validate the DanhMuc in the database
        List<DanhMuc> danhMucList = danhMucRepository.findAll();
        assertThat(danhMucList).hasSize(databaseSizeBeforeUpdate);
        DanhMuc testDanhMuc = danhMucList.get(danhMucList.size() - 1);
        assertThat(testDanhMuc.getDanhMucCode()).isEqualTo(UPDATED_DANH_MUC_CODE);
        assertThat(testDanhMuc.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDanhMuc.getNhomDanhMucCode()).isEqualTo(UPDATED_NHOM_DANH_MUC_CODE);
        assertThat(testDanhMuc.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testDanhMuc.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testDanhMuc.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testDanhMuc.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDanhMuc.getProgram()).isEqualTo(UPDATED_PROGRAM);
    }

    @Test
    @Transactional
    public void updateNonExistingDanhMuc() throws Exception {
        int databaseSizeBeforeUpdate = danhMucRepository.findAll().size();

        // Create the DanhMuc

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDanhMucMockMvc.perform(put("/api/danh-mucs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(danhMuc)))
            .andExpect(status().isBadRequest());

        // Validate the DanhMuc in the database
        List<DanhMuc> danhMucList = danhMucRepository.findAll();
        assertThat(danhMucList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDanhMuc() throws Exception {
        // Initialize the database
        danhMucService.save(danhMuc);

        int databaseSizeBeforeDelete = danhMucRepository.findAll().size();

        // Delete the danhMuc
        restDanhMucMockMvc.perform(delete("/api/danh-mucs/{id}", danhMuc.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DanhMuc> danhMucList = danhMucRepository.findAll();
        assertThat(danhMucList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DanhMuc.class);
        DanhMuc danhMuc1 = new DanhMuc();
        danhMuc1.setId(1L);
        DanhMuc danhMuc2 = new DanhMuc();
        danhMuc2.setId(danhMuc1.getId());
        assertThat(danhMuc1).isEqualTo(danhMuc2);
        danhMuc2.setId(2L);
        assertThat(danhMuc1).isNotEqualTo(danhMuc2);
        danhMuc1.setId(null);
        assertThat(danhMuc1).isNotEqualTo(danhMuc2);
    }
}
