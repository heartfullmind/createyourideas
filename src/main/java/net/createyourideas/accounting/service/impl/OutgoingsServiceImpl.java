package net.createyourideas.accounting.service.impl;

import net.createyourideas.accounting.service.OutgoingsService;
import net.createyourideas.accounting.domain.Outgoings;
import net.createyourideas.accounting.repository.OutgoingsRepository;
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
 * Service Implementation for managing {@link Outgoings}.
 */
@Service
@Transactional
public class OutgoingsServiceImpl implements OutgoingsService {

    private final Logger log = LoggerFactory.getLogger(OutgoingsServiceImpl.class);

    private final OutgoingsRepository outgoingsRepository;

    public OutgoingsServiceImpl(OutgoingsRepository outgoingsRepository) {
        this.outgoingsRepository = outgoingsRepository;
    }

    /**
     * Save a outgoings.
     *
     * @param outgoings the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Outgoings save(Outgoings outgoings) {
        log.debug("Request to save Outgoings : {}", outgoings);
        return outgoingsRepository.save(outgoings);
    }

    /**
     * Get all the outgoings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Outgoings> findAll(Pageable pageable) {
        log.debug("Request to get all Outgoings");
        return outgoingsRepository.findAll(pageable);
    }

    /**
     * Get one outgoings by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Outgoings> findOne(Long id) {
        log.debug("Request to get Outgoings : {}", id);
        return outgoingsRepository.findById(id);
    }

    /**
     * Delete the outgoings by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Outgoings : {}", id);
        outgoingsRepository.deleteById(id);
    }

    @Override
    public Page<Outgoings> findAllByIdeaId(Long ideaId, Pageable pageable) {
        return outgoingsRepository.findAllByIdeaId(ideaId, pageable);
    }

    @Override
    public Page<Outgoings> findAllByDate(Date date, Pageable pageable) {
        return outgoingsRepository.findAllByDate(date, pageable);
    }

    @Override
    public Page<Outgoings> findAllByDateAndIdeaId(LocalDate date, Long ideaId, Pageable pageable) {
        return outgoingsRepository.findAllByDateAndIdeaId(date, ideaId, pageable);
    }
}
