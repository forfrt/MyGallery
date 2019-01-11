package forfrt.sheffiled.edu.assignment;

import android.media.ExifInterface;

import java.io.File;

public class ColumnImage {

    int image=-1;
    File file=null;
    ExifInterface exif;

    public ColumnImage(int image, File file, ExifInterface exif) {
        this.image = image;
        this.file = file;
        this.exif = exif;
    }

    public ColumnImage(int image) {
        this.image = image;
        }
    public ColumnImage(File fileX) {
            this.file= fileX;
        }
    public ColumnImage(File file, ExifInterface exif) {
        this.file = file;
        this.exif = exif;
    }
}
