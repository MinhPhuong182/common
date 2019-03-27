package manager.com.common.service;

import manager.com.common.domain.DoiTuong;
import manager.com.common.repository.DoiTuongRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing DoiTuong.
 */
@Service
@Transactional
public class DoiTuongService {

    private final Logger log = LoggerFactory.getLogger(DoiTuongService.class);

    private final DoiTuongRepository doiTuongRepository;

    public DoiTuongService(DoiTuongRepository doiTuongRepository) {
        this.doiTuongRepository = doiTuongRepository;
    }

    /**
     * Save a doiTuong.
     *
     * @param doiTuong the entity to save
     * @return the persisted entity
     */
    public DoiTuong save(DoiTuong doiTuong) {
        log.debug("Request to save DoiTuong : {}", doiTuong);
        return doiTuongRepository.save(doiTuong);
    }

    /**
     * Get all the doiTuongs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DoiTuong> findAll(Pageable pageable) {
        log.debug("Request to get all DoiTuongs");
        return doiTuongRepository.findAll(pageable);
    }


    /**
     * Get one doiTuong by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DoiTuong> findOne(Long id) {
        log.debug("Request to get DoiTuong : {}", id);
        return doiTuongRepository.findById(id);
    }

    /**
     * Delete the doiTuong by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DoiTuong : {}", id);
        doiTuongRepository.deleteById(id);
    }
}
