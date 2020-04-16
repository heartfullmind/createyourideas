package net.createyourideas.accounting.service.impl;

import net.createyourideas.accounting.service.ProfitBalanceService;
import net.createyourideas.accounting.domain.ProfitBalance;
import net.createyourideas.accounting.repository.ProfitBalanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProfitBalance}.
 */
@Service
@Transactional
public class ProfitBalanceServiceImpl implements ProfitBalanceService {

    private final Logger log = LoggerFactory.getLogger(ProfitBalanceServiceImpl.class);

    private final ProfitBalanceRepository profitBalanceRepository;

    public ProfitBalanceServiceImpl(ProfitBalanceRepository profitBalanceRepository) {
        this.profitBalanceRepository = profitBalanceRepository;
    }

    /**
     * Save a profitBalance.
     *
     * @param profitBalance the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProfitBalance save(ProfitBalance profitBalance) {
        log.debug("Request to save ProfitBalance : {}", profitBalance);
        return profitBalanceRepository.save(profitBalance);
    }

    /**
     * Get all the profitBalances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProfitBalance> findAll(Pageable pageable) {
        log.debug("Request to get all ProfitBalances");
        return profitBalanceRepository.findAll(pageable);
    }


    /**
     * Get one profitBalance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProfitBalance> findOne(Long id) {
        log.debug("Request to get ProfitBalance : {}", id);
        return profitBalanceRepository.findById(id);
    }

    /**
     * Delete the profitBalance by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProfitBalance : {}", id);
        profitBalanceRepository.deleteById(id);
    }
}
