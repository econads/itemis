package exercise.domain;

import exercise.exceptions.ValidationException;

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

    private final List<ReceiptItem> items = new ArrayList<>();

    public void addItem(ReceiptItem item) {
        items.add(item);
    }

    public List<ReceiptItem> getItems(){
        return List.copyOf(items);
    }

    public void readInReceiptFromFile(String filename){
        File input = new File(filename);
        try (BufferedReader reader = new BufferedReader(new FileReader(input))){
            String line;
            while ((line = reader.readLine()) != null){
                line = line.substring(2);
                String[] substrings = line.split(" at ");
                addItem(new ReceiptItem(removeImportedFromName(substrings[0]), getTaxFromName(substrings[0]), new BigDecimal(substrings[1])));
            }
        } catch (IOException | ValidationException e) {
            throw new RuntimeException(e);
        }
    }

    private String removeImportedFromName(String name) {
        if (name.contains("imported")){
            String[] substrings = name.split("imported ");
            name = Arrays.stream(substrings).reduce(String::concat).orElseThrow(IllegalArgumentException::new);
        }
        return name;
    }

    private Set<TaxType> getTaxFromName(String name) {
        Set<TaxType> taxTypes = new HashSet<>();
        if (name.contains("imported")){
            taxTypes.add(TaxType.IMPORT);
        }
        if (name.contains("music") || name.contains("perfume")){
            taxTypes.add(TaxType.SALES);
        }
        return taxTypes;
    }

    public void printReceipt() {
        StringBuilder stringBuilder = generateReceiptContents();
        printReceiptToFile(stringBuilder);
    }

    private void printReceiptToFile(StringBuilder stringBuilder) {
        File output = new File("Receipt.txt");
        try (FileWriter writer = new FileWriter(output)) {
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
