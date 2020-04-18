package net.createyourideas.accounting.service.impl;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.createyourideas.accounting.domain.Balance;
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

    public Float getProfitFromNode(Long id) {
        Node node = TreeUtils.getNode(id);
        List<Node> childrenOfNode = TreeUtils.getAllChild(node.getId());
        Float profit = 0f;
        for(Node child : childrenOfNode) {
            Iterator<Balance> iterator = child.getIdea().getBalances().iterator();
            Balance balance = null;
            while(iterator.hasNext()) {
                balance = iterator.next();
                LocalDate now = LocalDate.now();
                if(balance.getDate() == now) {
                    break;
                }
            }
            profit +=  balance.getDailyBalance() * node.getIdea().getInterest();
        }
        return profit;
    }

    @Override
    public Float getProfitToSpend(Long id) {
        Node node = TreeUtils.getNode(id);
        Iterator<Balance> iterator = node.getIdea().getBalances().iterator();
        Balance balance = null;
        while(iterator.hasNext()) {
            balance = iterator.next();
            LocalDate now = LocalDate.now();
            if(balance.getDate() == now) {
                break;
            }
        }
        return 0.75f * balance.getProfit();
    }

    @Override
    public Float getNetProfit(Long id) {
        Node node = TreeUtils.getNode(id);
        Iterator<Balance> iterator = node.getIdea().getBalances().iterator();
        Balance balance = null;
        while(iterator.hasNext()) {
            balance = iterator.next();
            LocalDate now = LocalDate.now();
            if(balance.getDate() == now) {
                break;
            }
        }
        return balance.getProfit() - balance.getProfitToSpend();
    }

    @Override
    public Float getCollectionFromRoot() {
        Node root = TreeUtils.getRoot();
        List<Node> children = root.getChildren();
        Iterator<Balance> iterator = root.getIdea().getBalances().iterator();
        Balance balance = null;
        while(iterator.hasNext()) {
            balance = iterator.next();
            LocalDate now = LocalDate.now();
            if(balance.getDate() == now) {
                break;
            }
        }
        Float collection = balance.getProfitToSpend() / children.size();
        return collection;
    }

}
