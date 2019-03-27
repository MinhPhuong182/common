package manager.com.common.service;

import manager.com.common.domain.NhomDanhMuc;
import manager.com.common.repository.NhomDanhMucRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing NhomDanhMuc.
 */
@Service
@Transactional
public class NhomDanhMucService {

    private final Logger log = LoggerFactory.getLogger(NhomDanhMucService.class);

    private final NhomDanhMucRepository nhomDanhMucRepository;

    public NhomDanhMucService(NhomDanhMucRepository nhomDanhMucRepository) {
        this.nhomDanhMucRepository = nhomDanhMucRepository;
    }

    /**
     * Save a nhomDanhMuc.
     *
     * @param nhomDanhMuc the entity to save
     * @return the persisted entity
     */
    public NhomDanhMuc save(NhomDanhMuc nhomDanhMuc) {
        log.debug("Request to save NhomDanhMuc : {}", nhomDanhMuc);
        return nhomDanhMucRepository.save(nhomDanhMuc);
    }

    /**
     * Get all the nhomDanhMucs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<NhomDanhMuc> findAll(Pageable pageable) {
        log.debug("Request to get all NhomDanhMucs");
        return nhomDanhMucRepository.findAll(pageable);
    }


    /**
     * Get one nhomDanhMuc by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<NhomDanhMuc> findOne(Long id) {
        log.debug("Request to get NhomDanhMuc : {}", id);
        return nhomDanhMucRepository.findById(id);
    }

    /**
     * Delete the nhomDanhMuc by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete NhomDanhMuc : {}", id);
        nhomDanhMucRepository.deleteById(id);
    }
}
