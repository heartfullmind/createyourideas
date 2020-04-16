package net.createyourideas.accounting.service;

public interface CalcService {

    Float getDailyBalance(Long id);

    Float getProfitFromRoot();

    Float getProfitFromNode(Long id);
}
