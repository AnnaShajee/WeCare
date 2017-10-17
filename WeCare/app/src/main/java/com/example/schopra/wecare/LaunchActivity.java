package com.example.schopra.wecare;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LaunchActivity extends AppCompatActivity {

    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Log.d("AppDebug", "getActionBar is null: "+ String.valueOf(getActionBar() == null));

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1e293a")));
        addListenerOnButton();
    }

    public void addListenerOnButton() {

        final Context context = this;

        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);

            }

        });
    }

}
