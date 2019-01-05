package forfrt.sheffiled.edu.mygallery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ColumnAdapter extends RecyclerView.Adapter<ColumnAdapter.View_Holder> {
    private Context context;
    private int column_id;
    private List<ColumnImage> column_images;

    public ColumnAdapter(int column_id, List<ColumnImage> column_images){
        this.column_id=column_id;
        this.column_images=column_images;

    }

    public ColumnAdapter(Context context, int column_id, List<ColumnImage> column_images){
        super();
        this.context=context;
        this.column_id=column_id;
        this.column_images=column_images;

    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_list_images,
                parent, false);
        View_Holder holder = new View_Holder(v);
        this.context= parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(final View_Holder holder, final int position) {

        if (holder!=null && column_images.get(position)!=null) {
            if (column_images.get(position).image!=-1) {
                holder.imageView.setImageResource(column_images.get(position).image);
            } else if (column_images.get(position).file!=null){
                Bitmap myBitmap = BitmapFactory.decodeFile(column_images.get(position).file.getAbsolutePath());
                holder.imageView.setImageBitmap(myBitmap);
            }

            final int col_id=this.column_id;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShowImageActivity.class);
                    intent.putExtra("position", position);
                    intent.putExtra("column_id", col_id);
                    context.startActivity(intent);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return this.column_images.size();
    }

    public static class View_Holder extends RecyclerView.ViewHolder {
        ImageView imageView;

        View_Holder(View itemView) {
            super(itemView);
            this.imageView= (ImageView) itemView.findViewById(R.id.image_item);
        }
    }


}
