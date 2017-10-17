package com.example.schopra.wecare;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DiaryActivity extends AppCompatActivity {

    private ListView nListViewNotes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nListViewNotes = (ListView)findViewById(R.id.main_ListView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_journal, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_main_new_note:
                startActivity(new Intent(this, NoteActivity.class));
                return true;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        nListViewNotes.setAdapter(null);

        ArrayList<Note> notes = Utilities.getSavedNotes(this);

        if(notes == null || notes.size()==0) {
            Toast.makeText(this, "No saved notes.", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            NoteAdapter na = new NoteAdapter(this, R.layout.note_list, notes);
            nListViewNotes.setAdapter(na);

            nListViewNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String fileName = ((Note)nListViewNotes.getItemAtPosition(position)).getDateTime()
                            + Utilities.FILE_EXT;
                    Intent viewNoteIntent = new Intent(getApplicationContext(), NoteActivity.class);
                    viewNoteIntent.putExtra("NOTE_FILE", fileName );
                    startActivity(viewNoteIntent);
                }
            });
        }
    }


    public void next(View view) {
        Intent intentDiary = new Intent(this, NoteActivity.class);
        startActivity(intentDiary);
    }
}
