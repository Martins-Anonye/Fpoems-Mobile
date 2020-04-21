package com.example.martins.fpoems;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.martins.fpoems.DbLinkMaker.DbLinkModel;
import com.example.martins.fpoems.DbLinkMaker.ModelForCoverPageAndExamGuid;
import com.example.martins.fpoems.DbLinkMaker.copydbLinkToDestination;
import com.example.martins.fpoems.DownloadeFileMaker.CopyDownloadedDatabaseToInternalStrogeForSQLithelperToUse;
import com.example.martins.fpoems.DownloadeFileMaker.DownloadFpoEmsFileFromServer;
import com.example.martins.fpoems.JsonFileGetterFromServer.CheckJsonServerStatus;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class EMSSetupActivity extends AppCompatActivity {
    FloatingActionButton startmyfpoems;
    CircularImageView circurerimageview, viewmyExamguid;
    static AppCompatSpinner myListOfDatabseBeenDownloaded;
    static String rootOfDir;
    private long enqueue;
    private DownloadManager dm;


    static List<String> myListOfDatabaseFilteredContainOlandNew = new ArrayList<>();

    static List<String> myListOfDatabaseFilteredContainOlandNewExtension = new ArrayList<>();

    static String selectedDbnameAndItsExtension;

    static String selectedDBTableName;
    static String concatedSelectedDatabasenameExtensionandItsRoot;
    static final List<String> myListOfDatabaseFilter = new ArrayList<>();
    public static final List<String> myListOfDatabaseFilterWithNameAndExtension = new ArrayList<>();
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emssetup);
        startmyfpoems = (FloatingActionButton) findViewById(R.id.startmyfpoems);
        circurerimageview = (CircularImageView) findViewById(R.id.circurerimageview);
        viewmyExamguid = (CircularImageView) findViewById(R.id.viewmyExamguid);
        myListOfDatabseBeenDownloaded = (AppCompatSpinner) findViewById(R.id.mydownloadedDatabaseListSpinner);
        dm = (DownloadManager) EMSSetupActivity.this.getSystemService(Context.DOWNLOAD_SERVICE);

        DownloadFpoEmsFileFromServer.setPararmeterForStartDownloading(EMSSetupActivity.this, dm);

        registerReceiver(new DownloadFpoEmsFileFromServer(), new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        mAdView = (AdView) findViewById(R.id.adView1);
        AdRequest adRequest2 = new AdRequest.Builder().build();

        mAdView.loadAd(adRequest2);

        AdRequest adRequestint = new AdRequest.Builder().build();

        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-2789057166231954/3411959102");
        interstitialAd.loadAd(adRequestint);
        interstitialAd.show();


        makeSpinnerwork(EMSSetupActivity.this);
        startmyfpoems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadFpoEmsFileFromServer.setSelectedDbnameAndItsExtension(selectedDbnameAndItsExtension);
                DownloadFpoEmsFileFromServer.setSelectedDBTableName(selectedDBTableName);
                showDownloadandStarttheLstActivity();


            }
        });

        circurerimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //downloadfile();
                CheckJsonServerStatus.jsonStatus(EMSSetupActivity.this);
                delayDownloadForJsonStatus();
                if (isExternalStorageAvailable() || isExternalStorageReadOnly()) {
                    executeMyDownload();
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(EMSSetupActivity.this);
                    alert.setIcon(R.drawable.ic_error_black_24dp);
                    alert.setTitle(" SDCARD ERROR");
                    alert.setMessage("Pls insert sdcard/memory card for  data storage");
                    alert.setPositiveButton("Continou", new DialogInterface.OnClickListener() {


                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            AlertDialog.Builder alert2 = new AlertDialog.Builder(EMSSetupActivity.this);
                            alert2.setIcon(R.drawable.ic_close_black_24dp);
                            alert2.setTitle("Try IT");
                            alert2.setMessage("Pls insert sdcard. \n This maight course error. \n Do you want to continue");
                            alert2.setCancelable(true);
                            alert2.setPositiveButton("Yes Continue", new DialogInterface.OnClickListener() {


                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    executeMyDownload();


                                    String dirPathExternal = "/sdcard/Android/data/" + EMSSetupActivity.this.getPackageName() + "/files/data/data/" + EMSSetupActivity.this.getPackageName() + "/databases/";


                                    File dir0external = new File(dirPathExternal);

                                    if (!dir0external.exists()) {
                                        Toast.makeText(EMSSetupActivity.this, "Sdcard error occured. \n Pls insert card", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                            alert2.show();

                        }
                    });
                    alert.show();

                }


            }
        });


        viewmyExamguid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ModelForCoverPageAndExamGuid> valuesFromDbLinkForCoverPageandExamGuide = new copydbLinkToDestination(getApplicationContext()).getValuesFromDbLinkForCoverPageandExamGuide();
                ModelForCoverPageAndExamGuid modelForCoverPageAndExamGuid = valuesFromDbLinkForCoverPageandExamGuide.get(0);
                String linkname = modelForCoverPageAndExamGuid.getLinkname();

                String dirPath = Environment.DIRECTORY_DOWNLOADS + "/" + linkname;
                openDocument(dirPath);
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        DownloadFpoEmsFileFromServer.onRequestPermissionResult(requestCode, permissions, grantResults);

    }


    public void openDocument(String name) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
        File file = new File(name);
        String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
        String mimetype = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        if (extension.equalsIgnoreCase("") || mimetype == null) {
            // if there is no extension or there is no definite mimetype, still try to open the file
            intent.setDataAndType(Uri.fromFile(file), "text/*");
        } else {
            intent.setDataAndType(Uri.fromFile(file), mimetype);
        }
        // custom message for the intent
        startActivity(Intent.createChooser(intent, "Choose an Application:"));
    }
    //


    public static void makeSpinnerwork(final Context mgetContext) {


        // for old existind database
        String dirPath = "/data/data/" + mgetContext.getPackageName() + "/databases/";
        String dirPathExternal = "/sdcard/Android/data/" + mgetContext.getPackageName() + "/files/data/data/" + mgetContext.getPackageName() + "/databases/";


        //String  dirPath = Environment.DIRECTORY_DOWNLOADS;

        File dir0 = new File(dirPath);
        File dir0external = new File(dirPathExternal);

        File[] files;

        if (dir0external.exists()) {

            files = dir0external.listFiles();

        } else {

            files = dir0.listFiles();
        }

        int i = 0;
        final List<String> myListOfDatabaseFilterextensionholder = new ArrayList<>();

        final List<String> myListOfDatabasenotFiltered = new ArrayList<>();
        final List<String> myfilesize = new ArrayList<>();

        for (File fil : files) {
            String s = fil.toString();
            int namestart = s.lastIndexOf("/") + 1;


            String substringdbfiletname = s.substring(namestart, s.length());

            myListOfDatabasenotFiltered.add(substringdbfiletname);

            if (substringdbfiletname.startsWith("FPO") && (substringdbfiletname.endsWith(".db") || substringdbfiletname.endsWith(".db1") || substringdbfiletname.endsWith(".db2") || substringdbfiletname.endsWith(".db3"))) {
                //  if(substringdbfiletname.startsWith("FPO")     ){
                myListOfDatabaseFilterWithNameAndExtension.add(substringdbfiletname);

                int dotend = substringdbfiletname.lastIndexOf(".");
                String onlythename = substringdbfiletname.substring(0, dotend);
                String fileSizeKiloBytes = getFileSizeKiloBytes(fil);
                myfilesize.add(onlythename + " " + fileSizeKiloBytes);

                String onlythenamedotextension = substringdbfiletname.substring(dotend, substringdbfiletname.length());
                myListOfDatabaseFilterextensionholder.add(onlythenamedotextension);
                myListOfDatabaseFilter.add(onlythename);

            }
            rootOfDir = s.substring(0, namestart);

        }


///for the new downloaded database
        ArrayList<DbLinkModel> valuesFromDbLinkForFPOEMSTABLE = new copydbLinkToDestination(mgetContext).getValuesFromDbLinkForFPOEMSTABLE();
        DbLinkModel dbLinkModel = valuesFromDbLinkForFPOEMSTABLE.get(0);

        if (!dbLinkModel.getDbname().equalsIgnoreCase(null)) {

            if (dbLinkModel.getDbname().contains(".")) {

                String newdbname = dbLinkModel.getDbname();
                int newdbdotend = newdbname.lastIndexOf(".");
                String newdbextensiononly = newdbname.substring(newdbdotend, newdbname.length());

                String newdbnameonly = newdbname.substring(0, newdbdotend);


                if (myListOfDatabaseFilter.contains(newdbnameonly)) {

                    int removeItIfexisting = myListOfDatabaseFilter.indexOf(newdbnameonly);
                    myListOfDatabaseFilter.remove(removeItIfexisting);
                    int removeItsExtensionAlso = myListOfDatabaseFilterextensionholder.indexOf(newdbextensiononly);
                    myListOfDatabaseFilterextensionholder.remove(removeItsExtensionAlso);


                    //we reoder the result i.e add others with out re-adding the the database
                    myListOfDatabaseFilteredContainOlandNew.add(newdbnameonly);
                    myListOfDatabaseFilteredContainOlandNewExtension.add(newdbextensiononly);


                    // add others also at the bottom
                    myListOfDatabaseFilteredContainOlandNew.addAll(myListOfDatabaseFilter);
                    myListOfDatabaseFilteredContainOlandNewExtension.addAll(myListOfDatabaseFilterextensionholder);

                } else {

                    myListOfDatabaseFilteredContainOlandNew = myListOfDatabaseFilter;
                    myListOfDatabaseFilteredContainOlandNewExtension = myListOfDatabaseFilterextensionholder;
                }

            } else {
                myListOfDatabaseFilteredContainOlandNew = myListOfDatabaseFilter;
                myListOfDatabaseFilteredContainOlandNewExtension = myListOfDatabaseFilterextensionholder;


            }


        } else {


            myListOfDatabaseFilteredContainOlandNew = myListOfDatabaseFilter;
            myListOfDatabaseFilteredContainOlandNewExtension = myListOfDatabaseFilterextensionholder;

        }


        // ArrayAdapter<String> dataAdapterdatabase = new ArrayAdapter<String>(mgetContext, R.layout.spinnerlayout,myListOfDatabaseFilter);

        ArrayAdapter<String> dataAdapterdatabase = new ArrayAdapter<String>(mgetContext, R.layout.spinnerlayout, myfilesize);
        dataAdapterdatabase.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myListOfDatabseBeenDownloaded.setAdapter(dataAdapterdatabase);

        myListOfDatabseBeenDownloaded.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int spinerpositions, long id) {
                String dbnameonly = myListOfDatabaseFilteredContainOlandNew.get(spinerpositions);
                String nameextension = myListOfDatabaseFilteredContainOlandNewExtension.get(spinerpositions);
                selectedDbnameAndItsExtension = dbnameonly.concat(nameextension);

                selectedDBTableName = dbnameonly;

                concatedSelectedDatabasenameExtensionandItsRoot = rootOfDir.concat(selectedDbnameAndItsExtension);
                // myListOfDatabasenotFiltered.
                Log.e("nameTableName", selectedDBTableName + "  nameandExtension" + selectedDbnameAndItsExtension);


                Log.e("Spinner", dbnameonly + "\n \n \n " + nameextension + "   is selected    \n" + concatedSelectedDatabasenameExtensionandItsRoot);

                String substringnotLastForwardSlash = rootOfDir.substring(0, rootOfDir.length() - 1);


                CopyDownloadedDatabaseToInternalStrogeForSQLithelperToUse.setDbName(selectedDbnameAndItsExtension);
                CopyDownloadedDatabaseToInternalStrogeForSQLithelperToUse.setFielSource(concatedSelectedDatabasenameExtensionandItsRoot);
                CopyDownloadedDatabaseToInternalStrogeForSQLithelperToUse.setmContext(mgetContext);

                AsyncTask myCopier = new AsyncTask() {


                    @Override
                    protected Object doInBackground(Object[] params) {
                        new CopyDownloadedDatabaseToInternalStrogeForSQLithelperToUse().createDataBaseDontDelete();

                        return null;
                    }
                };


                myCopier.execute("");

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //    String env = "/sdcard/Android/data/" + mgetContext.getPackageName() + "/files/data/data/" + mgetContext.getPackageName() + "/databases";
        // final File dbFile = new File(getFilesDir().getParent()+"/databases/");
        // final File dbFile = new File(Environment.getDataDirectory()+"/data/"+mgetContext.getPackageName()+"/databases");
        String env = Environment.getExternalStorageDirectory().getAbsolutePath() + "Android/data/" + mgetContext.getPackageName() + "/files/data/data/" + mgetContext.getPackageName() + "/databases";


        String path = Environment.getExternalStorageDirectory().getPath();
        Log.e("checker path:       ", path);
        String pathinteneal = Environment.getDataDirectory().getPath();
        Log.e("chec inneal:       ", pathinteneal);


        File rootDirectory = Environment.getRootDirectory();

        Log.e("checker:   root    ", rootDirectory.toString());


/////


    }


    public void showDownloadandStarttheLstActivity() {


        //  startActivity(listOfDPTINDBActivity);
        Intent listOfDPTINDBActivity = new Intent(EMSSetupActivity.this, ListOfDPTINDBActivity.class);
        listOfDPTINDBActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        listOfDPTINDBActivity.putExtra("DBNAMEANDEXTENSION", selectedDbnameAndItsExtension);
        listOfDPTINDBActivity.putExtra("TABLENAME", selectedDBTableName);

        //show downloaded items that where  completed
        //   i.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);


        startActivity(listOfDPTINDBActivity);
    }


    private static String getFileSizeKiloBytes(File file) {
        DecimalFormat dcimal = new DecimalFormat("###.##");
        double v = (double) file.length() / 1024;
        String format = dcimal.format(v);
        return format + "  kb";
    }


    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }


    private void executeMyDownload() {


        copydbLinkToDestination mcopydbLinkToDestination = new copydbLinkToDestination(EMSSetupActivity.this);
        ArrayList<DbLinkModel> valuesFromDbLink = mcopydbLinkToDestination.getValuesFromDbLinkForFPOEMSTABLE();
        DbLinkModel dbLinkModel = valuesFromDbLink.get(0);
        String dbname = dbLinkModel.getDbname();


        if (myListOfDatabaseFilterWithNameAndExtension.contains(dbname)) {
            AlertDialog.Builder buidr = new AlertDialog.Builder(EMSSetupActivity.this);
            buidr.setTitle("Exam File Already Downloaded");
            buidr.setMessage("Exam File Already on Device ");
            buidr.setIcon(R.drawable.ic_warning_black_24dp);
            buidr.show();

        }
        // new DownloadTask(EMSSetupActivity.this).execute("https://www.dropbox.com/s/kp1orvnxjc4rpvh/cbtmode.db?dl=0");
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (EMSSetupActivity.this.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                    // persmission denied, request it


                    String permit = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

                    String[] permissionsneeded = new String[]{permit};
                    final int PERMISSION_STRORAG_CODE = 1000;
                    EMSSetupActivity.this.requestPermissions(permissionsneeded, PERMISSION_STRORAG_CODE);

                } else {
                    //permission already granted
                    // perform download
                    DownloadFpoEmsFileFromServer.startDownloading();
                }
            } else {
                dm = (DownloadManager) EMSSetupActivity.this.getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadFpoEmsFileFromServer.startDownloading();


            }


        }

    }




    private void delayDownloadForJsonStatus(){

        Handler myHandle = new Handler( );
        myHandle.postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        }, 5000);

    }
}

