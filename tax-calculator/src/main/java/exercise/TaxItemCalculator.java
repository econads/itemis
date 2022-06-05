package exercise;

import java.math.BigDecimal;
import java.util.Set;

/**
 * This class calculates the tax for a receipt item
 */
public class TaxItemCalculator {

    public BigDecimal calculateTax(String item, Set<TaxType> taxTypes, BigDecimal price){
        return new BigDecimal("1.00");
    }
}
