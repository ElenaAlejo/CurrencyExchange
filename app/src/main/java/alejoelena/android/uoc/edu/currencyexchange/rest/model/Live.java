package alejoelena.android.uoc.edu.currencyexchange.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Live {

    @SerializedName("success")
    private boolean success;

    @SerializedName("terms")
    private String terms;

    @SerializedName("timestamp")
    private String timestamp;

    @SerializedName("source")
    private String source;

    @SerializedName("quotes")
    private Map<String, String> quotes;

    public String[] getQuotes() {
        String[] exchangeRates = new String[quotes.size()];
        int i = 0;
       for(String key : quotes.keySet()){
           exchangeRates[i] =  quotes.get(key) + key.replace(source, " ");
           i++;
       }

       return  exchangeRates;
    }
}
