package alejoelena.android.uoc.edu.currencyexchange.rest.service;

import java.util.List;

import alejoelena.android.uoc.edu.currencyexchange.rest.constants.ApiConstants;
import alejoelena.android.uoc.edu.currencyexchange.rest.model.Currencies;
import alejoelena.android.uoc.edu.currencyexchange.rest.model.Live;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CurrencyLayerService {
    @GET(ApiConstants.BASE_CURRENCY_LAYER + ApiConstants.CURRENCY_LAYER_LIST + "?access_key=" + ApiConstants.ACCESS_KEY)
    Call<Currencies> getCurrencies();

    @GET(ApiConstants.BASE_CURRENCY_LAYER + ApiConstants.CURRENCY_LAYER_LIVE + "?access_key=" + ApiConstants.ACCESS_KEY)
    Call<Live> getLive(
            @Query("source") String currency
    );
}
