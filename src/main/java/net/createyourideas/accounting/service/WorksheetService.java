package net.createyourideas.accounting.service;

import net.createyourideas.accounting.domain.Idea;
import net.createyourideas.accounting.domain.Worksheet;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Worksheet}.
 */
public interface WorksheetService {

    /**
     * Save a worksheet.
     *
     * @param worksheet the entity to save.
     * @return the persisted entity.
     */
    Worksheet save(Worksheet worksheet);

    /**
     * Get all the worksheets.
     *
     * @return the list of entities.
     */
    List<Worksheet> findAll();


    /**
     * Get the "id" worksheet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Worksheet> findOne(Long id);

    /**
     * Delete the "id" worksheet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    List<Worksheet> findByUserIsCurrentUser();
}
