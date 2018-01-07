package actio.ashcompany.com.travelagentv11;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by admin on 3/27/2015.
*/
public class databasehelper extends SQLiteOpenHelper {


    private static int DB_VERSION = 2;
    private static String DB_NAME = "Travel2.db";
    Context c = null;
    String userName;
    String userPass;

    public databasehelper(Context c)
    {

        super(c,DB_NAME,null,DB_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table LOGGER (name TEXT,age NUMBER,gender TEXT,address TEXT,username TEXT,password TEXT,reg NUMBER,phno NUMBER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        db.execSQL("Drop Table LOGGER");

    }
    public String getyourdata(String user, String pass) {
        SQLiteDatabase sb=this.getReadableDatabase();
        //SELECT
        String[] columns = {"name"};

        //WHERE clause
        String selection = "username=? AND password=?";
        userName=user;
        userPass=pass;
        //WHERE clause arguments
        String[] selectionArgs = {userName, userPass};
        Cursor c;
        String data = null;
        String no_such_data="INVALID!!!";
        try{
            //SELECT name FROM login WHERE username=userName AND password=userPass
            c = sb.query("LOGGER", columns, selection, selectionArgs, null, null, null);
            c.moveToFirst();

            int i = c.getCount();
            data = c.getString(c.getColumnIndex("name"));
            c.close();
            if(i <= 0){

                return no_such_data;
            }
            return data;
        }catch(Exception e){
            e.printStackTrace();
            return no_such_data;
        } finally {
            this.close();
        }
    }
    public String getyourdata1(String user, String pass) {
        SQLiteDatabase sb=this.getReadableDatabase();
        //SELECT
        String[] columns = {"phno"};

        //WHERE clause
        String selection = "username=? AND password=?";
        userName=user;
        userPass=pass;
        //WHERE clause arguments
        String[] selectionArgs = {userName, userPass};
        Cursor c;
        String data = null;
        String no_such_data="INVALID!!!";
        try{
            //SELECT name FROM login WHERE username=userName AND password=userPass
            c = sb.query("LOGGER", columns, selection, selectionArgs, null, null, null);
            c.moveToFirst();

            int i = c.getCount();
            data = c.getString(c.getColumnIndex("phno"));
            c.close();
            if(i <= 0){

                return no_such_data;
            }
            return data;
        }catch(Exception e){
            e.printStackTrace();
            return no_such_data;
        }
        finally {
            this.close();
        }
    }
    public int getyourdata2(String user, String pass) {
        SQLiteDatabase sb=this.getReadableDatabase();
        //SELECT
        String[] columns = {"reg"};

        //WHERE clause
        String selection = "username=? AND password=?";
        userName=user;
        userPass=pass;
        //WHERE clause arguments
        String[] selectionArgs = {userName, userPass};
        Cursor c;
        int data;
        int no_such_data=0;
        try{
            //SELECT name FROM login WHERE username=userName AND password=userPass
            c = sb.query("LOGGER", columns, selection, selectionArgs, null, null, null);
            c.moveToFirst();

            int i = c.getCount();
            data = c.getInt(c.getColumnIndex("reg"));
            c.close();
            if(i <= 0){
                return no_such_data;
            }
            return data;
        }catch(Exception e){
            e.printStackTrace();
            return no_such_data;
        }
        finally {
            this.close();
        }
    }
    /*
    public int getyourdata3(String user, String pass) {
        SQLiteDatabase sb=this.getReadableDatabase();
        //SELECT
        String[] columns = {"count1"};

        //WHERE clause
        String selection = "username=? AND password=?";
        userName=user;
        userPass=pass;
        //WHERE clause arguments
        String[] selectionArgs = {userName, userPass};
        Cursor c;
        int data;
        int no_such_data=0;
        try{
            //SELECT name FROM login WHERE username=userName AND password=userPass
            c = sb.query("UPDATER", columns, selection, selectionArgs, null, null, null);
            c.moveToFirst();

            int i = c.getCount();
            data = c.getInt(c.getColumnIndex("count"));
            c.close();
            if(i <= 0){

                return no_such_data;
            }
            return data;
        }catch(Exception e){
            e.printStackTrace();
            return no_such_data;
        }
    }
    public int getyourdata4(String user, String pass) {
        SQLiteDatabase sb=this.getReadableDatabase();
        //SELECT
        String[] columns = {"count2"};

        //WHERE clause
        String selection = "username=? AND password=?";
        userName=user;
        userPass=pass;
        //WHERE clause arguments
        String[] selectionArgs = {userName, userPass};
        Cursor c;
        int data;
        int no_such_data=0;
        try{
            //SELECT name FROM login WHERE username=userName AND password=userPass
            c = sb.query("UPDATER", columns, selection, selectionArgs, null, null, null);
            c.moveToFirst();

            int i = c.getCount();
            data = c.getInt(c.getColumnIndex("count"));
            c.close();
            if(i <= 0){

                return no_such_data;
            }
            return data;
        }catch(Exception e){
            e.printStackTrace();
            return no_such_data;
        }
    }
    public int getyourdata5(String user, String pass) {
        SQLiteDatabase sb=this.getReadableDatabase();
        //SELECT
        String[] columns = {"count3"};

        //WHERE clause
        String selection = "username=? AND password=?";
        userName=user;
        userPass=pass;
        //WHERE clause arguments
        String[] selectionArgs = {userName, userPass};
        Cursor c;
        int data;
        int no_such_data=0;
        try{
            //SELECT name FROM login WHERE username=userName AND password=userPass
            c = sb.query("UPDATER", columns, selection, selectionArgs, null, null, null);
            c.moveToFirst();

            int i = c.getCount();
            data = c.getInt(c.getColumnIndex("count"));
            c.close();
            if(i <= 0){

                return no_such_data;
            }
            return data;
        }catch(Exception e){
            e.printStackTrace();
            return no_such_data;
        }
    }
    public int getyourdata6(String user, String pass) {
        SQLiteDatabase sb=this.getReadableDatabase();
        //SELECT
        String[] columns = {"count4"};

        //WHERE clause
        String selection = "username=? AND password=?";
        userName=user;
        userPass=pass;
        //WHERE clause arguments
        String[] selectionArgs = {userName, userPass};
        Cursor c;
        int data;
        int no_such_data=0;
        try{
            //SELECT name FROM login WHERE username=userName AND password=userPass
            c = sb.query("UPDATER", columns, selection, selectionArgs, null, null, null);
            c.moveToFirst();

            int i = c.getCount();
            data = c.getInt(c.getColumnIndex("count"));
            c.close();
            if(i <= 0){

                return no_such_data;
            }
            return data;
        }catch(Exception e){
            e.printStackTrace();
            return no_such_data;
        }
    }
    public String getyourdata7(String user, String pass) {
        SQLiteDatabase sb=this.getReadableDatabase();
        //SELECT
        String[] columns = {"username"};

        //WHERE clause
        String selection = "username=? AND password=?";
        userName=user;
        userPass=pass;
        //WHERE clause arguments
        String[] selectionArgs = {userName, userPass};
        Cursor c;
        String data = null;
        String no_such_data="INVALID!!!";
        try{
            //SELECT name FROM login WHERE username=userName AND password=userPass
            c = sb.query("UPDATER", columns, selection, selectionArgs, null, null, null);
            c.moveToFirst();

            int i = c.getCount();
            data = c.getString(c.getColumnIndex("username"));
            c.close();
            if(i <= 0){

                return no_such_data;
            }
            return data;
        }catch(Exception e){
            e.printStackTrace();
            return no_such_data;
        }
    }
    public String getyourdata8(String user, String pass) {
        SQLiteDatabase sb=this.getReadableDatabase();
        //SELECT
        String[] columns = {"password"};

        //WHERE clause
        String selection = "username=? AND password=?";
        userName=user;
        userPass=pass;
        //WHERE clause arguments
        String[] selectionArgs = {userName, userPass};
        Cursor c;
        String data = null;
        String no_such_data="INVALID!!!";
        try{
            //SELECT name FROM login WHERE username=userName AND password=userPass
            c = sb.query("UPDATER", columns, selection, selectionArgs, null, null, null);
            c.moveToFirst();

            int i = c.getCount();
            data = c.getString(c.getColumnIndex("password"));
            c.close();
            if(i <= 0){

                return no_such_data;
            }
            return data;
        }catch(Exception e){
            e.printStackTrace();
            return no_such_data;
        }
    }
    */
}
