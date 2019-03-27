package manager.com.common.service;

import manager.com.common.domain.DanhMuc;
import manager.com.common.repository.DanhMucRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing DanhMuc.
 */
@Service
@Transactional
public class DanhMucService {

    private final Logger log = LoggerFactory.getLogger(DanhMucService.class);

    private final DanhMucRepository danhMucRepository;

    public DanhMucService(DanhMucRepository danhMucRepository) {
        this.danhMucRepository = danhMucRepository;
    }

    /**
     * Save a danhMuc.
     *
     * @param danhMuc the entity to save
     * @return the persisted entity
     */
    public DanhMuc save(DanhMuc danhMuc) {
        log.debug("Request to save DanhMuc : {}", danhMuc);
        return danhMucRepository.save(danhMuc);
    }

    /**
     * Get all the danhMucs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DanhMuc> findAll(Pageable pageable) {
        log.debug("Request to get all DanhMucs");
        return danhMucRepository.findAll(pageable);
    }


    /**
     * Get one danhMuc by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DanhMuc> findOne(Long id) {
        log.debug("Request to get DanhMuc : {}", id);
        return danhMucRepository.findById(id);
    }

    /**
     * Delete the danhMuc by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DanhMuc : {}", id);
        danhMucRepository.deleteById(id);
    }
}
