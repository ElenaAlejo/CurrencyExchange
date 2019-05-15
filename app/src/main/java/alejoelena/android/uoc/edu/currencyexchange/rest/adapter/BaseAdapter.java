package alejoelena.android.uoc.edu.currencyexchange.rest.adapter;

import alejoelena.android.uoc.edu.currencyexchange.BuildConfig;
import alejoelena.android.uoc.edu.currencyexchange.rest.constants.ApiConstants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseAdapter {
    private static Retrofit retrofit;
    private static final HttpLoggingInterceptor.Level LEVEL_LOG = HttpLoggingInterceptor.Level.BODY;

    BaseAdapter() {
        init(ApiConstants.BASE_CURRENCY_LAYER);
    }

    private void init(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                    .client(getClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    private OkHttpClient getClient() {
        OkHttpClient.Builder builderClientHttp = new OkHttpClient().newBuilder();
        // Show HTTPS logs in dev mode
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(LEVEL_LOG);
            builderClientHttp.addInterceptor(interceptor);
        }
        return builderClientHttp.build();
    }

    <T> T createService(Class<T> _class) {
        return retrofit.create(_class);
    }
}
