package com.example.bhimanshu_kalra.managementapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.os.*;

public class TeacherActivity extends AppCompatActivity {

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
    TextView subjectTextView;
    String fileName;
    EditText fileNameEditText;
    Spinner spinnerOne,spinnerTwo;
    ArrayAdapter<CharSequence> adapterOne,adapterTwo;
    String selectionOne,selectionTwo;
    StorageReference mStorageRef;
    private static final int PICK_PDF_CODE = 2342;
    private static final int GALLERY_INTENT = 2;
    String selection,username;
    Uri uri;
    ProgressBar progressBar;
    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        progressBar = findViewById(R.id.progressBar4);

        subjectTextView = findViewById(R.id.subjectTextView);

        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        selection =  sharedPreferences.getString("sub","");
        username = sharedPreferences.getString("username","");

        subjectTextView.setText(""+selection+"'s hw");

        fileNameEditText = findViewById(R.id.fileNameEditText);

        spinnerOne = (Spinner) findViewById(R.id.typeSpinner);
        adapterOne = ArrayAdapter.createFromResource(this, R.array.fileTypeStrings, android.R.layout.simple_spinner_item);
        adapterOne.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOne.setAdapter(adapterOne);
        spinnerOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectionOne = String.valueOf(spinnerOne.getSelectedItem());
                //Toast.makeText(getBaseContext(),adapterView.getItemAtPosition(i)+" selected",Toast.LENGTH_LONG).show();
                //Toast.makeText(TeacherActivity.this, selectionOne + " selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerTwo = (Spinner) findViewById(R.id.spinner6);
        adapterTwo = ArrayAdapter.createFromResource(this, R.array.ageGroupStrings, android.R.layout.simple_spinner_item);
        adapterTwo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTwo.setAdapter(adapterTwo);
        spinnerTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectionTwo = String.valueOf(spinnerTwo.getSelectedItem());
                //Toast.makeText(getBaseContext(),adapterView.getItemAtPosition(i)+" selected",Toast.LENGTH_LONG).show();
                //Toast.makeText(TeacherActivity.this, selectionTwo + " selected", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        findViewById(R.id.uploadButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fileName = fileNameEditText.getText().toString() ;

                mStorageRef = FirebaseStorage.getInstance().getReference();

                if( "Image".equals(selectionOne)) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, GALLERY_INTENT);
                }
                else if( "Document".equals(selectionOne) ) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("application/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, PICK_PDF_CODE);
                }


            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_PDF_CODE && resultCode == RESULT_OK)
        {
            progressBar.setVisibility(View.VISIBLE);
            uri = data.getData();
//            StorageReference filepath = mStorageRef.child("files").child(fileName);


            StorageReference filepath = mStorageRef.child(selectionOne).child(fileName);

            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    url = downloadUrl.toString();
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(TeacherActivity.this, "Uploaded.", Toast.LENGTH_SHORT).show();


                        databasefun(""+fileName,""+url);
                    //databasefun("fileName","url");

                    //Log.d("data123",fileName+" "+url);
                    //databaseReference.setValue(""+url);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(TeacherActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                }
            });
        }

        else if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK)
        {
            progressBar.setVisibility(View.VISIBLE);
            uri = data.getData();
            StorageReference filepath = mStorageRef.child(selectionOne).child(fileName);
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    url = downloadUrl.toString();
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(TeacherActivity.this, "Uploaded.", Toast.LENGTH_SHORT).show();


                        databasefun(""+fileName,""+url);
                    //databasefun("fileName","url");

                    //Log.d("data123",fileName+" "+url);
                    //databaseReference.setValue(""+url);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(TeacherActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void databasefun(String name,String url)
    {
        Log.d("data123","S: "+name+" "+url);
        /*
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("url");
        databaseReference.getParent().child("aa"+name).setValue("aa"+url);
*/
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("students").child(selectionTwo).child("homework").child(selection).child("Teacher's copy");

        String id = myRef.push().getKey();

        DatabaseClass databaseClass = new DatabaseClass(""+name,""+url);

        myRef.child(id).setValue(databaseClass);


        Toast.makeText(TeacherActivity.this, "Database", Toast.LENGTH_SHORT).show();
    }












































    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.teacher_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch(item.getItemId())
        {

            case R.id.teachingMaterialButton:


                editor.putString("subType", "Teaching material");
                editor.apply();

                startActivity(new Intent(TeacherActivity.this,TeacherSecondActivity.class));
                return true;
            case R.id.trainingMaterialButton:

                editor.putString("subType", "Training material");
                editor.apply();

                startActivity(new Intent(TeacherActivity.this,TeacherSecondActivity.class));
                return true;
            case R.id.guidanceButton:

                editor.putString("subType", "Guidance");
                editor.apply();

                startActivity(new Intent(TeacherActivity.this,TeacherSecondActivity.class));
                return true;
            case R.id.pimButton:

                editor.putString("subType", "Parents interaction meeting");
                editor.apply();

                startActivity(new Intent(TeacherActivity.this,TeacherSecondActivity.class));
                return true;
            case R.id.ExtensionActivityButton:

                editor.putString("subType", "Extension Activity");
                editor.apply();

                startActivity(new Intent(TeacherActivity.this,TeacherSecondActivity.class));
                return true;
            case R.id.logoutButton:
            finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to log out", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
