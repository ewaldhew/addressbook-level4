package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.CoinBook;
import seedu.address.model.ReadOnlyCoinBook;
import seedu.address.model.coin.Coin;
import seedu.address.model.coin.Name;
import seedu.address.model.coin.exceptions.DuplicateCoinException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code CoinBook} with sample data.
 */
public class SampleDataUtil {
    public static Coin[] getSampleCoins() {
        return new Coin[] {
            new Coin(new Name("Alex Yeoh"),
                getTagSet("friends")),
            new Coin(new Name("Bernice Yu"),
                getTagSet("colleagues", "friends")),
            new Coin(new Name("Charlotte Oliveiro"),
                getTagSet("neighbours")),
            new Coin(new Name("David Li"),
                getTagSet("family")),
            new Coin(new Name("Irfan Ibrahim"),
                getTagSet("classmates")),
            new Coin(new Name("Roy Balakrishnan"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyCoinBook getSampleAddressBook() {
        try {
            CoinBook sampleAb = new CoinBook();
            for (Coin sampleCoin : getSampleCoins()) {
                sampleAb.addCoin(sampleCoin);
            }
            return sampleAb;
        } catch (DuplicateCoinException e) {
            throw new AssertionError("sample data cannot contain duplicate coins", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

}
