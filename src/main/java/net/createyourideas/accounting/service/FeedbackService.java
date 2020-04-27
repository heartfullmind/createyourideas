package net.createyourideas.accounting.service;

import net.createyourideas.accounting.domain.Feedback;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Feedback}.
 */
public interface FeedbackService {

    /**
     * Save a feedback.
     *
     * @param feedback the entity to save.
     * @return the persisted entity.
     */
    Feedback save(Feedback feedback);

    /**
     * Get all the feedbacks.
     *
     * @return the list of entities.
     */
    List<Feedback> findAll();


    /**
     * Get the "id" feedback.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Feedback> findOne(Long id);

    /**
     * Delete the "id" feedback.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
