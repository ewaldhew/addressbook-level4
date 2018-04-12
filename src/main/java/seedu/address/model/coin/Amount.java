//@@author ewaldhew
package seedu.address.model.coin;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents the amount of the coin held in the address book.
 */
public class Amount implements Comparable<Amount> {

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
     * Constructs an {@code Amount} with given value.
     * For internal use only.
     */
    private Amount(BigDecimal value) {
        this.value = value;
    }


    /**
     * Adds two amounts together and returns a new object.
     * @return the sum of the two arguments
     */
    public static Amount getSum(Amount first, Amount second) {
        return new Amount(first.value.add(second.value));
    }

    /**
     * Subtracts second from first and returns a new object.
     * @return the difference of the two arguments
     */
    public static Amount getDiff(Amount first, Amount second) {
        return new Amount(first.value.subtract(second.value));
    }

    /**
     * Multiplus two amounts together and returns a new object.
     * @return the product of the two arguments
     */
    public static Amount getMult(Amount first, Amount second) {
        return new Amount(first.value.multiply(second.value));
    }

    /**
     * Adds addAmount to the current value.
     *
     * @param addAmount amount to be added.
     */
    public void addValue(Amount addAmount) {
        value = value.add(addAmount.value);
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
                && this.value.compareTo(((Amount) other).value) == 0); // state check
    }

    @Override
    public String toString() {
        return value.setScale(4, RoundingMode.UP).toPlainString();
    }

    @Override
    public int compareTo(Amount other) {
        return value.compareTo(other.value);
    }
}
