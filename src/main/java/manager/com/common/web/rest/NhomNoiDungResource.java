package manager.com.common.web.rest;
import manager.com.common.domain.NhomNoiDung;
import manager.com.common.service.NhomNoiDungService;
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
 * REST controller for managing NhomNoiDung.
 */
@RestController
@RequestMapping("/api")
public class NhomNoiDungResource {

    private final Logger log = LoggerFactory.getLogger(NhomNoiDungResource.class);

    private static final String ENTITY_NAME = "commonNhomNoiDung";

    private final NhomNoiDungService nhomNoiDungService;

    public NhomNoiDungResource(NhomNoiDungService nhomNoiDungService) {
        this.nhomNoiDungService = nhomNoiDungService;
    }

    /**
     * POST  /nhom-noi-dungs : Create a new nhomNoiDung.
     *
     * @param nhomNoiDung the nhomNoiDung to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nhomNoiDung, or with status 400 (Bad Request) if the nhomNoiDung has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nhom-noi-dungs")
    public ResponseEntity<NhomNoiDung> createNhomNoiDung(@Valid @RequestBody NhomNoiDung nhomNoiDung) throws URISyntaxException {
        log.debug("REST request to save NhomNoiDung : {}", nhomNoiDung);
        if (nhomNoiDung.getId() != null) {
            throw new BadRequestAlertException("A new nhomNoiDung cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NhomNoiDung result = nhomNoiDungService.save(nhomNoiDung);
        return ResponseEntity.created(new URI("/api/nhom-noi-dungs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nhom-noi-dungs : Updates an existing nhomNoiDung.
     *
     * @param nhomNoiDung the nhomNoiDung to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nhomNoiDung,
     * or with status 400 (Bad Request) if the nhomNoiDung is not valid,
     * or with status 500 (Internal Server Error) if the nhomNoiDung couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nhom-noi-dungs")
    public ResponseEntity<NhomNoiDung> updateNhomNoiDung(@Valid @RequestBody NhomNoiDung nhomNoiDung) throws URISyntaxException {
        log.debug("REST request to update NhomNoiDung : {}", nhomNoiDung);
        if (nhomNoiDung.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NhomNoiDung result = nhomNoiDungService.save(nhomNoiDung);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nhomNoiDung.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nhom-noi-dungs : get all the nhomNoiDungs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of nhomNoiDungs in body
     */
    @GetMapping("/nhom-noi-dungs")
    public ResponseEntity<List<NhomNoiDung>> getAllNhomNoiDungs(Pageable pageable) {
        log.debug("REST request to get a page of NhomNoiDungs");
        Page<NhomNoiDung> page = nhomNoiDungService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/nhom-noi-dungs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /nhom-noi-dungs/:id : get the "id" nhomNoiDung.
     *
     * @param id the id of the nhomNoiDung to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nhomNoiDung, or with status 404 (Not Found)
     */
    @GetMapping("/nhom-noi-dungs/{id}")
    public ResponseEntity<NhomNoiDung> getNhomNoiDung(@PathVariable Long id) {
        log.debug("REST request to get NhomNoiDung : {}", id);
        Optional<NhomNoiDung> nhomNoiDung = nhomNoiDungService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nhomNoiDung);
    }

    /**
     * DELETE  /nhom-noi-dungs/:id : delete the "id" nhomNoiDung.
     *
     * @param id the id of the nhomNoiDung to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nhom-noi-dungs/{id}")
    public ResponseEntity<Void> deleteNhomNoiDung(@PathVariable Long id) {
        log.debug("REST request to delete NhomNoiDung : {}", id);
        nhomNoiDungService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
