package manager.com.common.web.rest;
import manager.com.common.domain.NhomPhanLoai;
import manager.com.common.service.NhomPhanLoaiService;
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
 * REST controller for managing NhomPhanLoai.
 */
@RestController
@RequestMapping("/api")
public class NhomPhanLoaiResource {

    private final Logger log = LoggerFactory.getLogger(NhomPhanLoaiResource.class);

    private static final String ENTITY_NAME = "commonNhomPhanLoai";

    private final NhomPhanLoaiService nhomPhanLoaiService;

    public NhomPhanLoaiResource(NhomPhanLoaiService nhomPhanLoaiService) {
        this.nhomPhanLoaiService = nhomPhanLoaiService;
    }

    /**
     * POST  /nhom-phan-loais : Create a new nhomPhanLoai.
     *
     * @param nhomPhanLoai the nhomPhanLoai to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nhomPhanLoai, or with status 400 (Bad Request) if the nhomPhanLoai has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nhom-phan-loais")
    public ResponseEntity<NhomPhanLoai> createNhomPhanLoai(@Valid @RequestBody NhomPhanLoai nhomPhanLoai) throws URISyntaxException {
        log.debug("REST request to save NhomPhanLoai : {}", nhomPhanLoai);
        if (nhomPhanLoai.getId() != null) {
            throw new BadRequestAlertException("A new nhomPhanLoai cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NhomPhanLoai result = nhomPhanLoaiService.save(nhomPhanLoai);
        return ResponseEntity.created(new URI("/api/nhom-phan-loais/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nhom-phan-loais : Updates an existing nhomPhanLoai.
     *
     * @param nhomPhanLoai the nhomPhanLoai to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nhomPhanLoai,
     * or with status 400 (Bad Request) if the nhomPhanLoai is not valid,
     * or with status 500 (Internal Server Error) if the nhomPhanLoai couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nhom-phan-loais")
    public ResponseEntity<NhomPhanLoai> updateNhomPhanLoai(@Valid @RequestBody NhomPhanLoai nhomPhanLoai) throws URISyntaxException {
        log.debug("REST request to update NhomPhanLoai : {}", nhomPhanLoai);
        if (nhomPhanLoai.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NhomPhanLoai result = nhomPhanLoaiService.save(nhomPhanLoai);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nhomPhanLoai.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nhom-phan-loais : get all the nhomPhanLoais.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of nhomPhanLoais in body
     */
    @GetMapping("/nhom-phan-loais")
    public ResponseEntity<List<NhomPhanLoai>> getAllNhomPhanLoais(Pageable pageable) {
        log.debug("REST request to get a page of NhomPhanLoais");
        Page<NhomPhanLoai> page = nhomPhanLoaiService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/nhom-phan-loais");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /nhom-phan-loais/:id : get the "id" nhomPhanLoai.
     *
     * @param id the id of the nhomPhanLoai to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nhomPhanLoai, or with status 404 (Not Found)
     */
    @GetMapping("/nhom-phan-loais/{id}")
    public ResponseEntity<NhomPhanLoai> getNhomPhanLoai(@PathVariable Long id) {
        log.debug("REST request to get NhomPhanLoai : {}", id);
        Optional<NhomPhanLoai> nhomPhanLoai = nhomPhanLoaiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nhomPhanLoai);
    }

    /**
     * DELETE  /nhom-phan-loais/:id : delete the "id" nhomPhanLoai.
     *
     * @param id the id of the nhomPhanLoai to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nhom-phan-loais/{id}")
    public ResponseEntity<Void> deleteNhomPhanLoai(@PathVariable Long id) {
        log.debug("REST request to delete NhomPhanLoai : {}", id);
        nhomPhanLoaiService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
