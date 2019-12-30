package net.createyourideas.accounting.repository;
import net.createyourideas.accounting.domain.Income;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Income entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

}
