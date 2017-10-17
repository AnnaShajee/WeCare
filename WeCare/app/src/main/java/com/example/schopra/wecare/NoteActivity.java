package com.example.schopra.wecare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    private EditText nTitle;
    private EditText nContent;
    private String nFileName;
    private Note nLoaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nTitle = (EditText)findViewById(R.id.note_title);
        nContent = (EditText)findViewById(R.id.note_content);
        nFileName = getIntent().getStringExtra("NOTE_FILE");
        if(nFileName != null && !nFileName.isEmpty()) {
            nLoaded = Utilities.getNoteByName(getApplicationContext(), nFileName);
            if(nLoaded != null) {
                nTitle.setText(nLoaded.getTitle());
                nContent.setText(nLoaded.getContent());
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notes, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save_note:
                saveNote();
                return true;
            case R.id.action_delete_note:
                deleteNote();
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }

    private void saveNote() {
        Note note;
        if(nTitle.getText().toString().isEmpty()) {
            Toast.makeText(NoteActivity.this, "Please enter the title."
                    , Toast.LENGTH_SHORT).show();
            return;
        }

        if(nContent.getText().toString().isEmpty()) {
            Toast.makeText(NoteActivity.this, "Please enter the note content."
                    , Toast.LENGTH_SHORT).show();
            return;
        }
        if(nLoaded == null) {
            note = new Note(System.currentTimeMillis(), nTitle.getText().toString(), nContent.getText().toString());
        } else {
            note = new Note(nLoaded.getDateTime(), nTitle.getText().toString(), nContent.getText().toString());
        }
        if(Utilities.saveNote(this, note)) {
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Not saved. Ensure you have enough space.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
    private void deleteNote(){
        if(nLoaded == null) {
            finish();
        }
        else{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this).setTitle("Delete")
                    .setMessage("Are you sure you wish to delete " + nTitle.getText().toString()+ "?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Utilities.deleteNote(getApplicationContext(), nLoaded.getDateTime() + Utilities.FILE_EXT);
                            Toast.makeText(getApplicationContext(), "You have deleted "
                                    + nTitle.getText().toString(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).setNegativeButton("No", null).setCancelable(false);
            dialog.show();
        }
    }
}
