package net.createyourideas.accounting.service.impl;

import net.createyourideas.accounting.service.WorksheetService;
import net.createyourideas.accounting.domain.Worksheet;
import net.createyourideas.accounting.repository.WorksheetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Worksheet}.
 */
@Service
@Transactional
public class WorksheetServiceImpl implements WorksheetService {

    private final Logger log = LoggerFactory.getLogger(WorksheetServiceImpl.class);

    private final WorksheetRepository worksheetRepository;

    public WorksheetServiceImpl(WorksheetRepository worksheetRepository) {
        this.worksheetRepository = worksheetRepository;
    }

    /**
     * Save a worksheet.
     *
     * @param worksheet the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Worksheet save(Worksheet worksheet) {
        log.debug("Request to save Worksheet : {}", worksheet);
        return worksheetRepository.save(worksheet);
    }

    /**
     * Get all the worksheets.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Worksheet> findAll() {
        log.debug("Request to get all Worksheets");
        return worksheetRepository.findAll();
    }

    /**
     * Get one worksheet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Worksheet> findOne(Long id) {
        log.debug("Request to get Worksheet : {}", id);
        return worksheetRepository.findById(id);
    }

    /**
     * Delete the worksheet by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Worksheet : {}", id);
        worksheetRepository.deleteById(id);
    }

    @Override
    public Page<Worksheet> findAllByIdeaId(Long ideaId, Pageable pageable) {
        return worksheetRepository.findAllByIdeaId(ideaId, pageable);
    }
}
