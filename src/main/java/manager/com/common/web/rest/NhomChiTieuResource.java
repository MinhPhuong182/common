package manager.com.common.web.rest;
import manager.com.common.domain.NhomChiTieu;
import manager.com.common.service.NhomChiTieuService;
import manager.com.common.web.rest.errors.BadRequestAlertException;
import manager.com.common.web.rest.util.HeaderUtil;
import manager.com.common.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing NhomChiTieu.
 */
@RestController
@RequestMapping("/api")
public class NhomChiTieuResource {

    private final Logger log = LoggerFactory.getLogger(NhomChiTieuResource.class);

    private static final String ENTITY_NAME = "commonNhomChiTieu";

    private final NhomChiTieuService nhomChiTieuService;

    public NhomChiTieuResource(NhomChiTieuService nhomChiTieuService) {
        this.nhomChiTieuService = nhomChiTieuService;
    }

    /**
     * POST  /nhom-chi-tieus : Create a new nhomChiTieu.
     *
     * @param nhomChiTieu the nhomChiTieu to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nhomChiTieu, or with status 400 (Bad Request) if the nhomChiTieu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nhom-chi-tieus")
    public ResponseEntity<NhomChiTieu> createNhomChiTieu(@Valid @RequestBody NhomChiTieu nhomChiTieu) throws URISyntaxException {
        log.debug("REST request to save NhomChiTieu : {}", nhomChiTieu);
        if (nhomChiTieu.getId() != null) {
            throw new BadRequestAlertException("A new nhomChiTieu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NhomChiTieu result = nhomChiTieuService.save(nhomChiTieu);
        return ResponseEntity.created(new URI("/api/nhom-chi-tieus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nhom-chi-tieus : Updates an existing nhomChiTieu.
     *
     * @param nhomChiTieu the nhomChiTieu to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nhomChiTieu,
     * or with status 400 (Bad Request) if the nhomChiTieu is not valid,
     * or with status 500 (Internal Server Error) if the nhomChiTieu couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nhom-chi-tieus")
    public ResponseEntity<NhomChiTieu> updateNhomChiTieu(@Valid @RequestBody NhomChiTieu nhomChiTieu) throws URISyntaxException {
        log.debug("REST request to update NhomChiTieu : {}", nhomChiTieu);
        if (nhomChiTieu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NhomChiTieu result = nhomChiTieuService.save(nhomChiTieu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nhomChiTieu.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nhom-chi-tieus : get all the nhomChiTieus.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of nhomChiTieus in body
     */
    @GetMapping("/nhom-chi-tieus")
    public ResponseEntity<List<NhomChiTieu>> getAllNhomChiTieus(Pageable pageable) {
        log.debug("REST request to get a page of NhomChiTieus");
        Page<NhomChiTieu> page = nhomChiTieuService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/nhom-chi-tieus");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /nhom-chi-tieus/:id : get the "id" nhomChiTieu.
     *
     * @param id the id of the nhomChiTieu to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nhomChiTieu, or with status 404 (Not Found)
     */
    @GetMapping("/nhom-chi-tieus/{id}")
    public ResponseEntity<NhomChiTieu> getNhomChiTieu(@PathVariable Long id) {
        log.debug("REST request to get NhomChiTieu : {}", id);
        Optional<NhomChiTieu> nhomChiTieu = nhomChiTieuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nhomChiTieu);
    }

    /**
     * DELETE  /nhom-chi-tieus/:id : delete the "id" nhomChiTieu.
     *
     * @param id the id of the nhomChiTieu to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nhom-chi-tieus/{id}")
    public ResponseEntity<Void> deleteNhomChiTieu(@PathVariable Long id) {
        log.debug("REST request to delete NhomChiTieu : {}", id);
        nhomChiTieuService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
