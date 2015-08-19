package com.gmartinsribeiro.recyclerviewapp.utility;

import android.util.Log;

import com.gmartinsribeiro.recyclerviewapp.exception.DateException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Gonçalo Martins Ribeiro on 20-08-2015.
 */
public class DateUtils {

    public static final String TAG = "DateUtils";

    private final static String DATE_FORMAT = "dd/MM/yyyy HH:mm";

    /**
     * Converts the date string to a java object
     * @param formattedDate String of data in "yyyy-MM-dd'T'HH:mm:ss" format
     * @return Date object
     */
    public static Date parseStringToDate(String formattedDate) throws DateException {
        Date date = null;
        if (formattedDate != null) {
            SimpleDateFormat inputFormat = new SimpleDateFormat(DATE_FORMAT);
            try {
                date = inputFormat.parse(formattedDate);
            } catch (ParseException e) {
                Log.e(TAG, e.getMessage());
                throw new DateException(e.getMessage());
            }
        }
        return date;
    }

    /**
     * Converts a java date object to a string representation
     * @param date Data java object
     * @return String in "yyyy-MM-dd'T'HH:mm:ss" format
     */
    public static String parseDateToString(Date date) {
        SimpleDateFormat outputFormat = new SimpleDateFormat(DATE_FORMAT);
        return outputFormat.format(date);
    }
}
