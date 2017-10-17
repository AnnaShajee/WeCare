package com.example.schopra.wecare;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by USER_PC on 16/10/2017.
 */

public class Utilities {

    public static final String FILE_EXT = ".bin";

    public static boolean saveNote(Context context, Note note) {
        String filename = String.valueOf(note.getDateTime()) + FILE_EXT;
        FileOutputStream fos;
        ObjectOutputStream oos;
        try {
            fos = context.openFileOutput(filename, context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(note);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static ArrayList<Note> getSavedNotes(Context context) {
        ArrayList<Note> notes = new ArrayList<>();

        File filesDir = context.getFilesDir();
        ArrayList<String> notefiles = new ArrayList<>();

        for(String file: filesDir.list()) {
            if(file.endsWith(FILE_EXT)) {
                notefiles.add(file);
            }
        }

        FileInputStream fis;
        ObjectInputStream ois;

        for(int i = 0; i < notefiles.size(); i++) {
            try{
                fis = context.openFileInput(notefiles.get(i));
                ois = new ObjectInputStream(fis);
                notes.add((Note)ois.readObject());
                ois.close();
                fis.close();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }

        return notes;
    }

    public static Note getNoteByName(Context context, String fileName) {
        File file = new File(context.getFilesDir(), fileName);
        Note note;
        if(file.exists()) {

            FileInputStream fis;
            ObjectInputStream ois;

            try{
                fis = context.openFileInput(fileName);
                ois = new ObjectInputStream(fis);
                note = (Note)ois.readObject();
                ois.close();
                fis.close();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
            return note;
        }
        return null;
    }

    public static void deleteNote(Context context, String fileName) {
        File dir = context.getFilesDir();
        File file = new File(dir, fileName);
        if (file.exists()) {
            file.delete();
        }
    }
}
