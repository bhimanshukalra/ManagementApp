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

public class homeworkSubjectActivity extends AppCompatActivity {

    String selection;
    TextView listViewTextView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        listViewTextView = (TextView) findViewById(R.id.listViewTextView);
        listViewTextView.setText("Choose one to setup the app:");

        ArrayList<listClass> listClassArrayList = new ArrayList<>();

        listClassArrayList.add(new listClass("Science",R.drawable.right_arrow));
        listClassArrayList.add(new listClass("Mathematics",R.drawable.right_arrow));
        listClassArrayList.add(new listClass("Social Science",R.drawable.right_arrow));
        listClassArrayList.add(new listClass("English",R.drawable.right_arrow));
        listClassArrayList.add(new listClass("Life skills",R.drawable.right_arrow));
        listClassArrayList.add(new listClass("Art",R.drawable.right_arrow));

        final listViewAdapter listViewAdapter = new listViewAdapter(getApplicationContext(),R.layout.layout_list_view_contents,listClassArrayList);

        final ListView list = (ListView) findViewById(R.id.listViewXml);
        list.setAdapter(listViewAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0)
                    selection = "Science";
                else if(i==1)
                {
                    selection = "Mathematics";
                }
                else if(i==2)
                {
                    selection = "Social Science";
                }
                else if(i==3)
                {
                    selection = "English";
                }
                else if(i == 4)
                    selection = "Life skills";
                else
                    selection = "ArtCraft";



                sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("homeworkSubject", selection);
                editor.apply();

                    startActivity(new Intent(homeworkSubjectActivity.this,StudentSecondActivity.class));
            }
        });
    }
}
