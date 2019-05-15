package alejoelena.android.uoc.edu.currencyexchange.rest.adapter;

import alejoelena.android.uoc.edu.currencyexchange.rest.model.Currencies;
import alejoelena.android.uoc.edu.currencyexchange.rest.model.Live;
import alejoelena.android.uoc.edu.currencyexchange.rest.service.CurrencyLayerService;
import retrofit2.Call;

public class CurrencyLayerAdapter extends BaseAdapter implements CurrencyLayerService {

    private final CurrencyLayerService currencyLayerService;

    public CurrencyLayerAdapter() {
        super();
        currencyLayerService = createService(CurrencyLayerService.class);
    }

    @Override
    public Call<Currencies> getCurrencies() {
        return currencyLayerService.getCurrencies();
    }

    @Override
    public Call<Live> getLive(String currency) {
        return currencyLayerService.getLive(currency);
    }
}
