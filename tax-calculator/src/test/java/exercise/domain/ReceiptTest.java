package exercise.domain;

import exercise.exceptions.ValidationException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static exercise.domain.TaxType.IMPORT;
import static exercise.domain.TaxType.SALES;
import static org.assertj.core.api.Assertions.assertThat;

class ReceiptTest {

    @Test
    public void readInInput1() throws ValidationException {
        //given
        Receipt underTest = new Receipt();
        underTest.readInReceiptFromFile("src/test/resources/Input1.txt");

        List<ReceiptItem> expected = new ArrayList<>();
        expected.add(new ReceiptItem("book", Set.of(), new BigDecimal("12.49")));
        expected.add(new ReceiptItem("music CD", Set.of(SALES), new BigDecimal("14.99")));
        expected.add(new ReceiptItem("chocolate bar", Set.of(), new BigDecimal("0.85")));

        //when
        List<ReceiptItem> items = underTest.getItems();
        assertThat(items.size()).isEqualTo(3);
        assertThat(items).containsAll(expected);
    }
    @Test
    public void readInInput2() throws ValidationException {
        //given
        Receipt underTest = new Receipt();
        underTest.readInReceiptFromFile("src/test/resources/Input2.txt");

        List<ReceiptItem> expected = new ArrayList<>();
        expected.add(new ReceiptItem("box of chocolates", Set.of(IMPORT), new BigDecimal("10.00")));
        expected.add(new ReceiptItem("bottle of perfume", Set.of(IMPORT, SALES), new BigDecimal("47.50")));
        //when
        List<ReceiptItem> items = underTest.getItems();
        assertThat(items.size()).isEqualTo(2);
        assertThat(items).containsAll(expected);
    }
    @Test
    public void readInInput3() throws ValidationException {
        //given
        Receipt underTest = new Receipt();
        underTest.readInReceiptFromFile("src/test/resources/Input3.txt");

        List<ReceiptItem> expected = new ArrayList<>();
        expected.add(new ReceiptItem("bottle of perfume", Set.of(IMPORT, SALES), new BigDecimal("27.99")));
        expected.add(new ReceiptItem("bottle of perfume", Set.of(SALES), new BigDecimal("18.99")));
        expected.add(new ReceiptItem("packet of headache pills", Set.of(), new BigDecimal("9.75")));
        expected.add(new ReceiptItem("box of chocolates", Set.of(IMPORT), new BigDecimal("11.25")));

        //when
        List<ReceiptItem> items = underTest.getItems();
        assertThat(items.size()).isEqualTo(4);
        assertThat(items).containsAll(expected);
    }
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
        checkFilesMatch("Output1.txt");
    }

    @Test
    public void printReceiptOutput2() throws ValidationException {
        //given
        Receipt underTest = new Receipt();
        underTest.addItem(new ReceiptItem("box of chocolates", Set.of(IMPORT), new BigDecimal("10.00")));
        underTest.addItem(new ReceiptItem("bottle of perfume", Set.of(IMPORT, SALES), new BigDecimal("47.50")));

        //when
        underTest.printReceipt();

        //then
        checkFilesMatch("Output2.txt");
    }

    @Test
    public void printReceiptOutput3() throws ValidationException {
        //given
        Receipt underTest = new Receipt();
        underTest.addItem(new ReceiptItem("bottle of perfume", Set.of(IMPORT, SALES), new BigDecimal("27.99")));
        underTest.addItem(new ReceiptItem("bottle of perfume", Set.of(SALES), new BigDecimal("18.99")));
        underTest.addItem(new ReceiptItem("packet of headache pills", Set.of(), new BigDecimal("9.75")));
        underTest.addItem(new ReceiptItem("box of chocolates", Set.of(IMPORT), new BigDecimal("11.25")));

        //when
        underTest.printReceipt();

        //then
        checkFilesMatch("Output3.txt");
    }

    private void checkFilesMatch(String expectedFileName){
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