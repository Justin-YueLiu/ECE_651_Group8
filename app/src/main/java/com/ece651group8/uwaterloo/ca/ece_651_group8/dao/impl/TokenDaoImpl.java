package com.ece651group8.uwaterloo.ca.ece_651_group8.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ece651group8.uwaterloo.ca.ece_651_group8.dao.TokenDao;


//save, add, update and query token

public class TokenDaoImpl implements TokenDao {


    @Override
    public boolean save(SQLiteDatabase db, String token) {

        String s = query(db);

        if(!"".equals(s)){
            return update(db, token);
        }else{
            return add(db, token);
        }
    }


    private boolean add(SQLiteDatabase db, String token) {
        long flag = 0;
        ContentValues cv = new ContentValues();
        cv.put("id","token");
        cv.put("value",token);
        try{
            flag = db.insert("token",null,cv);
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag > 0;
    }

    private boolean update(SQLiteDatabase db, String token) {
        int flag = 0;
        ContentValues cv = new ContentValues();
        cv.put("value", token);
        try{
            flag = db.update("token", cv, "id = ?", new String[]{"token"});
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag > 0;
    }

    @Override
    public String query(SQLiteDatabase db) {
        String value = "";
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM token WHERE id = ?", new String[]{"token"});
            while (cursor.moveToNext()) {
                value= cursor.getString(cursor.getColumnIndex("value"));
            }
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return value;
    }
}
