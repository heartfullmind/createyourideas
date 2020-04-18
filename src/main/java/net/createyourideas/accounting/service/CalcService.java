package net.createyourideas.accounting.service;

import net.createyourideas.accounting.tree.Node;

public interface CalcService {

    Float getDailyBalance(Long id);

    Float getProfitFromRoot();

    Float getProfitFromNode(Long id);

    Float getProfitFromNode(Node node);

    Float getProfitToSpend(Long id);

    Float getNetProfit(Long id);
}
