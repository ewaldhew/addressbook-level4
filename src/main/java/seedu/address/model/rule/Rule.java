//@@author ewaldhew
package seedu.address.model.rule;

import seedu.address.logic.commands.Command;

/**
 * Represents a Rule in the rule book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Rule {

    protected final Command action;
    private final String value;

    protected Rule(String value, Command action) {
        this.value = value;
        this.action = action;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Rule)) {
            return false;
        }

        Rule otherRule = (Rule) other;
        return otherRule.value.equals(this.value);
    }

}
