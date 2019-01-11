package forfrt.sheffiled.edu.assignment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import forfrt.sheffiled.edu.assignment.model.PhotoData;
import forfrt.sheffiled.edu.assignment.presenter.EditImageViewModel;
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

        this.editViewModel=ViewModelProviders.of(this).get(EditImageViewModel.class);
//        this.initData();
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

//    private void initData(){
//
//        ArrayList<ColumnImage> column_1_images=new ArrayList<ColumnImage>();
//        column_1_images.add(new ColumnImage(R.drawable.joe1));
//        column_1_images.add(new ColumnImage(R.drawable.joe1));
//        column_1_images.add(new ColumnImage(R.drawable.joe1));
//        column_1_images.add(new ColumnImage(R.drawable.joe2));
//        column_1_images.add(new ColumnImage(R.drawable.joe3));
//
//        ArrayList<ColumnImage> column_2_images=new ArrayList<ColumnImage>();
//        column_2_images.add(new ColumnImage(R.drawable.joe2));
//        column_2_images.add(new ColumnImage(R.drawable.joe2));
//        column_2_images.add(new ColumnImage(R.drawable.joe2));
//        column_2_images.add(new ColumnImage(R.drawable.joe2));
//        column_2_images.add(new ColumnImage(R.drawable.joe2));
//        column_2_images.add(new ColumnImage(R.drawable.joe3));
//        column_2_images.add(new ColumnImage(R.drawable.joe1));
//
//        ArrayList<ColumnImage> column_3_images=new ArrayList<ColumnImage>();
//        column_3_images.add(new ColumnImage(R.drawable.joe3));
//        column_3_images.add(new ColumnImage(R.drawable.joe1));
//        column_3_images.add(new ColumnImage(R.drawable.joe2));
//
//        this.columns.add(new GalleryColumns("column_1", column_1_images));
//        this.columns.add(new GalleryColumns("column_2", column_2_images));
//        this.columns.add(new GalleryColumns("column_3", column_3_images));
//    }

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
//        ArrayList<ColumnImage> column_4_images=new ArrayList<ColumnImage>();



        try {
            String filePath, dateStr, day;
            ExifInterface exif;
            Date fullDate;
            int position;

            SimpleDateFormat fullFormat=new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
            SimpleDateFormat dayFormat=new SimpleDateFormat("yyyy:MM:dd");
            List<ColumnImage> newColumn;
            for(File file: returnedPhotos){
                filePath=file.getAbsolutePath();
                exif=new ExifInterface(filePath);

                dateStr=exif.getAttribute(ExifInterface.TAG_DATETIME);
                fullDate=fullFormat.parse(dateStr);
                day=dayFormat.format(fullDate);

                int index=this.days.indexOf(day);
                Log.v("onPhotosReturned", "-----------------------");
                Log.v("onPhotosReturned", "The datetime of this Photo is: "+dateStr);
                Log.v("onPhotosReturned", "The day of this Photo is: "+day);
                Log.v("onPhotosReturned", "Index of this Photo is :"+index);
                Log.v("onPhotosReturned", "filePath of this Photo is :"+filePath);
                if(index!=-1){
                    position=this.columns.get(index).addColumnImage(new ColumnImage(file, exif), fullDate);
                    if(position==-1){

                    }else{
                        this.photoDatas.add(new PhotoData(filePath, index, position, exif));
                        Log.v("onPhotosReturned", "The Index Photo is :"+index);
                        Log.v("onPhotosReturned", "The Position Photo is :"+position);
//                        this.editViewModel.insertSelectedImage(filePath, index, position, exif);
                    }

                }else{
                    index=Util.insertIntoSortedDates(day, this.days, dayFormat);
                    if(index==-1){

                    }else{
                        this.columns.add(index, new GalleryColumns(day));
                        this.columns.get(index).addColumnImage(new ColumnImage(file, exif), fullDate);

                        this.photoDatas.add(new PhotoData(filePath, index, 0, exif));
                        Log.v("onPhotosReturned", "The Index Photo is :"+index);
                        Log.v("onPhotosReturned", "The Position Photo is :"+0);
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

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.sortDate:
                Toast.makeText(this, "Sort by Date", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sortPlace:
                Toast.makeText(this, "Sort by Place", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
                break;
        }

        return false;
    }

}
