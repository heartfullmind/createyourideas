package net.createyourideas.accounting.web.rest;

import net.createyourideas.accounting.domain.Idea;
import net.createyourideas.accounting.domain.User;
import net.createyourideas.accounting.service.IdeaService;
import net.createyourideas.accounting.service.UserService;
import net.createyourideas.accounting.tree.Pair;
import net.createyourideas.accounting.tree.Node;
import net.createyourideas.accounting.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing {@link net.createyourideas.accounting.domain.Idea}.
 */
@RestController
@RequestMapping("/api")
public class IdeaResource {

    private final Logger log = LoggerFactory.getLogger(IdeaResource.class);

    private static final String ENTITY_NAME = "idea";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IdeaService ideaService;

    private final UserService userService;

    public IdeaResource(IdeaService ideaService, UserService userService) {
        this.ideaService = ideaService;
        this.userService = userService;
    }

    /**
     * {@code POST  /ideas} : Create a new idea.
     *
     * @param idea the idea to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new idea, or with status {@code 400 (Bad Request)} if the idea has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ideas")
    public ResponseEntity<Idea> createIdea(@RequestBody Idea idea) throws URISyntaxException {
        log.debug("REST request to save Idea : {}", idea);
        if (idea.getId() != null) {
            throw new BadRequestAlertException("A new idea cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Optional<User> userOpt = userService.getUserWithAuthorities();
        idea.setUser(userOpt.get());
        Idea result = ideaService.save(idea);
        return ResponseEntity.created(new URI("/api/ideas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ideas} : Updates an existing idea.
     *
     * @param idea the idea to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated idea,
     * or with status {@code 400 (Bad Request)} if the idea is not valid,
     * or with status {@code 500 (Internal Server Error)} if the idea couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ideas")
    public ResponseEntity<Idea> updateIdea(@RequestBody Idea idea) throws URISyntaxException {
        log.debug("REST request to update Idea : {}", idea);
        if (idea.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Idea result = ideaService.save(idea);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, idea.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ideas} : get all the ideas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ideas in body.
     */
    @GetMapping("/ideas")
    public ResponseEntity<List<Idea>> getAllIdeas(Pageable pageable) {
        log.debug("REST request to get a page of Ideas");
        Page<Idea> page = ideaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/ideas/user")
    public ResponseEntity<List<Idea>> getAllIdeasByCurrentUser(Pageable pageable) {
        log.debug("REST request to get a page of Ideas");
        Page<Idea> page = ideaService.findByUserIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/ideas/ideafunnel")
    public ResponseEntity<String> getIdeafunnel() {
        log.debug("REST get ideafunnel");
        String json = "";
        List<Idea> ideas = ideaService.findAll();
        List<Node> nodes = new ArrayList<>();
        try {
            for(Idea idea : ideas) {
                if(idea.getIdea() == null)
                    nodes.add(new Node(idea.getTitle(), idea.getId().toString(), null));
                else
                    nodes.add(new Node(idea.getTitle(), idea.getId().toString(), idea.getIdea().getId().toString()));
            }
            json = "{\n" + 
                        "\"format\":\"nodeTree\",\n" + 
                        "\"data\": \n" +
                            createTree(nodes) + "\n" + 
                    "}";
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return ResponseEntity.ok(json);
    }

    /**
     * {@code GET  /ideas/:id} : get the "id" idea.
     *
     * @param id the id of the idea to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the idea, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ideas/{id}")
    public ResponseEntity<Idea> getIdea(@PathVariable Long id) {
        log.debug("REST request to get Idea : {}", id);
        Optional<Idea> idea = ideaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(idea);
    }

    /**
     * {@code DELETE  /ideas/:id} : delete the "id" idea.
     *
     * @param id the id of the idea to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ideas/{id}")
    public ResponseEntity<Void> deleteIdea(@PathVariable Long id) {
        log.debug("REST request to delete Idea : {}", id);
        ideaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    //Private method to create the ideafunnel-tree (node-tree)
    private static String createTree(List<Node> nodes) {
 
        Map<String, Node> mapTmp = new HashMap<>();
        
        //Save all nodes to a map
        for (Node current : nodes) {
            mapTmp.put(current.getId(), current);
        }
 
        //loop and assign parent/child relationships
        for (Node current : nodes) {
            String parentId = current.getParentId();
 
            if (parentId != null) {
                Node parent = mapTmp.get(parentId);
                if (parent != null) {
                    current.setParent(parent);
                    parent.addChild(current);
                    mapTmp.put(parentId, parent);
                    mapTmp.put(current.getId(), current);
                }
            }
 
        }
 
    
        //get the root
        Node root = null;
        for (Node node : mapTmp.values()) {
            if(node.getParent() == null) {
                root = node;
                break;
            }
        }
 
        return root.toString();
    }
}
