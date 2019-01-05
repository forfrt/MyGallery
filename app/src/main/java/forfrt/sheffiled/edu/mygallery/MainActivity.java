package forfrt.sheffiled.edu.mygallery;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_READ_EXTERNAL_STORAGE = 2987;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 7829;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<GalleryColumns> columns;

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_main);

        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.activity=this;

        this.initData();
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

    private void initData(){
        this.columns=new ArrayList<GalleryColumns>();

        ArrayList<ColumnImage> column_1_images=new ArrayList<ColumnImage>();
        column_1_images.add(new ColumnImage(R.drawable.joe1));
        column_1_images.add(new ColumnImage(R.drawable.joe1));
        column_1_images.add(new ColumnImage(R.drawable.joe1));
        column_1_images.add(new ColumnImage(R.drawable.joe2));
        column_1_images.add(new ColumnImage(R.drawable.joe3));

        ArrayList<ColumnImage> column_2_images=new ArrayList<ColumnImage>();
        column_2_images.add(new ColumnImage(R.drawable.joe2));
        column_2_images.add(new ColumnImage(R.drawable.joe2));
        column_2_images.add(new ColumnImage(R.drawable.joe2));
        column_2_images.add(new ColumnImage(R.drawable.joe2));
        column_2_images.add(new ColumnImage(R.drawable.joe2));
        column_2_images.add(new ColumnImage(R.drawable.joe3));
        column_2_images.add(new ColumnImage(R.drawable.joe1));

        ArrayList<ColumnImage> column_3_images=new ArrayList<ColumnImage>();
        column_3_images.add(new ColumnImage(R.drawable.joe3));
        column_3_images.add(new ColumnImage(R.drawable.joe1));
        column_3_images.add(new ColumnImage(R.drawable.joe2));

        this.columns.add(new GalleryColumns("column_1", column_1_images));
        this.columns.add(new GalleryColumns("column_2", column_2_images));
        this.columns.add(new GalleryColumns("column_3", column_3_images));
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
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
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
        ArrayList<ColumnImage> column_4_images=new ArrayList<ColumnImage>();

//        for(File file: returnedPhotos){
//            ExifInterface exif=new ExifInterface(file);
//            Log.v("onPhotosReturned", );
//        }
        this.columns.add(new GalleryColumns("column_4", getImageElements(returnedPhotos)));
        mAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(returnedPhotos.size() - 1);
    }

    /**
     * given a list of photos, it creates a list of myElements
     * @param returnedPhotos
     * @return
     */
    private List<ColumnImage> getImageElements(List<File> returnedPhotos) {
        List<ColumnImage> imageElementList= new ArrayList<>();
        for (File file: returnedPhotos){
            ColumnImage element= new ColumnImage(file);
            imageElementList.add(element);
        }
        return imageElementList;
    }

    public Activity getActivity(){
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
            case R.id.sortPlace:
                Toast.makeText(this, "Sort by Place", Toast.LENGTH_SHORT).show();
            case R.id.setting:
                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

}
