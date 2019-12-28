package net.createyourideas.accounting.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import net.createyourideas.accounting.web.rest.TestUtil;

public class WorksheetTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Worksheet.class);
        Worksheet worksheet1 = new Worksheet();
        worksheet1.setId(1L);
        Worksheet worksheet2 = new Worksheet();
        worksheet2.setId(worksheet1.getId());
        assertThat(worksheet1).isEqualTo(worksheet2);
        worksheet2.setId(2L);
        assertThat(worksheet1).isNotEqualTo(worksheet2);
        worksheet1.setId(null);
        assertThat(worksheet1).isNotEqualTo(worksheet2);
    }
}
