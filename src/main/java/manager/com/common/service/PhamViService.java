package manager.com.common.service;

import manager.com.common.domain.PhamVi;
import manager.com.common.repository.PhamViRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing PhamVi.
 */
@Service
@Transactional
public class PhamViService {

    private final Logger log = LoggerFactory.getLogger(PhamViService.class);

    private final PhamViRepository phamViRepository;

    public PhamViService(PhamViRepository phamViRepository) {
        this.phamViRepository = phamViRepository;
    }

    /**
     * Save a phamVi.
     *
     * @param phamVi the entity to save
     * @return the persisted entity
     */
    public PhamVi save(PhamVi phamVi) {
        log.debug("Request to save PhamVi : {}", phamVi);
        return phamViRepository.save(phamVi);
    }

    /**
     * Get all the phamVis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PhamVi> findAll(Pageable pageable) {
        log.debug("Request to get all PhamVis");
        return phamViRepository.findAll(pageable);
    }



    /**
     *  get all the phamVis where Donvi is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<PhamVi> findAllWhereDonviIsNull() {
        log.debug("Request to get all phamVis where Donvi is null");
        return StreamSupport
            .stream(phamViRepository.findAll().spliterator(), false)
            .filter(phamVi -> phamVi.getDonvi() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one phamVi by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PhamVi> findOne(Long id) {
        log.debug("Request to get PhamVi : {}", id);
        return phamViRepository.findById(id);
    }

    /**
     * Delete the phamVi by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PhamVi : {}", id);
        phamViRepository.deleteById(id);
    }
}
