package com.yenyu.a20180122_01;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

   public void click1(View v) //點擊讀取資料庫資料
   {
       File dbFile = new File(getFilesDir(),"student.db");
       //新建一個檔案（路徑名稱，檔案名稱）
       InputStream is = getResources().openRawResource(R.raw.student);
       //存入檔案 = 從R.raw的student
       try {
           OutputStream os = new FileOutputStream(dbFile); //讀檔案
           int r;
           while((r= is.read()) != -1) // 讀取檔案直到沒有內容的時候會顯示-1
           {
               os.write(r); //將讀取的檔案write寫入
           }
           is.close(); //存入檔案最後一個步驟
           os.close(); //讀取檔案最後一個步驟

       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }

   }

   public void click2(View v) //點擊抓取資料庫資料
   {
       File DBFile = new File(getFilesDir(),"student.db");
       //新增一個檔案
       SQLiteDatabase db = SQLiteDatabase.openDatabase(DBFile.getAbsolutePath(),
               null,SQLiteDatabase.OPEN_READWRITE);
       //SQLiteDatabase
       Cursor c = db.rawQuery("Select * from students",null);
       //Cursor 為資料指標
       c.moveToFirst(); //將指標第一次移動
       Log.d("DB",c.getString(1)+","+c.getInt(2));
       while(c.moveToNext())
           //指標往下一個地方移動
       Log.d("DB",c.getString(1)+","+c.getInt(2));
   }

   public void click3(View v) //點擊顯示指定條件的資料庫內容 方法1
   {
       File dbFile = new File(getFilesDir(),"student.db");
       SQLiteDatabase db=SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(),null,
               SQLiteDatabase.OPEN_READWRITE);
       String strSql= "Select * from students where _id=?"; //將全部資料表按照指定條件key=?顯示資料
       Cursor c = db.rawQuery(strSql,new String[]{"1"} ); //selectArgs 新增字串陣列 輸入value
       c.moveToNext();
       Log.d("DB",c.getString(1)+","+c.getInt(2));
   }

   public void click4(View v) //點擊顯示指定條件的資料庫內容 方法2
   {
       File dbFile = new File(getFilesDir(),"student.db");
       SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(),null,
               SQLiteDatabase.OPEN_READWRITE);
       Cursor c = db.query("students",new String[]{"_id","name","score"},
               null,null,null,null,null);
       //使用query 有七個參數 未指定條件時
       c.moveToFirst(); //將Cursor指標指向第一個
       Log.d("DB",c.getString(1)+","+c.getInt(2));
   }

   public void click5(View v) //點擊顯示指定條件資料庫內容 方法3
   {
       File dbFile = new File(getFilesDir(),"student.db");
       SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(),null,
               SQLiteDatabase.OPEN_READWRITE);
       Cursor c = db.query("students",new String[]{"_id","name","score"},"_id=?",
               new String[]{"2"},null,null,null);
       //query 七個參數 有指定條件時，在selection 寫欲尋找的key=?
       //在 selectionArgs 輸入 value
       c.moveToFirst();
       Log.d("DB",c.getString(1)+","+c.getInt(2));

   }

   public void click6(View v) //點擊 新增資料進資料庫 (SQL語法)
   {
       File dbFile = new File(getFilesDir(),"student.db");
       SQLiteDatabase db= SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(),null,
               SQLiteDatabase.OPEN_READWRITE);
       db.execSQL("Insert into students(_id,name,score) values(3,'Bob',98)");
       db.close();
   }
   public void click7(View v) //點擊 新增資料進資料庫 (contenvalues)
   {
       File dbFile = new File (getFilesDir(),"student.db");
       SQLiteDatabase db= SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(),null,
               SQLiteDatabase.OPEN_READWRITE);
       ContentValues cv = new ContentValues();
       cv.put("_id",5);
       cv.put("name","Bee");
       cv.put("score",98);
       db.insert("students",null,cv);
       db.close();
   }

   public void click8(View v) //點擊修改資料庫的資料
   {
       File dbFile = new File(getFilesDir(),"student.db");
       SQLiteDatabase db= SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(),null,
               SQLiteDatabase.OPEN_READWRITE);
       ContentValues cv = new ContentValues();
       cv.put("score",87); //利用contentvalues(KEY,VALUE);
       db.update("students",cv,"_id=?",new String[]{"2"});
       //db.update("表格名稱",cv,"KEY",第幾個的value)
       db.close();
   }

   public void click9(View v) //點擊刪除資料庫的資料
   {
       File dbFile= new File(getFilesDir(),"student.db");
       SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(),null,
               SQLiteDatabase.OPEN_READWRITE);
       db.delete("students","_id=?",new String[]{"2"});
       db.close();
   }
}
