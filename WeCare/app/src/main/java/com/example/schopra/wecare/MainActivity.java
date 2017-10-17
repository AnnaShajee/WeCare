package com.example.schopra.wecare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                if(preferences.contains("mContact")) {
                    String emergency = preferences.getString("mContact",null);
                    String contact = "tel:";
                    contact = contact.concat(emergency);
                    Uri number = Uri.parse(contact);
                    Intent callEmergency = new Intent(Intent.ACTION_DIAL, number);
                    PackageManager packageManager = getPackageManager();
                    List<ResolveInfo> activities = packageManager.queryIntentActivities(callEmergency, 0);
                    boolean isIntentSafe = activities.size() > 0;
                    if (isIntentSafe) {
                        startActivity(callEmergency);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "No app to handle activity.", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Snackbar.make(view, "Emergency Contact not set.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
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
