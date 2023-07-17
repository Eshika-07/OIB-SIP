package com.example.todo.Utility;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.todo.Model.TodoModel;


import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

   private SQLiteDatabase db;
    private static final String DATABASE_NAME="TODO_DATABASE";
    private static final String TABLE_NAME="TODO_TABLE";
    private static final String COL_1="ID";
    private static final String COL_2="TASK";
    private static final String COL_3="STATUS";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
       db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_NAME+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT, TASK TEXT,STATUS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
       db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
       onCreate(db);
    }

    public void insertTask(TodoModel model){
        db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_2,model.getTask());
        values.put(COL_3,0);
        db.insert(TABLE_NAME,null,values);
    }

    public void updateTask(int id,String Task){
        db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_2,Task);
        db.update(TABLE_NAME,values,"ID+?",new String[]{String.valueOf(id)});
    }

    public void updateStatus(int id,int status){
        db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_3,status);
        db.update(TABLE_NAME,values,"ID+?",new String[]{String.valueOf(id)});
    }

    public void deleteTask(int id){
        db=this.getWritableDatabase();
        db.delete(TABLE_NAME,"ID=?",new String[]{String.valueOf(id)});
    }

    @SuppressLint("Range")
    public List<TodoModel> getAllTask(){
        db=this.getWritableDatabase();
        Cursor cur=null;
        List<TodoModel> modelList=new ArrayList<>();

         db.beginTransaction();
         try {
             cur=db.query(TABLE_NAME,null,null,null,null,null,null,null);
             if (cur!=null){
                 if (cur.moveToFirst()){
                     do {
                         TodoModel task=new TodoModel();
                         task.setId(cur.getInt(cur.getColumnIndex(COL_1)));
                         task.setTask(cur.getString(cur.getColumnIndex(COL_2)));
                         task.setStatus(cur.getInt(cur.getColumnIndex(COL_3)));
                         modelList.add(task);
                     }while (cur.moveToNext());
                 }
             }
         }finally {
             db.endTransaction();
             cur.close();
         }
         return modelList;
        }
    }

