package net.createyourideas.accounting.web.rest;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.createyourideas.accounting.service.CalcService;

@RestController
@RequestMapping("/api")
public class CalculationsManager {

    private final CalcService calcService;


    public CalculationsManager(CalcService calcService) {
        this.calcService = calcService;
    }

    @GetMapping("/calculation/{id}/dailyBalance")
    public Float calculateDailyBalance(@PathVariable Long id, Pageable pageable) {
        return this.calcService.getDailyBalance(id);
    }

    @GetMapping("/calculation/profitFromRoot")
    public Float calculateProfitFromRoot(Pageable pageable) {
        return this.calcService.getProfitFromRoot();
    }

    @GetMapping("/calculation/{id}/profitFromNode")
    public Float getProfitFromNode(@PathVariable Long id, Pageable pageable) {
        return this.calcService.getProfitFromNode(id);
    }
}
