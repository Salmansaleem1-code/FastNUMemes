package com.example.fastnumemes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Login_database extends SQLiteOpenHelper {


    public List<Images> image;

    public static final int dbverion=1;
    public static  final String dbname="Login.db";
    public static final String Key_username="username";
    public static final String Key_password="password";
    public static final String Key_login="log";

    public static final String columns[]={Key_username, Key_password,Key_login};

    public  Login_database( Context context) {
        super(context,dbname,null,dbverion);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="Create Table login(username Text Primary Key ,"+"password Text,"+"log INTEGER)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table IF Exists login");
        this.onCreate(db);

    }

    public void onDowngrade(SQLiteDatabase db,Register reg) {
        db.delete("login",Key_username +"=?",new String[]{String.valueOf(reg.username)});
        db.close();

    }

    public ArrayList<Register> FetchUsers(){
        String column1Value[] = new String[100];
        String column2Value[] = new String[100];
        int column3value[] =new int[100];
        String status[] = new String[100];


      ArrayList<Register> Regusers=new ArrayList<>();
        String query="Select * FROM "+"login";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(query,null);
int i=0,j=0;
        if(cursor.moveToFirst()){
            do{

                int column1Index = cursor.getColumnIndex("username");

                column1Value[i] = cursor.getString(column1Index);

                // Get column 2 value.
                int column2Index = cursor.getColumnIndex("password");
                column2Value[j] = cursor.getString(column2Index);
                int column3Index=cursor.getColumnIndex(Key_login);
                column3value[j]=cursor.getInt(column3Index);
          Regusers.add(new Register(column1Value[i],column2Value[j],column3value[j]));
          i++;j++;

            }
            while(cursor.moveToNext());



        }


        return Regusers;
    }




    public void DeleteAllStudent(){
        String query="Delete From Image";
        getWritableDatabase().execSQL(query);


    }
    public void InsertUser(Register reg){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(Key_username,reg.username);
        values.put(Key_password,reg.password);
        values.put(Key_login,reg.value);


        db.insert("login",null,values);
        db.close();
    }
    public void removeStudent(Register reg){

        SQLiteDatabase db=this.getWritableDatabase();


        db.delete("login",Key_username +"=?",new String[]{String.valueOf(reg.username)});
        db.close();



    }
    public int updateDb(Register reg){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(Key_username,reg.username);
        values.put(Key_password,reg.password);

        values.put(Key_login,reg.value);


        int i=db.update("login",values,Key_username +"=?",new String[]{String.valueOf(reg.username)});
        db.close();
        return i;
    }


}
