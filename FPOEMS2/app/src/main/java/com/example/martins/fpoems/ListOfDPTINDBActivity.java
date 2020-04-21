package com.example.martins.fpoems;

import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.martins.fpoems.DataModelForListOfDPTINDB.CopyDataBaseInAssetsToInternalStorage;
import com.example.martins.fpoems.DataModelForListOfDPTINDB.DataBaseHelper;
import com.example.martins.fpoems.DataModelForListOfDPTINDB.DataBaseHelperSpinnerMaker;
import com.example.martins.fpoems.DataModelForListOfDPTINDB.MyListOfExamTimeTableModel;
import com.example.martins.fpoems.DataModelForListOfDPTINDB.PupulateListViewBaseOnSelectionOfSpinner;
import com.example.martins.fpoems.DbLinkMaker.copydbLinkToDestination;
import com.example.martins.fpoems.SearchViewSearchingOperation.SearchViewSearchingOperation;
import com.example.martins.fpoems.listitemofdptexam.MyListAdapterAdder;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ListOfDPTINDBActivity extends AppCompatActivity {
    public static ListView listofexamtime;
    android.support.v7.widget.AppCompatSpinner selectdptspinner;
    HashSet<String> userListspinnerclassnaddpt;
    ArrayList<String> arrayList;
    Toolbar toolbar;
    MaterialSearchView searchView;
    ScaleGestureDetector scaleGestureDetector;
    private float mscalFactor = 1.0f;

    public static String mDBNAMEANDEXTENSION;
    public static String mTABLENAME;
    AdView mAdView;
    RelativeLayout myRootLayout;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_dptindb);
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);// to display back arror which act as home button
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.showmenu);
        listofexamtime = (ListView) findViewById(R.id.listofexamtime);
        searchView = (MaterialSearchView) findViewById(R.id.sear);

        Bundle transfereddata = getIntent().getExtras();
        mDBNAMEANDEXTENSION = transfereddata.get("DBNAMEANDEXTENSION").toString();
        mTABLENAME = transfereddata.get("TABLENAME").toString();


        DataBaseHelper.setDbName(mDBNAMEANDEXTENSION);
        DataBaseHelperSpinnerMaker.setDbName(mDBNAMEANDEXTENSION);
        DataBaseHelperSpinnerMaker.setTABLENAME(mTABLENAME);
        PupulateListViewBaseOnSelectionOfSpinner.setDbName(mDBNAMEANDEXTENSION);
        PupulateListViewBaseOnSelectionOfSpinner.setTABLENAME(mTABLENAME);


        mAdView = (AdView) findViewById(R.id.adView1);
        AdRequest adRequest2 = new AdRequest.Builder().build();

        mAdView.loadAd(adRequest2);


        AdRequest adRequestint = new AdRequest.Builder().build();

        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-2789057166231954/3411959102");
        interstitialAd.loadAd(adRequestint);
        interstitialAd.show();


        new AsyntaskclassrunnerMakeForCreatingDB().execute("");

        selectdptspinner = (android.support.v7.widget.AppCompatSpinner) findViewById(R.id.selectdptspinner);
        selectdptspinner.setPrompt("search e.g>> CTE,HND 2  or CTE,HND 2 R1/R2");
        SearchViewSearchingOperation searchoperation = new SearchViewSearchingOperation(this, searchView, listofexamtime);


        new AsyntaskclassrunnerSpinnerClassAndDPTResultMaker().execute("");

        String query = "select * from \"" + mTABLENAME + "\" where \"COURSETITLE/LECTURER\" !='NULL'";
        new AsyntaskclassrunnerMakeAllForAllDPT().execute(query);


        selectdptspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int spinerpositions, long id) {

                String s = arrayList.get(spinerpositions);
                if (s.equals("ALL")) {

                    String query = "select * from \"" + mTABLENAME + "\" where \"COURSETITLE/LECTURER\" !='NULL'";
                    new AsyntaskclassrunnerMakeAllForAllDPT().execute(query);


                } else {
                    String[] split = s.split(",");
                    String dpt = split[0];

                    String trimdpt = dpt.trim();

                    String myclass = split[1];
                    String trimmyclass = myclass.trim();


                    String query = "select * from \"" + mTABLENAME + "\" where \"DEPT\" like '%s' AND \"CLASS\" like '%s'";

                    String query1 = String.format(query, trimdpt, trimmyclass);
                    Log.e("querry result", query1);
                    new AsyntaskclassrunnerMakeAllForAllDPT().execute(query1);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem men = menu.findItem(R.id.sach);

        //sad=(SearchView) MenuItemCompat.getActionView(men);
        searchView.setMenuItem(men);


        return super.onCreateOptionsMenu(menu);


    }

    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == R.id.reflesh) {

            String query = "select * from \"" + mTABLENAME + "\"";
            new AsyntaskclassrunnerMakeAllForAllDPT().execute(query);

        }


        if (item.getItemId() == R.id.sach) {

            // SearchViewSearchingOperation.valuetopassinnewactivitytoavoidredundancy = "";

        }

        return super.onOptionsItemSelected(item);


    }


    public class AsyntaskclassrunnerMakeAllForAllDPT extends AsyncTask<String, String, List> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List fpoemsmodel) {
            super.onPostExecute(fpoemsmodel);
            // new RecyclerviewMaker(recycle,MainActivity.this,gpcgpmodel);
            new MyListAdapterAdder(fpoemsmodel, ListOfDPTINDBActivity.this, listofexamtime);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected List doInBackground(String... params) {
            DataBaseHelper.setDbName(mDBNAMEANDEXTENSION);


            // MyListOfExamTimeTableModel
            DataBaseHelper mydbcreator = new DataBaseHelper(ListOfDPTINDBActivity.this);

//
            String querry = params[0];

                ArrayList<MyListOfExamTimeTableModel> usersData = mydbcreator.getUsersDataFromDataBase(querry);

                return usersData;



        }


    }

    public class AsyntaskclassrunnerSpinnerClassAndDPTResultMaker extends AsyncTask<String, String, HashSet> {


        @Override
        protected void onPostExecute(HashSet list) {

            super.onPostExecute(list);
            arrayList = new ArrayList<>();
            arrayList.add(0, "ALL");
            arrayList.addAll(list);
            ArrayAdapter<String> dataAdapterSGT = new ArrayAdapter<String>(ListOfDPTINDBActivity.this, R.layout.spinnerlayout, arrayList);
            dataAdapterSGT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            selectdptspinner.setAdapter(dataAdapterSGT);

        }

        @Override
        protected HashSet<String> doInBackground(String... params) {


            DataBaseHelperSpinnerMaker makeclassanddpt = new DataBaseHelperSpinnerMaker(ListOfDPTINDBActivity.this);

            userListspinnerclassnaddpt = makeclassanddpt.getUsersDataClassandDpt();
            return userListspinnerclassnaddpt;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


    }


    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {


        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            mscalFactor *= scaleGestureDetector.getScaleFactor();
            mscalFactor = Math.max(0.1f, Math.min(mscalFactor, 10.0f));
            listofexamtime.setScaleX(mscalFactor);
            listofexamtime.setScaleY(mscalFactor);
            return true;
        }
    }


    public class AsyntaskclassrunnerMakeForCreatingDB extends AsyncTask<String, String, List> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List gpcgpmodel) {
            super.onPostExecute(gpcgpmodel);
            // new RecyclerviewMaker(recycle,MainActivity.this,gpcgpmodel);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected List doInBackground(String... params) {


            DataBaseHelper mydbcreator = new DataBaseHelper(ListOfDPTINDBActivity.this);
            mydbcreator.createDataBase();


            return null;
        }


    }

}





