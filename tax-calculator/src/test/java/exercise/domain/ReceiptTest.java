package exercise.domain;

import exercise.TestUtils;
import exercise.exceptions.BlockingException;
import exercise.exceptions.ValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static exercise.domain.TaxType.IMPORT;
import static exercise.domain.TaxType.SALES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReceiptTest {

    private final TestUtils testUtils = new TestUtils();

    @Test
    public void readInInput1() throws ValidationException, BlockingException {
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
    public void readInInput2() throws ValidationException, BlockingException {
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
    public void readInInput3() throws ValidationException, BlockingException {
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

        //then
        assertThat(items.size()).isEqualTo(4);
        assertThat(items).containsAll(expected);
    }

    @Test
    public void emptyInputFile() {
        //when
        BlockingException exception = assertThrows(BlockingException.class,
                () -> new Receipt().readInReceiptFromFile("src/test/resources/EmptyInput.txt"));

        //then
        assertThat(exception.getCause()).hasMessage(Receipt.EMPTY_FILE_ERROR);

        File actual = new File("Receipt.txt");
        assertThat(actual.exists()).isFalse();
    }

    @Test
    public void invalidLine() throws BlockingException, ValidationException {
        //given
        List<ReceiptItem> expected = new ArrayList<>();
        expected.add(new ReceiptItem("bottle of perfume", Set.of(IMPORT, SALES), new BigDecimal("27.99")));
        expected.add(new ReceiptItem("packet of headache pills", Set.of(), new BigDecimal("9.75")));
        expected.add(new ReceiptItem("box of chocolates", Set.of(IMPORT), new BigDecimal("11.25")));

        //when
        Receipt underTest = new Receipt();
        underTest.readInReceiptFromFile("src/test/resources/InvalidLine.txt");
        List<ReceiptItem> items = underTest.getItems();

        //then
        assertThat(items.size()).isEqualTo(3);
        assertThat(items).containsAll(expected);
    }

    @Test
    public void unknownItem() throws BlockingException, ValidationException {
        //given
        List<ReceiptItem> expected = new ArrayList<>();
        expected.add(new ReceiptItem("bananas", Set.of(IMPORT), new BigDecimal("10.00")));
        expected.add(new ReceiptItem("bottle of perfume", Set.of(SALES), new BigDecimal("47.50")));

        //when
        Receipt underTest = new Receipt();
        underTest.readInReceiptFromFile("src/test/resources/UnknownItem.txt");
        List<ReceiptItem> items = underTest.getItems();

        //then
        assertThat(items.size()).isEqualTo(2);
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
        testUtils.checkFilesMatch("Output1.txt");
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
        testUtils.checkFilesMatch("Output2.txt");
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
        testUtils.checkFilesMatch("Output3.txt");
    }

    @AfterEach
    public void cleanup(){
        File actual = new File("Receipt.txt");
        actual.delete();
    }

}