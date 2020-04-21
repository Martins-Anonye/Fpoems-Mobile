package com.example.martins.fpoems.listitemofdptexam;

import android.content.Context;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Martins on 12/31/2019.
 */

public class MyListAdapterAdder {
    public MyListAdapterAdder(List fpoemsmodel,Context con, ListView vls) {

      vls.setAdapter(new MyCustomAdapter(fpoemsmodel,con));
    }


}
