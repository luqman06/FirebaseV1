package com.example.firebasev1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btCreateDB;
    private Button btViewBD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btCreateDB = (Button) findViewById(R.id.bt_createdata);
        btViewBD = (Button) findViewById(R.id.bt_viewdata);

        btCreateDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //kelas yang di jalankan

                startActivity(FireBAseDBCreateActivity2.getActIntent(MainActivity.this));

            }
        });
        btViewBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(FirebaseDBReadActivity2.getActIntent(MainActivity.this));
            }
        });


    }
}
