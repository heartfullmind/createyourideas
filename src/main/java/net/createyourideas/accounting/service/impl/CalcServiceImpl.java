package net.createyourideas.accounting.service.impl;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.createyourideas.accounting.domain.Income;
import net.createyourideas.accounting.domain.Outgoings;
import net.createyourideas.accounting.repository.IncomeRepository;
import net.createyourideas.accounting.repository.OutgoingsRepository;
import net.createyourideas.accounting.service.CalcService;
import net.createyourideas.accounting.tree.Node;
import net.createyourideas.accounting.tree.TreeUtils;


/**
 * Service Implementation for managing financeservice.
 */
@Service
@Transactional
public class CalcServiceImpl implements CalcService {


    private final IncomeRepository incomeRepository;
    private final OutgoingsRepository outgoingsRepository;

    public CalcServiceImpl(IncomeRepository incomeRepository, OutgoingsRepository outgoingsRepository) {
        this.incomeRepository = incomeRepository;
        this.outgoingsRepository = outgoingsRepository;
    }

    @Override
    public Float getDailyBalance(Long id) {

        List<Income> incomes = incomeRepository.findAllByIdeaId(id);
        List<Outgoings> outgoings = outgoingsRepository.findAllByIdeaId(id);
        Float totalIncomes = 0F;
        Float totalOutgoings = 0F;
        for (Income i : incomes) {
            totalIncomes += i.getValue();
        }
        for (Outgoings o : outgoings) {
            totalOutgoings += o.getValue();
        }
        return totalIncomes - totalOutgoings;
    }

    public Float getProfitFromRoot() {
        Node root = TreeUtils.getRoot();
        List<Node> childrenOfRoot = TreeUtils.getAllChild(root.getId());
        Float profit = 0f;
        for(Node child : childrenOfRoot) {
            profit += (this.getDailyBalance(Long.parseLong(child.getId())) * root.getInterest());
        }
        return profit;
    }

    public Float getProfitFromNode(Long id) {
        Node node = TreeUtils.getNode(id);
        List<Node> childrenOfNode = TreeUtils.getAllChild(node.getId());
        Float profit = 0f;
        for(Node child : childrenOfNode) {
            profit += child.getDailyBalance() * node.getInterest();
        }
        return profit;
    }

}
