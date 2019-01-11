package forfrt.sheffiled.edu.assignment.presenter;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.media.ExifInterface;

import java.io.IOException;
import java.util.List;

import forfrt.sheffiled.edu.assignment.EditImageViewInterface;
import forfrt.sheffiled.edu.assignment.Util;
import forfrt.sheffiled.edu.assignment.model.PhotoData;
import forfrt.sheffiled.edu.assignment.model.PhotoModel;

public class EditImageViewModel extends AndroidViewModel {

    PhotoModel photoModel;

    public EditImageViewModel(Application application) {
        super(application);

        this.photoModel=new PhotoModel(application);
    }

    public void insertSelectedImages(List<PhotoData> photoDatas){
        this.photoModel.insertSelectedImages(photoDatas);
    }

    public void updateTitleDesc(String title, String description, String filePath, EditImageViewInterface editImageViewInterface) {
        this.photoModel.updateTitleDesc(title, description, filePath, editImageViewInterface);
    }

    public LiveData<PhotoData> getPhotoData(String filePath){
        LiveData<PhotoData> photodata=this.photoModel.getPhotoData(filePath);

        if (photodata == null) {
            photodata = new MutableLiveData<PhotoData>();
        }
        return photodata;
    }

}
