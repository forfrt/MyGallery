package forfrt.sheffiled.edu.assignment.model;

import java.io.File;

/**
 * Data class for store all kind of images
 */
public class ImageElement {
    public int image=-1;
    public File file=null;
    public PhotoData photoData=null;

    public ImageElement(int image) {
        this.image = image;
    }

    public ImageElement(PhotoData photoData) {
        this.photoData=photoData;
    }

    public ImageElement(File file) {
        this.file=file;
    }
}
