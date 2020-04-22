package net.createyourideas.accounting.web.rest;

import net.createyourideas.accounting.domain.Balance;
import net.createyourideas.accounting.domain.Idea;
import net.createyourideas.accounting.service.BalanceService;
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

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IdeaService ideaService;

    private final IdeaAdditionService ideaAdditionService;

    private final CalcService calcService;

    private final BalanceService balanceService;

    public IdeaFunnelResource(IdeaService ideaService, IdeaAdditionService ideaAdditionService, CalcService calcService, BalanceService balanceService) {
        this.ideaService = ideaService;
        this.ideaAdditionService = ideaAdditionService;
        this.calcService = calcService;
        this.balanceService = balanceService;
    }

    @GetMapping("/ideas/ideafunnel")
    public ResponseEntity<String> getIdeafunnel(Pageable pageable) {
        log.debug("REST get ideafunnel");
        ideaAdditionService.loadNodes();
        String json = "";
        json = getIdeaFunnelJSON();
        return ResponseEntity.ok(json);
    }

    @GetMapping("/ideas/calculateProfit")
    public ResponseEntity<String> calculateProfitFromButton() {
        this.calculateProfit();
        String json = "";
        json = getIdeaFunnelJSON();
        return ResponseEntity.ok(json);
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
            balanceService.save(balance);

        }
        Integer count = 0;
        for(Idea idea : ideas) {
            /*
            count++;
            Iterator<Balance> iterator = idea.getBalances().iterator();
            Balance balance = null;
            while(iterator.hasNext()) {
                balance = iterator.next();
                LocalDate now = LocalDate.now();
                if(balance.getDate() == now) {
                    break;
                }
            }
            */
            Balance balance = balanceService.findOneByIdeaIdAndDate(idea.getId(), LocalDate.now());
            Float profit = this.calcService.getProfitFromNode(idea.getId(), balance);
            balance.setProfit(profit);
            Float profitToSpend = this.calcService.getProfitToSpend(idea.getId(), balance);
            balance.setProfitToSpend(profitToSpend);
            Float netProfit = this.calcService.getNetProfit(idea.getId(), balance);
            balance.setNetProfit(netProfit);
            //Float collectionRoot = this.calcService.getCollectionFromRoot(balance);
            /*if(idea.getId() == 1 && count == ideas.size()) {
                Idea root = this.ideaService.findOne(1l).get();
                Iterator<Balance> iteratorRoot = root.getBalances().iterator();
                Balance balanceRoot = null;
                while(iteratorRoot.hasNext()) {
                    balanceRoot = iteratorRoot.next();
                    LocalDate now = LocalDate.now();
                    if(balance.getDate() == now) {
                        break;
                    }
                }
                balanceRoot.setProfitToSpend(0f);
            } else {
                balance.setNetProfit(balance.getNetProfit() + collectionRoot);
            } */


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
