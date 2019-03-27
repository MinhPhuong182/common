package manager.com.common.web.rest;

import manager.com.common.CommonApp;

import manager.com.common.domain.ChiTieu;
import manager.com.common.repository.ChiTieuRepository;
import manager.com.common.service.ChiTieuService;
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
 * Test class for the ChiTieuResource REST controller.
 *
 * @see ChiTieuResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommonApp.class)
public class ChiTieuResourceIntTest {

    private static final String DEFAULT_CHI_TIEU_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CHI_TIEU_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NHOM_CHI_TIEU_CODE = "AAAAAAAAAA";
    private static final String UPDATED_NHOM_CHI_TIEU_CODE = "BBBBBBBBBB";

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
    private ChiTieuRepository chiTieuRepository;

    @Autowired
    private ChiTieuService chiTieuService;

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

    private MockMvc restChiTieuMockMvc;

    private ChiTieu chiTieu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChiTieuResource chiTieuResource = new ChiTieuResource(chiTieuService);
        this.restChiTieuMockMvc = MockMvcBuilders.standaloneSetup(chiTieuResource)
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
    public static ChiTieu createEntity(EntityManager em) {
        ChiTieu chiTieu = new ChiTieu()
            .chiTieuCode(DEFAULT_CHI_TIEU_CODE)
            .name(DEFAULT_NAME)
            .nhomChiTieuCode(DEFAULT_NHOM_CHI_TIEU_CODE)
            .userName(DEFAULT_USER_NAME)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .status(DEFAULT_STATUS)
            .program(DEFAULT_PROGRAM);
        return chiTieu;
    }

    @Before
    public void initTest() {
        chiTieu = createEntity(em);
    }

