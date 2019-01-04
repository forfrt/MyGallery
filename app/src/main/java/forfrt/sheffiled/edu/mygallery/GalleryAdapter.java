package forfrt.sheffiled.edu.mygallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.View_Holder> {
    private Context context;
    private List<GalleryColumns> columns;

    public GalleryAdapter(List<GalleryColumns> columns) {
        this.columns = columns;
    }

    public GalleryAdapter(Context cont, List<GalleryColumns> columns) {
        super();
        this.context = cont;
        this.columns = columns;
    }

    @Override
    public GalleryAdapter.View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_columns,
                parent, false);

        View_Holder holder = new View_Holder(v);
        this.context= parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(GalleryAdapter.View_Holder holder, int position) {
        //Use the provided View Holder on the onCreateViewHolder method to populate the
        // current row on the RecyclerView

        if (holder!=null && this.columns.get(position)!=null) {
            Log.v("GalleryAdapter", "onBindViewHolder: ");
            holder.column_title.setText(this.columns.get(position).column_title);

            int number_of_column=4;
            holder.recyclerView.setLayoutManager(new GridLayoutManager(this.context, number_of_column));

            ColumnAdapter columnAdapter=new ColumnAdapter(this.columns.get(position).images);
            holder.recyclerView.setAdapter(columnAdapter);

        }
        //animate(holder);
    }

    @Override
    public int getItemCount() {
        return this.columns.size();
    }

    public class View_Holder extends RecyclerView.ViewHolder {
        TextView column_title;
        RecyclerView recyclerView;


        View_Holder(View itemView) {
            super(itemView);
            column_title = (TextView) itemView.findViewById(R.id.gallery_column_title);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.grid_recycler_view);
        }
    }
}
