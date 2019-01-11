package forfrt.sheffiled.edu.assignment;

import android.app.Activity;
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

//import androidx.appcompat.app.AppCompatActivity;

import forfrt.sheffiled.edu.assignment.presenter.EditImageViewModel;

public class EditImageActivity extends AppCompatActivity implements EditImageViewInterface {

    private ColumnImage columnImage;
    EditImageViewModel viewModel;

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
            Log.v("ShowImageActivity", column_id+"/"+position);
            if (position!=-1 && column_id!=-1){
                ImageView imageView = (ImageView) findViewById(R.id.image);
                final AutoCompleteTextView titleView = (AutoCompleteTextView) findViewById(R.id.title);
                final AutoCompleteTextView descriptionView = (AutoCompleteTextView) findViewById(R.id.description);

                this.columnImage= GalleryAdapter.getItems().get(column_id).images.get(position);
                String filePath=this.columnImage.file.getAbsolutePath();
                Log.v("EditImageActivity", filePath);

                this.viewModel.getPhotoData(filePath).observe(this, newValue->{
                    if(newValue!=null){
                        titleView.setText(newValue.getTitle());
                        descriptionView.setText(newValue.getDescription());
                    }
                });

                if (this.columnImage.image!=-1) {
                    imageView.setImageResource(this.columnImage.image);
                } else if (this.columnImage.file!=null) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(this.columnImage.file.getAbsolutePath());
                    imageView.setImageBitmap(myBitmap);
                }

                Button button =  findViewById(R.id.store_data);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String title = titleView.getText().toString();
                        String description = descriptionView.getText().toString();

                        // hiding the keyboard after button click
                        InputMethodManager imm = (InputMethodManager)getSystemService(EditImageActivity.this.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(descriptionView.getWindowToken(), 0);

                        viewModel.updateTitleDesc(title, description, columnImage.file.getAbsolutePath(), viewInterface);
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
