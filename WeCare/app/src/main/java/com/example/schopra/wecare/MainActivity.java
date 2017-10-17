package com.example.schopra.wecare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String emergency = preferences.getString("emergency_contact", "");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emergency != null) {
                    Uri number = Uri.parse(emergency);
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, number);

                    PackageManager packageManager = getPackageManager();
                    List activities = packageManager.queryIntentActivities(callIntent, PackageManager.MATCH_DEFAULT_ONLY);
                    boolean isIntentSafe = activities.size() > 0;

                    if (isIntentSafe != false) {
                        startActivity(callIntent);
                    }

                }
                else {
                    Toast.makeText(MainActivity.this, "No emergency contact has been set.",
                            Toast.LENGTH_SHORT).show();
                }
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
                Intent intent;
                if (position == 0) {
                    intent = new Intent(MainActivity.this, PatientActivity.class);
                }
                else if (position == 1) {
                    intent = new Intent(MainActivity.this, LearningActivity.class);
                }
                else if (position == 2) {
                    intent = new Intent(MainActivity.this, Calendar.class);
                }
                else if (position == 3) {
                    intent = new Intent(MainActivity.this, DiaryActivity.class);
                }
                else if (position == 4) {
                    intent = new Intent(MainActivity.this, PatientActivity.class);
                }
                else if (position == 5) {
                    intent = new Intent(MainActivity.this, PatientActivity.class);
                }
                else {
                    intent = new Intent(MainActivity.this, LaunchActivity.class);
                }
                startActivity(intent);
            }
        });
    }

}
