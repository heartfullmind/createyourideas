package net.createyourideas.accounting.service.impl;

import net.createyourideas.accounting.service.IdeaAdditionService;
import net.createyourideas.accounting.service.IdeaService;
import net.createyourideas.accounting.tree.Node;
import net.createyourideas.accounting.tree.TreeUtils;
import net.createyourideas.accounting.domain.Idea;
import net.createyourideas.accounting.repository.IdeaRepository;

import java.util.ArrayList;
import java.util.List;

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

    private final IdeaRepository ideaRepository;

    private final IdeaService ideaService;

    public IdeaAdditionServiceImpl(IdeaRepository ideaRepository, IdeaService ideaService) {
        this.ideaRepository = ideaRepository;
        this.ideaService = ideaService;
        this.loadNodes();
    }

    @Override
    public Page<Idea> findByUserIsCurrentUser(Pageable pageable) {
        return ideaRepository.findByUserIsCurrentUser(pageable);
    }

    @Override
    public Page<Idea> findAllById(Long id, Pageable pageable) {
        return ideaRepository.findAllById(id, pageable);
    }


    public void loadNodes() {
        List<Node> nodes = new ArrayList<>();
        List<Idea> ideas = ideaService.findAll();
        for (Idea idea : ideas) {
                if (idea.getIdea() == null) {
                    nodes.add(new Node(idea.getId().toString(), null, idea));
                } else {
                    nodes.add(new Node(idea.getId().toString(), idea.getIdea().getId().toString(), idea));
                }
        }
        TreeUtils.createTree(nodes);
    }

}
