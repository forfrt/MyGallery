package forfrt.sheffiled.edu.assignment.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import forfrt.sheffiled.edu.assignment.R;
import forfrt.sheffiled.edu.assignment.model.ImageElement;

/**
 * Adapter for the inner recyclerView
 */
public class ColumnAdapter extends RecyclerView.Adapter<ColumnAdapter.View_Holder> {
    private int column_id;
    private Context context;
    // images shown in this recycleView
    private List<ImageElement> images;

    public ColumnAdapter(int column_id, List<ImageElement> images){
        this.column_id=column_id;
        this.images=images;
    }

    public ColumnAdapter(Context context, int column_id, List<ImageElement> images){
        super();
        this.context=context;
        this.column_id=column_id;
        this.images=images;
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
        ImageElement image=this.images.get(position);

        if (holder!=null && image!=null) {
            if (image.image!=-1) {
                holder.imageView.setImageResource(image.image);
            }else{
                new UploadSingleImageTask().execute(new HolderAndPosition(position, holder));
            }

            final int col_id=this.column_id;
            // Switch to the showImageActivity with information to locate the clicked photo
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(GalleryAdapter.getItems().get(col_id).images.get(position).photoData==null) {
                        Toast.makeText(context, "Cannot edit unsaved photo", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent = new Intent(context, ShowImageActivity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("column_id", col_id);
                        context.startActivity(intent);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return this.images.size();
    }

    public static class View_Holder extends RecyclerView.ViewHolder {
        ImageView imageView;

        View_Holder(View itemView) {
            super(itemView);
            this.imageView= (ImageView) itemView.findViewById(R.id.image_item);
        }
    }

    public static Bitmap decodeSampledBitmapFromResource(String filePath, int reqWidth, int reqHeight){
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    private class UploadSingleImageTask extends AsyncTask<HolderAndPosition, Void, Bitmap> {
        HolderAndPosition holdAndPos;

        @Override
        protected Bitmap doInBackground(HolderAndPosition... holderAndPositions) {
            holdAndPos=holderAndPositions[0];
            ImageElement image=images.get(holdAndPos.position);
            String filePath;
            if(image.file!=null){
                filePath = image.file.getAbsolutePath();
            }else {
                filePath = image.photoData.getFilePath();
            }
            if (filePath != null) {
                Log.v("ColumnAdapter", filePath);
                return decodeSampledBitmapFromResource(filePath, 150, 150);
            }
            Log.v("ColumnAdapter", "null");

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){
            holdAndPos.holder.imageView.setImageBitmap(bitmap);
        }
    }

    private class HolderAndPosition{
        int position;
        View_Holder holder;

        public HolderAndPosition(int position, View_Holder holder){
            this.position=position;
            this.holder=holder;
        }

    }

}
