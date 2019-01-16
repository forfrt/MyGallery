/*
 * Copyright (c) 2018. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package forfrt.sheffiled.edu.assignment.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.support.annotation.NonNull;

@Entity(indices={@Index(value={"title"})})
public class PhotoData {
    @PrimaryKey(autoGenerate = true)
    @android.support.annotation.NonNull
    private int id=0;
    private String guid;
    private String title;
    private String description;
    private int lastColumnId;
    private int lastColumnPosition;
    private String La;
    private String LaRef;
    private String Lo;
    private String LoRef;
    private String length;
    private String width;
    private String orientation;
    private String filePath;
//    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
//    public byte[] picture;



    public PhotoData(String guid, String title, String description, String filePath,
                     int lastColumnId, int lastColumnPosition,
                     String la, String laRef, String lo, String loRef,
                     String length, String width, String orientation) {
        this.guid=guid;
        this.title = title;
        this.description = description;
        this.filePath=filePath;
        this.lastColumnId = lastColumnId;
        this.lastColumnPosition = lastColumnPosition;
        this.La = la;
        this.LaRef = laRef;
        this.Lo = lo;
        this.LoRef = loRef;
        this.length = length;
        this.width = width;
        this.orientation = orientation;
    }

    public PhotoData(String guid, String filePath,
                     int lastColumnId, int lastColumnPosition, ExifInterface exif){
        this(guid, null, null, filePath,
                lastColumnId, lastColumnPosition,
                exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE),
                exif.getAttribute(ExifInterface.TAG_GPS_DEST_LATITUDE_REF),
                exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE),
                exif.getAttribute(ExifInterface.TAG_GPS_DEST_LONGITUDE_REF),
                exif.getAttribute(ExifInterface.TAG_IMAGE_LENGTH),
                exif.getAttribute(ExifInterface.TAG_IMAGE_WIDTH),
                exif.getAttribute(ExifInterface.TAG_ORIENTATION)
        );
    }

    public PhotoData(String title, String description,
                     int lastColumnId, int lastColumnPosition) {
        this.title= title;
        this.description= description;
        this.lastColumnId=lastColumnId;
        this.lastColumnPosition=lastColumnPosition;
    }

    @android.support.annotation.NonNull
    public int getId() {
        return id;
    }
    public void setId(@android.support.annotation.NonNull int id) {
        this.id = id;
    }

    public Bitmap getBitmap() {
        return BitmapFactory.decodeFile(this.filePath);
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getLastColumnId() {
        return lastColumnId;
    }

    public void setLastColumnId(int lastColumnId) {
        this.lastColumnId = lastColumnId;
    }

    public int getLastColumnPosition() {
        return lastColumnPosition;
    }

    public void setLastColumnPosition(int lastColumnPosition) {
        this.lastColumnPosition = lastColumnPosition;
    }

    public String getLa() {
        return La;
    }

    public void setLa(String la) {
        La = la;
    }

    public String getLaRef() {
        return LaRef;
    }

    public void setLaRef(String laRef) {
        LaRef = laRef;
    }

    public String getLo() {
        return Lo;
    }

    public void setLo(String lo) {
        Lo = lo;
    }

    public String getLoRef() {
        return LoRef;
    }

    public void setLoRef(String loRef) {
        LoRef = loRef;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

//    public byte[] getPicture() {
//        return picture;
//    }
//    public void setPicture(byte[] picture) {
//        this.picture = picture;
//    }
}
