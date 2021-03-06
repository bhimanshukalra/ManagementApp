package com.example.bhimanshu_kalra.managementapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentSecondActivity extends AppCompatActivity {

    String ageGroup,studentChoice;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String[] urlArray,nameArray;
    String name,url,hwSubject;
    int i,j,count;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        progressBar = findViewById(R.id.progressBar3);
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        ageGroup =  sharedPreferences.getString("studentAge","");
        studentChoice = sharedPreferences.getString("studentChoice","");

        hwSubject = sharedPreferences.getString("homeworkSubject", "");

        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference("students").child(ageGroup).child(studentChoice);
        if( "homework".equals(studentChoice) )
        databaseReference = firebaseDatabase.getReference("students").child(ageGroup).child(studentChoice).child(hwSubject).child("Teacher's copy");
        else
            databaseReference = firebaseDatabase.getReference("students").child(ageGroup).child(studentChoice);

        }


    @Override
    protected void onStart() {
        super.onStart();

        Log.d("check123",ageGroup+"  "+studentChoice);

        count=0;
        progressBar.setVisibility(View.VISIBLE);

        databaseReference.addValueEventListener(new ValueEventListener() {

            ArrayList<listClass> listClassArrayList = new ArrayList<>();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                urlArray= new String[100];
                nameArray= new String[100];

                listClassArrayList.clear();

                for(i=0;i<100;i++)
                {
                    nameArray[i]="";
                }
                i=0;
                Log.d("data123", " onDataChange ");


                progressBar.setVisibility(View.INVISIBLE);

                for(DataSnapshot ds: dataSnapshot.getChildren())
                {

                    DatabaseClass databaseClass = ds.getValue(DatabaseClass.class);

                    name = databaseClass.getName();
                    url = databaseClass.getUrl();

                    if( name != null && !("".equals(name)) )
                    {
                        listClassArrayList.add(new listClass(" "+name, R.drawable.right_arrow));
                        urlArray[i++]=url;
                        count++;
                    }
                }

                for(j=0;j<=i+1;j++)
                {
                    Log.d("urlArray", j+"   "+urlArray[j]);
                }

                if(count==0)
                    Toast.makeText(StudentSecondActivity.this, "No record found", Toast.LENGTH_SHORT).show();



                final listViewAdapter listViewAdapter = new listViewAdapter(getApplicationContext(),R.layout.layout_list_view_contents,listClassArrayList);

                final ListView list = (ListView) findViewById(R.id.listViewXml);
                list.setAdapter(listViewAdapter);

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        Toast.makeText(StudentSecondActivity.this, ""+i, Toast.LENGTH_SHORT).show();

                        url = urlArray[i];


                        Log.d("finalUrl",""+url);

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
