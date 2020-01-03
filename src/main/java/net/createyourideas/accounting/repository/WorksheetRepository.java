package net.createyourideas.accounting.repository;

import net.createyourideas.accounting.domain.Worksheet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Worksheet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorksheetRepository extends JpaRepository<Worksheet, Long> {

    @Query("select worksheet from Worksheet worksheet where worksheet.user.login = ?#{principal.username}")
    List<Worksheet> findByUserIsCurrentUser();

    Page<Worksheet> findAllByIdeaId(Long ideaId, Pageable pageable);

}
