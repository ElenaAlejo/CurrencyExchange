package alejoelena.android.uoc.edu.currencyexchange.rest.content;

import java.io.IOException;
import java.util.List;

import alejoelena.android.uoc.edu.currencyexchange.rest.adapter.CurrencyLayerAdapter;
import alejoelena.android.uoc.edu.currencyexchange.rest.model.Currencies;
import alejoelena.android.uoc.edu.currencyexchange.rest.service.CurrencyLayerService;
import retrofit2.Call;

public class CurrenciesContent {
    public static Currencies currencies;

    public static void Init() throws IOException {
        if(currencies == null) {
            CurrencyLayerService currencyLayerService = new CurrencyLayerAdapter();
            Call<Currencies> call = currencyLayerService.getCurrencies();
            currencies = call.execute().body();
        }
    }
}
