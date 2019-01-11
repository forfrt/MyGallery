package forfrt.sheffiled.edu.assignment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * this class represents the data supporting the adapter
 */

class GalleryColumns {
    protected String column_title;
    protected List<ColumnImage> images;
    protected List<Date> dates;

    public GalleryColumns(String column_title) {
        this.column_title = column_title;
        this.images=new ArrayList<ColumnImage>();
        this.dates=new ArrayList<Date>();
    }

    public GalleryColumns(String column_title, List<ColumnImage> images) {
        this.column_title = column_title;
        this.images=images;
        this.dates=new ArrayList<Date>();
    }

    public int addColumnImage(ColumnImage image, Date date){
        int position=Util.insertIntoSortedDates(date, this.dates);
        this.images.add(position, image);
//        this.dates.add(position, date);
        return position;
    }

    public List<Date> getDates(){
        return this.dates;
    }

}
