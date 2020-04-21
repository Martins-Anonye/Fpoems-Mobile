package com.example.martins.fpoems.DownloadeFileMaker;


import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Path;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;
import com.example.martins.fpoems.DbLinkMaker.DbLinkModel;
import com.example.martins.fpoems.DbLinkMaker.ModelForCoverPageAndExamGuid;
import com.example.martins.fpoems.DbLinkMaker.copydbLinkToDestination;
import com.example.martins.fpoems.EMSSetupActivity;
import com.example.martins.fpoems.JsonFileGetterFromServer.MyJsonHelperToDownload;
import com.example.martins.fpoems.ListOfDPTINDBActivity;
import com.example.martins.fpoems.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.martins.fpoems.EMSSetupActivity.makeSpinnerwork;
import static com.example.martins.fpoems.EMSSetupActivity.myListOfDatabaseFilterWithNameAndExtension;


/**
 * Created by Martins on 2/8/2020.
 */

public class DownloadFpoEmsFileFromServer extends BroadcastReceiver {
    private static long enqueue;

    static DownloadManager manager, managerguid;
    static FirebaseStorage storag1;
    static StorageReference storagref1;
    static StorageReference storagref2;
    static String selectedDbnameAndItsExtension;
    static final List<String> filterWithNameAndExtension = new ArrayList<>();

    static String selectedDBTableName;

    static Context cont;
    static Intent intent;


    public static void setSelectedDbnameAndItsExtension(String selectedDbnameAndItsExtension) {
        DownloadFpoEmsFileFromServer.selectedDbnameAndItsExtension = selectedDbnameAndItsExtension;
    }

    public static void setSelectedDBTableName(String selectedDBTableName) {
        DownloadFpoEmsFileFromServer.selectedDBTableName = selectedDBTableName;
    }


    public static Context getCont() {
        return cont;
    }

    public static void setPararmeterForStartDownloading(final Context cont, final DownloadManager managere) {
        DownloadFpoEmsFileFromServer.cont = cont;

        managerguid = managere;
        manager = managere;

    }

