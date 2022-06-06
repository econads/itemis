package exercise.business;

import exercise.domain.Receipt;
import exercise.domain.ReceiptItem;
import exercise.exceptions.ValidationException;

import java.math.BigDecimal;
import java.util.Set;

import static exercise.domain.TaxType.SALES;

public class ReceiptManager {
    public static void main(String[] args) throws ValidationException {

        Receipt underTest = new Receipt();
        underTest.addItem(new ReceiptItem("book", Set.of(), new BigDecimal("12.49")));
        underTest.addItem(new ReceiptItem("music CD", Set.of(SALES), new BigDecimal("14.99")));
        underTest.addItem(new ReceiptItem("chocolate bar", Set.of(), new BigDecimal("0.85")));

        underTest.printReceipt();
    }
}
