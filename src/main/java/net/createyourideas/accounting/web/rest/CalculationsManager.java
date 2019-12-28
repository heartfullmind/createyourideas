package net.createyourideas.accounting.web.rest;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.createyourideas.accounting.domain.Idea;
import net.createyourideas.accounting.domain.Income;
import net.createyourideas.accounting.domain.Outgoings;
import net.createyourideas.accounting.service.IdeaService;

@RestController
@RequestMapping("/api")
public class CalculationsManager {
	
	private Idea idea;

    private final IdeaService ideaService;


    public CalculationsManager(IdeaService ideaService) {
        this.ideaService = ideaService;
    }
    
    @GetMapping("/calculation/income")
    public Float calculateTotalIncome() {
    	Float sum = 0.0f;
    	return sum;
    }
    
    
    @GetMapping("/calculation/outgoings")
    public Float calculateTotalOutgoings() {
    	Float sum = 0.0f;
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
