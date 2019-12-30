package net.createyourideas.accounting.service;

import net.createyourideas.accounting.domain.Idea;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Idea}.
 */
public interface IdeaService {

    /**
     * Save a idea.
     *
     * @param idea the entity to save.
     * @return the persisted entity.
     */
    Idea save(Idea idea);

    /**
     * Get all the ideas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Idea> findAll(Pageable pageable);


    /**
     * Get the "id" idea.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Idea> findOne(Long id);

    /**
     * Delete the "id" idea.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Page<Idea> findByUserIsCurrentUser(Pageable pageable);

}
