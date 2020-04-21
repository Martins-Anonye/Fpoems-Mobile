package com.example.martins.fpoems.listitemofdptexam;


import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.martins.fpoems.DataModelForListOfDPTINDB.DataBaseHelper;
import com.example.martins.fpoems.DataModelForListOfDPTINDB.MyListOfExamTimeTableModel;
import com.example.martins.fpoems.ListOfDPTINDBActivity;
import com.example.martins.fpoems.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.martins.fpoems.ListOfDPTINDBActivity.mTABLENAME;

/**
 * Created by Martins on 12/31/2019.
 */

public class MyCustomAdapter extends BaseAdapter {
    LayoutInflater linflate;
    Context cont;
    List<MyListOfExamTimeTableModel> fpoemspmodellist;


    public MyCustomAdapter(List<MyListOfExamTimeTableModel> fpoemspmodellist, Context con) {

        this.fpoemspmodellist = fpoemspmodellist;
        cont = con;

        linflate = LayoutInflater.from(cont);


    }


    static class InitializeViewHolder {
        TextView dptexamrowid, dptexamcoscod, dptexamcostitle, dptexamlect, dptexamdate;
        TextView dptexamstarttime, dpteaxmendtime, dptexamvenue, dptexamclasssize, dptexammode, dptandclass;

        //the following are not active yet
        FloatingActionButton closeinvigilatorlayoutbtn,sitemat;
        LinearLayout invigilatorlayout;
        ListView invigilatorlayoutlist;
    }

    @Override
    public int getCount() {
        return fpoemspmodellist.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View v, ViewGroup parent) {
        final InitializeViewHolder vholder;
        if (v == null) {

            v = linflate.inflate(R.layout.listitemofexamtime, null);
            vholder = new InitializeViewHolder();
            //vholder initialization component
            vholder.dptexamrowid = (TextView) v.findViewById(R.id.dptexamrowidno);
            vholder.dptexamcoscod = (TextView) v.findViewById(R.id.dptexamcoscod);
            vholder.dptexamcostitle = (TextView) v.findViewById(R.id.dptexamcostitle);
            vholder.dptexamlect = (TextView) v.findViewById(R.id.dptexamlect);
            vholder.dptexamdate = (TextView) v.findViewById(R.id.dptexamdate);
            vholder.dptexamstarttime = (TextView) v.findViewById(R.id.dptexamstarttime);
            vholder.dpteaxmendtime = (TextView) v.findViewById(R.id.dpteaxmendtime);
            vholder.dptexamvenue = (TextView) v.findViewById(R.id.dptexamvenue);
            vholder.dptexamclasssize = (TextView) v.findViewById(R.id.dptexamclasssize);
            vholder.dptexammode = (TextView) v.findViewById(R.id.dptexammode);

            vholder.dptandclass = (TextView) v.findViewById(R.id.dptandclass);

            vholder.sitemat = (FloatingActionButton) v.findViewById(R.id.sitemat);



            v.setTag(vholder);

        } else {
            vholder = (InitializeViewHolder) v.getTag();


        }


        //  setValue to gui component


        int pos = position + 1;
        MyListOfExamTimeTableModel myListOfExamTimeTableModel = fpoemspmodellist.get(position);

        //vholder.dptexamrowid.setText(myListOfExamTimeTableModel.getDptexamrowid());

        vholder.dptexamrowid.setText("" + pos);
        vholder.dptexamcoscod.setText(myListOfExamTimeTableModel.getDptexamcoscod());
        vholder.dptexamcostitle.setText(myListOfExamTimeTableModel.getDptexamcostitle());
        vholder.dptexamlect.setText(myListOfExamTimeTableModel.getDptexamcosLecturer());
        vholder.dptexamdate.setText(myListOfExamTimeTableModel.getDptexamdate());
        vholder.dptexamstarttime.setText(myListOfExamTimeTableModel.getDptexamstarttime());
        vholder.dpteaxmendtime.setText(myListOfExamTimeTableModel.getExamendtime());
        vholder.dptexamvenue.setText(myListOfExamTimeTableModel.getDptexamvenue());
        vholder.dptexamclasssize.setText(myListOfExamTimeTableModel.getDptexamclasssize());
        vholder.dptexammode.setText(myListOfExamTimeTableModel.getExammode());
        vholder.dptandclass.setText(myListOfExamTimeTableModel.getDptexamdpt() + "," + myListOfExamTimeTableModel.getDptexamclass());
        //closeinvigilatorlist(vholder);

        if (myListOfExamTimeTableModel.getDptexamcosLecturer().equalsIgnoreCase("empty")) {

            vholder.dptexamrowid.setTextColor(Color.parseColor("red"));
            vholder.dptexamcoscod.setTextColor(Color.parseColor("red"));
            vholder.dptexamcostitle.setTextColor(Color.parseColor("red"));
            vholder.dptexamlect.setTextColor(Color.parseColor("red"));
            vholder.dptexamdate.setTextColor(Color.parseColor("red"));
            vholder.dptexamstarttime.setTextColor(Color.parseColor("red"));
            vholder.dpteaxmendtime.setTextColor(Color.parseColor("red"));
            vholder.dptexamvenue.setTextColor(Color.parseColor("red"));
            vholder.dptexamclasssize.setTextColor(Color.parseColor("red"));
            vholder.dptexammode.setTextColor(Color.parseColor("red"));
            vholder.dptandclass.setTextColor(Color.parseColor("red"));
            //holder.snnumm.setTextColor(Color.parseColor("red"));

            Toast.makeText(cont, "No Exam Found!", Toast.LENGTH_SHORT).show();

        }

        vholder.dptexamvenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = "select * from \""+mTABLENAME+"\" where \"VENUE\"  = '"+vholder.dptexamvenue.getText().toString()+"'  and  \"DATE\" = '"+vholder.dptexamdate.getText().toString()+"'";

                new AsyntaskclassrunnerVenueSelector().execute(query);


            }
        });

        vholder.sitemat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = "select * from \""+mTABLENAME+"\" where \"VENUE\"  = '"+vholder.dptexamvenue.getText().toString()+"'  and  \"DATE\" = '"+vholder.dptexamdate.getText().toString()+"'";

                new AsyntaskclassrunnerVenueSelector().execute(query);


            }
        });
        return v;
    }








    public  class AsyntaskclassrunnerVenueSelector extends AsyncTask<String, String, List> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List fpoemsmodel) {
            super.onPostExecute(fpoemsmodel);


            new MyListAdapterAdder(fpoemsmodel, cont, ListOfDPTINDBActivity.listofexamtime);


        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected List doInBackground(String... params) {
            DataBaseHelper.setDbName(ListOfDPTINDBActivity.mDBNAMEANDEXTENSION);

            DataBaseHelper mydbcreator = new DataBaseHelper(cont);


            String querry = params[0];
            if( mydbcreator.checkDataBase()){

                ArrayList<MyListOfExamTimeTableModel> usersData = mydbcreator.getUsersDataFromDataBase(querry);

                return usersData;

            }
            else{

                return null;
            }



        }


    }

    //
}
