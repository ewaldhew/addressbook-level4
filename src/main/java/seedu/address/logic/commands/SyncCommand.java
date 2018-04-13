//@@author laichengyu

package seedu.address.logic.commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FetchUtil;
import seedu.address.commons.util.UrlBuilderUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.coin.Amount;
import seedu.address.model.coin.Price;
import seedu.address.model.coin.exceptions.CoinNotFoundException;
import seedu.address.model.coin.exceptions.DuplicateCoinException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Updates all coins in the coin book with latest cryptocurrency data
 */
public class SyncCommand extends Command {

    public static final String COMMAND_WORD = "sync";
    public static final String COMMAND_ALIAS = "sy";

    public static final String MESSAGE_SUCCESS = "Synced all coins with latest cryptocurrency data";

    private static final Logger logger = LogsCenter.getLogger(SyncCommand.class);

    private static final String historicalPriceApiUrl = "https://min-api.cryptocompare.com/data/histohour";
    private static final String cryptoCompareApiUrl = "https://min-api.cryptocompare.com/data/pricemultifull";

    private static final String HISTORICAL = "historical";
    private static final String CURRENT = "current";

    private static final String CODE_PARAM = "fsym";
    private static final String CURRENCY_PARAM = "tsym";
    private static final String PLURALIZE = "s";
    private static final String CURRENCY_TYPE = "USD";
    private static final String LIMIT_PARAM = "limit";
    private static final String HISTORICAL_DATA_HOURS_LIMIT = "168";

    /**
     * Creates and returns a {@code List<NameValuePair>} with at least two key-value pairs, coin symbols and currency.
     * Additional parameters are optional based on the {@code type}
     * @param commaSeparatedCodes cannot be null
     * @param type specifies type of data required
     * @return parameters for specified API call
     */
    private List<NameValuePair> buildParams(String commaSeparatedCodes, String type) {
        List<NameValuePair> parameters = new ArrayList<>();
        addBasicNecessaryParams(parameters, commaSeparatedCodes, type);
        addAdditionalParams(parameters, type);
        return parameters;
    }


    private void addBasicNecessaryParams(List<NameValuePair> params, String commaSeparatedCodes, String type) {
        switch (type) {
        case HISTORICAL:
            params.add(new BasicNameValuePair(CODE_PARAM, commaSeparatedCodes));
            params.add(new BasicNameValuePair(CURRENCY_PARAM, CURRENCY_TYPE));
        case CURRENT:
            params.add(new BasicNameValuePair(CODE_PARAM + PLURALIZE, commaSeparatedCodes));
            params.add(new BasicNameValuePair(CURRENCY_PARAM + PLURALIZE, CURRENCY_TYPE));
        default:
            break;
        }
    }

    /**
     * Adds any additional parameters required for the API call
     * @param params cannot be null
     * @param type specifies type of data required
     */
    private void addAdditionalParams(List<NameValuePair> params, String type) {
        switch (type) {
        case HISTORICAL:
            params.add(new BasicNameValuePair(LIMIT_PARAM, HISTORICAL_DATA_HOURS_LIMIT));
            break;
        default:
            //no additional parameters
        }
    }

    /**
     * Concatenates a list of strings into one with each string separated by a comma
     * @param list of strings to be concatenated
     * @return comma separated string
     */
    private String concatenateByComma(List<String> list) {
        return String.join(",", list);
    }

    /**
     * Adds parameters to the CryptoCompare API URL.
     *
     * @param params cannot be null
     */
    private String buildApiUrl(String url, List<NameValuePair> params) {
        return UrlBuilderUtil.buildUrl(url, params);
    }

    private JsonObject getRawData(JsonObject data) {
        return data.get("RAW").getAsJsonObject();
    }

    /**
     * Creates and returns a {@code HashMap<String, Price>} of code and price metrics as key-value pairs.
     * @param currentPriceData contains the latest prices of each of the user's coin
     * @return HashMap containing price metrics of each coin retrieval by its code
     */
    private HashMap<String, Price> createPriceObjects(JsonObject currentPriceData, JsonObject histoPriceData) {
        requireAllNonNull(currentPriceData, histoPriceData);

        HashMap<String, Price> priceObjs = new HashMap<>();
        List<String> codes = model.getCodeList();

        for (String code : codes) {
            JsonElement coinCurrentPriceMetrics = currentPriceData.get(code);

            if (coinCurrentPriceMetrics == null) {
                continue;
            }

            Price newCurrentPrice = new Price();
            newCurrentPrice.setCurrent(new Amount(coinCurrentPriceMetrics
                    .getAsJsonObject()
                    .get(CURRENCY_TYPE)
                    .getAsJsonObject()
                    .get("PRICE")
                    .getAsString()));

            priceObjs.put(code, newCurrentPrice);
        }
        return priceObjs;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            String commaSeparatedCodes = concatenateByComma(model.getCodeList());

            List<NameValuePair> currentPriceParams = buildParams(commaSeparatedCodes, CURRENT);
            String currentPriceUrl = buildApiUrl(cryptoCompareApiUrl, currentPriceParams);
            JsonObject currentPriceData = FetchUtil.asyncFetch(currentPriceUrl);

            List<NameValuePair> histoPriceParams = buildParams(commaSeparatedCodes, HISTORICAL);
            String histoPriceUrl = buildApiUrl(historicalPriceApiUrl, histoPriceParams);
            JsonObject histoPriceData = FetchUtil.asyncFetch(histoPriceUrl);

            HashMap<String, Price> newPriceMetrics =
                    createPriceObjects(getRawData(currentPriceData), histoPriceData);
            model.syncAll(newPriceMetrics);
        } catch (DuplicateCoinException dpe) {
            throw new CommandException("Unexpected code path!");
        } catch (CoinNotFoundException cnfe) {
            throw new AssertionError("The target coin cannot be missing");
        } catch (InterruptedException ie) {
            logger.warning("Thread interrupted");
        } catch (ExecutionException ee) {
            logger.warning("Data fetching error");
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
