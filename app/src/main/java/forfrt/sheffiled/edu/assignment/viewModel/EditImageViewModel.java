package forfrt.sheffiled.edu.assignment.viewModel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import forfrt.sheffiled.edu.assignment.EditImageViewInterface;
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

    public void updateTitleDescByFilePath(String title, String description, String filePath, EditImageViewInterface editImageViewInterface) {
        this.photoModel.updateTitleDescByFilePath(title, description, filePath, editImageViewInterface);
    }

    public void updateTitleDescByGuid(String title, String description, String guid, EditImageViewInterface editImageViewInterface) {
        this.photoModel.updateTitleDescByGuid(title, description, guid, editImageViewInterface);
    }

    public LiveData<PhotoData> getPhotoDataByFilePath(String filePath){
        LiveData<PhotoData> photodata=this.photoModel.getPhotoDataByFilePath(filePath);

        if (photodata == null) {
            photodata = new MutableLiveData<PhotoData>();
        }
        return photodata;
    }

    public LiveData<PhotoData> getPhotoDataByGuid(String guid){
        LiveData<PhotoData> photodata=this.photoModel.getPhotoDataByGuid(guid);

        if (photodata == null) {
            photodata = new MutableLiveData<PhotoData>();
        }
        return photodata;
    }

    public LiveData<List<PhotoData>> getAllImage() {
        return this.photoModel.getAllImage();
    }
}
