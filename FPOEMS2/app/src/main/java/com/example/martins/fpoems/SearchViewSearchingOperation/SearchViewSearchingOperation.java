package com.example.martins.fpoems.SearchViewSearchingOperation;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;


import com.example.martins.fpoems.DataModelForListOfDPTINDB.DataBaseHelper;
import com.example.martins.fpoems.DataModelForListOfDPTINDB.MyListOfExamTimeTableModel;
import com.example.martins.fpoems.ListOfDPTINDBActivity;
import com.example.martins.fpoems.listitemofdptexam.MyListAdapterAdder;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juliet on 9/12/2019.
 */

public class SearchViewSearchingOperation {
    MaterialSearchView searchView;
    ListView listView;
    Context cont;

    int a = 0;
    int b = 0;
    int c = 0;

    public SearchViewSearchingOperation(final Context cont, final MaterialSearchView searchView, final ListView listView) {
        this.searchView = searchView;
        this.listView = listView;
        this.cont = cont;

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                // searchfilterGennerator(s);
                return true;


            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchfilterGennerator(s);
                return true;
            }

        });// end of searchevent


    }// end of method


    public void searchfilterGennerator(String s) {


        String toString = s.trim();
        //                  String character = newValue.toString();


        ////

        //if(toString.contains(" ")){
        if (toString.contains(",")) {


            if (b == 0) {
                Toast.makeText(cont, "Waiting for  class Type ", Toast.LENGTH_SHORT).show();
                b++;
            }


            if (toString.length() > 4) {

                String s1 = toString.toUpperCase();
                int i = s1.indexOf(',')+1;
                String[] split = s1.split(",");
                String substring = s1.substring(i, s1.length());
                String dpt = split[0];

                String trimdpt = dpt.trim();
              //  String myclass = "";
                String trimmyclass =  substring.trim();
                // we sense for space e.g >>>HND 2


                if ((!trimmyclass.contains(" ")) && (trimmyclass.contains("1") || (trimmyclass.contains("2")))) {
                    // we sense for space e.g >>>HND 2
                    // we sense for space e.g >>>HND 1
                    //we sense for space e.g >>>ND 1
                    //we sense for space e.g >>>ND 2
                    String trimmyclass1 = trimmyclass.replaceFirst("D", "D ");
                    Log.e("querry result search", toString + " " + trimmyclass);

                    searchfortheResult(trimmyclass1, trimdpt, trimmyclass1);
                }


                searchfortheResult(trimmyclass, trimdpt, trimmyclass);


                //////
                //////
                ///////


                // a++;
            } else {
                if (a == 0) {
                    Toast.makeText(cont, "Pls complite your class type e.g HND1", Toast.LENGTH_SHORT).show();
                    a++;
                }
                // a = 0;
            }


        }//end  of if

        else {
            //initiallize our project topic field, even when there is no value
            if (a == 0) {
                Toast.makeText(cont, "Waiting for  your department and class Type ", Toast.LENGTH_SHORT).show();
                a++;
            }


        }
        Log.e("querry result search", toString);


    }//end of searchfiltergennerator


/// category search operation


    public class AsyntaskclassrunnerMakeAllForAllDPT extends AsyncTask<String, String, List> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List fpoemsmodel) {
            super.onPostExecute(fpoemsmodel);
            // new RecyclerviewMaker(recycle,MainActivity.this,gpcgpmodel);
            new MyListAdapterAdder(fpoemsmodel, cont, listView);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected List doInBackground(String... params) {


            // MyListOfExamTimeTableModel
            DataBaseHelper mydbcreator = new DataBaseHelper(cont);

//
            String querry = params[0];
            if (mydbcreator.checkDataBase()) {

                ArrayList<MyListOfExamTimeTableModel> usersData = mydbcreator.getUsersDataFromDataBase(querry);

                return usersData;

            } else {

                return null;
            }


        }


    }


    private void searchfortheResult(String myclass, String trimdpt, String trimmyclass) {

        if ((myclass.length() > 2) && (myclass.contains(" ") && !myclass.endsWith(" ") && !myclass.endsWith(",")) && ((myclass.contains("1")) || (myclass.contains("2")))) {


            Toast.makeText(cont, "Searching for Your Input.....", Toast.LENGTH_SHORT).show();


            String query = "select * from \""+ListOfDPTINDBActivity.mTABLENAME+"\"   where\"COURSETITLE/LECTURER\" !='NULL'\n" +
                    "AND (\"DEPT\" like '%s' AND \"CLASS\" like '%s')";

            //LIKE 'name%' >> % is wild character for  0 or many character

            String trimmyclass1 =trimmyclass+"%";
            String trimdpt1 =trimdpt+"%";


            String query1 = String.format(query, trimdpt1, trimmyclass1);
            Log.e("querry result search", query1);
            new AsyntaskclassrunnerMakeAllForAllDPT().execute(query1);


        }
    }

}//end of upper class








