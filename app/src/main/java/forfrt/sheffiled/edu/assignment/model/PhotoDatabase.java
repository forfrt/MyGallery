/*
 * Copyright (c) 2018. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package forfrt.sheffiled.edu.assignment.model;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

@android.arch.persistence.room.Database(entities = {PhotoData.class}, version = 1, exportSchema = false)
public abstract class PhotoDatabase extends RoomDatabase {
    public abstract PhotoDAO photoDao();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile PhotoDatabase INSTANCE;

    public static PhotoDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PhotoDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = android.arch.persistence.room.Room.databaseBuilder(context.getApplicationContext(),
                            PhotoDatabase.class, "photo_database")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     *
     * If you want to populate the database only when the database is created for the 1st time,
     * override RoomDatabase.Callback()#onCreate
     */
    private static Callback sRoomDatabaseCallback = new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // do any init operation about any initialisation here
        }
    };

}
