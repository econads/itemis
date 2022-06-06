package exercise.domain;

import exercise.exceptions.ValidationException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.Set;

import static exercise.domain.TaxType.IMPORT;
import static exercise.domain.TaxType.SALES;
import static org.assertj.core.api.Assertions.assertThat;

class ReceiptTest {

    @Test
    public void printReceiptOutput1() throws ValidationException {
        //given
        Receipt underTest = new Receipt();
        underTest.addItem(new ReceiptItem("book", Set.of(), new BigDecimal("12.49")));
        underTest.addItem(new ReceiptItem("music CD", Set.of(SALES), new BigDecimal("14.99")));
        underTest.addItem(new ReceiptItem("chocolate bar", Set.of(), new BigDecimal("0.85")));

        //when
        underTest.printReceipt();

        //then
        doFilesMatch("Output1.txt");
    }

    public void printReceiptOutput2() throws ValidationException {
        //given
        Receipt underTest = new Receipt();
        underTest.addItem(new ReceiptItem("box of chocolates", Set.of(IMPORT), new BigDecimal("10.00")));
        underTest.addItem(new ReceiptItem("bottle of perfume", Set.of(IMPORT, SALES), new BigDecimal("47.50")));

        //when
        underTest.printReceipt();

        //then
        doFilesMatch("Output2.txt");
    }

    public void printReceiptOutput3() throws ValidationException {
        //given
        Receipt underTest = new Receipt();
        underTest.addItem(new ReceiptItem("book", Set.of(), new BigDecimal("12.49")));
        underTest.addItem(new ReceiptItem("music CD", Set.of(SALES), new BigDecimal("14.99")));
        underTest.addItem(new ReceiptItem("chocolate bar", Set.of(), new BigDecimal("0.85")));

        //when
        underTest.printReceipt();

        //then
        doFilesMatch("Output3.txt");
    }

    private void doFilesMatch(String expectedFileName){
        File expected = new File("src/test/resources/" + expectedFileName);
        File actual = new File("Receipt.txt");
        assertThat(expected.exists()).isTrue();
        assertThat(actual.exists()).isTrue();
        assertThat(actual).hasSameTextualContentAs(expected);
    }

    @AfterAll
    public static void cleanup(){
        File actual = new File("Receipt.txt");
        actual.deleteOnExit();
    }

}