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
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class PatientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(preferences.contains("mContact")) {
            String emergency = preferences.getString("mContact","");
            EditText eContact = (EditText)findViewById(R.id.number);
            eContact.setText(emergency);
        }

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
                    List<ResolveInfo> activities = packageManager.queryIntentActivities(callEmergency, PackageManager.MATCH_DEFAULT_ONLY);
                    boolean isIntentSafe = activities.size() > 0;
                    if (isIntentSafe) {
                        startActivity(callEmergency);
                    }
                    else {
                        Toast.makeText(PatientActivity.this, "No app to handle activity.", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Snackbar.make(view, "Emergency Contact not set.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void saveDetails(View view) {
        // All contents to be saved in Shared Preferences

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        String number = ((EditText)findViewById(R.id.number)).getText().toString();
        editor.putString("mContact", number);
        editor.apply();
        finish();
    }

}
