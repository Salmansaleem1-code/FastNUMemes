package com.example.fastnumemes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class GalleryDatabase extends SQLiteOpenHelper {
    public List<Profile> profile;

    public static final int dbverion=1;
    public static  final String dbname="picture.db";
    public static final String Key_name="username";
    public static final String Key_pic="pic";

    public static final String columns[]={Key_name,Key_pic};

    public  GalleryDatabase( Context context) {
        super(context,dbname,null,dbverion);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="Create Table picture(username Text Primary Key ,"+"pic Text)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table IF Exists picture");
        this.onCreate(db);

    }

    public void onDowngrade(SQLiteDatabase db,Profile p) {
        db.delete("picture",Key_name +"=?",new String[]{String.valueOf(p.username)});
        db.close();

    }



    public void DeleteAllStudent(){
        String query="Delete From Picture";
        getWritableDatabase().execSQL(query);


    }

    public List<Profile> Fetchprofile(){
       String column1Value[] = new String[100];
        String column2Value[] = new String[100];
        int colum3value[]=new int[100];
        int column4value[]=new int [100];



        List<Profile> profile=new ArrayList<>();
        String query="Select * FROM "+"picture";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(query,null);
        int i=0,j=0;
        if(cursor.moveToFirst()){
            do{

                int column1Index = cursor.getColumnIndex(Key_name);

                column1Value[i] = cursor.getString(column1Index);

                // Get column 2 value.
                int column2Index = cursor.getColumnIndex(Key_pic);
                column2Value[j] = cursor.getString(column2Index);

                profile.add(new Profile(column1Value[i],column2Value[j]));
                i++;j++;

            }
            while(cursor.moveToNext());



        }


        return profile;
    }
    public void InsertImage(Profile pf){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(Key_name,pf.username);
        values.put(Key_pic,pf.pic);



        db.insert("picture",null,values);
        db.close();
    }
    public void removeStudent(Profile pic){

        SQLiteDatabase db=this.getWritableDatabase();


        db.delete("picture",Key_name +"=?",new String[]{String.valueOf(pic.username)});
        db.close();



    }
    public int updateStudent(Profile pic){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(Key_name,pic.username);
        values.put(Key_pic,pic.pic);



        int i=db.update("Image",values,Key_name +"=?",new String[]{String.valueOf(pic.username)});
        db.close();
        return i;
    }


}



