package net.createyourideas.accounting.web.rest;

import net.createyourideas.accounting.domain.Income;
import net.createyourideas.accounting.service.IncomeService;
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

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link net.createyourideas.accounting.domain.Income}.
 */
@RestController
@RequestMapping("/api")
public class IncomeResource {

    private final Logger log = LoggerFactory.getLogger(IncomeResource.class);

    private static final String ENTITY_NAME = "income";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IncomeService incomeService;

    public IncomeResource(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    /**
     * {@code POST  /incomes} : Create a new income.
     *
     * @param income the income to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new income, or with status {@code 400 (Bad Request)} if the income has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/incomes")
    public ResponseEntity<Income> createIncome(@Valid @RequestBody Income income) throws URISyntaxException {
        log.debug("REST request to save Income : {}", income);
        if (income.getId() != null) {
            throw new BadRequestAlertException("A new income cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Income result = incomeService.save(income);
        return ResponseEntity.created(new URI("/api/incomes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /incomes} : Updates an existing income.
     *
     * @param income the income to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated income,
     * or with status {@code 400 (Bad Request)} if the income is not valid,
     * or with status {@code 500 (Internal Server Error)} if the income couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/incomes")
    public ResponseEntity<Income> updateIncome(@Valid @RequestBody Income income) throws URISyntaxException {
        log.debug("REST request to update Income : {}", income);
        if (income.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Income result = incomeService.save(income);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, income.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /incomes} : get all the incomes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of incomes in body.
     */
    @GetMapping("/incomes")
    public ResponseEntity<List<Income>> getAllIncomes(Pageable pageable) {
        log.debug("REST request to get a page of Incomes");
        Page<Income> page = incomeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /incomes/:id} : get the "id" income.
     *
     * @param id the id of the income to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the income, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/incomes/{id}")
    public ResponseEntity<Income> getIncome(@PathVariable Long id) {
        log.debug("REST request to get Income : {}", id);
        Optional<Income> income = incomeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(income);
    }

    /**
     * {@code DELETE  /incomes/:id} : delete the "id" income.
     *
     * @param id the id of the income to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/incomes/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id) {
        log.debug("REST request to delete Income : {}", id);
        incomeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/incomes/{id}/allByIdeaId")
    public ResponseEntity<List<Income>> getAllIncomesByIdeaId(@PathVariable Long id, Pageable pageable) {
        log.debug("REST request to get a page of Incomes by ideaId");
        Page<Income> page = incomeService.findAllByIdeaId(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/incomes/{date}")
    public ResponseEntity<List<Income>> getAllIncomesByDate(@PathVariable Date date, Pageable pageable) {
        log.debug("REST request to get a page of Incomes with given date");
        Page<Income> page = incomeService.findAllByDate(date, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/incomes/{id}/{date}")
    public ResponseEntity<List<Income>> getAllIncomesByDateAndIdeaId(@PathVariable LocalDate date, @PathVariable Long id, Pageable pageable) {
        log.debug("REST request to get a page of Incomes with given date");
        Page<Income> page = incomeService.findAllByDateAndIdeaId(date, id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
