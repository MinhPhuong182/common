package manager.com.common.web.rest;
import manager.com.common.domain.NhomDanhMuc;
import manager.com.common.service.NhomDanhMucService;
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
 * REST controller for managing NhomDanhMuc.
 */
@RestController
@RequestMapping("/api")
public class NhomDanhMucResource {

    private final Logger log = LoggerFactory.getLogger(NhomDanhMucResource.class);

    private static final String ENTITY_NAME = "commonNhomDanhMuc";

    private final NhomDanhMucService nhomDanhMucService;

    public NhomDanhMucResource(NhomDanhMucService nhomDanhMucService) {
        this.nhomDanhMucService = nhomDanhMucService;
    }

    /**
     * POST  /nhom-danh-mucs : Create a new nhomDanhMuc.
     *
     * @param nhomDanhMuc the nhomDanhMuc to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nhomDanhMuc, or with status 400 (Bad Request) if the nhomDanhMuc has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nhom-danh-mucs")
    public ResponseEntity<NhomDanhMuc> createNhomDanhMuc(@Valid @RequestBody NhomDanhMuc nhomDanhMuc) throws URISyntaxException {
        log.debug("REST request to save NhomDanhMuc : {}", nhomDanhMuc);
        if (nhomDanhMuc.getId() != null) {
            throw new BadRequestAlertException("A new nhomDanhMuc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NhomDanhMuc result = nhomDanhMucService.save(nhomDanhMuc);
        return ResponseEntity.created(new URI("/api/nhom-danh-mucs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nhom-danh-mucs : Updates an existing nhomDanhMuc.
     *
     * @param nhomDanhMuc the nhomDanhMuc to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nhomDanhMuc,
     * or with status 400 (Bad Request) if the nhomDanhMuc is not valid,
     * or with status 500 (Internal Server Error) if the nhomDanhMuc couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nhom-danh-mucs")
    public ResponseEntity<NhomDanhMuc> updateNhomDanhMuc(@Valid @RequestBody NhomDanhMuc nhomDanhMuc) throws URISyntaxException {
        log.debug("REST request to update NhomDanhMuc : {}", nhomDanhMuc);
        if (nhomDanhMuc.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NhomDanhMuc result = nhomDanhMucService.save(nhomDanhMuc);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nhomDanhMuc.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nhom-danh-mucs : get all the nhomDanhMucs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of nhomDanhMucs in body
     */
    @GetMapping("/nhom-danh-mucs")
    public ResponseEntity<List<NhomDanhMuc>> getAllNhomDanhMucs(Pageable pageable) {
        log.debug("REST request to get a page of NhomDanhMucs");
        Page<NhomDanhMuc> page = nhomDanhMucService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/nhom-danh-mucs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /nhom-danh-mucs/:id : get the "id" nhomDanhMuc.
     *
     * @param id the id of the nhomDanhMuc to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nhomDanhMuc, or with status 404 (Not Found)
     */
    @GetMapping("/nhom-danh-mucs/{id}")
    public ResponseEntity<NhomDanhMuc> getNhomDanhMuc(@PathVariable Long id) {
        log.debug("REST request to get NhomDanhMuc : {}", id);
        Optional<NhomDanhMuc> nhomDanhMuc = nhomDanhMucService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nhomDanhMuc);
    }

    /**
     * DELETE  /nhom-danh-mucs/:id : delete the "id" nhomDanhMuc.
     *
     * @param id the id of the nhomDanhMuc to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nhom-danh-mucs/{id}")
    public ResponseEntity<Void> deleteNhomDanhMuc(@PathVariable Long id) {
        log.debug("REST request to delete NhomDanhMuc : {}", id);
        nhomDanhMucService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
