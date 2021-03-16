package com.mc.mcfirebase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.Nullable;

import com.mc.mcfirebase.Model.foodData;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static final String DBNAME = "himanshuDB";
    private static final int VERSION = 1;
    private String tablename;
    private static final String id = "id";
    private static final String foodname = "name";
    private static final String quantity = "quantity";
    private static final String price = "price";
    public Database(@Nullable Context context,String tablename) {
        super(context,DBNAME, null, VERSION);
        this.tablename = "canteen"+tablename;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String creating_table = "CREATE TABLE " + this.tablename + " ( "
                + id + " INTEGER PRIMARY KEY, " + foodname + " TEXT," + quantity + " INTEGER, " +price +" INTEGER " + ")";
        Log.e("DBBBBBBBBB",creating_table);
        db.execSQL(creating_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tablename);
        onCreate(db);
    }

    void addToCart(foodData food, int quant) {
        Log.e("DBBBBBBBBB","Woooow");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(id, Integer.parseInt(food.getId()));
        values.put(foodname,food.getName());
        values.put(quantity,quant);
        values.put(price,Integer.parseInt(food.getPrice()));
        db.insert(tablename, null, values);
        db.close();
    }

    public List<Pair<foodData, Integer>> getCart() {
        //Pair<foodData, Integer> pair ;
        List<Pair<foodData, Integer>> foodList = new ArrayList<Pair<foodData, Integer>>();
        String selectQuery = "SELECT  * FROM " + tablename;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                foodData food = new foodData();
                food.setId(String.valueOf(cursor.getInt(0)));
                food.setName(cursor.getString(1));
                food.setPrice(String.valueOf(cursor.getInt(3)));
                foodList.add(new Pair<foodData, Integer>(food, cursor.getInt(2)));
            } while (cursor.moveToNext());
        }

        return foodList;
    }

    public int update(foodData food,int quant) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(foodname, food.getName());
        values.put(quantity, quant);
        values.put(price,Integer.parseInt(food.getPrice()));

        // updating row
        return db.update(tablename, values, id + " = ?",
                new String[] { String.valueOf(food.getId()) });
    }

    public void delete(foodData food) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tablename, id + " = ?",
                new String[] { String.valueOf(food.getId()) });
        db.close();
    }





}
