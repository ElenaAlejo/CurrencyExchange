package alejoelena.android.uoc.edu.currencyexchange.rest.content;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import alejoelena.android.uoc.edu.currencyexchange.rest.adapter.CurrencyLayerAdapter;
import alejoelena.android.uoc.edu.currencyexchange.rest.model.Currencies;
import alejoelena.android.uoc.edu.currencyexchange.rest.model.Live;
import alejoelena.android.uoc.edu.currencyexchange.rest.service.CurrencyLayerService;
import retrofit2.Call;

public class LiveContent {
    public static Map<String, Live> live;

    public static void Init(String currency) throws IOException {
        if (live == null) {
            live = new HashMap<>();
        }
        if (!live.containsKey(currency)) {
            CurrencyLayerService currencyLayerService = new CurrencyLayerAdapter();
            Call<Live> call = currencyLayerService.getLive(currency);
            live.put(currency, call.execute().body());
        }
    }

    public static void Reload(String currency) throws IOException {

        live = new HashMap<>();
        CurrencyLayerService currencyLayerService = new CurrencyLayerAdapter();
        Call<Live> call = currencyLayerService.getLive(currency);
        live.put(currency, call.execute().body());

    }
}
