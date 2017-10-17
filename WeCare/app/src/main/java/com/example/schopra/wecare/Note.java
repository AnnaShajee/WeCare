package com.example.schopra.wecare;

import android.content.Context;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by USER_PC on 16/10/2017.
 */

public class Note implements Serializable {

    private long nDateTime;
    private String nTitle;
    private String nContent;

    public Note(long datetime, String title, String content) {
        nDateTime = datetime;
        nContent = content;
        nTitle = title;
    }

    public void setContent(String content) {
        nContent = content;
    }

    public void setDateTime(long datetime) {
        nDateTime = datetime;
    }

    public void setTitle(String title) {
        nTitle = title;
    }

    public long getDateTime() {
        return nDateTime;
    }

    public String getContent() {
        return nContent;
    }

    public String getTitle() {
        return nTitle;
    }

    public String getDateTimeFormatted(Context context) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", context.getResources().getConfiguration().locale);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date(nDateTime));


    }
}
