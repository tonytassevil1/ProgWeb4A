package org.esiea.tassevil_mierzynski.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.os.Handler;
import android.view.Window;
import android.widget.ListView;

public class ListRecycler extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.myListSimple);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        ArrayList <Model> listElements = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            // new item
            listElements.add(new Model("test " + i, "ttt"));
        }

        adapter = new RecyclerSimpleViewAdapter(listElements);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //IntentFilter intentFilter = new IntentFilter(Biers_update);
        //LocalBroadcastManager.getInstance(this).registerReceiver(new BierUpdate(),intentFilter);
    }

    //public class abstract BierUpdate extends BroadcastReceiver{
      //  @Override
        //public void onRecieve(Context context, Intent intent){
            //Notification
            //MAJ interface
        //}
   // }
}
