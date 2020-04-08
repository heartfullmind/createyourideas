package net.createyourideas.accounting.service;

import java.time.LocalDate;

import org.springframework.data.domain.Pageable;

public interface CalcService {

    Float getDailyBalance(Long id, Pageable pageable);

    Float getDailyBalancePerDate(Long id, LocalDate date, Pageable pageable);

}
