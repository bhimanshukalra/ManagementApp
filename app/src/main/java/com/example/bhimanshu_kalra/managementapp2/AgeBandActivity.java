package com.example.bhimanshu_kalra.managementapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AgeBandActivity extends AppCompatActivity {

    TextView listViewTextView;
    String selection,temp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        listViewTextView = (TextView) findViewById(R.id.listViewTextView);

        listViewTextView.setText("Select age group:");

        ArrayList<listClass> listClassArrayList = new ArrayList<>();

        listClassArrayList.add(new listClass("3-6",R.drawable.right_arrow));
        listClassArrayList.add(new listClass("7-11",R.drawable.right_arrow));
        listClassArrayList.add(new listClass("12-14",R.drawable.right_arrow));
        listClassArrayList.add(new listClass("15-18",R.drawable.right_arrow));

        final listViewAdapter listViewAdapter = new listViewAdapter(getApplicationContext(),R.layout.layout_list_view_contents,listClassArrayList);

        final ListView list = (ListView) findViewById(R.id.listViewXml);
        list.setAdapter(listViewAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listClass listClass = (listClass) listViewAdapter.getItem(i);
                SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if(i==0)
                    temp = "3 - 6" ;
                else if(i==1)
                    temp = "7 - 11" ;
                else if(i==2)
                    temp = "12 - 14" ;
                else if(i==3)
                    temp = "15 - 18" ;




                editor.putString("studentAge", ""+temp);
                editor.apply();
                startActivity(new Intent(AgeBandActivity.this, StudentActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
