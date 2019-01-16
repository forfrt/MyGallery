/*
 * Copyright (c) 2017. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package forfrt.sheffiled.edu.assignment;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import forfrt.sheffiled.edu.assignment.model.PhotoData;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.View_Holder> {
    static private Context context;

    //defined the field images  as a list
    private static List<PhotoData> images = new ArrayList<>();

    //define a getter for this field
    public static List<PhotoData> getImages() {
        return images;
    }
    //define a setter for this field
    public static void setImages(List<PhotoData> items) {

        MyAdapter.images = items;
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_image,
                parent, false);
        View_Holder holder = new View_Holder(v);
        context= parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(final View_Holder holder, final int position) {

        //Use the provided View Holder on the onCreateViewHolder method to populate the
        // current row on the RecyclerView
            holder.imageView.setImageBitmap(images.get(position).getBitmap());
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Modify the onClickListener in onBindViewHolder in My Adpter
                    Intent intent = new Intent(context, ShowImageActivity.class);
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });

        //animate(holder);
    }

    // convenience method for getting data at click position
    PhotoData getImage(int id) {
        return images.get(id);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class View_Holder extends RecyclerView.ViewHolder  {
        ImageView imageView;


        View_Holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_item);

        }


    }
}