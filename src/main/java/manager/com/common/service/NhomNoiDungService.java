package manager.com.common.service;

import manager.com.common.domain.NhomNoiDung;
import manager.com.common.repository.NhomNoiDungRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing NhomNoiDung.
 */
@Service
@Transactional
public class NhomNoiDungService {

    private final Logger log = LoggerFactory.getLogger(NhomNoiDungService.class);

    private final NhomNoiDungRepository nhomNoiDungRepository;

    public NhomNoiDungService(NhomNoiDungRepository nhomNoiDungRepository) {
        this.nhomNoiDungRepository = nhomNoiDungRepository;
    }

    /**
     * Save a nhomNoiDung.
     *
     * @param nhomNoiDung the entity to save
     * @return the persisted entity
     */
    public NhomNoiDung save(NhomNoiDung nhomNoiDung) {
        log.debug("Request to save NhomNoiDung : {}", nhomNoiDung);
        return nhomNoiDungRepository.save(nhomNoiDung);
    }

    /**
     * Get all the nhomNoiDungs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<NhomNoiDung> findAll(Pageable pageable) {
        log.debug("Request to get all NhomNoiDungs");
        return nhomNoiDungRepository.findAll(pageable);
    }


    /**
     * Get one nhomNoiDung by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<NhomNoiDung> findOne(Long id) {
        log.debug("Request to get NhomNoiDung : {}", id);
        return nhomNoiDungRepository.findById(id);
    }

    /**
     * Delete the nhomNoiDung by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete NhomNoiDung : {}", id);
        nhomNoiDungRepository.deleteById(id);
    }
}
