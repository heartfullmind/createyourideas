package net.createyourideas.accounting.service;

import net.createyourideas.accounting.domain.Balance;

public interface CalcService {

    Float getDailyBalance(Long id);

    Float getProfitFromNode(Long id, Balance balance);

    Float getProfitToSpend(Long id, Balance balance);

    Float getNetProfit(Long id, Balance balance);

    Float getCollectionFromRoot(Balance balance);
}
