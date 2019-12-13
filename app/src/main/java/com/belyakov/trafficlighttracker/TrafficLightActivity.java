package com.belyakov.trafficlighttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TrafficLightActivity extends AppCompatActivity {

    private EditText nameBox;
    private Button delButton;
    private Button saveButton;
    private Button timeButton;
    private TextView greenStartTextView;
    private TextView greenEndTextView;
    private TextView redEndTextView;
    private long greenStart = 0;
    private long greenEnd = 0;
    private long redEnd = 0;

    private DatabaseAdapter adapter;
    private long trafficLightId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_light);

        nameBox = (EditText) findViewById(R.id.name);
        delButton = (Button) findViewById(R.id.deleteButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        timeButton = (Button) findViewById(R.id.timeButton);
        greenStartTextView = (TextView) findViewById(R.id.greenStartTextView);
        greenEndTextView = (TextView) findViewById(R.id.greenEndTextView);
        redEndTextView = (TextView) findViewById(R.id.redEndTextView);
        adapter = new DatabaseAdapter(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            trafficLightId = extras.getLong("id");
        }
        if (trafficLightId > 0) {
            adapter.open();
            TrafficLight trafficLight = adapter.getTrafficLight(trafficLightId);
            nameBox.setText(trafficLight.getName());
            String greenStart = new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date (trafficLight.getGreenStart() * 1000));
            greenStartTextView.setText(greenStart);
            String greenEnd = new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date (trafficLight.getGreenEnd() * 1000));
            greenEndTextView.setText(greenEnd);
            String redEnd = new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date (trafficLight.getRedEnd() * 1000));
            redEndTextView.setText(redEnd);
            adapter.close();
        } else {
            delButton.setVisibility(View.GONE);
            saveButton.setVisibility(View.GONE);
        }
    }

    public void save(View view){

        if(greenStart == 0 && greenEnd == 0 && redEnd == 0 && trafficLightId > 0) {

            adapter.open();
            TrafficLight trafficLight = adapter.getTrafficLight(trafficLightId);
            String name = nameBox.getText().toString();
            TrafficLight trafficLightNew = new TrafficLight(trafficLightId, name, trafficLight.getGreenStart(), trafficLight.getGreenEnd(), trafficLight.getRedEnd(), 1);
            adapter.update(trafficLightNew);
            adapter.close();
            goHome();
        }
        else Toast.makeText(this, "Необходимо, чтобы были введены название и 3 времени переключения", Toast.LENGTH_LONG).show();
    }

    public void delete(View view){

        adapter.open();
        adapter.delete(trafficLightId);
        adapter.close();
        goHome();
    }
    private void goHome(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void setTimes(View view) {
        if(greenStart > 0){
            if (greenEnd > 0){
                redEnd = System.currentTimeMillis()/1000;
                String redEndStr = new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date (System.currentTimeMillis()));
                redEndTextView.setText(redEndStr);
                String name = nameBox.getText().toString();

                adapter.open();
                TrafficLight trafficLight = new TrafficLight(trafficLightId, name, greenStart, greenEnd, redEnd, 1);
                if (trafficLightId > 0) {
                    adapter.update(trafficLight);
                } else {
                    adapter.insert(trafficLight);
                }
                adapter.close();
                goHome();
            }
            else {
                greenEnd = System.currentTimeMillis()/1000;
                String greenEndStr = new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date (System.currentTimeMillis()));
                greenEndTextView.setText(greenEndStr);
            }
        }
        else {
            greenStart = System.currentTimeMillis()/1000;
            String greenStartStr = new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date (System.currentTimeMillis()));
            greenStartTextView.setText(greenStartStr);
        }
    }
}
