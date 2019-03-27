package manager.com.common.service;

import manager.com.common.domain.DonVi;
import manager.com.common.repository.DonViRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing DonVi.
 */
@Service
@Transactional
public class DonViService {

    private final Logger log = LoggerFactory.getLogger(DonViService.class);

    private final DonViRepository donViRepository;

    public DonViService(DonViRepository donViRepository) {
        this.donViRepository = donViRepository;
    }

    /**
     * Save a donVi.
     *
     * @param donVi the entity to save
     * @return the persisted entity
     */
    public DonVi save(DonVi donVi) {
        log.debug("Request to save DonVi : {}", donVi);
        return donViRepository.save(donVi);
    }

    /**
     * Get all the donVis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DonVi> findAll(Pageable pageable) {
        log.debug("Request to get all DonVis");
        return donViRepository.findAll(pageable);
    }


    /**
     * Get one donVi by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DonVi> findOne(Long id) {
        log.debug("Request to get DonVi : {}", id);
        return donViRepository.findById(id);
    }

    /**
     * Delete the donVi by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DonVi : {}", id);
        donViRepository.deleteById(id);
    }
}
