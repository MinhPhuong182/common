package manager.com.common.service;

import manager.com.common.domain.NhomChiTieu;
import manager.com.common.repository.NhomChiTieuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing NhomChiTieu.
 */
@Service
@Transactional
public class NhomChiTieuService {

    private final Logger log = LoggerFactory.getLogger(NhomChiTieuService.class);

    private final NhomChiTieuRepository nhomChiTieuRepository;

    public NhomChiTieuService(NhomChiTieuRepository nhomChiTieuRepository) {
        this.nhomChiTieuRepository = nhomChiTieuRepository;
    }

    /**
     * Save a nhomChiTieu.
     *
     * @param nhomChiTieu the entity to save
     * @return the persisted entity
     */
    public NhomChiTieu save(NhomChiTieu nhomChiTieu) {
        log.debug("Request to save NhomChiTieu : {}", nhomChiTieu);
        return nhomChiTieuRepository.save(nhomChiTieu);
    }

    /**
     * Get all the nhomChiTieus.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<NhomChiTieu> findAll(Pageable pageable) {
        log.debug("Request to get all NhomChiTieus");
        return nhomChiTieuRepository.findAll(pageable);
    }


    /**
     * Get one nhomChiTieu by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<NhomChiTieu> findOne(Long id) {
        log.debug("Request to get NhomChiTieu : {}", id);
        return nhomChiTieuRepository.findById(id);
    }

    /**
     * Delete the nhomChiTieu by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete NhomChiTieu : {}", id);
        nhomChiTieuRepository.deleteById(id);
    }
}
