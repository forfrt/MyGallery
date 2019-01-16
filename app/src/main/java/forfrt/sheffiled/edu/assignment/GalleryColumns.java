package forfrt.sheffiled.edu.assignment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import forfrt.sheffiled.edu.assignment.model.ImageElement;
import forfrt.sheffiled.edu.assignment.model.PhotoData;

/**
 * this class represents the data supporting the adapter
 */

class GalleryColumns {
    protected String column_title;
    protected List<ImageElement> images;
    protected List<Date> dates;
//    protected List<ColumnImage> images;

    public GalleryColumns(String column_title) {
//        this.images=new ArrayList<ColumnImage>();
        this.column_title = column_title;
        this.images=new ArrayList<ImageElement>();
        this.dates=new ArrayList<Date>();
    }

    public int addDate(Date date){
        int position=Util.insertIntoSortedDates(date, this.dates);
        return position;
    }

    public void addPhotoDate(int index, ImageElement image){
        if(index==this.images.size()) {
            this.images.add(image);
        }
        else{
            this.images.add(index, image);
        }
    }

    public List<Date> getDates(){
        return this.dates;
    }
}
