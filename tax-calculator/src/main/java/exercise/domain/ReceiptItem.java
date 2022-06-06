package exercise.domain;

import exercise.exceptions.ValidationException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ReceiptItem {

    private final String name;
    private final Set<TaxType> taxTypesDue;
    private final BigDecimal netValue;
    private BigDecimal grossValue;
    private BigDecimal taxes;

    /**
     * Signifies a single line (item) on the receipt
     *
     * @param name        the name of the item - cannot be null or empty
     * @param taxTypesDue the type of taxes relevant to this item
     * @param netValue    the value of the item without any taxes added - must be greater than zero
     * @throws ValidationException if any of the parameters are invalid
     */
    public ReceiptItem(String name, Set<TaxType> taxTypesDue, BigDecimal netValue) throws ValidationException {
        validate(name, taxTypesDue, netValue);
        this.name = name;
        this.taxTypesDue = taxTypesDue == null ? Set.of() : taxTypesDue;
        this.netValue = netValue;
        setCalculatedFields();
    }

    /**
     * This method creates a {@link ReceiptItem} from a line formatted like this:
     * "1 [item name] at [item net value]".
     * Where item name includes the word 'imported' import tax will be added.
     * For all valid items except books, food and medical products, sales tax will be added
     *
     * @param stringToParse string in the format described
     * @return new {@link ReceiptItem}
     */
    public static ReceiptItem parseReceiptItemFromString(String stringToParse) throws ValidationException {
        stringToParse = stringToParse.substring(2);
        String[] constructorArguments = stringToParse.split(" at ");
        if (constructorArguments.length != 2){
            throw new ValidationException(List.of("Badly formatted line"));
        }
        String name = constructorArguments[0];
        String netValue = constructorArguments[1];

        return new ReceiptItem(removeImportedFromName(name), getTaxFromName(name), new BigDecimal(netValue));
    }

    private void validate(String name, Set<TaxType> taxTypesDue, BigDecimal netValue) throws ValidationException {
        List<String> errors = new ArrayList<>();
        if (name == null || name.isBlank()) {
            errors.add("Item needs a name");
        }
        if (netValue == null || netValue.compareTo(BigDecimal.ZERO) < 1) {
            errors.add("Item needs to have a positive value");
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private static String removeImportedFromName(String name) throws ValidationException {
        if (name.contains("imported")) {
            String[] substrings = name.split("imported ");
            //put the string back together without 'imported' in it
            name = Arrays.stream(substrings).reduce(String::concat).orElseThrow(ValidationException::new);
        }
        return name;
    }

    private static Set<TaxType> getTaxFromName(String name) {
        Set<TaxType> taxTypes = new HashSet<>();
        if (name.contains("imported")) {
            taxTypes.add(TaxType.IMPORT);
        }
        if (name.contains("music") || name.contains("perfume")) {
            taxTypes.add(TaxType.SALES);
        }
        return taxTypes;
    }

    private void setCalculatedFields() {

        BigDecimal totalPercentage = BigDecimal.ZERO;

        for(TaxType taxType : taxTypesDue){
            totalPercentage = totalPercentage.add(taxType.getPercentage());
        }

        this.taxes = roundUpToNearest05( this.netValue.multiply(totalPercentage) );
        this.grossValue = this.netValue.add(this.taxes);
    }

    /**
     * This method rounds up to the next 0.05
     * For example, if the passed in parameter is the value on the left below, the value on the right is returned:
     *  12.43 -> 12.45
     *  12.96 -> 13.00
     *  11.00 -> 11.00
     *  13.46 -> 13.50
     * @return rounded value
     */
    private BigDecimal roundUpToNearest05(BigDecimal toRound){
        BigDecimal multiplicand = new BigDecimal("20.00");
        return toRound.multiply(multiplicand)
                .setScale(0, RoundingMode.UP)
                .divide(multiplicand)
                .setScale(2);
    }

    public String getName() {
        return name;
    }

    public BigDecimal getGrossValue() {
        return grossValue;
    }

    public BigDecimal getTaxes() {
        return taxes;
    }

    public boolean isImported() {
        return taxTypesDue.contains(TaxType.IMPORT);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReceiptItem that = (ReceiptItem) o;
        return name.equals(that.name) && Objects.equals(taxTypesDue, that.taxTypesDue) && netValue.equals(that.netValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, taxTypesDue, netValue);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ReceiptItem{");
        sb.append("name='").append(name).append('\'');
        sb.append(", taxTypesDue=").append(taxTypesDue);
        sb.append(", netValue=").append(netValue);
        sb.append(", grossValue=").append(grossValue);
        sb.append(", taxes=").append(taxes);
        sb.append('}');
        return sb.toString();
    }
}
