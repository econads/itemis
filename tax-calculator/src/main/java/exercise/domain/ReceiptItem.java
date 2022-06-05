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

    public boolean isImported(){
        return taxTypesDue.contains(TaxType.IMPORT);
    }
}
