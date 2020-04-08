package net.createyourideas.accounting.repository;

import net.createyourideas.accounting.domain.Outgoings;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Outgoings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OutgoingsRepository extends JpaRepository<Outgoings, Long> {

    Page<Outgoings> findAllByIdeaId(Long ideaId, Pageable pageable);

    Page<Outgoings> findAllByDate(Date date, Pageable pageable);

    Page<Outgoings> findAllByDateAndIdeaId(LocalDate date, Long ideaId, Pageable pageable);
}
