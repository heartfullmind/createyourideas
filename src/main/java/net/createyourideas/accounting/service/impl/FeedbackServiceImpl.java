package net.createyourideas.accounting.service.impl;

import net.createyourideas.accounting.service.FeedbackService;
import net.createyourideas.accounting.domain.Feedback;
import net.createyourideas.accounting.repository.FeedbackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Feedback}.
 */
@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

    private final Logger log = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    private final FeedbackRepository feedbackRepository;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    /**
     * Save a feedback.
     *
     * @param feedback the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Feedback save(Feedback feedback) {
        log.debug("Request to save Feedback : {}", feedback);
        return feedbackRepository.save(feedback);
    }

    /**
     * Get all the feedbacks.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Feedback> findAll() {
        log.debug("Request to get all Feedbacks");
        return feedbackRepository.findAll();
    }


    /**
     * Get one feedback by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Feedback> findOne(Long id) {
        log.debug("Request to get Feedback : {}", id);
        return feedbackRepository.findById(id);
    }

    /**
     * Delete the feedback by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Feedback : {}", id);
        feedbackRepository.deleteById(id);
    }
}
