package exercise.domain;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Receipt {

    private final List<ReceiptItem> items = new ArrayList<>();

    public void addItem(ReceiptItem item) {
        items.add(item);
    }

    public void printReceipt() {
        StringBuilder stringBuilder = generateReceiptContents();
        printReceiptToFile(stringBuilder);
    }

    private void printReceiptToFile(StringBuilder stringBuilder) {
        File file = new File("Receipt.txt");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(stringBuilder.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private StringBuilder generateReceiptContents() {
        StringBuilder stringBuilder = new StringBuilder();
        BigDecimal tax = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;
        for (ReceiptItem item : items) {
            stringBuilder.append("1 ")
                    .append(item.isImported() ? "imported " : "")
                    .append(item.getName())
                    .append(": ")
                    .append(item.getGrossValue())
                    .append(System.lineSeparator());

            tax = tax.add(item.getTaxes());
            total = total.add(item.getGrossValue());
        }
        stringBuilder.append("Sales Taxes: ").append(tax.setScale(2).toPlainString()).append(System.lineSeparator());
        stringBuilder.append("Total: ").append(total.setScale(2).toPlainString()).append(System.lineSeparator());
        return stringBuilder;
    }
}
