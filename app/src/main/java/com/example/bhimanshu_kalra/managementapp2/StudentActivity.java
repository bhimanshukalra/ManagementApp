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

public class StudentActivity extends AppCompatActivity {
    String selection;
    TextView listViewTextView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        listViewTextView = (TextView) findViewById(R.id.listViewTextView);
        listViewTextView.setText("Choose :");




        ArrayList<listClass> listClassArrayList = new ArrayList<>();

        listClassArrayList.add(new listClass("Home Work",R.drawable.right_arrow));
        listClassArrayList.add(new listClass("Base Material",R.drawable.right_arrow));
        listClassArrayList.add(new listClass("Extra Reading",R.drawable.right_arrow));
        listClassArrayList.add(new listClass("Quizzes",R.drawable.right_arrow));

        final listViewAdapter listViewAdapter = new listViewAdapter(getApplicationContext(),R.layout.layout_list_view_contents,listClassArrayList);

        final ListView list = (ListView) findViewById(R.id.listViewXml);
        list.setAdapter(listViewAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0)
                {
                    selection = "homework";
                }
                else if(i==1)
                {
                    selection = "Base material";
                }
                else if(i==2)
                {
                    selection = "Extra reading";
                }
                else if(i==3)
                {
                    selection = "Quizzes";
                }


                sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("studentChoice", selection);
                editor.apply();
                if( "homework".equals(selection))
                    startActivity(new Intent(StudentActivity.this,homeworkSubjectActivity.class));
                else
                    startActivity(new Intent(StudentActivity.this,StudentSecondActivity.class));

            }
        });
    }
}
