package manager.com.common.web.rest;

import manager.com.common.CommonApp;

import manager.com.common.domain.DoiTuong;
import manager.com.common.repository.DoiTuongRepository;
import manager.com.common.service.DoiTuongService;
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
 * Test class for the DoiTuongResource REST controller.
 *
 * @see DoiTuongResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommonApp.class)
public class DoiTuongResourceIntTest {

    private static final String DEFAULT_DOI_TUONG_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DOI_TUONG_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NHOM_PHAN_LOAI_CODE = "AAAAAAAAAA";
    private static final String UPDATED_NHOM_PHAN_LOAI_CODE = "BBBBBBBBBB";

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
    private DoiTuongRepository doiTuongRepository;

    @Autowired
    private DoiTuongService doiTuongService;

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

    private MockMvc restDoiTuongMockMvc;

    private DoiTuong doiTuong;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DoiTuongResource doiTuongResource = new DoiTuongResource(doiTuongService);
        this.restDoiTuongMockMvc = MockMvcBuilders.standaloneSetup(doiTuongResource)
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
    public static DoiTuong createEntity(EntityManager em) {
        DoiTuong doiTuong = new DoiTuong()
            .doiTuongCode(DEFAULT_DOI_TUONG_CODE)
            .nhomPhanLoaiCode(DEFAULT_NHOM_PHAN_LOAI_CODE)
            .name(DEFAULT_NAME)
            .userName(DEFAULT_USER_NAME)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .status(DEFAULT_STATUS)
            .program(DEFAULT_PROGRAM);
        return doiTuong;
    }

    @Before
    public void initTest() {
        doiTuong = createEntity(em);
    }

