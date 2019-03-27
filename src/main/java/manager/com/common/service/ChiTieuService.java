package manager.com.common.service;

import manager.com.common.domain.ChiTieu;
import manager.com.common.repository.ChiTieuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ChiTieu.
 */
@Service
@Transactional
public class ChiTieuService {

    private final Logger log = LoggerFactory.getLogger(ChiTieuService.class);

    private final ChiTieuRepository chiTieuRepository;

    public ChiTieuService(ChiTieuRepository chiTieuRepository) {
        this.chiTieuRepository = chiTieuRepository;
    }

    /**
     * Save a chiTieu.
     *
     * @param chiTieu the entity to save
     * @return the persisted entity
     */
    public ChiTieu save(ChiTieu chiTieu) {
        log.debug("Request to save ChiTieu : {}", chiTieu);
        return chiTieuRepository.save(chiTieu);
    }

    /**
     * Get all the chiTieus.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ChiTieu> findAll(Pageable pageable) {
        log.debug("Request to get all ChiTieus");
        return chiTieuRepository.findAll(pageable);
    }


    /**
     * Get one chiTieu by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ChiTieu> findOne(Long id) {
        log.debug("Request to get ChiTieu : {}", id);
        return chiTieuRepository.findById(id);
    }

    /**
     * Delete the chiTieu by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ChiTieu : {}", id);
        chiTieuRepository.deleteById(id);
    }
}
