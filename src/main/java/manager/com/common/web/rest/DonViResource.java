package manager.com.common.web.rest;
import manager.com.common.domain.DonVi;
import manager.com.common.service.DonViService;
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
 * REST controller for managing DonVi.
 */
@RestController
@RequestMapping("/api")
public class DonViResource {

    private final Logger log = LoggerFactory.getLogger(DonViResource.class);

    private static final String ENTITY_NAME = "commonDonVi";

    private final DonViService donViService;

    public DonViResource(DonViService donViService) {
        this.donViService = donViService;
    }

    /**
     * POST  /don-vis : Create a new donVi.
     *
     * @param donVi the donVi to create
     * @return the ResponseEntity with status 201 (Created) and with body the new donVi, or with status 400 (Bad Request) if the donVi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/don-vis")
    public ResponseEntity<DonVi> createDonVi(@Valid @RequestBody DonVi donVi) throws URISyntaxException {
        log.debug("REST request to save DonVi : {}", donVi);
        if (donVi.getId() != null) {
            throw new BadRequestAlertException("A new donVi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DonVi result = donViService.save(donVi);
        return ResponseEntity.created(new URI("/api/don-vis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /don-vis : Updates an existing donVi.
     *
     * @param donVi the donVi to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated donVi,
     * or with status 400 (Bad Request) if the donVi is not valid,
     * or with status 500 (Internal Server Error) if the donVi couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/don-vis")
    public ResponseEntity<DonVi> updateDonVi(@Valid @RequestBody DonVi donVi) throws URISyntaxException {
        log.debug("REST request to update DonVi : {}", donVi);
        if (donVi.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DonVi result = donViService.save(donVi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, donVi.getId().toString()))
            .body(result);
    }

    /**
     * GET  /don-vis : get all the donVis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of donVis in body
     */
    @GetMapping("/don-vis")
    public ResponseEntity<List<DonVi>> getAllDonVis(Pageable pageable) {
        log.debug("REST request to get a page of DonVis");
        Page<DonVi> page = donViService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/don-vis");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /don-vis/:id : get the "id" donVi.
     *
     * @param id the id of the donVi to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the donVi, or with status 404 (Not Found)
     */
    @GetMapping("/don-vis/{id}")
    public ResponseEntity<DonVi> getDonVi(@PathVariable Long id) {
        log.debug("REST request to get DonVi : {}", id);
        Optional<DonVi> donVi = donViService.findOne(id);
        return ResponseUtil.wrapOrNotFound(donVi);
    }

    /**
     * DELETE  /don-vis/:id : delete the "id" donVi.
     *
     * @param id the id of the donVi to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/don-vis/{id}")
    public ResponseEntity<Void> deleteDonVi(@PathVariable Long id) {
        log.debug("REST request to delete DonVi : {}", id);
        donViService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
