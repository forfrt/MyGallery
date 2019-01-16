package forfrt.sheffiled.edu.assignment.view;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import forfrt.sheffiled.edu.assignment.R;
import forfrt.sheffiled.edu.assignment.model.PhotoData;
import forfrt.sheffiled.edu.assignment.viewModel.EditImageViewModel;

/**
 * Activity to show the detailed information about photo.
 * Users can edit the title and description of photo at here.
 */
public class EditImageActivity extends AppCompatActivity implements EditImageViewInterface {

    EditImageViewModel viewModel;
    private PhotoData photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_edit);

        this.viewModel=ViewModelProviders.of(this).get(EditImageViewModel.class);
        final EditImageViewInterface viewInterface=this;

        Bundle b = getIntent().getExtras();
        int position=-1;
        int column_id=-1;
        if(b != null) {
            position = b.getInt("position");
            column_id = b.getInt("column_id");

            if (position!=-1 && column_id!=-1){
                ImageView imageView = (ImageView) findViewById(R.id.image);
                final AutoCompleteTextView titleView = (AutoCompleteTextView) findViewById(R.id.title);
                final AutoCompleteTextView descriptionView = (AutoCompleteTextView) findViewById(R.id.description);
                final TextView timeView=(TextView) findViewById(R.id.time);

                this.photo= GalleryAdapter.getItems().get(column_id).images.get(position).photoData;

                /**
                 * Get the information about this photo by GUID or by filePath
                 */
                if(photo.getGuid()!=null){
                    this.viewModel.getPhotoDataByGuid(photo.getGuid()).observe(this, newValue->{
                        if(newValue.get(0)!=null){
                            titleView.setText(newValue.get(0).getTitle());
                            descriptionView.setText(newValue.get(0).getDescription());
                            Log.v("EditImageActivity", "Query PhotoData with guid: "
                                    +photo.getGuid()+" title:"+newValue.get(0).getTitle()+" desc"+newValue.get(0).getDescription());
                        }
                    });
                }else if(photo.getFilePath()!=null){
                    this.viewModel.getPhotoDataByFilePath(photo.getFilePath()).observe(this, newValue->{
                        if(newValue!=null){
                            titleView.setText(newValue.get(0).getTitle());
                            descriptionView.setText(newValue.get(0).getDescription());
                            Log.v("EditImageActivity", "Query PhotoData with FilePath: "
                                    +newValue.get(0).getFilePath()+" title:"+newValue.get(0).getTitle()+" desc"+newValue.get(0).getDescription());
                        }
                    });
                }else{
                    Log.v("EditImageActivity", "Update PhotoData have no FilePath and no Guid");
                }


                if(this.photo.getFilePath()!=null){
                    Bitmap myBitmap = BitmapFactory.decodeFile(this.photo.getFilePath());
                    imageView.setImageBitmap(myBitmap);
                }else{
                    Log.v("EditImageActivity", "Update PhotoData have no FilePath");
                }

                Button button =  findViewById(R.id.store_data);
                /**
                 * Edit and update the title and description of this photo
                 */
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String title = titleView.getText().toString();
                        String description = descriptionView.getText().toString();

                        // hiding the keyboard after button click
                        InputMethodManager imm = (InputMethodManager)getSystemService(EditImageActivity.this.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(descriptionView.getWindowToken(), 0);

                        photo.setTitle(title);
                        photo.setDescription(description);

                        Log.v("EditImageActivity", "Update PhotoData with FilePath: "
                                +photo.getFilePath()+" title:"+title+" desc"+description);

                        viewModel.updateTitleDesc(photo, viewInterface);

//                        if(photo.getGuid()!=null){
//                            viewModel.updateTitleDescByGuid(title, description, photo.getGuid(), viewInterface);
//                            Log.v("EditImageActivity", "Update PhotoData with GUID: "
//                                    +photo.getGuid()+" title:"+title+" desc"+description);
//                        }else if(photo.getFilePath()!=null){
//                            viewModel.updateTitleDescByFilePath(title, description, photo.getFilePath(), viewInterface);
//                            Log.v("EditImageActivity", "Update PhotoData with FilePath: "
//                                    +photo.getFilePath()+" title:"+title+" desc"+description);
//                        }else{
//                            Log.v("EditImageActivity", "Update PhotoData have no FilePath and no Guid");
//                        }
                    }
                });
            }
        }
    }

    @Override
    public void titleDescritpionInsertedFeedback(String title, String description) {
        View anyView=EditImageActivity.this.findViewById(R.id.description);

        String stringToShow= "Correctly inserted Title: " +title + " Description: "+ description;
        Snackbar.make(anyView, stringToShow, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void titleDescritpionError(String title, String description, String errorString) {
        View anyView=EditImageActivity.this.findViewById(R.id.description);

        String stringToShow= "Error in inserting: " +errorString;
        Snackbar.make(anyView, stringToShow, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

}
