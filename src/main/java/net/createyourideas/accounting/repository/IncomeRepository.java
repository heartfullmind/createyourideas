package net.createyourideas.accounting.repository;

import net.createyourideas.accounting.domain.Income;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Income entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    Page<Income> findAllByIdeaId(Long ideaId, Pageable pageable);

    Page<Income> findAllByIdeaIdAndDate(Long id, LocalDate date, Pageable pageable);

    List<Income> findAllByIdeaId(Long ideaId);

    List<Income> findAllByIdeaIdAndDate(Long id, LocalDate date);
}
