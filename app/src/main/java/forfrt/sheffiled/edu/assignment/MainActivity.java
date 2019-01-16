package forfrt.sheffiled.edu.assignment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import forfrt.sheffiled.edu.assignment.model.ImageElement;
import forfrt.sheffiled.edu.assignment.model.PhotoData;
import forfrt.sheffiled.edu.assignment.viewModel.EditImageViewModel;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_READ_EXTERNAL_STORAGE = 2987;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 7829;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<GalleryColumns> columns;
    private List<PhotoData> photoDatas;
    private List<String> days;
    private List<String> filePaths;

    private EditImageViewModel editViewModel;

    private AppCompatActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_main);

        this.activity=this;
        this.columns=new ArrayList<GalleryColumns>();
        this.photoDatas=new ArrayList<PhotoData>();
        this.days=new ArrayList<String>();

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent ;
                switch (item.getItemId()) {
                    case R.id.gallery_mode:
                        intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                        return true;
                    case R.id.map_mode:
                        intent = new Intent(activity, MapsActivity.class);
                        activity.startActivity(intent);
                        return true;
                    case R.id.album_mode:
                        return true;
                }
                return false;
            }
        });

        this.editViewModel=ViewModelProviders.of(this).get(EditImageViewModel.class);
        Log.v("MainActivity", "Data initilaized");

        checkPermissions(getApplicationContext());
        initEasyImage();

        mRecyclerView = (RecyclerView) findViewById(R.id.gallery_content_columns);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Log.v("MainActivity", "Layout set");
        mAdapter = new GalleryAdapter(columns);
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab_camera = (FloatingActionButton) findViewById(R.id.gallery_main_camera);
        fab_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyImage.openCamera(getActivity(), 0);
            }
        });

        FloatingActionButton fab_gal = (FloatingActionButton) findViewById(R.id.gallery_main_gallery);
        fab_gal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyImage.openGallery(getActivity(), 0);
            }
        });
    }

    private void initEasyImage() {
        EasyImage.configuration(this)
                .setImagesFolderName("EasyImage sample")
                .setCopyTakenPhotosToPublicGalleryAppFolder(true)
                .setCopyPickedImagesToPublicGalleryAppFolder(false)
                .setAllowMultiplePickInGallery(true);
    }

    private void checkPermissions(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder=new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert=alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
                }

            }
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Writing external storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
                e.printStackTrace();
            }

            @Override
            public void onImagesPicked(List<File> imageFiles, EasyImage.ImageSource source, int type) {
                onPhotosReturned(imageFiles);
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                //Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(getActivity());
                    if (photoFile != null) photoFile.delete();
                }
            }
        });
    }

    /**
     * add to the grid
     * @param returnedPhotos
     */
    private void onPhotosReturned(List<File> returnedPhotos) {

        try {
            String filePath, dateStr, day, guid;
            ExifInterface exif;
            Date fullDate;
            int position;
            Bitmap map;
            byte[] mapBytes;
            PhotoData photoData;

            SimpleDateFormat fullFormat=new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
            SimpleDateFormat dayFormat=new SimpleDateFormat("yyyy:MM:dd");
            GalleryColumns newColumn;
            for(File file: returnedPhotos){
                filePath=file.getAbsolutePath();
                exif=new ExifInterface(filePath);
                dateStr=exif.getAttribute(ExifInterface.TAG_DATETIME);
                Log.v("onPhotosReturned", "filePath of image:"+file.getAbsolutePath());
                Log.v("onPhotosReturned", "dateStr of image:"+dateStr);

                if(dateStr==null){
                    Date today=new Date();
                    ImageElement image=new ImageElement(file);
                    day=dayFormat.format(today);
                    int index=this.days.indexOf(day);
                    if(index!=-1){
                        position=this.columns.get(index).addDate(today);
                        this.columns.get(index).addPhotoDate(position, image);
                    }else {
                        newColumn=new GalleryColumns(day);
                        newColumn.addDate(today);
                        newColumn.addPhotoDate(0, image);
                        this.columns.add(0, newColumn);
                    }
                }else {

                    guid = exif.getAttribute(ExifInterface.TAG_IMAGE_UNIQUE_ID);
                    fullDate = fullFormat.parse(dateStr);
                    day = dayFormat.format(fullDate);

                    Log.v("MainActivity", String.format("file iterating"));
                    int index = this.days.indexOf(day);

                    if (index != -1) {
                        Log.v("MainActivity", String.format("index=%d", index));

                        position = this.columns.get(index).addDate(fullDate);
                        Log.v("onPhotosReturned", "index of image:" + index + " position of image: " + position);

                        photoData = new PhotoData(guid, filePath, index, position, exif);
                        this.columns.get(index).addPhotoDate(position, new ImageElement(photoData));
                        if (position == -1) {
                            Log.v("onPhotosReturned", "this.columns.get(index).dates is null");

                        } else {
                            this.photoDatas.add(photoData);
                        }

                    } else {
                        index = Util.insertIntoSortedDates(day, this.days, dayFormat);
                        Log.v("MainActivity", String.format("index=%d", index));
                        if (index == -1) {
                            Log.v("MainActivity", "index=-1");

                        } else {
                            newColumn = new GalleryColumns(day);
                            position = newColumn.addDate(fullDate);
                            photoData = new PhotoData(guid, filePath, index, position, exif);
                            newColumn.addPhotoDate(position, new ImageElement(photoData));
                            Log.v("onPhotosReturned", "index of image:" + index + "position of image: " + position);
                            this.columns.add(index, newColumn);
                            this.photoDatas.add(photoData);
                        }
                    }
                }
            }

            this.editViewModel.insertSelectedImages(this.photoDatas);

            mAdapter.notifyDataSetChanged();
            mRecyclerView.scrollToPosition(returnedPhotos.size() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AppCompatActivity getActivity(){
        return this.activity;
    }

}
