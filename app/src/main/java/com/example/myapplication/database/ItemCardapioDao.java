package com.example.myapplication.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemCardapioDao {

    @Query("SELECT * FROM cardapio")
    List<ItemCardapioEntity> getAll();

    @Insert
    void insertAll(List<ItemCardapioEntity> itens);

    @Update
    void update(ItemCardapioEntity item);

    @Query("DELETE FROM cardapio")
    void clearAll();
}
