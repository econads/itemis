package exercise.domain;

import java.math.BigDecimal;

/**
 * Denotes the possible types of tax and information for calculating them.
 * e.g. SALES tax charges 10% of the total. Here we hold it in decimal form as 0.1 which indicates that if the price
 * before tax is 10.00, then the price after tax is 10.10
 */
public enum TaxType {

    IMPORT(new BigDecimal("0.05")),
    SALES(new BigDecimal("0.10"));

    private final BigDecimal percentage;

    /**
     * @param percentage of tax this type charges as a decimal between 0 and 1, e.g. 50% tax should be 0.5 here
     */
    TaxType(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }
}
