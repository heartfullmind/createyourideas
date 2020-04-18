package net.createyourideas.accounting.service;

public interface CalcService {

    Float getDailyBalance(Long id);

    Float getProfitFromNode(Long id);

    Float getProfitToSpend(Long id);

    Float getNetProfit(Long id);

    Float getCollectionFromRoot();
}
