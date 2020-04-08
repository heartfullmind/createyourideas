package net.createyourideas.accounting.service;

import net.createyourideas.accounting.domain.Outgoings;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

/**
 * Service Interface for managing {@link Outgoings}.
 */
public interface OutgoingsService {

    /**
     * Save a outgoings.
     *
     * @param outgoings the entity to save.
     * @return the persisted entity.
     */
    Outgoings save(Outgoings outgoings);

    /**
     * Get all the outgoings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Outgoings> findAll(Pageable pageable);


    /**
     * Get the "id" outgoings.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Outgoings> findOne(Long id);

    /**
     * Delete the "id" outgoings.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Page<Outgoings> findAllByIdeaId(Long ideaId, Pageable pageable);

    Page<Outgoings> findAllByDate(Date date, Pageable pageable);

    Page<Outgoings> findAllByDateAndIdeaId(LocalDate date, Long ideaId, Pageable pageable);
}
