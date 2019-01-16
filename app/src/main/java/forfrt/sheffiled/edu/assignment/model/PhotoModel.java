package forfrt.sheffiled.edu.assignment.model;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.util.Log;


import java.io.IOException;
import java.util.List;

import forfrt.sheffiled.edu.assignment.EditImageViewInterface;
import forfrt.sheffiled.edu.assignment.Util;

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
    public void updateTitleDescByGuid(String title, String description, String guid, EditImageViewInterface editViewInterface) {
        if (!title.isEmpty() && (!description.isEmpty())) {
            // data insertion cannot be done on the UI thread. Use an ASync process!!
            Log.v("PhotoModel", String.format("title: %s, desc: %s, guid:%s", title, description, guid));
            new UpdateByGuidDbAsync(this.mPhotoDao,
                    title, description, guid, editViewInterface).execute();
        } else{
            editViewInterface.titleDescritpionError(title, description, "Tile or Description should not be empty");
        }
    }

    public void updateTitleDescByFilePath(String title, String description, String filePath, EditImageViewInterface editViewInterface) {
        if (!title.isEmpty() && (!description.isEmpty())) {
            // data insertion cannot be done on the UI thread. Use an ASync process!!
            Log.v("PhotoModel", String.format("title: %s, desc: %s, filePath:%s", title, description, filePath));
            new UpdateByFilePathDbAsync(this.mPhotoDao,
                    title, description, filePath, editViewInterface).execute();
        } else{
            editViewInterface.titleDescritpionError(title, description, "Tile or Description should not be empty");
        }
    }

    public void insertSelectedImages(List<PhotoData> photoDatas) {
        new InsertDbAsync(this.mPhotoDao, photoDatas).execute();
    }

    public LiveData<PhotoData> getPhotoDataByGuid(String guid){
        return this.mPhotoDao.retrieveOneDataByGuid(guid);
    }

    public LiveData<PhotoData> getPhotoDataByFilePath(String filePath){
//        LiveData<PhotoData> liveData= this.mPhotoDao.retrieveOneDataByFilePath(filePath);
//        PhotoData data= liveData.getValue();
//        Log.v("Query", String.format("Photo with FilePath: %s, " +
//                "desc: %s, filePath: %s Updated", data.getTitle(), data.getDescription(), data.getFilePath()));
//        return liveData;
        Log.v("Query", String.format("Query Photo with FilePath: %s", filePath));
        return this.mPhotoDao.retrieveOneDataByFilePath(filePath);
    }

    private static class UpdateByGuidDbAsync extends AsyncTask<Void, Void, Void> {
        private final PhotoDAO photoDao;
        private final String title;
        private final String description;
        private final String guid;
        public EditImageViewInterface viewInterface=null;

        public UpdateByGuidDbAsync(PhotoDAO photoDao,
                                 String title, String description, String guid,
                                 EditImageViewInterface viewInterface) {
            this.photoDao = photoDao;
            this.title = title;
            this.description = description;
            this.guid = guid;
            this.viewInterface = viewInterface;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            this.photoDao.updateTitleDescByGuid(this.title, this.description, this.guid);
            Log.v("UpdateDbAsync", String.format("Photo with FilePath: %s, " +
                    "desc: %s, guid: %s Updated", this.title, this.description, this.guid));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            viewInterface.titleDescritpionInsertedFeedback(this.title, this.description);
        }
    }

    private static class UpdateByFilePathDbAsync extends AsyncTask<Void, Void, Void> {
        private final PhotoDAO photoDao;
        private final String title;
        private final String description;
        private final String filePath;
        public EditImageViewInterface viewInterface=null;

        public UpdateByFilePathDbAsync(PhotoDAO photoDao,
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
            this.photoDao.updateTitleDescByFilePath(this.title, this.description, this.filePath);
            Log.v("UpdateDbAsync", String.format("Photo with FilePath: %s, " +
                    "desc: %s, filePath: %s Updated", this.title, this.description, this.filePath));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
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
//            Bitmap map;
//            byte[] mapBytes;
            for(PhotoData photoData: this.photoDatas){
//                    map=BitmapFactory.decodeFile(photoData.getFilePath());
//                    mapBytes=Util.serialize(map);
//                    photoData.setPicture(mapBytes);
                this.photoDao.insert(photoData);
                Log.v("InsertDbAsync", String.format("Photo with FilePath: %s, " +
                        "desc: %s, guid: %s, filePath: %s Inserted", photoData.getTitle(), photoData.getDescription(),
                        photoData.getGuid(), photoData.getFilePath()));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.v("PhotoModel", "Selected images inserted");
        }
    }

}
