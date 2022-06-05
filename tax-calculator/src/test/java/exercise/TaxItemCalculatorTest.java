package exercise;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static exercise.TaxType.*;

import static org.assertj.core.api.Assertions.assertThat;

class TaxItemCalculatorTest {

    private final TaxItemCalculator underTest = new TaxItemCalculator();

    @Test
    public void calculateNormalSalesTax() {
        assertThat(underTest.calculateTax("music CD", Set.of(SALES), new BigDecimal("14.99"))).isEqualTo(new BigDecimal("1.50"));
    }

    @Test
    public void calculateTaxExempt(){
        assertThat(underTest.calculateTax("book", Set.of(EXEMPT), new BigDecimal("12.49"))).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void calculateImportTax(){
        assertThat(underTest.calculateTax("imported chocolates", Set.of(IMPORT), new BigDecimal("10.00"))).isEqualTo(new BigDecimal("0.50"));
    }

    @Test
    public void calculateImportAndSalesTax(){
        assertThat(underTest.calculateTax("imported perfume", Set.of(IMPORT, SALES), new BigDecimal("47.50"))).isEqualTo(new BigDecimal("7,15"));
    }
}