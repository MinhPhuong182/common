package manager.com.common.web.rest;
import manager.com.common.domain.ChiTieu;
import manager.com.common.service.ChiTieuService;
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
 * REST controller for managing ChiTieu.
 */
@RestController
@RequestMapping("/api")
public class ChiTieuResource {

    private final Logger log = LoggerFactory.getLogger(ChiTieuResource.class);

    private static final String ENTITY_NAME = "commonChiTieu";

    private final ChiTieuService chiTieuService;

    public ChiTieuResource(ChiTieuService chiTieuService) {
        this.chiTieuService = chiTieuService;
    }

    /**
     * POST  /chi-tieus : Create a new chiTieu.
     *
     * @param chiTieu the chiTieu to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chiTieu, or with status 400 (Bad Request) if the chiTieu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chi-tieus")
    public ResponseEntity<ChiTieu> createChiTieu(@Valid @RequestBody ChiTieu chiTieu) throws URISyntaxException {
        log.debug("REST request to save ChiTieu : {}", chiTieu);
        if (chiTieu.getId() != null) {
            throw new BadRequestAlertException("A new chiTieu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChiTieu result = chiTieuService.save(chiTieu);
        return ResponseEntity.created(new URI("/api/chi-tieus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /chi-tieus : Updates an existing chiTieu.
     *
     * @param chiTieu the chiTieu to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chiTieu,
     * or with status 400 (Bad Request) if the chiTieu is not valid,
     * or with status 500 (Internal Server Error) if the chiTieu couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chi-tieus")
    public ResponseEntity<ChiTieu> updateChiTieu(@Valid @RequestBody ChiTieu chiTieu) throws URISyntaxException {
        log.debug("REST request to update ChiTieu : {}", chiTieu);
        if (chiTieu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChiTieu result = chiTieuService.save(chiTieu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, chiTieu.getId().toString()))
            .body(result);
    }

    /**
     * GET  /chi-tieus : get all the chiTieus.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of chiTieus in body
     */
    @GetMapping("/chi-tieus")
    public ResponseEntity<List<ChiTieu>> getAllChiTieus(Pageable pageable) {
        log.debug("REST request to get a page of ChiTieus");
        Page<ChiTieu> page = chiTieuService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/chi-tieus");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /chi-tieus/:id : get the "id" chiTieu.
     *
     * @param id the id of the chiTieu to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chiTieu, or with status 404 (Not Found)
     */
    @GetMapping("/chi-tieus/{id}")
    public ResponseEntity<ChiTieu> getChiTieu(@PathVariable Long id) {
        log.debug("REST request to get ChiTieu : {}", id);
        Optional<ChiTieu> chiTieu = chiTieuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chiTieu);
    }

    /**
     * DELETE  /chi-tieus/:id : delete the "id" chiTieu.
     *
     * @param id the id of the chiTieu to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chi-tieus/{id}")
    public ResponseEntity<Void> deleteChiTieu(@PathVariable Long id) {
        log.debug("REST request to delete ChiTieu : {}", id);
        chiTieuService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
