/*
 * Copyright (c) 2018. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package forfrt.sheffiled.edu.assignment.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PhotoDAO {
    @Insert
    void insertAll(PhotoData... photodata);

    @Insert
    void insert(PhotoData photodata);

    @Delete
    void delete(PhotoData photoData);

    @Query("SELECT * FROM PhotoData ORDER BY title ASC")
    List<PhotoData> retrieveAllData();

    @Delete
    void deleteAll(PhotoData... photoData);

    @Query("SELECT * FROM PhotoData WHERE guid = :guid LIMIT 1")
    LiveData<PhotoData> retrieveOneDataByGuid(String guid);

    @Query("UPDATE PhotoData SET title = :title AND description = :description WHERE guid = :guid")
    int updateTitleDescByGuid(String title, String description, String guid);

    @Query("SELECT * FROM PhotoData WHERE filePath = :filePath LIMIT 1")
    LiveData<PhotoData> retrieveOneDataByFilePath(String filePath);

    @Query("UPDATE PhotoData SET title = :title AND description = :description WHERE filePath = :filePath")
    int updateTitleDescByFilePath(String title, String description, String filePath);
}
