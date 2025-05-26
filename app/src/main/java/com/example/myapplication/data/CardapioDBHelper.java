// src/main/java/com/example/myapplication/data/CardapioDBHelper.java
package com.example.myapplication.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.model.ItemCardapio;

import java.util.ArrayList;
import java.util.List;

public class CardapioDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "cardapio.db";
    private static final int DB_VERSION = 1;

    public CardapioDBHelper(Context ctx) {
        super(ctx, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddl = "CREATE TABLE cardapio (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT, " +
                "preco TEXT, " +
                "imagePath TEXT" +
                ");";
        db.execSQL(ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS cardapio");
        onCreate(db);
    }

    /**
     * Apaga tudo antes de inserir uma nova carga
     */
    public void clearAll() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM cardapio");
    }

    /**
     * Insere um item no banco
     */
    public void insertItem(ItemCardapio item) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO cardapio (nome, preco, imagePath) VALUES (?, ?, ?)";
        db.execSQL(sql, new Object[]{
                item.getNome(),
                item.getPreco(),
                item.getImagePath()
        });
    }

    /**
     * Busca tudo e monta lista
     */
    public List<ItemCardapio> getAllItems() {
        List<ItemCardapio> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT nome, preco, imagePath FROM cardapio",
                null
        );
        if (c.moveToFirst()) {
            do {
                String nome = c.getString(0);
                String preco = c.getString(1);
                String path = c.getString(2);
                ItemCardapio it = new ItemCardapio(nome, preco, null);
                it.setImagePath(path);
                list.add(it);
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }
}
