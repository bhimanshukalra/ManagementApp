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

public class UserTypeActivity extends AppCompatActivity {

    TextView listViewTextView;
    String selection;

    SharedPreferences sharedPreferences;
    String usermode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

/*
        sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        usermode =  sharedPreferences.getString("usermode","");
        if( "admin_menu".equals(usermode) || "teacher".equals(usermode) )
            startActivity(new Intent(UserTypeActivity.this,MainActivity.class));
        else if ( "student".equals(usermode) )
            startActivity(new Intent(UserTypeActivity.this,AgeBandActivity.class));
*/
        listViewTextView = (TextView) findViewById(R.id.listViewTextView);
        listViewTextView.setText("Choose one to setup the app:");




        ArrayList<listClass> listClassArrayList = new ArrayList<>();

        listClassArrayList.add(new listClass("Admin",R.drawable.right_arrow));
        listClassArrayList.add(new listClass("Teacher",R.drawable.right_arrow));
        listClassArrayList.add(new listClass("Student",R.drawable.right_arrow));

        final listViewAdapter listViewAdapter = new listViewAdapter(getApplicationContext(),R.layout.layout_list_view_contents,listClassArrayList);

        final ListView list = (ListView) findViewById(R.id.listViewXml);
        list.setAdapter(listViewAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if(i==0)
                {
                    selection = "admin_menu";
                    editor.putString("usermode", selection);
                    editor.apply();
                    //startActivity(new Intent(UserTypeActivity.this, UploadResourcesByAdminActivity.class));
                    startActivity(new Intent(UserTypeActivity.this,MainActivity.class));
                }
                else if(i==1)
                {
                    selection = "teacher";
                    editor.putString("usermode", selection);
                    editor.apply();
                    startActivity(new Intent(UserTypeActivity.this, MainActivity.class));
                    //startActivity(new Intent(UserTypeActivity.this, MainActivity.class));
                }
                else if(i==2)
                {
                    selection = "student";
                    editor.putString("usermode", selection);
                    editor.apply();
                    startActivity(new Intent(UserTypeActivity.this, AgeBandActivity.class));
                //startActivity(new Intent(UserTypeActivity.this, AgeBandActivity.class));
                }

                //startActivity(new Intent(UserTypeActivity.this,MainActivity.class));

            }
        });



    }
}
