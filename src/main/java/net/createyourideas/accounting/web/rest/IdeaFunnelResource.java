package net.createyourideas.accounting.web.rest;

import net.createyourideas.accounting.domain.Idea;
import net.createyourideas.accounting.domain.ProfitBalance;
import net.createyourideas.accounting.service.CalcService;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * REST controller for managing
 * {@link net.createyourideas.accounting.domain.Idea}.
 */
@RestController
@RequestMapping("/api")
public class IdeaFunnelResource {

    private final Logger log = LoggerFactory.getLogger(IdeaResource.class);

    List<Node> nodes = new ArrayList<>();

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IdeaService ideaService;

    private final IdeaAdditionService ideaAdditionService;

    private final CalcService calcService;

    public IdeaFunnelResource(IdeaService ideaService, IdeaAdditionService ideaAdditionService,
            CalcService calcService) {
        this.ideaService = ideaService;
        this.ideaAdditionService = ideaAdditionService;
        this.calcService = calcService;
    }

    @GetMapping("/ideas/user")
    public ResponseEntity<List<Idea>> getAllIdeasByCurrentUser(Pageable pageable) {
        log.debug("REST request to get a page of Ideas");
        Page<Idea> page = ideaAdditionService.findByUserIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/ideas/ideafunnel")
    public ResponseEntity<String> getIdeafunnel(Pageable pageable) {
        log.debug("REST get ideafunnel");
        String json = "";
        json = getIdeafunnelJSON();
        return ResponseEntity.ok(json);
    }

    private String getIdeafunnelJSON() {
        log.debug("REST get ideafunnel");
        String json = "";
        List<Idea> ideas = ideaService.findAll();

        try {
            for (Idea idea : ideas) {
                String image = "hallo";// DatatypeConverter.printBase64Binary(idea.getLogo());
                if (idea.getIdea() == null)
                    nodes.add(new Node(idea.getId().toString(), null, idea.getTitle(), idea.getInterest(),
                            idea.getDistribution(), idea.getInvestment(), idea.getIdeatype().toString(),
                            idea.getDescription(), image, idea.isActive(), idea.getLogoContentType()));
                else
                    nodes.add(new Node(idea.getId().toString(), idea.getIdea().getId().toString(), idea.getTitle(),
                            idea.getInterest(), idea.getDistribution(), idea.getInvestment(),
                            idea.getIdeatype().toString(), idea.getDescription(), image, idea.isActive(),
                            idea.getLogoContentType()));
            }
            json = "{\n" + "\"format\":\"nodeTree\",\n" + "\"data\": \n" + TreeUtils.createTree(nodes) + "\n" + "}";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return json;
    }

    @GetMapping("/ideas/{id}/allById")
    public ResponseEntity<List<Idea>> getAllIdeasById(@PathVariable Long id, Pageable pageable) {
        log.debug("REST request to get a page of Ideas by id");
        Page<Idea> page = ideaAdditionService.findAllById(id, pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @Scheduled(cron = "0 5 * * * ?")
    public void scheduleTaskCalculateProfit() {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = null;
        try {
            actualObj = mapper.readTree(getIdeafunnelJSON());
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.debug(actualObj.toString());

        /*
        for(Idea idea : ideas) {
            Float dailyBalance = this.calcService.getDailyBalance(idea.getId());

            log.debug("dailyBalance from idea " + idea.getId() + " - " + dailyBalance);
            List<Node> children = TreeUtils.getAllChild(idea.getId().toString());
            for(Node node: children){

            }
        }
        */
    }
}
