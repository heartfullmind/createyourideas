package net.createyourideas.accounting.web.rest;

import net.createyourideas.accounting.domain.Balance;
import net.createyourideas.accounting.domain.Idea;
import net.createyourideas.accounting.service.BalanceService;
import net.createyourideas.accounting.service.CalcService;
import net.createyourideas.accounting.service.IdeaAdditionService;
import net.createyourideas.accounting.service.IdeaService;
import net.createyourideas.accounting.tree.TreeUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


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
            balanceService.save(balance);
        }
        for(Idea idea : ideas) {
            Balance balance = balanceService.findOneByIdeaIdAndDate(idea.getId(), LocalDate.now());
            Float profit = this.calcService.getProfitFromNode(idea.getId(), balance);
            balance.setProfit(profit);
            Float profitToSpend = this.calcService.getProfitToSpend(idea.getId(), balance);
            balance.setProfitToSpend(profitToSpend);
            Float netProfit = this.calcService.getNetProfit(idea.getId(), balance);
            balance.setNetProfit(netProfit);
            balanceService.save(balance);
        }

        Balance balance = balanceService.findOneByIdeaIdAndDate(1l, LocalDate.now());
        Float collectionRoot = this.calcService.getCollectionFromRoot(balance);
        List<Balance> balances = balanceService.findAll();
        for(Balance b : balances) {
            if(b.getId() == 1l) {
                b.profitToSpend(0f);
                balanceService.save(b);
            } else {
                b.setNetProfit(b.getNetProfit() + collectionRoot);
                balanceService.save(b);
            }
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
