package net.createyourideas.accounting.service.impl;

import net.createyourideas.accounting.service.BalanceService;
import net.createyourideas.accounting.domain.Balance;
import net.createyourideas.accounting.repository.BalanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Balance}.
 */
@Service
@Transactional
public class BalanceServiceImpl implements BalanceService {

    private final Logger log = LoggerFactory.getLogger(BalanceServiceImpl.class);

    private final BalanceRepository balanceRepository;

    public BalanceServiceImpl(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    /**
     * Save a balance.
     *
     * @param balance the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Balance save(Balance balance) {
        log.debug("Request to save Balance : {}", balance);
        return balanceRepository.save(balance);
    }

    /**
     * Get all the balances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Balance> findAll(Pageable pageable) {
        log.debug("Request to get all Balances");
        return balanceRepository.findAll(pageable);
    }

    /**
     * Get one balance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Balance> findOne(Long id) {
        log.debug("Request to get Balance : {}", id);
        return balanceRepository.findById(id);
    }

    /**
     * Delete the balance by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Balance : {}", id);
        balanceRepository.deleteById(id);
    }
    @Override
    public List<Balance> findAllByIdeaId(Long ideaId) {
        log.debug("Request to get all Balances from specific idea");
        return balanceRepository.findAllByIdeaId(ideaId);
    }
    @Override
    public Page<Balance> findAllByIdeaId(Long ideaId, Pageable pageable) {
        log.debug("Request to get all Balances from specific idea");
        return balanceRepository.findAllByIdeaId(ideaId, pageable);
    }

    public Balance findOneByIdeaIdAndDate(Long ideaId, LocalDate date) {
        log.debug("Request to get one Balances from specific idea and date");
        return balanceRepository.findOneByIdeaIdAndDate(ideaId, date);
    }
}
