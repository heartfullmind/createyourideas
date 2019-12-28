package net.createyourideas.accounting.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import net.createyourideas.accounting.web.rest.TestUtil;

public class OutgoingsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Outgoings.class);
        Outgoings outgoings1 = new Outgoings();
        outgoings1.setId(1L);
        Outgoings outgoings2 = new Outgoings();
        outgoings2.setId(outgoings1.getId());
        assertThat(outgoings1).isEqualTo(outgoings2);
        outgoings2.setId(2L);
        assertThat(outgoings1).isNotEqualTo(outgoings2);
        outgoings1.setId(null);
        assertThat(outgoings1).isNotEqualTo(outgoings2);
    }
}
