package net.createyourideas.accounting.web.rest;

import net.createyourideas.accounting.domain.Idea;
import net.createyourideas.accounting.service.IdeaAdditionService;
import net.createyourideas.accounting.service.IdeaService;
import net.createyourideas.accounting.tree.Node;
import net.createyourideas.accounting.tree.TreeUtils;

import io.github.jhipster.web.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for managing {@link net.createyourideas.accounting.domain.Idea}.
 */
@RestController
@RequestMapping("/api")
public class IdeaFunnelResource {

    private final Logger log = LoggerFactory.getLogger(IdeaResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IdeaService ideaService;

    private final IdeaAdditionService ideaAdditionService;

    public IdeaFunnelResource(IdeaService ideaService,IdeaAdditionService ideaAdditionService) {
        this.ideaService = ideaService;
        this.ideaAdditionService = ideaAdditionService;
    }

    @GetMapping("/ideas/user")
    public ResponseEntity<List<Idea>> getAllIdeasByCurrentUser(Pageable pageable) {
        log.debug("REST request to get a page of Ideas");
        Page<Idea> page = ideaAdditionService.findByUserIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/ideas/ideafunnel")
    public ResponseEntity<String> getIdeafunnel(Pageable pageable) {
        log.debug("REST get ideafunnel");
        String json = "";
        Page<Idea> ideasPage = ideaService.findAll(pageable);
        List<Idea> ideas = ideasPage.getContent();
        List<Node> nodes = new ArrayList<>();
        try {
            for(Idea idea : ideas) {
                if(idea.getIdea() == null)
                    nodes.add(new Node(idea.getTitle(), idea.getInterest(), idea.getDistribution(), idea.getInvestment(), idea.getIdeatype().toString(), idea.getId().toString(), null));
                else
                    nodes.add(new Node(idea.getTitle(), idea.getInterest(), idea.getDistribution(), idea.getInvestment(), idea.getIdeatype().toString(), idea.getId().toString(), idea.getIdea().getId().toString()));
            }
            json = "{\n" +
                        "\"format\":\"nodeTree\",\n" +
                        "\"data\": \n" +
                        TreeUtils.createTree(nodes) + "\n" +
                    "}";
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return ResponseEntity.ok(json);
    }

    @GetMapping("/ideas/{id}/allById")
    public ResponseEntity<List<Idea>> getAllIdeasById(@PathVariable Long id, Pageable pageable) {
        log.debug("REST request to get a page of Ideas by id");
        Page<Idea> page = ideaAdditionService.findAllById(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
