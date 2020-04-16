package net.createyourideas.accounting.repository;

import net.createyourideas.accounting.domain.Idea;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Idea entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {

    @Query("select idea from Idea idea where idea.user.login = ?#{principal.username}")
    Page<Idea> findByUserIsCurrentUser(Pageable pageable);

    Page<Idea> findAllById(Long id, Pageable pageable);
}
