//@@author ewaldhew
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowNotificationRequestEvent;
import seedu.address.model.coin.Coin;

/**
 * Spawns a pop-up notification in the corner of the screen.
 */
public class SpawnNotificationCommand extends ActionCommand<Coin> {

    private final String message;
    private Coin jumpTo;

    public SpawnNotificationCommand(String message) {
        this.message = message;
    }

    @Override
    public void setExtraData(Coin targetCoin) {
        jumpTo = targetCoin;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowNotificationRequestEvent(message));
        return new CommandResult("");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SpawnNotificationCommand // instanceof handles nulls
                && this.message.equals(((SpawnNotificationCommand) other).message)); // state check
    }
}
