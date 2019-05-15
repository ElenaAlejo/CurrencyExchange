package alejoelena.android.uoc.edu.currencyexchange.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Currencies {

    @SerializedName("success")
    private boolean success;

    @SerializedName("terms")
    private String terms;

    @SerializedName("timestamp")
    private Date timestamp;

    @SerializedName("source")
    private String source;

    @SerializedName("currencies")
    private Map<String, String> currencies;

    public Map<String, String> getCurrencies() {
        return currencies;
    }
}
