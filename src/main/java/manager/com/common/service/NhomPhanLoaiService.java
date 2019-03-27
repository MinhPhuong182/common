package manager.com.common.service;

import manager.com.common.domain.NhomPhanLoai;
import manager.com.common.repository.NhomPhanLoaiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing NhomPhanLoai.
 */
@Service
@Transactional
public class NhomPhanLoaiService {

    private final Logger log = LoggerFactory.getLogger(NhomPhanLoaiService.class);

    private final NhomPhanLoaiRepository nhomPhanLoaiRepository;

    public NhomPhanLoaiService(NhomPhanLoaiRepository nhomPhanLoaiRepository) {
        this.nhomPhanLoaiRepository = nhomPhanLoaiRepository;
    }

    /**
     * Save a nhomPhanLoai.
     *
     * @param nhomPhanLoai the entity to save
     * @return the persisted entity
     */
    public NhomPhanLoai save(NhomPhanLoai nhomPhanLoai) {
        log.debug("Request to save NhomPhanLoai : {}", nhomPhanLoai);
        return nhomPhanLoaiRepository.save(nhomPhanLoai);
    }

    /**
     * Get all the nhomPhanLoais.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<NhomPhanLoai> findAll(Pageable pageable) {
        log.debug("Request to get all NhomPhanLoais");
        return nhomPhanLoaiRepository.findAll(pageable);
    }


    /**
     * Get one nhomPhanLoai by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<NhomPhanLoai> findOne(Long id) {
        log.debug("Request to get NhomPhanLoai : {}", id);
        return nhomPhanLoaiRepository.findById(id);
    }

    /**
     * Delete the nhomPhanLoai by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete NhomPhanLoai : {}", id);
        nhomPhanLoaiRepository.deleteById(id);
    }
}
