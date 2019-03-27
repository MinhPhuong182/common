package manager.com.common.web.rest;
import manager.com.common.domain.NoiDung;
import manager.com.common.service.NoiDungService;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing NoiDung.
 */
@RestController
@RequestMapping("/api")
public class NoiDungResource {

    private final Logger log = LoggerFactory.getLogger(NoiDungResource.class);

    private static final String ENTITY_NAME = "commonNoiDung";

    private final NoiDungService noiDungService;

    public NoiDungResource(NoiDungService noiDungService) {
        this.noiDungService = noiDungService;
    }

    /**
     * POST  /noi-dungs : Create a new noiDung.
     *
     * @param noiDung the noiDung to create
     * @return the ResponseEntity with status 201 (Created) and with body the new noiDung, or with status 400 (Bad Request) if the noiDung has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/noi-dungs")
    public ResponseEntity<NoiDung> createNoiDung(@RequestBody NoiDung noiDung) throws URISyntaxException {
        log.debug("REST request to save NoiDung : {}", noiDung);
        if (noiDung.getId() != null) {
            throw new BadRequestAlertException("A new noiDung cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NoiDung result = noiDungService.save(noiDung);
        return ResponseEntity.created(new URI("/api/noi-dungs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /noi-dungs : Updates an existing noiDung.
     *
     * @param noiDung the noiDung to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated noiDung,
     * or with status 400 (Bad Request) if the noiDung is not valid,
     * or with status 500 (Internal Server Error) if the noiDung couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/noi-dungs")
    public ResponseEntity<NoiDung> updateNoiDung(@RequestBody NoiDung noiDung) throws URISyntaxException {
        log.debug("REST request to update NoiDung : {}", noiDung);
        if (noiDung.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NoiDung result = noiDungService.save(noiDung);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, noiDung.getId().toString()))
            .body(result);
    }

    /**
     * GET  /noi-dungs : get all the noiDungs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of noiDungs in body
     */
    @GetMapping("/noi-dungs")
    public ResponseEntity<List<NoiDung>> getAllNoiDungs(Pageable pageable) {
        log.debug("REST request to get a page of NoiDungs");
        Page<NoiDung> page = noiDungService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/noi-dungs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /noi-dungs/:id : get the "id" noiDung.
     *
     * @param id the id of the noiDung to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the noiDung, or with status 404 (Not Found)
     */
    @GetMapping("/noi-dungs/{id}")
    public ResponseEntity<NoiDung> getNoiDung(@PathVariable Long id) {
        log.debug("REST request to get NoiDung : {}", id);
        Optional<NoiDung> noiDung = noiDungService.findOne(id);
        return ResponseUtil.wrapOrNotFound(noiDung);
    }

    /**
     * DELETE  /noi-dungs/:id : delete the "id" noiDung.
     *
     * @param id the id of the noiDung to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/noi-dungs/{id}")
    public ResponseEntity<Void> deleteNoiDung(@PathVariable Long id) {
        log.debug("REST request to delete NoiDung : {}", id);
        noiDungService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
