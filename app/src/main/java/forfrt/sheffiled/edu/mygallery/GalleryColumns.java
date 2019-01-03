package forfrt.sheffiled.edu.mygallery;

import java.util.List;

/**
 * this class represents the data supporting the adapter
 */

class GalleryColumns {
    protected String column_title;
    protected List<ColumnImage> images;

    public GalleryColumns(String sort_title, List<ColumnImage> images) {
        this.column_title = column_title;
        this.images=images;
    }
}
