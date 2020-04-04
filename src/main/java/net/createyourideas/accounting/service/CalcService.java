package net.createyourideas.accounting.service;

import org.springframework.data.domain.Pageable;

public interface CalcService {

    Float getDailyBalance(Long id, Pageable pageable);

}
