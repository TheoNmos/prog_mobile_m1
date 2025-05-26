package com.example.myapplication.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ItemCardapioEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ItemCardapioDao itemCardapioDao();
}
