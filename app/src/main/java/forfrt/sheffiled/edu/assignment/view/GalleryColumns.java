package forfrt.sheffiled.edu.assignment.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import forfrt.sheffiled.edu.assignment.Util;
import forfrt.sheffiled.edu.assignment.model.ImageElement;
import forfrt.sheffiled.edu.assignment.model.PhotoData;

/**
 * this class represents the data supporting the adapter GalleryAdapter
 */

class GalleryColumns {
    protected String column_title;
    protected List<ImageElement> images;
    // Needed to get the index of new added ImageElement
    protected List<Date> dates;

    public GalleryColumns(String column_title) {
        this.column_title = column_title;
        this.images=new ArrayList<ImageElement>();
        this.dates=new ArrayList<Date>();
    }

    /**
     * Add date to this.dates and get the current index to insert the ImageElement
     * @param date
     * @return The index to insert the ImageElement
     */
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
