package forfrt.sheffiled.edu.assignment;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * The adapter classs for the outer RecyclerView
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.View_Holder> {
    private Context context;
    private static List<GalleryColumns> columns;

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

        if (holder!=null && columns.get(position)!=null) {
            Log.v("GalleryAdapter", "onBindViewHolder: "+position);
            holder.column_title.setText(columns.get(position).column_title);

            int number_of_column=4;
            holder.recyclerView.setLayoutManager(new GridLayoutManager(this.context, number_of_column));

            ColumnAdapter columnAdapter=new ColumnAdapter(position, columns.get(position).images);
            holder.recyclerView.setAdapter(columnAdapter);

        }
    }

    @Override
    public int getItemCount() {
        return columns.size();
    }

    /**
     *
     * @return All GalleryColumns added into this recyclerView, so other activity could get specific
     * photo by column_id and position in that column
     */
    public static List<GalleryColumns> getItems(){
        return columns;
    }

    public class View_Holder extends RecyclerView.ViewHolder {
        TextView column_title;
        RecyclerView recyclerView;

        View_Holder(View itemView) {
            super(itemView);
            column_title = (TextView) itemView.findViewById(R.id.gallery_columns_title);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.gallery_columns_recycle);
        }
    }
}
