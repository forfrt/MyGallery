package forfrt.sheffiled.edu.mygallery;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Gallery;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<GalleryColumns> columns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_main);

        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        columns=new ArrayList<GalleryColumns>();
        columns.add(new GalleryColumns("column_1", column_1_images));
        columns.add(new GalleryColumns("column_2", column_2_images));
        columns.add(new GalleryColumns("column_3", column_3_images));

        Log.v("MainActivity", "Data initilaized");

//        mRecyclerView = (RecyclerView) findViewById(R.id.gallery_columns);
//        mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//
//        Log.v("MainActivity", "Layout set");
//        // specify an adapter (see also next example)
//        mAdapter = new GalleryAdapter(columns);
//        mRecyclerView.setAdapter(mAdapter);

        int number_of_column=4;
        mRecyclerView = (RecyclerView) findViewById(R.id.gallery_columns);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, number_of_column));


        Log.v("MainActivity", "Layout set");
        // specify an adapter (see also next example)
        mAdapter = new GalleryAdapter(columns);
        mRecyclerView.setAdapter(mAdapter);
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
