package net.createyourideas.accounting.web.rest;

import net.createyourideas.accounting.domain.Worksheet;
import net.createyourideas.accounting.service.WorksheetService;
import net.createyourideas.accounting.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link net.createyourideas.accounting.domain.Worksheet}.
 */
@RestController
@RequestMapping("/api")
public class WorksheetResource {

    private final Logger log = LoggerFactory.getLogger(WorksheetResource.class);

    private static final String ENTITY_NAME = "worksheet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorksheetService worksheetService;

    public WorksheetResource(WorksheetService worksheetService) {
        this.worksheetService = worksheetService;
    }

    /**
     * {@code POST  /worksheets} : Create a new worksheet.
     *
     * @param worksheet the worksheet to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new worksheet, or with status {@code 400 (Bad Request)} if the worksheet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/worksheets")
    public ResponseEntity<Worksheet> createWorksheet(@Valid @RequestBody Worksheet worksheet) throws URISyntaxException {
        log.debug("REST request to save Worksheet : {}", worksheet);
        if (worksheet.getId() != null) {
            throw new BadRequestAlertException("A new worksheet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Worksheet result = worksheetService.save(worksheet);
        return ResponseEntity.created(new URI("/api/worksheets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /worksheets} : Updates an existing worksheet.
     *
     * @param worksheet the worksheet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated worksheet,
     * or with status {@code 400 (Bad Request)} if the worksheet is not valid,
     * or with status {@code 500 (Internal Server Error)} if the worksheet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/worksheets")
    public ResponseEntity<Worksheet> updateWorksheet(@Valid @RequestBody Worksheet worksheet) throws URISyntaxException {
        log.debug("REST request to update Worksheet : {}", worksheet);
        if (worksheet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Worksheet result = worksheetService.save(worksheet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, worksheet.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /worksheets} : get all the worksheets.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of worksheets in body.
     */
    @GetMapping("/worksheets")
    public List<Worksheet> getAllWorksheets() {
        log.debug("REST request to get all Worksheets");
        return worksheetService.findAll();
    }

    /**
     * {@code GET  /worksheets/:id} : get the "id" worksheet.
     *
     * @param id the id of the worksheet to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the worksheet, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/worksheets/{id}")
    public ResponseEntity<Worksheet> getWorksheet(@PathVariable Long id) {
        log.debug("REST request to get Worksheet : {}", id);
        Optional<Worksheet> worksheet = worksheetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(worksheet);
    }

    /**
     * {@code DELETE  /worksheets/:id} : delete the "id" worksheet.
     *
     * @param id the id of the worksheet to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/worksheets/{id}")
    public ResponseEntity<Void> deleteWorksheet(@PathVariable Long id) {
        log.debug("REST request to delete Worksheet : {}", id);
        worksheetService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
