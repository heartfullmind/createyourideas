package net.createyourideas.accounting.service;

import net.createyourideas.accounting.domain.Idea;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Idea}.
 */
public interface IdeaAdditionService {

    Page<Idea> findByUserIsCurrentUser(Pageable pageable);

    Page<Idea> findAllById(Long id, Pageable pageable);

}