    @Test
    @Transactional
    public void createChiTieu() throws Exception {
        int databaseSizeBeforeCreate = chiTieuRepository.findAll().size();

        // Create the ChiTieu
        restChiTieuMockMvc.perform(post("/api/chi-tieus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chiTieu)))
            .andExpect(status().isCreated());

        // Validate the ChiTieu in the database
        List<ChiTieu> chiTieuList = chiTieuRepository.findAll();
        assertThat(chiTieuList).hasSize(databaseSizeBeforeCreate + 1);
        ChiTieu testChiTieu = chiTieuList.get(chiTieuList.size() - 1);
        assertThat(testChiTieu.getChiTieuCode()).isEqualTo(DEFAULT_CHI_TIEU_CODE);
        assertThat(testChiTieu.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testChiTieu.getNhomChiTieuCode()).isEqualTo(DEFAULT_NHOM_CHI_TIEU_CODE);
        assertThat(testChiTieu.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testChiTieu.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testChiTieu.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testChiTieu.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testChiTieu.getProgram()).isEqualTo(DEFAULT_PROGRAM);
    }

    @Test
    @Transactional
    public void createChiTieuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chiTieuRepository.findAll().size();

        // Create the ChiTieu with an existing ID
        chiTieu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChiTieuMockMvc.perform(post("/api/chi-tieus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chiTieu)))
            .andExpect(status().isBadRequest());

        // Validate the ChiTieu in the database
        List<ChiTieu> chiTieuList = chiTieuRepository.findAll();
        assertThat(chiTieuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkChiTieuCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = chiTieuRepository.findAll().size();
        // set the field null
        chiTieu.setChiTieuCode(null);

        // Create the ChiTieu, which fails.

        restChiTieuMockMvc.perform(post("/api/chi-tieus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chiTieu)))
            .andExpect(status().isBadRequest());

        List<ChiTieu> chiTieuList = chiTieuRepository.findAll();
        assertThat(chiTieuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChiTieus() throws Exception {
        // Initialize the database
        chiTieuRepository.saveAndFlush(chiTieu);

        // Get all the chiTieuList
        restChiTieuMockMvc.perform(get("/api/chi-tieus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chiTieu.getId().intValue())))
            .andExpect(jsonPath("$.[*].chiTieuCode").value(hasItem(DEFAULT_CHI_TIEU_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].nhomChiTieuCode").value(hasItem(DEFAULT_NHOM_CHI_TIEU_CODE.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].program").value(hasItem(DEFAULT_PROGRAM.toString())));
    }
    
    @Test
    @Transactional
    public void getChiTieu() throws Exception {
        // Initialize the database
        chiTieuRepository.saveAndFlush(chiTieu);

        // Get the chiTieu
        restChiTieuMockMvc.perform(get("/api/chi-tieus/{id}", chiTieu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chiTieu.getId().intValue()))
            .andExpect(jsonPath("$.chiTieuCode").value(DEFAULT_CHI_TIEU_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.nhomChiTieuCode").value(DEFAULT_NHOM_CHI_TIEU_CODE.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.program").value(DEFAULT_PROGRAM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingChiTieu() throws Exception {
        // Get the chiTieu
        restChiTieuMockMvc.perform(get("/api/chi-tieus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChiTieu() throws Exception {
        // Initialize the database
        chiTieuService.save(chiTieu);

        int databaseSizeBeforeUpdate = chiTieuRepository.findAll().size();

        // Update the chiTieu
        ChiTieu updatedChiTieu = chiTieuRepository.findById(chiTieu.getId()).get();
        // Disconnect from session so that the updates on updatedChiTieu are not directly saved in db
        em.detach(updatedChiTieu);
        updatedChiTieu
            .chiTieuCode(UPDATED_CHI_TIEU_CODE)
            .name(UPDATED_NAME)
            .nhomChiTieuCode(UPDATED_NHOM_CHI_TIEU_CODE)
            .userName(UPDATED_USER_NAME)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .status(UPDATED_STATUS)
            .program(UPDATED_PROGRAM);

        restChiTieuMockMvc.perform(put("/api/chi-tieus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChiTieu)))
            .andExpect(status().isOk());

        // Validate the ChiTieu in the database
        List<ChiTieu> chiTieuList = chiTieuRepository.findAll();
        assertThat(chiTieuList).hasSize(databaseSizeBeforeUpdate);
        ChiTieu testChiTieu = chiTieuList.get(chiTieuList.size() - 1);
        assertThat(testChiTieu.getChiTieuCode()).isEqualTo(UPDATED_CHI_TIEU_CODE);
        assertThat(testChiTieu.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testChiTieu.getNhomChiTieuCode()).isEqualTo(UPDATED_NHOM_CHI_TIEU_CODE);
        assertThat(testChiTieu.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testChiTieu.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testChiTieu.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testChiTieu.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testChiTieu.getProgram()).isEqualTo(UPDATED_PROGRAM);
    }

    @Test
    @Transactional
    public void updateNonExistingChiTieu() throws Exception {
        int databaseSizeBeforeUpdate = chiTieuRepository.findAll().size();

        // Create the ChiTieu

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChiTieuMockMvc.perform(put("/api/chi-tieus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chiTieu)))
            .andExpect(status().isBadRequest());

        // Validate the ChiTieu in the database
        List<ChiTieu> chiTieuList = chiTieuRepository.findAll();
        assertThat(chiTieuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChiTieu() throws Exception {
        // Initialize the database
        chiTieuService.save(chiTieu);

        int databaseSizeBeforeDelete = chiTieuRepository.findAll().size();

        // Delete the chiTieu
        restChiTieuMockMvc.perform(delete("/api/chi-tieus/{id}", chiTieu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ChiTieu> chiTieuList = chiTieuRepository.findAll();
        assertThat(chiTieuList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChiTieu.class);
        ChiTieu chiTieu1 = new ChiTieu();
        chiTieu1.setId(1L);
        ChiTieu chiTieu2 = new ChiTieu();
        chiTieu2.setId(chiTieu1.getId());
        assertThat(chiTieu1).isEqualTo(chiTieu2);
        chiTieu2.setId(2L);
        assertThat(chiTieu1).isNotEqualTo(chiTieu2);
        chiTieu1.setId(null);
        assertThat(chiTieu1).isNotEqualTo(chiTieu2);
    }
}
