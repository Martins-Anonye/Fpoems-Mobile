package com.example.martins.fpoems.DataModelForListOfDPTINDB;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.util.HashSet;

/**
 * Created by Martins on 2/3/2020.
 */

public class PupulateListViewBaseOnSelectionOfSpinner  extends SQLiteOpenHelper {
    private static String TAG = "PupulationOfSpinner"; // Tag just for the LogCat window
    //destination path (location) of our database on device
    private static String DB_PATH = "";
    private static String DB_NAME;

    private static String TABLENAME ;

    private SQLiteDatabase mDataBase;
    private final Context mContext;
// for getcategorymethod

    String  myCLASS, myDEPT, myclassandDpt,dptexamsn;

    public PupulateListViewBaseOnSelectionOfSpinner(Context context) {
        super(context, DB_NAME, null, 1);// 1? its Database Version


        String dirPathInternal = "/data/data/" + context.getPackageName() + "/databases/";
        String dirPathExternal = "/sdcard/Android/data/" + context.getPackageName() + "/files/data/data/" + context.getPackageName() + "/databases/";



        File dir0 = new File(dirPathInternal);
        File dir0external = new File(dirPathExternal);


        if(dir0external.exists()){
            DB_PATH=dirPathExternal;

        }
        else{
            DB_PATH=dirPathInternal;
        }


        Log.i(TAG, DB_PATH);
        this.mContext = context;
    }




    //Copy the database from assets


    //Open the database, so we can query it
    public boolean openDataBase() throws SQLException {

        String mPath = DB_PATH + DB_NAME;
        //Log.v("mPath", mPath);

        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.OPEN_READWRITE);
        //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return mDataBase != null;
    }


    @Override
    public void onCreate(SQLiteDatabase arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

    //// only for closing the database
    public void closeDatabase() {
        if (mDataBase != null) {
            mDataBase.close();

        }
    }


    public HashSet<String> getUsersDataClassandDpt() {
        //MyListSpinnerModel
        SQLiteDatabase db = this.getWritableDatabase();
        //  ArrayList<String> userListspinnerclassnaddpt = new ArrayList<>();
        HashSet<String> userListspinnerclassnaddpt = new HashSet<String>();
        //   String query = "SELECT * FROM projectdec";
        String query = "select * from "+TABLENAME+" where\"COURSETITLE/LECTURER\" !='NULL'";
        //    String s = String.format("SELECT * FROM projectdec WHERE  %s LIKE %s order by %s ",tablecoln,fog,tablecoln);

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() == 0) {

            Log.e("querry result", "cursor is  emty");

            return null;
        } else {


            while (cursor.moveToNext()) {


                dptexamsn = cursor.getString(cursor.getColumnIndex("S/N"));
                if ((dptexamsn!=null)) {
                    myCLASS = cursor.getString(cursor.getColumnIndex("CLASS"));

                    myDEPT = cursor.getString(cursor.getColumnIndex("DEPT"));

                    myclassandDpt =  myDEPT+ " , " +myCLASS;

                    userListspinnerclassnaddpt.add(myclassandDpt);
                    Log.e("querry result", "cursor is not  emty");
                }

            }


            return userListspinnerclassnaddpt;
        }

    }


    public Integer deleteval(String tablename, String col, String sn) {


        SQLiteDatabase dba = this.getWritableDatabase();


        int delrowresult = dba.delete(tablename, col + "= ?", new String[]{sn});


        return delrowresult;
    }


    public SQLiteDatabase creatObjectOfThisDatabasOutSideThisClass() {

        SQLiteDatabase dba = this.getWritableDatabase();
        return dba;
    }

    public static void setDbName(String dbName) {
        DB_NAME = dbName;
    }

    public static void setTABLENAME(String TABLENAME) {
        PupulateListViewBaseOnSelectionOfSpinner.TABLENAME = TABLENAME;
    }
}
