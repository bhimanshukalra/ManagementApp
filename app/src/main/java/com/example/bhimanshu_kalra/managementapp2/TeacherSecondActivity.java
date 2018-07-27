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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class TeacherSecondActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    ChildEventListener childEventListener;
    String name,url;
    TextView listViewTextView;
    //String urlArray[];
    String[] urlArray,nameArray;
    static int i,j;
    ValueEventListener valueEventListener;
    DatabaseReference databaseReference;
    ListView list;
    listViewAdapter listViewAdapter;
    ArrayList<listClass> listClassArrayList;
    ListView databaseListView;
    List<DatabaseClass> databaseList;
    StorageReference storageReference;
    TextView homeTextView;
    String selection,subject;
    int count;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        progressBar = findViewById(R.id.progressBar3);

        homeTextView = findViewById(R.id.listViewTextView);
        databaseListView = (ListView) findViewById(R.id.listViewXml);
        databaseList = new ArrayList<>();

        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        selection =  sharedPreferences.getString("subType","");
        subject = sharedPreferences.getString("sub","");

        homeTextView.setText(""+selection);

    }


    @Override
    protected void onStart() {
        super.onStart();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("subjects").child(subject).child(selection);

        progressBar.setVisibility(View.VISIBLE);

        databaseReference.addValueEventListener(new ValueEventListener() {

            ArrayList<listClass> listClassArrayList = new ArrayList<>();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                urlArray= new String[100];
                nameArray= new String[100];

                listClassArrayList.clear();
                count=0;

                for(i=0;i<100;i++)
                {
                    nameArray[i]="";
                }
                i=0;
                Log.d("data123", " onDataChange ");

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


                Log.d("count1",""+count);

                progressBar.setVisibility(View.INVISIBLE);

                if(count==0)
                    Toast.makeText(TeacherSecondActivity.this, "No records found", Toast.LENGTH_SHORT).show();


                final listViewAdapter listViewAdapter = new listViewAdapter(getApplicationContext(),R.layout.layout_list_view_contents,listClassArrayList);

                final ListView list = (ListView) findViewById(R.id.listViewXml);
                list.setAdapter(listViewAdapter);

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        Toast.makeText(TeacherSecondActivity.this, ""+i, Toast.LENGTH_SHORT).show();

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
