package com.example.johnny.snapcalendar;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;

/**
 * Created by brianzhang on 2017-01-21.
 */

public class DateParse {

    private String string;
    private DateFormat format;
    private Date date;

    public DateParse() {
        format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
    }

    public void printDate(String string) throws ParseException {
        this.string = string;
        date = format.parse(string);
        Log.d("printDate", String.valueOf(date));
    }

    public Date getDate() {
        return date;
    }
}
