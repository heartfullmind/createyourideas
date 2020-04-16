package net.createyourideas.accounting.repository;
import net.createyourideas.accounting.domain.ProfitBalance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProfitBalance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfitBalanceRepository extends JpaRepository<ProfitBalance, Long> {

}
