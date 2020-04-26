package net.createyourideas.accounting.repository;

import net.createyourideas.accounting.domain.Balance;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Balance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {

    Page<Balance> findAllByIdeaId(Long ideaId, Pageable pageable);

    List<Balance> findAllByIdeaId(Long ideaId);

    List<Balance> findAll();

    Balance findOneByIdeaIdAndDate(Long ideaId, LocalDate date);
}
