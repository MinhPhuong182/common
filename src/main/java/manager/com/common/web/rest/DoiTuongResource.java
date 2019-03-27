package manager.com.common.web.rest;
import manager.com.common.domain.DoiTuong;
import manager.com.common.service.DoiTuongService;
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
 * REST controller for managing DoiTuong.
 */
@RestController
@RequestMapping("/api")
public class DoiTuongResource {

    private final Logger log = LoggerFactory.getLogger(DoiTuongResource.class);

    private static final String ENTITY_NAME = "commonDoiTuong";

    private final DoiTuongService doiTuongService;

    public DoiTuongResource(DoiTuongService doiTuongService) {
        this.doiTuongService = doiTuongService;
    }

    /**
     * POST  /doi-tuongs : Create a new doiTuong.
     *
     * @param doiTuong the doiTuong to create
     * @return the ResponseEntity with status 201 (Created) and with body the new doiTuong, or with status 400 (Bad Request) if the doiTuong has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/doi-tuongs")
    public ResponseEntity<DoiTuong> createDoiTuong(@Valid @RequestBody DoiTuong doiTuong) throws URISyntaxException {
        log.debug("REST request to save DoiTuong : {}", doiTuong);
        if (doiTuong.getId() != null) {
            throw new BadRequestAlertException("A new doiTuong cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DoiTuong result = doiTuongService.save(doiTuong);
        return ResponseEntity.created(new URI("/api/doi-tuongs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /doi-tuongs : Updates an existing doiTuong.
     *
     * @param doiTuong the doiTuong to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated doiTuong,
     * or with status 400 (Bad Request) if the doiTuong is not valid,
     * or with status 500 (Internal Server Error) if the doiTuong couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/doi-tuongs")
    public ResponseEntity<DoiTuong> updateDoiTuong(@Valid @RequestBody DoiTuong doiTuong) throws URISyntaxException {
        log.debug("REST request to update DoiTuong : {}", doiTuong);
        if (doiTuong.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DoiTuong result = doiTuongService.save(doiTuong);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, doiTuong.getId().toString()))
            .body(result);
    }

    /**
     * GET  /doi-tuongs : get all the doiTuongs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of doiTuongs in body
     */
    @GetMapping("/doi-tuongs")
    public ResponseEntity<List<DoiTuong>> getAllDoiTuongs(Pageable pageable) {
        log.debug("REST request to get a page of DoiTuongs");
        Page<DoiTuong> page = doiTuongService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/doi-tuongs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /doi-tuongs/:id : get the "id" doiTuong.
     *
     * @param id the id of the doiTuong to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the doiTuong, or with status 404 (Not Found)
     */
    @GetMapping("/doi-tuongs/{id}")
    public ResponseEntity<DoiTuong> getDoiTuong(@PathVariable Long id) {
        log.debug("REST request to get DoiTuong : {}", id);
        Optional<DoiTuong> doiTuong = doiTuongService.findOne(id);
        return ResponseUtil.wrapOrNotFound(doiTuong);
    }

    /**
     * DELETE  /doi-tuongs/:id : delete the "id" doiTuong.
     *
     * @param id the id of the doiTuong to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/doi-tuongs/{id}")
    public ResponseEntity<Void> deleteDoiTuong(@PathVariable Long id) {
        log.debug("REST request to delete DoiTuong : {}", id);
        doiTuongService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
