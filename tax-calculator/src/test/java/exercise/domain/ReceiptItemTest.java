package exercise.domain;

import exercise.exceptions.ValidationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static exercise.domain.TaxType.IMPORT;
import static exercise.domain.TaxType.SALES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReceiptItemTest {

    @Test
    public void calculateNormalSalesTax() throws ValidationException {
        ReceiptItem underTest = new ReceiptItem("music CD", Set.of(SALES), new BigDecimal("14.99"));
        assertThat(underTest.getTaxes()).isEqualTo(new BigDecimal("1.50"));
        assertThat(underTest.getGrossValue()).isEqualTo(new BigDecimal("16.49"));
    }

    @Test
    public void calculateTaxExempt() throws ValidationException {
        String bookNetValue = "12.49";
        ReceiptItem underTest = new ReceiptItem("book", Set.of(), new BigDecimal(bookNetValue));
        assertThat(underTest.getTaxes()).isZero();
        assertThat(underTest.getGrossValue()).isEqualTo(bookNetValue);
    }

    @Test
    public void calculateImportTax() throws ValidationException {
        ReceiptItem underTest = new ReceiptItem("imported chocolates", Set.of(IMPORT), new BigDecimal("10.00"));
        assertThat(underTest.getTaxes()).isEqualTo(new BigDecimal("0.50"));
        assertThat(underTest.getGrossValue()).isEqualTo(new BigDecimal("10.50"));
    }

    @Test
    public void calculateImportAndSalesTaxRoundUp() throws ValidationException {
        ReceiptItem underTest = new ReceiptItem("imported perfume", Set.of(IMPORT, SALES), new BigDecimal("47.50"));
        assertThat(underTest.getGrossValue()).isEqualTo(new BigDecimal("54.65"));
        assertThat(underTest.getTaxes()).isEqualTo(new BigDecimal("7.15"));
    }

    @Test
    public void calculateImportAndSalesTaxRoundingUnneeded() throws ValidationException {
        ReceiptItem underTest = new ReceiptItem("imported perfume", Set.of(IMPORT, SALES), new BigDecimal("27.99"));
        assertThat(underTest.getGrossValue()).isEqualTo(new BigDecimal("32.19"));
        assertThat(underTest.getTaxes()).isEqualTo(new BigDecimal("4.20"));
    }

    @Test
    public void dealWithNulls() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> new ReceiptItem(null, null, null));
        assertThat(exception.getErrors()).containsAll(Set.of("Item needs a name", "Item needs to have a positive value"));
    }

    @Test
    public void dealWithNegative(){
        ValidationException exception = assertThrows(ValidationException.class,
                () -> new ReceiptItem("xxxx", null, new BigDecimal("-10")));
        assertThat(exception.getErrors()).contains("Item needs to have a positive value");
    }
}