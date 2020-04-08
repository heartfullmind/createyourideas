package net.createyourideas.accounting.web.rest;

import net.createyourideas.accounting.domain.Outgoings;
import net.createyourideas.accounting.service.OutgoingsService;
import net.createyourideas.accounting.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link net.createyourideas.accounting.domain.Outgoings}.
 */
@RestController
@RequestMapping("/api")
public class OutgoingsResource {

    private final Logger log = LoggerFactory.getLogger(OutgoingsResource.class);

    private static final String ENTITY_NAME = "outgoings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OutgoingsService outgoingsService;

    public OutgoingsResource(OutgoingsService outgoingsService) {
        this.outgoingsService = outgoingsService;
    }

    /**
     * {@code POST  /outgoings} : Create a new outgoings.
     *
     * @param outgoings the outgoings to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new outgoings, or with status {@code 400 (Bad Request)} if the outgoings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/outgoings")
    public ResponseEntity<Outgoings> createOutgoings(@Valid @RequestBody Outgoings outgoings) throws URISyntaxException {
        log.debug("REST request to save Outgoings : {}", outgoings);
        if (outgoings.getId() != null) {
            throw new BadRequestAlertException("A new outgoings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Outgoings result = outgoingsService.save(outgoings);
        return ResponseEntity.created(new URI("/api/outgoings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /outgoings} : Updates an existing outgoings.
     *
     * @param outgoings the outgoings to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated outgoings,
     * or with status {@code 400 (Bad Request)} if the outgoings is not valid,
     * or with status {@code 500 (Internal Server Error)} if the outgoings couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/outgoings")
    public ResponseEntity<Outgoings> updateOutgoings(@Valid @RequestBody Outgoings outgoings) throws URISyntaxException {
        log.debug("REST request to update Outgoings : {}", outgoings);
        if (outgoings.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Outgoings result = outgoingsService.save(outgoings);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, outgoings.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /outgoings} : get all the outgoings.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of outgoings in body.
     */
    @GetMapping("/outgoings")
    public ResponseEntity<List<Outgoings>> getAllOutgoings(Pageable pageable) {
        log.debug("REST request to get a page of Outgoings");
        Page<Outgoings> page = outgoingsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /outgoings/:id} : get the "id" outgoings.
     *
     * @param id the id of the outgoings to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the outgoings, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/outgoings/{id}")
    public ResponseEntity<Outgoings> getOutgoings(@PathVariable Long id) {
        log.debug("REST request to get Outgoings : {}", id);
        Optional<Outgoings> outgoings = outgoingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(outgoings);
    }

    /**
     * {@code DELETE  /outgoings/:id} : delete the "id" outgoings.
     *
     * @param id the id of the outgoings to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/outgoings/{id}")
    public ResponseEntity<Void> deleteOutgoings(@PathVariable Long id) {
        log.debug("REST request to delete Outgoings : {}", id);
        outgoingsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/outgoings/{id}/allByIdeaId")
    public ResponseEntity<List<Outgoings>> getAllOutgoingsByIdeaId(@PathVariable Long id, Pageable pageable) {
        log.debug("REST request to get a page of Outgoings by ideaId");
        Page<Outgoings> page = outgoingsService.findAllByIdeaId(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/outgoings/{date}")
    public ResponseEntity<List<Outgoings>> getAllOutgoingsByDate(@PathVariable Date date, Pageable pageable) {
        log.debug("REST request to get a page of Outgoings by a given date.");
        Page<Outgoings> page = outgoingsService.findAllByDate(date, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/outgoings/{id}/{date}")
    public ResponseEntity<List<Outgoings>> getAllOutgoingsByDateAndIdeaId(@PathVariable Date date, @PathVariable Long id, Pageable pageable) {
        log.debug("REST request to get a page of Outgoings by a given date.");
        Page<Outgoings> page = outgoingsService.findAllByDate(date, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
