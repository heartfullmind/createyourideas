package net.createyourideas.accounting.service;

import net.createyourideas.accounting.domain.ProfitBalance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ProfitBalance}.
 */
public interface ProfitBalanceService {

    /**
     * Save a profitBalance.
     *
     * @param profitBalance the entity to save.
     * @return the persisted entity.
     */
    ProfitBalance save(ProfitBalance profitBalance);

    /**
     * Get all the profitBalances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProfitBalance> findAll(Pageable pageable);


    /**
     * Get the "id" profitBalance.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProfitBalance> findOne(Long id);

    /**
     * Delete the "id" profitBalance.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
