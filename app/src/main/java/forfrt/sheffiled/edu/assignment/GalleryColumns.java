package forfrt.sheffiled.edu.assignment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import forfrt.sheffiled.edu.assignment.model.PhotoData;

/**
 * this class represents the data supporting the adapter
 */

class GalleryColumns {
    protected String column_title;
    protected List<PhotoData> photoDatas;
    protected List<Date> dates;
//    protected List<ColumnImage> images;

    public GalleryColumns(String column_title) {
//        this.images=new ArrayList<ColumnImage>();
        this.column_title = column_title;
        this.photoDatas=new ArrayList<PhotoData>();
        this.dates=new ArrayList<Date>();
    }

    public int addDate(Date date){
        int position=Util.insertIntoSortedDates(date, this.dates);
        return position;
    }

    public void addPhotoDate(int index, PhotoData photoData){
        if(index==this.photoDatas.size()) {
            this.photoDatas.add(photoData);
        }
        else{
            this.photoDatas.add(index, photoData);
        }
    }

    public List<Date> getDates(){
        return this.dates;
    }
}