    @Test
    @Transactional
    public void createDoiTuong() throws Exception {
        int databaseSizeBeforeCreate = doiTuongRepository.findAll().size();

        // Create the DoiTuong
        restDoiTuongMockMvc.perform(post("/api/doi-tuongs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doiTuong)))
            .andExpect(status().isCreated());

        // Validate the DoiTuong in the database
        List<DoiTuong> doiTuongList = doiTuongRepository.findAll();
        assertThat(doiTuongList).hasSize(databaseSizeBeforeCreate + 1);
        DoiTuong testDoiTuong = doiTuongList.get(doiTuongList.size() - 1);
        assertThat(testDoiTuong.getDoiTuongCode()).isEqualTo(DEFAULT_DOI_TUONG_CODE);
        assertThat(testDoiTuong.getNhomPhanLoaiCode()).isEqualTo(DEFAULT_NHOM_PHAN_LOAI_CODE);
        assertThat(testDoiTuong.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDoiTuong.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testDoiTuong.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testDoiTuong.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testDoiTuong.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDoiTuong.getProgram()).isEqualTo(DEFAULT_PROGRAM);
    }

    @Test
    @Transactional
    public void createDoiTuongWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = doiTuongRepository.findAll().size();

        // Create the DoiTuong with an existing ID
        doiTuong.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoiTuongMockMvc.perform(post("/api/doi-tuongs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doiTuong)))
            .andExpect(status().isBadRequest());

        // Validate the DoiTuong in the database
        List<DoiTuong> doiTuongList = doiTuongRepository.findAll();
        assertThat(doiTuongList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDoiTuongCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = doiTuongRepository.findAll().size();
        // set the field null
        doiTuong.setDoiTuongCode(null);

        // Create the DoiTuong, which fails.

        restDoiTuongMockMvc.perform(post("/api/doi-tuongs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doiTuong)))
            .andExpect(status().isBadRequest());

        List<DoiTuong> doiTuongList = doiTuongRepository.findAll();
        assertThat(doiTuongList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNhomPhanLoaiCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = doiTuongRepository.findAll().size();
        // set the field null
        doiTuong.setNhomPhanLoaiCode(null);

        // Create the DoiTuong, which fails.

        restDoiTuongMockMvc.perform(post("/api/doi-tuongs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doiTuong)))
            .andExpect(status().isBadRequest());

        List<DoiTuong> doiTuongList = doiTuongRepository.findAll();
        assertThat(doiTuongList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDoiTuongs() throws Exception {
        // Initialize the database
        doiTuongRepository.saveAndFlush(doiTuong);

        // Get all the doiTuongList
        restDoiTuongMockMvc.perform(get("/api/doi-tuongs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doiTuong.getId().intValue())))
            .andExpect(jsonPath("$.[*].doiTuongCode").value(hasItem(DEFAULT_DOI_TUONG_CODE.toString())))
            .andExpect(jsonPath("$.[*].nhomPhanLoaiCode").value(hasItem(DEFAULT_NHOM_PHAN_LOAI_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].program").value(hasItem(DEFAULT_PROGRAM.toString())));
    }
    
    @Test
    @Transactional
    public void getDoiTuong() throws Exception {
        // Initialize the database
        doiTuongRepository.saveAndFlush(doiTuong);

        // Get the doiTuong
        restDoiTuongMockMvc.perform(get("/api/doi-tuongs/{id}", doiTuong.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(doiTuong.getId().intValue()))
            .andExpect(jsonPath("$.doiTuongCode").value(DEFAULT_DOI_TUONG_CODE.toString()))
            .andExpect(jsonPath("$.nhomPhanLoaiCode").value(DEFAULT_NHOM_PHAN_LOAI_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.program").value(DEFAULT_PROGRAM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDoiTuong() throws Exception {
        // Get the doiTuong
        restDoiTuongMockMvc.perform(get("/api/doi-tuongs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDoiTuong() throws Exception {
        // Initialize the database
        doiTuongService.save(doiTuong);

        int databaseSizeBeforeUpdate = doiTuongRepository.findAll().size();

        // Update the doiTuong
        DoiTuong updatedDoiTuong = doiTuongRepository.findById(doiTuong.getId()).get();
        // Disconnect from session so that the updates on updatedDoiTuong are not directly saved in db
        em.detach(updatedDoiTuong);
        updatedDoiTuong
            .doiTuongCode(UPDATED_DOI_TUONG_CODE)
            .nhomPhanLoaiCode(UPDATED_NHOM_PHAN_LOAI_CODE)
            .name(UPDATED_NAME)
            .userName(UPDATED_USER_NAME)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .status(UPDATED_STATUS)
            .program(UPDATED_PROGRAM);

        restDoiTuongMockMvc.perform(put("/api/doi-tuongs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDoiTuong)))
            .andExpect(status().isOk());

        // Validate the DoiTuong in the database
        List<DoiTuong> doiTuongList = doiTuongRepository.findAll();
        assertThat(doiTuongList).hasSize(databaseSizeBeforeUpdate);
        DoiTuong testDoiTuong = doiTuongList.get(doiTuongList.size() - 1);
        assertThat(testDoiTuong.getDoiTuongCode()).isEqualTo(UPDATED_DOI_TUONG_CODE);
        assertThat(testDoiTuong.getNhomPhanLoaiCode()).isEqualTo(UPDATED_NHOM_PHAN_LOAI_CODE);
        assertThat(testDoiTuong.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDoiTuong.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testDoiTuong.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testDoiTuong.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testDoiTuong.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDoiTuong.getProgram()).isEqualTo(UPDATED_PROGRAM);
    }

    @Test
    @Transactional
    public void updateNonExistingDoiTuong() throws Exception {
        int databaseSizeBeforeUpdate = doiTuongRepository.findAll().size();

        // Create the DoiTuong

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoiTuongMockMvc.perform(put("/api/doi-tuongs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doiTuong)))
            .andExpect(status().isBadRequest());

        // Validate the DoiTuong in the database
        List<DoiTuong> doiTuongList = doiTuongRepository.findAll();
        assertThat(doiTuongList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDoiTuong() throws Exception {
        // Initialize the database
        doiTuongService.save(doiTuong);

        int databaseSizeBeforeDelete = doiTuongRepository.findAll().size();

        // Delete the doiTuong
        restDoiTuongMockMvc.perform(delete("/api/doi-tuongs/{id}", doiTuong.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DoiTuong> doiTuongList = doiTuongRepository.findAll();
        assertThat(doiTuongList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoiTuong.class);
        DoiTuong doiTuong1 = new DoiTuong();
        doiTuong1.setId(1L);
        DoiTuong doiTuong2 = new DoiTuong();
        doiTuong2.setId(doiTuong1.getId());
        assertThat(doiTuong1).isEqualTo(doiTuong2);
        doiTuong2.setId(2L);
        assertThat(doiTuong1).isNotEqualTo(doiTuong2);
        doiTuong1.setId(null);
        assertThat(doiTuong1).isNotEqualTo(doiTuong2);
    }
}
