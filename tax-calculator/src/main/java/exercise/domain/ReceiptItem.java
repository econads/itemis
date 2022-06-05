package exercise.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

public class ReceiptItem {

    private final String name;
    private final Set<TaxType> taxTypesDue;
    private final BigDecimal netValue;
    private BigDecimal grossValue;
    private BigDecimal taxes;

    /**
     * Signifies a single line (item) on the receipt
     * @param name the name of the item
     * @param taxTypesDue the type of taxes relevant to this item
     * @param netValue the value of the item without any taxes added
     */
    public ReceiptItem(String name, Set<TaxType> taxTypesDue, BigDecimal netValue) {
        this.name = name;
        this.taxTypesDue = taxTypesDue;
        this.netValue = netValue;
        setCalculatedFields();
    }
    private void setCalculatedFields() {

        BigDecimal totalPercentage = BigDecimal.ZERO;

        for(TaxType taxType : taxTypesDue){
            totalPercentage = totalPercentage.add(taxType.getPercentage());
        }

        this.taxes = this.netValue
                .multiply(totalPercentage)
                .setScale(2, RoundingMode.HALF_UP);
        this.grossValue = this.netValue
                .multiply(totalPercentage.add(new BigDecimal("1")))
                .setScale(2, RoundingMode.HALF_UP);
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
}
