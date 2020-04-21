package com.example.martins.fpoems.DownloadeFileMaker;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Martins on 2/21/2020.
 */

public class CopyDownloadedDatabaseToInternalStrogeForSQLithelperToUse {

    static String fielSource;


    private static String TAG = "CopyNewDB"; // Tag just for the LogCat window
    //destination path (location) of our database on device
    //   private static String DB_PATH = "";
    private static Context mContext;
    private static String DB_PATH;
    //private static String DB_NAME ="(students).sqlite";// Database name
    // private static String DB_NAME = "FPOEMS_SEC_SEM_02_01_20_BATCH_A.db";
    private static String DB_NAME;


    public void createDataBase() {
        //If database not exists copy it from the assets
        DB_PATH = "/data/data/" + mContext.getPackageName() + "/databases/";

        boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {

            try {
                //Copy the database from assests
                copyDataBase();
                Log.e(TAG, "createDatabase database created from " + fielSource + "  to" + DB_PATH);
            } catch (IOException mIOException) {
                Log.e(TAG, "createDataBase " + mIOException.getMessage() + "");
            }
        } else {
            Log.e(TAG, "createDatabase database Already IN Existance ,fullnameanddir" + fielSource);
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


            FileInputStream mInput = new FileInputStream(fielSource);
            OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
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


    public static void setDbName(String dbName) {
        DB_NAME = dbName;
    }

    public static void setmContext(Context mContext) {
        CopyDownloadedDatabaseToInternalStrogeForSQLithelperToUse.mContext = mContext;
    }

    public static void setFielSource(String fielSource) {
        CopyDownloadedDatabaseToInternalStrogeForSQLithelperToUse.fielSource = fielSource;
    }









    public void createDataBaseDontDelete() {
        // at first the download is always in SDCard
        // so we copy it internal storage
        DB_PATH = "/data/data/" + mContext.getPackageName() + "/databases/";

        boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {

            try {
                //Copy the database from assests
                copyDataBase();
                Log.e(TAG, "createDatabase database created from " + fielSource + "  to" + DB_PATH);
            } catch (IOException mIOException) {
                Log.e(TAG, "createDataBase " + mIOException.getMessage() + "");
            }
        }

    }

}
