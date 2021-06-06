package com.example.fastnumemes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class ImageDatabase  extends SQLiteOpenHelper {



    public List<Images> image;

    public static final int dbverion=1;
    public static  final String dbname="Images.db";
    public static final String Key_ID="img_id";
    public static final String Key_Name="img_name";
    public static final String Key_Like="btn_id";
    public static final String Key_Unlike="btn_id2";

    public static final String columns[]={Key_ID,Key_Name,Key_Like,Key_Unlike};

    public  ImageDatabase( Context context) {
        super(context,dbname,null,dbverion);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="Create Table Image(img_id INTEGER Primary Key ,"+"img_name TEXT ," +"btn_id INTEGER,"+"btn_id2 INTEGER)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table IF Exists Image");
        this.onCreate(db);

    }

    public void onDowngrade(SQLiteDatabase db,Images img) {
        db.delete("Image",Key_Name +"=?",new String[]{String.valueOf(img.name)});
        db.close();

    }



    public void DeleteAllStudent(){
        String query="Delete From Image";
        getWritableDatabase().execSQL(query);


    }

    public List<Images> FetchImg(){
        int column1Value[] = new int[100];
        String column2Value[] = new String[100];
        int colum3value[]=new int[100];
        int column4value[]=new int [100];



        List<Images> Regimg=new ArrayList<>();
        String query="Select * FROM "+"Image";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(query,null);
        int i=0,j=0;
        if(cursor.moveToFirst()){
            do{

                int column1Index = cursor.getColumnIndex(Key_ID);

                column1Value[i] = cursor.getInt(column1Index);

                // Get column 2 value.
                int column2Index = cursor.getColumnIndex(Key_Name);
                column2Value[j] = cursor.getString(column2Index);
               int column3Index=cursor.getColumnIndex(Key_Like);
               colum3value[j]=cursor.getInt(column3Index);

               int column4index=cursor.getColumnIndex((Key_Unlike));
               column4value[j]=cursor.getInt(column4index);
                Regimg.add(new Images(column1Value[i],column2Value[j],colum3value[j],column4value[j]));
                i++;j++;

            }
            while(cursor.moveToNext());



        }


        return Regimg;
    }
    public void InsertImage(Images img){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(Key_ID,img.imageid);
        values.put(Key_Name,img.name);
        values.put(Key_Like,img.btnid);
        values.put(Key_Unlike,img.btnid2);


        db.insert("Image",null,values);
        db.close();
    }
    public void removeStudent(Images img){

        SQLiteDatabase db=this.getWritableDatabase();


        db.delete("Image",Key_Name +"=?",new String[]{String.valueOf(img.name)});
        db.close();



    }
    public int updateStudent(Images img){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(Key_ID,img.imageid);
        values.put(Key_Name,img.name);
        values.put(Key_Like,img.btnid);
        values.put(Key_Unlike,img.btnid2);


        int i=db.update("Image",values,Key_Name +"=?",new String[]{String.valueOf(img.name)});
        db.close();
        return i;
    }


}

