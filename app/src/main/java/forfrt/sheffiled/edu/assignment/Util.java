package forfrt.sheffiled.edu.assignment;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Util {

    public static int insertIntoSortedDates(Date date, List<Date> dates) {
        if(dates==null) return -1;
        int size = dates.size();

        Log.v("onPhotosReturned", "Size of Current Dates"+size);
        if (size == 0) {
            dates.add(date);
            Log.v("onPhotosReturned", "Return position 0: "+0);
            return 0;
        }
        int i;
        for (i = 0; i < size; i++) {
            if (date.after(dates.get(i))) {
                dates.add(i, date);
                Log.v("onPhotosReturned", "Return position 1: "+i);
                return i;
            }
        }
        dates.add(date);
        Log.v("onPhotosReturned", "Return position 2: "+size);
        return size;
    }

    public static int insertIntoSortedDates(String dateStr, List<String> dateStrs, SimpleDateFormat format) {
        Date date = null;
        if(dateStr==null) return -1;
        int size = dateStrs.size();
        try {
            if (size == 0) {
                dateStrs.add(dateStr);
                return 0;
            }
            int i;
            date = format.parse(dateStr);
            for (i = 0; i < size; i++) {
                if (date.after(format.parse(dateStrs.get(i)))) {
                    dateStrs.add(i, dateStr);
                    return i;
                }
            }
            dateStrs.add(dateStr);
            return size;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
