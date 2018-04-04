//@@author ewaldhew
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.TokenType.PREFIXAMOUNT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.BuyCommand;
import seedu.address.logic.commands.CommandTarget;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new BuyCommand object
 */
public class BuyCommandParser implements Parser<BuyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BuyCommand
     * and returns an BuyCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public BuyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenizeToArgumentMultimap(args, PREFIXAMOUNT);
        if (!argMultimap.arePrefixesPresent(PREFIXAMOUNT)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BuyCommand.MESSAGE_USAGE));
        }

        double amountToAdd;
        try {
            amountToAdd = ParserUtil.parseDouble(argMultimap.getValue(PREFIXAMOUNT).get());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BuyCommand.MESSAGE_USAGE));
        }

        // Get target for command
        try {
            CommandTarget target = new CommandTarget(ParserUtil.parseIndex(argMultimap.getPreamble()));
            return new BuyCommand(target, amountToAdd);
        } catch (IllegalValueException ive) {
            try {
                CommandTarget target = new CommandTarget(ParserUtil.parseName(argMultimap.getPreamble()));
                return new BuyCommand(target, amountToAdd);
            } catch (IllegalValueException ive2) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BuyCommand.MESSAGE_USAGE));
            }
        }

    }

}
