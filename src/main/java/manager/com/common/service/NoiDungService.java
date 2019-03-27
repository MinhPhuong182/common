package manager.com.common.service;

import manager.com.common.domain.NoiDung;
import manager.com.common.repository.NoiDungRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing NoiDung.
 */
@Service
@Transactional
public class NoiDungService {

    private final Logger log = LoggerFactory.getLogger(NoiDungService.class);

    private final NoiDungRepository noiDungRepository;

    public NoiDungService(NoiDungRepository noiDungRepository) {
        this.noiDungRepository = noiDungRepository;
    }

    /**
     * Save a noiDung.
     *
     * @param noiDung the entity to save
     * @return the persisted entity
     */
    public NoiDung save(NoiDung noiDung) {
        log.debug("Request to save NoiDung : {}", noiDung);
        return noiDungRepository.save(noiDung);
    }

    /**
     * Get all the noiDungs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<NoiDung> findAll(Pageable pageable) {
        log.debug("Request to get all NoiDungs");
        return noiDungRepository.findAll(pageable);
    }


    /**
     * Get one noiDung by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<NoiDung> findOne(Long id) {
        log.debug("Request to get NoiDung : {}", id);
        return noiDungRepository.findById(id);
    }

    /**
     * Delete the noiDung by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete NoiDung : {}", id);
        noiDungRepository.deleteById(id);
    }
}
