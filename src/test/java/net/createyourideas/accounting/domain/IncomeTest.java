package net.createyourideas.accounting.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import net.createyourideas.accounting.web.rest.TestUtil;

public class IncomeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Income.class);
        Income income1 = new Income();
        income1.setId(1L);
        Income income2 = new Income();
        income2.setId(income1.getId());
        assertThat(income1).isEqualTo(income2);
        income2.setId(2L);
        assertThat(income1).isNotEqualTo(income2);
        income1.setId(null);
        assertThat(income1).isNotEqualTo(income2);
    }
}
