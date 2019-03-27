package manager.com.common.web.rest;
import manager.com.common.domain.PhamVi;
import manager.com.common.service.PhamViService;
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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing PhamVi.
 */
@RestController
@RequestMapping("/api")
public class PhamViResource {

    private final Logger log = LoggerFactory.getLogger(PhamViResource.class);

    private static final String ENTITY_NAME = "commonPhamVi";

    private final PhamViService phamViService;

    public PhamViResource(PhamViService phamViService) {
        this.phamViService = phamViService;
    }

    /**
     * POST  /pham-vis : Create a new phamVi.
     *
     * @param phamVi the phamVi to create
     * @return the ResponseEntity with status 201 (Created) and with body the new phamVi, or with status 400 (Bad Request) if the phamVi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pham-vis")
    public ResponseEntity<PhamVi> createPhamVi(@Valid @RequestBody PhamVi phamVi) throws URISyntaxException {
        log.debug("REST request to save PhamVi : {}", phamVi);
        if (phamVi.getId() != null) {
            throw new BadRequestAlertException("A new phamVi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PhamVi result = phamViService.save(phamVi);
        return ResponseEntity.created(new URI("/api/pham-vis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pham-vis : Updates an existing phamVi.
     *
     * @param phamVi the phamVi to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated phamVi,
     * or with status 400 (Bad Request) if the phamVi is not valid,
     * or with status 500 (Internal Server Error) if the phamVi couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pham-vis")
    public ResponseEntity<PhamVi> updatePhamVi(@Valid @RequestBody PhamVi phamVi) throws URISyntaxException {
        log.debug("REST request to update PhamVi : {}", phamVi);
        if (phamVi.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PhamVi result = phamViService.save(phamVi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, phamVi.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pham-vis : get all the phamVis.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of phamVis in body
     */
    @GetMapping("/pham-vis")
    public ResponseEntity<List<PhamVi>> getAllPhamVis(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("donvi-is-null".equals(filter)) {
            log.debug("REST request to get all PhamVis where donvi is null");
            return new ResponseEntity<>(phamViService.findAllWhereDonviIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of PhamVis");
        Page<PhamVi> page = phamViService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pham-vis");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /pham-vis/:id : get the "id" phamVi.
     *
     * @param id the id of the phamVi to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the phamVi, or with status 404 (Not Found)
     */
    @GetMapping("/pham-vis/{id}")
    public ResponseEntity<PhamVi> getPhamVi(@PathVariable Long id) {
        log.debug("REST request to get PhamVi : {}", id);
        Optional<PhamVi> phamVi = phamViService.findOne(id);
        return ResponseUtil.wrapOrNotFound(phamVi);
    }

    /**
     * DELETE  /pham-vis/:id : delete the "id" phamVi.
     *
     * @param id the id of the phamVi to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pham-vis/{id}")
    public ResponseEntity<Void> deletePhamVi(@PathVariable Long id) {
        log.debug("REST request to delete PhamVi : {}", id);
        phamViService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
