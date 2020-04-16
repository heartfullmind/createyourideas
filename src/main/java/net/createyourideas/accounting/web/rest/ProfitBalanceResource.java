package net.createyourideas.accounting.web.rest;

import net.createyourideas.accounting.domain.ProfitBalance;
import net.createyourideas.accounting.service.ProfitBalanceService;
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
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link net.createyourideas.accounting.domain.ProfitBalance}.
 */
@RestController
@RequestMapping("/api")
public class ProfitBalanceResource {

    private final Logger log = LoggerFactory.getLogger(ProfitBalanceResource.class);

    private static final String ENTITY_NAME = "profitBalance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfitBalanceService profitBalanceService;

    public ProfitBalanceResource(ProfitBalanceService profitBalanceService) {
        this.profitBalanceService = profitBalanceService;
    }

    /**
     * {@code POST  /profit-balances} : Create a new profitBalance.
     *
     * @param profitBalance the profitBalance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new profitBalance, or with status {@code 400 (Bad Request)} if the profitBalance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/profit-balances")
    public ResponseEntity<ProfitBalance> createProfitBalance(@RequestBody ProfitBalance profitBalance) throws URISyntaxException {
        log.debug("REST request to save ProfitBalance : {}", profitBalance);
        if (profitBalance.getId() != null) {
            throw new BadRequestAlertException("A new profitBalance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfitBalance result = profitBalanceService.save(profitBalance);
        return ResponseEntity.created(new URI("/api/profit-balances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /profit-balances} : Updates an existing profitBalance.
     *
     * @param profitBalance the profitBalance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profitBalance,
     * or with status {@code 400 (Bad Request)} if the profitBalance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the profitBalance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/profit-balances")
    public ResponseEntity<ProfitBalance> updateProfitBalance(@RequestBody ProfitBalance profitBalance) throws URISyntaxException {
        log.debug("REST request to update ProfitBalance : {}", profitBalance);
        if (profitBalance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProfitBalance result = profitBalanceService.save(profitBalance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, profitBalance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /profit-balances} : get all the profitBalances.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of profitBalances in body.
     */
    @GetMapping("/profit-balances")
    public ResponseEntity<List<ProfitBalance>> getAllProfitBalances(Pageable pageable) {
        log.debug("REST request to get a page of ProfitBalances");
        Page<ProfitBalance> page = profitBalanceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /profit-balances/:id} : get the "id" profitBalance.
     *
     * @param id the id of the profitBalance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the profitBalance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/profit-balances/{id}")
    public ResponseEntity<ProfitBalance> getProfitBalance(@PathVariable Long id) {
        log.debug("REST request to get ProfitBalance : {}", id);
        Optional<ProfitBalance> profitBalance = profitBalanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(profitBalance);
    }

    /**
     * {@code DELETE  /profit-balances/:id} : delete the "id" profitBalance.
     *
     * @param id the id of the profitBalance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/profit-balances/{id}")
    public ResponseEntity<Void> deleteProfitBalance(@PathVariable Long id) {
        log.debug("REST request to delete ProfitBalance : {}", id);
        profitBalanceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
