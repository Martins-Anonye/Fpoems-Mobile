package com.example.martins.fpoems.DbLinkMaker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Martins on 2/18/2020.
 */




/**
 * Created by Juliet on 9/5/2019.
 */


public class copydbLinkToDestination extends SQLiteOpenHelper {
    private static String TAG = "DataBaseHelper"; // Tag just for the LogCat window
    //destination path (location) of our database on device
    private static String DB_PATH = "";
    //private static String DB_NAME ="(students).sqlite";// Database name
    private static String DB_NAME = "DownloadedLink.db";
    private SQLiteDatabase mDataBase;
    private final Context mContext;
// for getcategorymethod

Integer id;
    String  links ,dbnameafterdownload, tablename;


    Integer idguid;
    String  linksguid ,linknameguide;

    public copydbLinkToDestination(Context context) {
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


    public void createDataBaseDownloadedLink() {
        //If database not exists copy it from the assets

        boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {
            this.getReadableDatabase();
            this.close();
            try {
                //Copy the database from assests
                copyDataBaseDownloadedLink();
                Log.e(TAG, "createDatabase DownloadedLink created");
            } catch (IOException mIOException) {
                Log.e(TAG, "createDataBase " + mIOException.getMessage() + "");
            }
        } else {
            Log.e(TAG, "createDatabase DownloadedLink Already IN Existance");
            File dbFile = new File(DB_PATH + DB_NAME);
            //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
            dbFile.delete();
            //Toast.makeText(mContext, "Database Ready", Toast.LENGTH_SHORT).show();
            try {
                copyDataBaseDownloadedLink();
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
    private void copyDataBaseDownloadedLink() throws IOException {
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
        public ArrayList<DbLinkModel> getValuesFromDbLinkForFPOEMSTABLE(){

     ArrayList<DbLinkModel> myDblinkmodel = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String query = "select * from DatabaseLinkHolder limit 1";

            Cursor cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {

                //   String  link ,dbnameafterdownload, tablename;
                id = cursor.getInt(cursor.getColumnIndex("Id"));

                links = cursor.getString(cursor.getColumnIndex("Links"));

                dbnameafterdownload = cursor.getString(cursor.getColumnIndex("DatabaseNameAfterDownload"));

                tablename = cursor.getString(cursor.getColumnIndex("TableName"));


                myDblinkmodel.add(new DbLinkModel(id,links, dbnameafterdownload, tablename));


            }
            return myDblinkmodel;

    }



    public  boolean updateContactForFPOEMSTABLE(Integer id, String link, String dbnameafterdownload, String tablename) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("Links", link);
        contentValues.put("DatabaseNameAfterDownload", dbnameafterdownload);
        contentValues.put("TableName", tablename);
        db.update("DatabaseLinkHolder", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }


    public  boolean updateContactForFPOEMSTABLEOnlyID(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);

        ArrayList<DbLinkModel> valuesFromDbLinkForFPOEMSTABLE = getValuesFromDbLinkForFPOEMSTABLE();
        DbLinkModel dbLinkModel = valuesFromDbLinkForFPOEMSTABLE.get(0);
        String dbname = dbLinkModel.dbname;
        db.update("DatabaseLinkHolder", contentValues, "DatabaseNameAfterDownload = ? ", new String[] { dbname } );
        return true;
    }




    public ArrayList<ModelForCoverPageAndExamGuid> getValuesFromDbLinkForCoverPageandExamGuide(){

        ArrayList<ModelForCoverPageAndExamGuid> myDblinkmodel = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from CoverPageAndGuidLinkHolder limit 1";

        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {



            //   String  link ,dbnameafterdownload, tablename;
            idguid = cursor.getInt(cursor.getColumnIndex("ID"));

            linksguid = cursor.getString(cursor.getColumnIndex("LINK"));

            linknameguide = cursor.getString(cursor.getColumnIndex("NAME"));



            myDblinkmodel.add(new ModelForCoverPageAndExamGuid(idguid,linksguid, linknameguide));


        }
        return myDblinkmodel;

    }



    public  boolean updateContactForCoverPageAndExamGuide(Integer id, String link, String linknam) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("LINK", link);
        contentValues.put("NAME", linknam);

        db.update("CoverPageAndGuidLinkHolder", contentValues, "ID = ? ", new String[] { Integer.toString(id) } );
        return true;
    }





    public SQLiteDatabase creatObjectOfThisDatabasOutSideThisClass() {

        SQLiteDatabase dba = this.getWritableDatabase();
        return dba;
    }
}