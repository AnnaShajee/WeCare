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
import android.widget.RadioButton;
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
        if(preferences.contains("pName")) {
            String name = preferences.getString("pName","");
            EditText eName = (EditText)findViewById(R.id.etName);
            eName.setText(name);
        }
        if(preferences.contains("pAge")) {
            String age = preferences.getString("pAge","");
            EditText eAge = (EditText)findViewById(R.id.etAge);
            eAge.setText(age);
        }
        if(preferences.contains("pGender")){
            String gender = preferences.getString("pGender","");
            if(gender == "Male") {
                RadioButton male = (RadioButton)findViewById(R.id.etGenderM);
                male.setChecked(true);
            }
            else {
                RadioButton female = (RadioButton)findViewById(R.id.etGenderF);
                female.setChecked(true);
            }
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
        String name = ((EditText)findViewById(R.id.etName)).getText().toString();
        editor.putString("pName", name);
        String age = ((EditText)findViewById(R.id.etAge)).getText().toString();
        editor.putString("pAge", age);
        String gender = ((RadioButton)findViewById(R.id.etGenderM)).isChecked()?"Male":"Female";
        editor.putString("pGender",gender);
        editor.apply();
        finish();
    }

}
