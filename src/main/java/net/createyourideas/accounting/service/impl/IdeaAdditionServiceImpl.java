package net.createyourideas.accounting.service.impl;

import net.createyourideas.accounting.service.IdeaAdditionService;
import net.createyourideas.accounting.domain.Idea;
import net.createyourideas.accounting.repository.IdeaRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Idea}.
 */
@Service
@Transactional
public class IdeaAdditionServiceImpl implements IdeaAdditionService {

    private final Logger log = LoggerFactory.getLogger(IdeaServiceImpl.class);

    private final IdeaRepository ideaRepository;

    public IdeaAdditionServiceImpl(IdeaRepository ideaRepository) {
        this.ideaRepository = ideaRepository;
    }

    @Override
    public Page<Idea> findByUserIsCurrentUser(Pageable pageable) {
        return ideaRepository.findByUserIsCurrentUser(pageable);
    }

    @Override
    public Page<Idea> findAllById(Long id, Pageable pageable) {
        return ideaRepository.findAllById(id, pageable);
    }

}
