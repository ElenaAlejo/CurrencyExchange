package alejoelena.android.uoc.edu.currencyexchange;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import alejoelena.android.uoc.edu.currencyexchange.rest.content.CurrenciesContent;
import alejoelena.android.uoc.edu.currencyexchange.rest.content.LiveContent;
import alejoelena.android.uoc.edu.currencyexchange.rest.model.Live;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import static java.lang.String.format;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String UPDATE = "Update";
    MyRecyclerViewAdapter adapter;
    private PeriodicWorkRequest saveRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // data to populate the RecyclerView with

        final String[] data = new String[] {"Loading..."};

        // set up the RecyclerView
        new Thread() {
            @Override
            public void run() {
                try {

                    CurrenciesContent.Init();
                    LiveContent.Init("USD");

                } catch (final Exception ex) {
                    // TODO add message
                }
                // code runs in a thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Spinner s = findViewById(R.id.spinner);
                        List<String> list = new ArrayList<String>(CurrenciesContent.currencies.getCurrencies().keySet());

                        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),
                                android.R.layout.simple_spinner_item, list);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        s.setAdapter(adapter);
                        s.setSelection(list.indexOf("USD"));

                        createRecyclerAdapter("USD");
                    }

                });
            }
        }.start();


        Spinner s = findViewById(R.id.spinner);
        s.setOnItemSelectedListener(this);
        createRecycleView(data);

        createPeriodicWorker((String) s.getSelectedItem());

        observeWork();

    }

    private void createRecycleView(String[] data) {
        RecyclerView recyclerView = findViewById(R.id.rvNumbers);
        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new MyRecyclerViewAdapter(this, data);
        recyclerView.setAdapter(adapter);
    }

    private void observeWork() {
        WorkManager.getInstance().getWorkInfosByTagLiveData(UPDATE).observe(this, new Observer<List<WorkInfo>>() {
            @Override
            public void onChanged(@Nullable List<WorkInfo> workInfos) {
                if(workInfos != null){
                    for(WorkInfo info : workInfos){
                        Spinner s = findViewById(R.id.spinner);
                        if(info.getState().isFinished()){
                            createRecyclerAdapter((String) s.getSelectedItem());
                            Toast.makeText(getApplicationContext(), "Data updated",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    }
                }
            });
    }


    private void createPeriodicWorker(String s) {
        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(true)
                .build();

        Data workerData = new Data.Builder().putString("Currency", s).build();
        saveRequest =
                new PeriodicWorkRequest.Builder(UpdateWorker.class, 30, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .setInputData(workerData)
                        .addTag(UPDATE)
                        .build();

        WorkManager.getInstance()
                .enqueueUniquePeriodicWork(UPDATE, ExistingPeriodicWorkPolicy.KEEP, saveRequest);
    }

    private void createRecyclerAdapter(String currency) {
        if (LiveContent.live != null && LiveContent.live.containsKey(currency)) {

            RecyclerView recyclerView = findViewById(R.id.rvNumbers);
            MyRecyclerViewAdapter adapterR = new MyRecyclerViewAdapter(getApplicationContext(),
                    LiveContent.live.get(currency).getQuotes());
            recyclerView.setAdapter(adapterR);
        }
    }

    @Override
    public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {
        parent.getItemAtPosition(position);
        ((TextView) view).setTextColor(Color.RED);

        // update WorkManager with new value.
        WorkManager.getInstance().cancelAllWork();
        createPeriodicWorker((String) parent.getItemAtPosition(position));

        new Thread() {
            @Override
            public void run() {
                try {

                    LiveContent.Init((String) parent.getItemAtPosition(position));

                } catch (final Exception ex) {
                    // TODO add message
                }
                // code runs in a thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        createRecyclerAdapter((String) parent.getItemAtPosition(position));
                    }

                });

            }
        }.start();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
