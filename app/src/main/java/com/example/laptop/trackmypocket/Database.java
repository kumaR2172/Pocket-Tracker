package com.example.laptop.trackmypocket;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    public static final String DBname = "TrackMyPocket";
    public static final String TBCategories = "Categories";
    public static final String TBTransaction = "Transactionss";
    public static final String TBUserInformation = "UserInformation";
    public static final String TBDefaultMonth = "DefaultMonth";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "CName";
    public static final String COL_3 = "Status";
    public static final String COL_4 = "UserId";


    public Database(Context context) {
        super(context, DBname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TBCategories + "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "CName VARCHAR,Status VARCHAR,UserId INTEGER)");
        db.execSQL("create table " + TBTransaction + "(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Amount Float,CId INTEGER,Status VARCHAR,Datee VARCHAR,ExtraNotes VARCHAR,UserId INTEGER)");
        db.execSQL("create table " + TBUserInformation + "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "UserName VARCHAR,MobileNumber VARCHAR,Password VARCHAR,LoginStatus VARCHAR)");
        db.execSQL("create table " + TBDefaultMonth + "(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Datee VARCHAR,UserId INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS%s", TBCategories));
        db.execSQL(String.format("DROP TABLE IF EXISTS%s", TBTransaction));
        db.execSQL(String.format("DROP TABLE IF EXISTS%s", TBUserInformation));
        db.execSQL(String.format("DROP TABLE IF EXISTS%s", TBDefaultMonth));
    }

    public boolean insertCategories(String CName, String Status, String UserId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2, CName);
        cv.put(COL_3, Status);
        cv.put(COL_4, UserId);
        long result = db.insert(TBCategories, null, cv);
        db.close();

        //check for data inserted or not
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getCategoriesReleatedTOIncome(String Status, int UserId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select Id,CName from Categories where Status=? and UserId=" + UserId, new String[]{Status});
        return c;
    }

    public boolean InsertTransaction(float Amount, int CId, String Status, String Datee, String ExtraNotes, int UserId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Amount", Amount);
        contentValues.put("CId", CId);
        contentValues.put("Status", Status);
        contentValues.put("Datee", Datee);
        contentValues.put("ExtraNotes", ExtraNotes);
        contentValues.put("UserId", UserId);
        long result = db.insert(TBTransaction, null, contentValues);
        if (result != -1) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor GetTransactionDetails(int UserId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select Transactionss.Id,Transactionss.Datee,Categories.CName,Transactionss.Amount" +
                ",Transactionss.Status,Transactionss.Datee from Transactionss inner join Categories" +
                " on Categories.Id=Transactionss.CId where Transactionss.UserId=" + UserId, null);
        return c;
    }

    public String InsertUserInformation(String UserName, String MobileNumber, String Password) {
        String UserId = "-1";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues ct = new ContentValues();
        ct.put("UserName", UserName);
        ct.put("MobileNumber", MobileNumber);
        ct.put("Password", Password);
        ct.put("LoginStatus", "1");
        long result = db.insert(TBUserInformation, null, ct);
        if (result != -1)
        {
            db = this.getReadableDatabase();
            Cursor c = db.rawQuery("select Id from " + TBUserInformation + " where MobileNumber=? and Password=?", new String[]{MobileNumber, Password});
            c.moveToNext();
            UserId = c.getString(0);
        }

        return UserId;
    }

    public String LoginSelect(String MobileNumber, String Password) {
        String UserId = "-1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select Id from " + TBUserInformation + " where MobileNumber=? and Password=?",
                new String[]{MobileNumber, Password});

        if (c.moveToNext()) {
            UserId = c.getString(0);
        } else {
            UserId = "-1";
        }

        return UserId;
    }

    public Cursor GetBalance(int UserId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select Amount,Status,Datee from " + TBTransaction + " where UserId=" + UserId, null);
        return c;
    }

    public boolean InsertDefaultDate(String Datee, int UserId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues ct = new ContentValues();
        ct.put("Datee", Datee);
        ct.put("UserId", UserId);
        long result = db.insert(TBDefaultMonth, null, ct);
        if (result != -1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean UpdateDefaultDate(String Datee, String UserId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues ct = new ContentValues();
        ct.put("Datee", Datee);
        ct.put("UserId",UserId);
        long result = db.update(TBDefaultMonth, ct, "UserId=" + UserId, null);
        if (result != -1) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor GetDefaultDate(int UserId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select Datee from " + TBDefaultMonth + " where UserId=" + UserId + "", null);
        return c;

    }

    public boolean UpdateUserPassword(int UserId, String NewPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues ct = new ContentValues();
        ct.put("Password", NewPassword);
        Log.d("Pas-------", NewPassword);
        //Cursor c=db.rawQuery("update "+TBUserInformation+" set Password=? where UserId="+UserId,new String[]{NewPassword});
        int result = db.update(TBUserInformation, ct, "Id=" + UserId, null);
        if (result != -1) {
            return true;
        } else {
            return false;
        }

    }

    public boolean Logout(int UserId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues ct = new ContentValues();
        ct.put("LoginStatus", "0");
        int result = db.update(TBUserInformation, ct, "Id=" + UserId, null);
        if (result != -1) {
            return true;
        } else {
            return false;
        }
    }

    public void InsertDefaultCategories(ArrayList Categories, int UserId, String CategoriesType) {
        int i = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues ct = new ContentValues();
        if (CategoriesType.equals("I")) {
            int size = Categories.size();
            for (i = 0; i < size; i++) {
                ct.put("CName", Categories.get(i).toString());
                ct.put("Status", "I");
                ct.put("UserId", UserId);
                db.insert(TBCategories, null, ct);
            }

        } else {
            int size = Categories.size();
            for (i = 0; i < size; i++) {
                ct.put("CName", Categories.get(i).toString());
                ct.put("Status", "E");
                ct.put("UserId", UserId);
                db.insert(TBCategories, null, ct);

            }
        }
    }
    public void UpdateNewCategory(int CategoryId,String NewCategory,int UserId)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues ct=new ContentValues();
        ct.put("CName",NewCategory);
        db.update(TBCategories,ct,"Id="+CategoryId+" and UserId="+UserId,null);
    }

    public  boolean DeleteCategory(int CategoryId,int UserId)
    {
        int chk=0;
        SQLiteDatabase db;
        db=this.getReadableDatabase();
        Cursor c=db.rawQuery("select count(*) as Value from "+TBTransaction+" where Id="+CategoryId+" and UserId="+UserId,null);
        int value;
        if(c.moveToNext())
        {
            chk=Integer.parseInt(c.getString(0));
        }

        if(chk>0) {
            db = this.getWritableDatabase();
            db.delete(TBCategories, "Id=" + CategoryId + " and UserId=" + UserId, null);
            return true;
        }
        else
        {
            return false;
        }
    }
}