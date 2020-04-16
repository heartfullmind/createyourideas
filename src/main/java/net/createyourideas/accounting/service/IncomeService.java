package net.createyourideas.accounting.service;

import net.createyourideas.accounting.domain.Income;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Service Interface for managing {@link Income}.
 */
public interface IncomeService {

    /**
     * Save a income.
     *
     * @param income the entity to save.
     * @return the persisted entity.
     */
    Income save(Income income);

    /**
     * Get all the incomes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Income> findAll(Pageable pageable);


    /**
     * Get the "id" income.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Income> findOne(Long id);

    /**
     * Delete the "id" income.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Page<Income> findAllByIdeaId(Long ideaId, Pageable pageable);

    Page<Income> findAllByIdeaIdAndDate(Long ideaId, LocalDate date, Pageable pageable);
}
