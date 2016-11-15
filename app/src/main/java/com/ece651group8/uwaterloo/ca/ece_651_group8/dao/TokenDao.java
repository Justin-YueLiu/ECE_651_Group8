package com.ece651group8.uwaterloo.ca.ece_651_group8.dao;


import android.database.sqlite.SQLiteDatabase;

public interface TokenDao {
    public boolean save(SQLiteDatabase db, String token);
    public String query(SQLiteDatabase db);
}
