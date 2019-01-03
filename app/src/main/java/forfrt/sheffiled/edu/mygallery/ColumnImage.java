package forfrt.sheffiled.edu.mygallery;

import java.io.File;

public class ColumnImage {

    int image=-1;
    File file=null;


    public ColumnImage(int image) {
        this.image = image;
        }
    public ColumnImage(File fileX) {
            this.file= fileX;
        }
}
