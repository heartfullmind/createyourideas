package net.createyourideas.accounting.service.impl;

import net.createyourideas.accounting.service.IncomeService;
import net.createyourideas.accounting.domain.Income;
import net.createyourideas.accounting.repository.IncomeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Income}.
 */
@Service
@Transactional
public class IncomeServiceImpl implements IncomeService {

    private final Logger log = LoggerFactory.getLogger(IncomeServiceImpl.class);

    private final IncomeRepository incomeRepository;

    public IncomeServiceImpl(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    /**
     * Save a income.
     *
     * @param income the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Income save(Income income) {
        log.debug("Request to save Income : {}", income);
        return incomeRepository.save(income);
    }

    /**
     * Get all the incomes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Income> findAll(Pageable pageable) {
        log.debug("Request to get all Incomes");
        return incomeRepository.findAll(pageable);
    }

    /**
     * Get one income by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Income> findOne(Long id) {
        log.debug("Request to get Income : {}", id);
        return incomeRepository.findById(id);
    }

    /**
     * Delete the income by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Income : {}", id);
        incomeRepository.deleteById(id);
    }

    @Override
    public Page<Income> findAllByIdeaId(Long ideaId, Pageable pageable) {
        return incomeRepository.findAllByIdeaId(ideaId, pageable);
    }

    @Override
    public Page<Income> findAllByDate(Date date, Pageable pageable) {
        return incomeRepository.findAllByDate(date, pageable);
    }

    @Override
    public Page<Income> findAllByDateAndIdeaId(LocalDate date, Long ideaId, Pageable pageable) {
        return incomeRepository.findAllByDateAndIdeaId(date, ideaId, pageable);
    }
}
