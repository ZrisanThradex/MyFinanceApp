package com.zrisan.my_finance.helpers;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class CategoryTableHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "my_finance.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CATEGORIES = "categories";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";

    private static final String CREATE_TABLE_CATEGORIES =
            "CREATE TABLE IF NOT EXISTS " + TABLE_CATEGORIES +
                    "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME + " TEXT" +
                    ")";

    public CategoryTableHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public boolean tableExists(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT DISTINCT tbl_name FROM sqlite_master WHERE tbl_name = ?", new String[]{TABLE_CATEGORIES});
        boolean tableExists = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) {
            cursor.close();
        }
        return tableExists;
    }

    public boolean isTableExists() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?", new String[]{TABLE_CATEGORIES});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CATEGORIES);
     
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Puedes implementar acciones específicas si la versión de la base de datos cambia en futuras actualizaciones.
    }

    public void insertCategory(String name) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, name);
            long rowId = db.insert(TABLE_CATEGORIES, null, values);
            if (rowId == -1) {
                // Error al insertar la categoría
                Log.e("DBHelper", "Error al insertar la categoría: " + name);
            } else {
                // Categoría insertada exitosamente
                Log.d("DBHelper", "Categoría insertada: " + name);
            }
        } catch (SQLException e) {
            // Error en la operación de base de datos
            Log.e("DBHelper", "Error en la operación de base de datos: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = this.getReadableDatabase();
            if (tableExists(db)) {
                cursor = db.query(TABLE_CATEGORIES, new String[]{COLUMN_NAME}, null, null, null, null, null);

                while (cursor.moveToNext()) {
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                    categories.add(name);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return categories;
    }

}
