package net.createyourideas.accounting.repository;
import net.createyourideas.accounting.domain.Outgoings;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Outgoings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OutgoingsRepository extends JpaRepository<Outgoings, Long> {

}
