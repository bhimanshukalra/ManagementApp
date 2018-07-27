package com.example.bhimanshu_kalra.managementapp2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    EditText userEditText,passEditText;
    String user,pass;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ProgressBar progressBar;
    String[] passArray, userArray, subjectArray;
    String username, password, subject,userType="";
    int i,j;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("onetwo override","onCreate");

        userEditText = findViewById(R.id.mainUser);
        passEditText = findViewById(R.id.mainPass);
        progressBar = findViewById(R.id.mainProgressBar);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");

        findViewById(R.id.mainLoginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user = userEditText.getText().toString();
                pass = passEditText.getText().toString();

                if(user.isEmpty())
                {
                    userEditText.setError("Username is required");
                    userEditText.requestFocus();
                    return;
                }


                if(pass.isEmpty())
                {
                    passEditText.setError("Password is required");
                    passEditText.requestFocus();
                    return;
                }

                userType="";
                        for(i=0;i<j;i++)
                        {
                            Log.d("onetwo all",user+"   "+userArray[i]);
                            if( user.equals(userArray[i]) && pass.equals(passArray[i]) )
                            {
                                Log.d("onetwo condition",user+"   "+userArray[i]);
                                userType=subjectArray[i];
                                Log.d("onetwo important","userType "+userType);
                                break;
                            }
                        }
                        if( "".equals(userType))
                            Toast.makeText(MainActivity.this, "Verify username and/or password and try again.", Toast.LENGTH_SHORT).show();

                        Log.d("log important condition","userType "+userType);

                        if( "admin".equals(userType) )
                            startActivity(new Intent(MainActivity.this, AdminActivity.class));
                            //startActivity(new Intent(MainActivity.this, AdminActivity.class));
                        else if( !("".equals(userType)) )
                        {
                            SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("sub", userType);
                            editor.putString("username", user);
                            editor.apply();

                            startActivity(new Intent(MainActivity.this, TeacherActivity.class));
                        }
                        /*
                        if( "3".equals(userType) )
                            startActivity(new Intent(MainActivity.this, AgeBandActivity.class));
                            */

            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.d("onetwo override","onStart");

        userEditText.setText("");passEditText.setText("");userEditText.requestFocus();

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userArray= new String[100];
                subjectArray= new String[100];
                passArray = new String[100];

                for(i=0;i<100;i++)
                {
                    userArray[i]="";
                }
                i=0;
                Log.d("onetwo important", " onDataChange ");

                for(DataSnapshot ds: dataSnapshot.getChildren())
                {

                    DatabaseTeacherClass databaseTeacherClass = ds.getValue(DatabaseTeacherClass.class);

                    username = databaseTeacherClass.getUser();
                    password = databaseTeacherClass.getPass();
                    subject = databaseTeacherClass.getSubject();

                    if( username != null && !("".equals(username)) && password != null && !("".equals(password)) && subject != null && !("".equals(subject)) )
                    {
                        userArray[i]=username;
                        passArray[i]=password;
                        subjectArray[i++]=subject;
                        //Log.d("inIf",i+"    "+name);
                    }
                    //Log.d("data1234",i+" Name: "+name+" url= "+url);
                }

                for(j=0;j<i;j++)
                {
                    Log.d("onetwo important",i+"   "+j);
                    Log.d("onetwo userArray", ""+userArray[j]);
                    Log.d("onetwo passArray", ""+passArray[j]);
                    Log.d("onetwo typeArray", ""+subjectArray[j]);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.changeUserType:finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
