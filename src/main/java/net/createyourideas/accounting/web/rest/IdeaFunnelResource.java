package net.createyourideas.accounting.web.rest;

import net.createyourideas.accounting.domain.Balance;
import net.createyourideas.accounting.domain.Idea;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


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

    public IdeaFunnelResource(IdeaService ideaService, IdeaAdditionService ideaAdditionService, CalcService calcService) {
        this.ideaService = ideaService;
        this.ideaAdditionService = ideaAdditionService;
        this.calcService = calcService;
        this.loadNodes();
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
        json = getIdeaFunnelJSON();
        return ResponseEntity.ok(json);
    }

    @GetMapping("/ideas/{id}/allById")
    public ResponseEntity<List<Idea>> getAllIdeasById(@PathVariable Long id, Pageable pageable) {
        log.debug("REST request to get a page of Ideas by id");
        Page<Idea> page = ideaAdditionService.findAllById(id, pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/ideas/calculateProfit")
    public ResponseEntity<String> calculateProfitFromButton() {
        this.calculateProfit();
        String json = "";
        json = getIdeaFunnelJSON();
        return ResponseEntity.ok(json);
    }

    private void loadNodes() {
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

    private void calculateProfit() {

        List<Idea> ideas = ideaService.findAll();

        for(Idea idea : ideas) {
            Balance balance = new Balance();
            LocalDate ld = LocalDate.now();
            balance.setDate(ld);
            Float dailyBalance = this.calcService.getDailyBalance(idea.getId());
            balance.setDailyBalance(dailyBalance);
            balance.setIdea(idea);
            Set<Balance> balances = idea.getBalances();
            balances.add(balance);

        }
        for(Idea idea : ideas) {
            Iterator<Balance> iterator = idea.getBalances().iterator();
            Balance balance = null;
            while(iterator.hasNext()) {
                balance = iterator.next();
                LocalDate now = LocalDate.now();
                if(balance.getDate() == now) {
                    break;
                }
            }
            Float profit = this.calcService.getProfitFromNode(idea.getId());
            balance.setProfit(profit);
            Float profitToSpend = this.calcService.getProfitToSpend(idea.getId());
            balance.setProfitToSpend(profitToSpend);
            Float netProfit = this.calcService.getNetProfit(idea.getId());
            balance.setNetProfit(netProfit);
            Float collectionRoot = this.calcService.getCollectionFromRoot();
            balance.setNetProfit(balance.getNetProfit() + collectionRoot);
            ideaService.save(idea);
        }
    }


    private String getIdeaFunnelJSON() {
        log.debug("REST get ideafunnel");
        String json = "";
        json = "{\n" + "\"format\":\"nodeTree\",\n" + "\"data\": \n" + TreeUtils.getIdeaFunnelJSON() + "\n" + "}";
        return json;
    }

    @Scheduled(cron = "0 57 23 * * ?")
    public void scheduleTaskCalculateProfit() {
        this.calculateProfit();
    }
}
