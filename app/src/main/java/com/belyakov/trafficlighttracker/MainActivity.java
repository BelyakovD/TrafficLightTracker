package com.belyakov.trafficlighttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ListView trafficLightList;
    ArrayAdapter<TrafficLight> arrayAdapter;
    private Timer mTimer;
    private MyTimerTask mMyTimerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trafficLightList = (ListView)findViewById(R.id.list);

        mTimer = new Timer();
        mMyTimerTask = new MyTimerTask();
        mTimer.schedule(mMyTimerTask, 0, 1000);

        trafficLightList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TrafficLight trafficLight = arrayAdapter.getItem(position);
                if(trafficLight!=null) {
                    Intent intent = new Intent(getApplicationContext(), TrafficLightActivity.class);
                    intent.putExtra("id", trafficLight.getId());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();

        List<TrafficLight> trafficLights = adapter.getTrafficLights();

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, trafficLights);
        trafficLightList.setAdapter(arrayAdapter);
        adapter.close();
    }

    public void add(View view){
        Intent intent = new Intent(this, TrafficLightActivity.class);
        startActivity(intent);
    }

    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    DatabaseAdapter adapter = new DatabaseAdapter(getApplicationContext());
                    adapter.open();
                    List<TrafficLight> trafficLights = adapter.getTrafficLights();
                    arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, trafficLights);
                    trafficLightList.setAdapter(arrayAdapter);
                    adapter.close();
                }
            });
        }
    }
}

