package net.createyourideas.accounting.service.impl;

import net.createyourideas.accounting.service.IdeaService;
import net.createyourideas.accounting.domain.Idea;
import net.createyourideas.accounting.repository.IdeaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Idea}.
 */
@Service
@Transactional
public class IdeaServiceImpl implements IdeaService {

    private final Logger log = LoggerFactory.getLogger(IdeaServiceImpl.class);

    private final IdeaRepository ideaRepository;

    public IdeaServiceImpl(IdeaRepository ideaRepository) {
        this.ideaRepository = ideaRepository;
    }

    /**
     * Save a idea.
     *
     * @param idea the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Idea save(Idea idea) {
        log.debug("Request to save Idea : {}", idea);
        return ideaRepository.save(idea);
    }

    /**
     * Get all the ideas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Idea> findAll(Pageable pageable) {
        log.debug("Request to get all Ideas");
        return ideaRepository.findAll(pageable);
    }


    /**
     * Get one idea by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Idea> findOne(Long id) {
        log.debug("Request to get Idea : {}", id);
        return ideaRepository.findById(id);
    }

    public Optional<Idea> findOneByNodeId(Long nodeId) {
        return ideaRepository.findOneByNodeId(nodeId);
    }

    /**
     * Delete the idea by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Idea : {}", id);
        ideaRepository.deleteById(id);
    }
}
