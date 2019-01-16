package forfrt.sheffiled.edu.assignment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Util {

    public static byte[] serialize(Bitmap map) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
//        os.writeObject(obj);
//        return out.toByteArray();

        map.compress(Bitmap.CompressFormat.PNG, 100, out);
        BitmapDataObject bitmapDataObject = new BitmapDataObject();
        bitmapDataObject.imageByteArray = out.toByteArray();
        os.writeObject(bitmapDataObject);
        return out.toByteArray();
    }

    public static Bitmap deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
//        return is.readObject();


        BitmapDataObject bitmapDataObject = (BitmapDataObject)is.readObject();
        Bitmap image = BitmapFactory.decodeByteArray(bitmapDataObject.imageByteArray, 0, bitmapDataObject.imageByteArray.length);
        return image;
    }

    public static int insertIntoSortedDates(Date date, List<Date> dates) {
        if(dates==null) return -1;
        int size = dates.size();

        Log.v("onPhotosReturned", "Size of Current Dates"+size);
        if (size == 0) {
            dates.add(date);
            Log.v("onPhotosReturned", "Return position 0: "+0);
            return 0;
        }
        for (int i = 0; i < size; i++) {
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
            date = format.parse(dateStr);
            for (int i = 0; i < size; i++) {
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

    protected static class BitmapDataObject implements Serializable {
        private static final long serialVersionUID = 111696345129311948L;
        public byte[] imageByteArray;
    }
}
