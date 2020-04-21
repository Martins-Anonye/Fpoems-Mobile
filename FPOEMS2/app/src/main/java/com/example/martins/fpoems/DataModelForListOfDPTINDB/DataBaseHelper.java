package com.example.martins.fpoems.DataModelForListOfDPTINDB;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Juliet on 9/5/2019.
 */


public class DataBaseHelper extends SQLiteOpenHelper {
    private static String TAG = "DataBaseHelper"; // Tag just for the LogCat window
    //destination path (location) of our database on device
    private static String DB_PATH;
    private static String DB_PATHExternal;
    //private static String DB_NAME ="(students).sqlite";// Database name
    //   private static String DB_NAME = "FPOEMS_SEC_SEM_02_01_20_BATCH_A.db";
    private static String DB_NAME;

    private SQLiteDatabase mDataBase;
    private final Context mContext;
// for getcategorymethod


    Integer dptexamrowid;
    String dptexamsn, dptexamcoscod, dptexamcostitle, dptexamLecturer, dptexamclass, dptexamdpt, dptexamdate;
    String dptexamstarttimeandendtime, dptexamvenue, dptexamclasssize, exammode;

    String dptstarttime, dptendtime;


    //String  dirPath = Environment.DIRECTORY_DOWNLOADS;



    public DataBaseHelper(Context context) {
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


    public void createDataBase() {
        //If database not exists copy it from the assets

        boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {
            this.getReadableDatabase();
            this.close();
            try {
                //Copy the database from assests
                copyDataBase();
                Log.e(TAG, "createDatabase database created");
            } catch (IOException mIOException) {
                Log.e(TAG, "createDataBase " + mIOException.getMessage() + "");
            }
        } else {
            Log.e(TAG, "createDatabase database Already IN Existance");
            File dbFile = new File(DB_PATH + DB_NAME);
            //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
            dbFile.delete();
            //Toast.makeText(mContext, "Database Ready", Toast.LENGTH_SHORT).show();
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }



    }

    //Check that the database exists here: /data/data/your package/databases/Da Name
    public boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        //Log.v("dbFile", dbFile + "   "+ dbFile.exists());

        return dbFile.exists();
    }

    //Copy the database from assets
    private void copyDataBase() throws IOException {
        try {
            InputStream mInput = mContext.getAssets().open(DB_NAME);
            String outFileName = DB_PATH + DB_NAME;
            OutputStream mOutput = new FileOutputStream(outFileName);
            byte[] mBuffer = new byte[1024];
            int mLength;
            while ((mLength = mInput.read(mBuffer)) > 0) {
                mOutput.write(mBuffer, 0, mLength);
            }
            mOutput.flush();
            mOutput.close();
            mInput.close();
        } catch (IOException mIOException) {
            Log.i(TAG, "copyDataBase " + mIOException + "");

        }
    }

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


    public ArrayList<MyListOfExamTimeTableModel> getUsersDataFromDataBase(String sqlqurry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<MyListOfExamTimeTableModel> userList = new ArrayList<>();
        //   String query = "SELECT * FROM projectdec";
        Log.e("Datatbase Name Been Use", "Db Been Use" + DB_NAME
        );

        //    String s = String.format("SELECT * FROM projectdec WHERE  %s LIKE %s order by %s ",tablecoln,fog,tablecoln);

        Cursor cursor = db.rawQuery(sqlqurry, null);
        if (cursor.getCount() == 0) {

            Log.e("querry result", "cursor is  emty");


            userList.add(new MyListOfExamTimeTableModel(0, "EMPTY", "EMPTY", "EMPTY", "EMPTY", "EMPTY",
                    "EMPTY", "EMPTY", "EMPTY", "EMPTY", "EMPTY", "EMPTY", "EMPTY"

            ));


            return userList;
        }


        while (cursor.moveToNext()) {


            dptexamrowid = cursor.getInt(cursor.getColumnIndex("RealRowIdPrimaryKey"));

            dptexamsn = cursor.getString(cursor.getColumnIndex("S/N"));
            //Integer intsn = Integer.parseInt(dptexamsn);

            dptexamcostitle = cursor.getString(cursor.getColumnIndex("COURSETITLE/LECTURER"));

            dptexamcoscod = cursor.getString(cursor.getColumnIndex("COURSECODE"));


            dptexamclass = cursor.getString(cursor.getColumnIndex("CLASS"));
            dptexamdpt = cursor.getString(cursor.getColumnIndex("DEPT"));

            dptexamdate = cursor.getString(cursor.getColumnIndex("DATE"));


            dptexamvenue = cursor.getString(cursor.getColumnIndex("VENUE"));
            dptexamclasssize = cursor.getString(cursor.getColumnIndex("CLASSSIZE"));
            exammode = cursor.getString(cursor.getColumnIndex("ExamMode"));


            if (exammode.equalsIgnoreCase("CBT")) {
                dptexamstarttimeandendtime = cursor.getString(cursor.getColumnIndex("TIME"));

                int i = dptexamstarttimeandendtime.indexOf('m') + 1;
                Log.e("querry  dptstarttime", "cursor is not  emty " + i);
                dptstarttime = dptexamstarttimeandendtime.substring(0, i);

                dptendtime = dptexamstarttimeandendtime.substring(5, dptexamstarttimeandendtime.length());

            }

            if (exammode.equalsIgnoreCase("POP")) {

                dptexamstarttimeandendtime = cursor.getString(cursor.getColumnIndex("TIME"));

                dptstarttime = dptexamstarttimeandendtime;
                dptendtime = "Till Stop";


            }


            dptexamLecturer = cursor.getString(cursor.getColumnIndex("Lecturer"));


            userList.add(new MyListOfExamTimeTableModel(dptexamrowid, dptexamsn, dptexamcoscod, dptexamcostitle, dptexamLecturer, dptexamdpt,
                    dptexamclass, dptexamdate, dptstarttime, dptendtime, dptexamvenue, dptexamclasssize, exammode

            ));
            Log.e("querry result", "cursor is not  emty");


            Log.v("dbFile", "querry db on progress");


        }


        return userList;
    }


    public SQLiteDatabase creatObjectOfThisDatabasOutSideThisClass() {

        SQLiteDatabase dba = this.getWritableDatabase();
        return dba;
    }

    public static void setDbName(String dbName) {
        DB_NAME = dbName;
    }


    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }






}



