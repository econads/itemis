package exercise.domain;

import exercise.exceptions.BlockingException;
import exercise.exceptions.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Receipt {

    private static final Logger logger = LogManager.getLogger(Receipt.class);
    public static final String EMPTY_FILE_ERROR = "Input file has no valid lines";

    private final List<ReceiptItem> items = new ArrayList<>();

    public void addItem(ReceiptItem item) {
        items.add(item);
    }

    public List<ReceiptItem> getItems() {
        return List.copyOf(items);
    }

    public void readInReceiptFromFile(String filename) throws BlockingException {
        File input = new File(filename);
        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
            String line;
            int linecount = 0;
            while ((line = reader.readLine()) != null) {
                try {
                    addItem(ReceiptItem.parseReceiptItemFromString(line));
                } catch (ValidationException e) {
                    logger.error("Could not parse line {}, skipping ", line);
                    continue;
                }
                linecount++;
            }
            if (linecount == 0){
                throw new IOException(EMPTY_FILE_ERROR);
            }
        } catch (IOException e) {
            throw new BlockingException("Could not read in file, please specify a different location.", e);
        }
    }

    public void printReceipt() {
        StringBuilder stringBuilder = generateReceiptContents();
        printReceiptToFile(stringBuilder);
    }

    private void printReceiptToFile(StringBuilder stringBuilder) {
        File output = new File("Receipt.txt");
        try (FileWriter writer = new FileWriter(output)) {
            writer.write(stringBuilder.toString());
            logger.info("Receipt printed to {}", output.getCanonicalPath());
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
