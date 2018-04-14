//@@author ewaldhew
package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to spawn a pop-up notification with the given message.
 */
public class ShowNotificationRequestEvent extends BaseEvent {

    /** The index of the coin that triggered this notification */
    public final Index targetIndex;

    private final String message;

    public ShowNotificationRequestEvent(String message, Index index) {
        this.message = message;
        this.targetIndex = index;
    }

    @Override
    public String toString() {
        return "Notifying about: " + message;
    }
}
