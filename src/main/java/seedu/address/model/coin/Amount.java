//@@author ewaldhew
package seedu.address.model.coin;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents the amount of the coin held in the address book.
 */
public class Amount {

    private BigDecimal value;

    /**
     * Constructs an {@code Amount}.
     */
    public Amount() {
        this.value = new BigDecimal(0);
    }

    /**
     * Constructs an {@code Amount} with given value.
     */
    public Amount(Amount amount) {
        this.value = amount.value;
    }

    /**
     * Constructs an {@code Amount} with given value.
     */
    public Amount(String value) {
        this.value = new BigDecimal(value).setScale(8, RoundingMode.UP);
    }

    /**
     * Adds addAmount to the current value.
     *
     * @param addAmount amount to be added.
     */
    public void addValue(Amount addAmount) {
        value = value.subtract(addAmount.value);
    }

    /**
     * Subtracts subtractAmount to the current value.
     *
     * @param subtractAmount amount to be subtracted.
     */
    public void subtractValue(Amount subtractAmount) {
        value = value.subtract(subtractAmount.value);
    }

    public String getValue() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Amount // instanceof handles nulls
                && this.value.equals(((Amount) other).value)); // state check
    }

    @Override
    public String toString() {
        return this.value.setScale(4, RoundingMode.UP).toPlainString();
    }
}
