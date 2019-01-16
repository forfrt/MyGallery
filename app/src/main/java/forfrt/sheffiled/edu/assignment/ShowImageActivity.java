package forfrt.sheffiled.edu.assignment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

import forfrt.sheffiled.edu.assignment.model.PhotoData;

//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import androidx.appcompat.app.AppCompatActivity;

public class ShowImageActivity extends AppCompatActivity {

    private PhotoData photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_fullscreen);

        Bundle b = getIntent().getExtras();
        int position=-1;
        int column_id=-1;
        if(b != null) {
            position = b.getInt("position");
            column_id = b.getInt("column_id");
            Log.v("ShowImageActivity", column_id+"/"+position);
            if (position!=-1){
                ImageView imageView = (ImageView) findViewById(R.id.image);

                this.photo= GalleryAdapter.getItems().get(column_id).images.get(position).photoData;

                if(this.photo==null){
                    Toast.makeText(getApplicationContext(), "Cannot edit unsaved photo", Toast.LENGTH_SHORT).show();
                }

                if(this.photo.getFilePath()!=null){
                    Bitmap myBitmap = BitmapFactory.decodeFile(this.photo.getFilePath());
                    imageView.setImageBitmap(myBitmap);
                }else{
//                    try {
//                        imageView.setImageBitmap((Bitmap) Util.deserialize(this.photo.picture));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (ClassNotFoundException e) {
//                        e.printStackTrace();
//                    }
                }
//                ColumnImage element= GalleryAdapter.getItems().get(column_id).images.get(position);
//                if (element.image!=-1) {
//                    imageView.setImageResource(element.image);
//                } else if (element.file!=null) {
//                    Bitmap myBitmap = BitmapFactory.decodeFile(element.file.getAbsolutePath());
//                    imageView.setImageBitmap(myBitmap);
//                }
            }
        }

        final int finalPosition = position;
        final int finalColumn_id = column_id;
        final Context context = getApplicationContext();

        FloatingActionButton fab_edit = (FloatingActionButton) findViewById(R.id.image_fs_edit);
        fab_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditImageActivity.class);
                intent.putExtra("position", finalPosition);
                intent.putExtra("column_id", finalColumn_id);
                context.startActivity(intent);
            }
        });
    }
}
