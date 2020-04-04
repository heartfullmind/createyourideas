package net.createyourideas.accounting.web.rest;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.createyourideas.accounting.domain.Idea;
import net.createyourideas.accounting.domain.Income;
import net.createyourideas.accounting.domain.Outgoings;
import net.createyourideas.accounting.service.CalcService;
import net.createyourideas.accounting.service.IdeaService;
import net.createyourideas.accounting.service.IncomeService;
import net.createyourideas.accounting.service.OutgoingsService;

@RestController
@RequestMapping("/api")
public class CalculationsManager {

	private Idea idea;

    private final IdeaService ideaService;
    private final IncomeService incomeService;
    private final OutgoingsService outgoingsService;
    private final CalcService calcService;


    public CalculationsManager(IdeaService ideaService, IncomeService incomeService, OutgoingsService outgoingsService, CalcService calcService) {
        this.ideaService = ideaService;
        this.incomeService = incomeService;
        this.outgoingsService = outgoingsService;
        this.calcService = calcService;
    }

    @GetMapping("/calculation/income")
    public Float calculateTotalIncome() {
    	Float sum = 0.0f;
    	return sum;
    }

    @GetMapping("/calculation/{id}/dailyBalance")
    public Float calculateDailyBalance(@PathVariable Long id, Pageable pageable) {
        return calcService.getDailyBalance(id, pageable);
    }


    @GetMapping("/calculation/outgoings")
    public Float calculateTotalOutgoings() {
    	Float sum = 0.0f;
    	return sum;
    }

    @GetMapping("/calculation/{id}/profit")
    public Float calculateTotalProfit(@PathVariable Long id, Pageable pageable) {
        Float sumIncome = 0.0f;
        Float sumOutgoings = 0.0f;
        Idea idea = this.ideaService.findOne(id).get();

        List<Income> incomes = incomeService.findAllByIdeaId(id, pageable).getContent();
        for(Income income : incomes) {
            sumIncome += income.getValue();
        }
        List<Outgoings> outgoingsAll = outgoingsService.findAllByIdeaId(id, pageable).getContent();
        for(Outgoings outgoings : outgoingsAll) {
            sumOutgoings += outgoings.getValue();
        }
        return (idea.getInvestment() + sumIncome - sumOutgoings);
    }

    @GetMapping("/calculation/{id}/interest")
    public Float calculateTotalInterest(@PathVariable Long id, Pageable pageable) {
        Float sumIncome = 0.0f;
        Float sumOutgoings = 0.0f;
        Idea idea = this.ideaService.findOne(id).get();
        List<Income> incomes = incomeService.findAllByIdeaId(id, pageable).getContent();
        for(Income income : incomes) {
            sumIncome += income.getValue();
        }
        List<Outgoings> outgoingsAll = outgoingsService.findAllByIdeaId(id, pageable).getContent();
        for(Outgoings outgoings : outgoingsAll) {
            sumOutgoings += outgoings.getValue();
        }
        List<Idea> ideas = this.ideaService.findAll(pageable).getContent();
        Float sum = 0.0f;
        for(Idea i: ideas) {
            if(i.getIdea() != null && i.getIdea().getId() == id) {
                sum = idea.getInterest() * this.calculateTotalProfit(i.getIdea().getId(), pageable);

            }

        }

        return sum;
    }


    @PostMapping("/calculation/setIdea")
    public void setIdea(@RequestBody Idea idea) {
    	this.idea = idea;
    }

    @GetMapping("/calculation/current")
    public Idea getIdea() {
    	return idea;
    }
}