    public static void startDownloading() {


        storagref1 = FirebaseStorage.getInstance().getReference();
        storagref1.child("FPOEMS_20_12_19_BATCH_A");
        storagref1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                //  String uri1 =uri.toString();
                // Note that filename = FPOEMS_20_12_19_BATCH_A


            }
        });

        // String url = "http://www.dropbox//maartins//examtimetable_20_03_20_BatchA_1.db";
        copydbLinkToDestination mcopydbLinkToDestination = new copydbLinkToDestination(cont);
        ArrayList<DbLinkModel> valuesFromDbLink = mcopydbLinkToDestination.getValuesFromDbLinkForFPOEMSTABLE();
        DbLinkModel dbLinkModel = valuesFromDbLink.get(0);
        Integer rowId = dbLinkModel.getId();

        String dblinks = dbLinkModel.getDblinks();
        Uri uri1 = android.net.Uri.parse(dblinks);
        String dbname = dbLinkModel.getDbname();


        if (rowId == 1) {

            // notification from json server

            MyJsonHelperToDownload.jsonDownload(cont);
        }

        if (rowId == 2) {
            // notification from firebase
            String justTestLink = "https://www.dropbox.com/s/lc61hfhc78zc4do/FPOEMS_TESTING.db?dl=1";
            final Uri uri1justTestLink = android.net.Uri.parse(justTestLink);

            final String dbnametest = "FPOEMS_TESTING.db";

           // myStartDownload(justTestLink, uri1justTestLink, dbnametest);


        }


        //downloadFileUsingProdownloaderForDB(uri1,dbname);


        ArrayList<ModelForCoverPageAndExamGuid> valuesFromDbLinkForCoverPageandExamGuide = mcopydbLinkToDestination.getValuesFromDbLinkForCoverPageandExamGuide();
        ModelForCoverPageAndExamGuid modelForCoverPageAndExamGuid = valuesFromDbLinkForCoverPageandExamGuide.get(0);
        String linkguid = modelForCoverPageAndExamGuid.getLink();
        String linkname = modelForCoverPageAndExamGuid.getLinkname();
        //  downloadFileUsingProdownloaderForguide(linkguid,linkname);


        // downloadFileUsingDownloadManagerForGuid(cont,linkname,linkguid);


    }


    private static void downloadFileUsingDownloadManagerForGuid(Context cont, String filenameandItsextension, String uri0) {
        Uri uri = Uri.parse(uri0);
        String dirPath = Environment.DIRECTORY_DOWNLOADS;

        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("FPOEMS Cover Page and Guide");
        request.setDescription("Downloading Cover Page and Guide.....");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        // request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, ""+ System.currentTimeMillis());
        request.setDestinationInExternalFilesDir(cont, dirPath, filenameandItsextension);


        managerguid.enqueue(request);

    }


    private static void downloadFileUsingDownloadManager(Context cont, String filenameandItsextension, Uri uri) {
        String dirPath = "/data/data/" + cont.getPackageName() + "/databases/";

        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("FPOEMS Time Table");
        request.setDescription("Updating FPOEMS Time System.....");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        // request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, ""+ System.currentTimeMillis());
        request.setDestinationInExternalFilesDir(cont, dirPath, filenameandItsextension);


        enqueue = manager.enqueue(request);

        DownloadManager.Query mquery = new DownloadManager.Query();
        mquery.setFilterById(enqueue);
        Cursor c = manager.query(mquery);
intent = new Intent();
        long downloadId = intent.getLongExtra(
                DownloadManager.EXTRA_DOWNLOAD_ID, 0);

// to check download status only, to check download completed: i used broadcast receiver
       // DownloadStatusChecker.DownloadStatus(cont, c, downloadId);


    }


    public static void onRequestPermissionResult(int requestcode, String[] permissions, int[] grantResults) {

        final int PERMISSION_STRORAG_CODE = 1000;

        switch (requestcode) {
            case PERMISSION_STRORAG_CODE: {

                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                    startDownloading();

                } else {

                    //permission denied from popup,
                    //show error message

                    Toast.makeText(cont, "Permission denied ......!", Toast.LENGTH_SHORT).show();
                }
            }


        }
    }


    //

    private static int downloadFileUsingProdownloaderForDB(Uri url, String fileName) {
        String suri = url.toString();
        // String dirPath= Environment.DIRECTORY_DOWNLOADS;
        // Context ctx = this; // for Activity, or Service. Otherwise simply get the context.
        // String dbname = "mydb.db";

        // Path dbpath = cont.getDatabasePath("dbfilename.db");
        String dirPath = "/data/data/" + cont.getPackageName() + "/databases/";
        //String dirPath = dbpath.toString();
        // String fileName = url.substring(url.lastIndexOf('/')+1);
        //  String dirPath = getFilesDir().getAbsolutePath()+File.separator+"downloads";
        //  //String dirPath = android.os.Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"downloads";

        return /*download id*/ PRDownloader.download(suri, dirPath, fileName)
                .build()
                .start(new OnDownloadListener() {

                    @Override
                    public void onDownloadComplete() {
                        Toast.makeText(cont, "Exam Time Table Updated Successfully", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Error error) {
                        Toast.makeText(cont, "Error occured", Toast.LENGTH_SHORT).show();
                    }
                });

    }


    private static int downloadFileUsingProdownloaderForguide(String url, String fileName) {

        String dirPath = Environment.DIRECTORY_DOWNLOADS;
        // Context ctx = this; // for Activity, or Service. Otherwise simply get the context.
        // String dbname = "mydb.db";

        // Path dbpath = cont.getDatabasePath("dbfilename.db");

        //String dirPath = dbpath.toString();
        // String fileName = url.substring(url.lastIndexOf('/')+1);
        //  String dirPath = getFilesDir().getAbsolutePath()+File.separator+"downloads";
        //  //String dirPath = android.os.Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"downloads";

        return /*download id*/ PRDownloader.download(url, dirPath, fileName)
                .build()
                .start(new OnDownloadListener() {

                    @Override
                    public void onDownloadComplete() {
                        Toast.makeText(cont, "Exam Guide Updated Successfully", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Error error) {
                        Toast.makeText(cont, "Error occured", Toast.LENGTH_SHORT).show();
                    }
                });

    }


    @Override
    public void onReceive(final Context context, Intent intent) {


        String action = intent.getAction();
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
            long downloadId = intent.getLongExtra(
                    DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(enqueue);
            Cursor c = manager.query(query);
            if (c.moveToFirst()) {
                int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
                if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {

                    String uriString = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));

                    Uri a = Uri.parse(uriString);
                    File d = new File(a.getPath());


                    CopyDownloadedDatabaseToInternalStrogeForSQLithelperToUse.setDbName(d.getName());
                    CopyDownloadedDatabaseToInternalStrogeForSQLithelperToUse.setFielSource(d.toString());
                    CopyDownloadedDatabaseToInternalStrogeForSQLithelperToUse.setmContext(cont);

                    AsyncTask myCopier = new AsyncTask() {


                        @Override
                        protected Object doInBackground(Object[] params) {
                            makeSpinnerwork(getCont());

                            new CopyDownloadedDatabaseToInternalStrogeForSQLithelperToUse().createDataBase();
                            return null;
                        }
                    };


                    myCopier.execute("");
                    //Toast toast = Toast(context);
                     Toast.makeText(context, "The file " + d.getName() + " is Downloaded Completly " + "  ", Toast.LENGTH_LONG).show();
                    //toast.setGravity(Gravity.TOP, 25, 400);
                    //toast.show();
                    Log.e("contDownloader", "the part   " + d.getPath() + "   the Name   " + d.getName());

                    EMSSetupActivity.makeSpinnerwork(context);
                    // we copy the file from here

                }
            }
        }


    }


    public static void myStartDownload(String justTestLink, final Uri uri1justTestLink, final String dbnametest) {


        if (myListOfDatabaseFilterWithNameAndExtension.contains(dbnametest)) {

            AlertDialog.Builder buder = new AlertDialog.Builder(cont);
            buder.setIcon(R.drawable.ic_warning_black_24dp);

            buder.setTitle("Download Manager Confirmation");
            buder.setMessage("Exam File Already \n on Device, Continou Download ");
            buder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String dirPathExternal = "/sdcard/Android/data/" + cont.getPackageName() + "/files/data/data/" + cont.getPackageName() + "/databases/";

// we delete the file if its already existing in the sdcard

                    File dir0external = new File(dirPathExternal);

                    File[] files;

                    if (dir0external.exists()) {

                        files = dir0external.listFiles();


                        int i = 0;

                        final List<String> myListOfDatabasenotFiltered = new ArrayList<>();

                        for (File fil : files) {
                            String s = fil.toString();
                            int namestart = s.lastIndexOf("/") + 1;


                            String substringdbfiletname = s.substring(namestart, s.length());

                            myListOfDatabasenotFiltered.add(substringdbfiletname);

                            if (substringdbfiletname.startsWith("FPO") && (substringdbfiletname.endsWith(".db") || substringdbfiletname.endsWith(".db1") || substringdbfiletname.endsWith(".db2") || substringdbfiletname.endsWith(".db3"))) {
                                //  if(substringdbfiletname.startsWith("FPO")     ){

                                if (substringdbfiletname.equalsIgnoreCase(dbnametest)) {

                                    fil.delete();
                                    downloadFileUsingDownloadManager(cont, dbnametest, uri1justTestLink);
                                    break;
                                }

                            }

                        }

                    }


                }
            });
        }
        // if its not existing do the following

        else {
            downloadFileUsingDownloadManager(cont, dbnametest, uri1justTestLink);


        }
        // downloadFileUsingDownloadManager(cont, dbname,uri1,managere);


    }
}


//////

/////

