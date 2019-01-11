package forfrt.sheffiled.edu.assignment.model;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.util.Log;


import java.util.List;

import forfrt.sheffiled.edu.assignment.EditImageViewInterface;

public class PhotoModel {
    private final PhotoDAO mPhotoDao;

    public PhotoModel(Context context) {

        PhotoDatabase db = PhotoDatabase.getDatabase(context);
        mPhotoDao = db.photoDao();
    }

    /**
     *  it generates a random integer (0 and 1) and returns either an error or a correct message
     * @param title
     * @param description
     */
    public void updateTitleDesc(String title, String description, String filePath, EditImageViewInterface editViewInterface) {
        if (!title.isEmpty() && (!description.isEmpty())) {
            // data insertion cannot be done on the UI thread. Use an ASync process!!
            new UpdateDbAsync(this.mPhotoDao,
                    title, description, filePath, editViewInterface).execute();
        } else{
            editViewInterface.titleDescritpionError(title, description, "Tile or Description should not be empty");
        }
    }

    public void insertSelectedImages(List<PhotoData> photoDatas) {
        new InsertDbAsync(this.mPhotoDao, photoDatas).execute();

    }

    public LiveData<PhotoData> getPhotoData(String filePath){
        return this.mPhotoDao.retrieveOneData(filePath);
    }

    private static class UpdateDbAsync extends AsyncTask<Void, Void, Void> {
        private final PhotoDAO photoDao;
        private final String title;
        private final String description;
        private final String filePath;
        public EditImageViewInterface viewInterface=null;

        public UpdateDbAsync(PhotoDAO photoDao,
                                 String title, String description, String filePath,
                                 EditImageViewInterface viewInterface) {
            this.photoDao = photoDao;
            this.title = title;
            this.description = description;
            this.filePath = filePath;
            this.viewInterface = viewInterface;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            Log.v("InsertIntoAsync", "photoDao.insert");
            this.photoDao.updateTitleDesc(title, description, filePath);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.v("InsertIntoAsync", "presenter.titleDescriptionInserted");
            viewInterface.titleDescritpionInsertedFeedback(this.title, this.description);
        }
    }

    private static class InsertDbAsync extends AsyncTask<Void, Void, Void> {
        private final PhotoDAO photoDao;
        private final List<PhotoData> photoDatas;

        public InsertDbAsync(PhotoDAO photoDao, List<PhotoData> photoDatas) {
            this.photoDao = photoDao;
            this.photoDatas = photoDatas;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            Log.v("InsertIntoAsync", "photoDao.insert");
            for(PhotoData photoData: this.photoDatas){
                this.photoDao.insert(photoData);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}
