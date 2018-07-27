package com.example.bhimanshu_kalra.managementapp2;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.*;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AdminActivity extends AppCompatActivity {


    Spinner spinnerOne,spinnerTwo,spinnerThree,spinnerFour;
    ArrayAdapter<CharSequence> adapterOne,adapterTwo,adapterThree,adapterFour;
    String selectionOne,selectionTwo,selectionThree,selectionFour;
    EditText editText;
    StorageReference mStorageRef;
    String fileName,url;
    Uri uri;
    private static final int PICK_PDF_CODE = 2342;
    private static final int GALLERY_INTENT = 2;
    int sfour;
    ProgressBar progressBar;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        sfour=0;
        progressBar = findViewById(R.id.progressBar2);

        spinnerOne = (Spinner) findViewById(R.id.spinner2);
        adapterOne = ArrayAdapter.createFromResource(this, R.array.subjectsStrings, android.R.layout.simple_spinner_item);
        adapterOne.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOne.setAdapter(adapterOne);
        spinnerOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectionOne = String.valueOf(spinnerOne.getSelectedItem());
                //Toast.makeText(getBaseContext(),adapterView.getItemAtPosition(i)+" selected",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinnerTwo = (Spinner) findViewById(R.id.spinner1);
        adapterTwo = ArrayAdapter.createFromResource(this, R.array.uploadDataTypeStrings, android.R.layout.simple_spinner_item);
        adapterTwo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTwo.setAdapter(adapterTwo);
        spinnerTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectionTwo = String.valueOf(spinnerTwo.getSelectedItem());
                if (i>5) {
                    spinnerFour.setVisibility(View.VISIBLE);
                    spinnerOne.setVisibility(View.INVISIBLE);
                    sfour=1;
                }
                else {
                    spinnerFour.setVisibility(View.INVISIBLE);
                    spinnerOne.setVisibility(View.VISIBLE);
                    sfour=0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerThree = (Spinner) findViewById(R.id.spinner3);
        adapterThree = ArrayAdapter.createFromResource(this, R.array.fileTypeStrings, android.R.layout.simple_spinner_item);
        adapterThree.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerThree.setAdapter(adapterThree);
        spinnerThree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectionThree = String.valueOf(spinnerThree.getSelectedItem());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerFour = (Spinner) findViewById(R.id.spinner4);
        adapterFour = ArrayAdapter.createFromResource(this, R.array.ageGroupStrings, android.R.layout.simple_spinner_item);
        adapterFour.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFour.setAdapter(adapterFour);
        spinnerFour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectionFour = String.valueOf(spinnerFour.getSelectedItem());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        findViewById(R.id.uploadButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                editText = findViewById(R.id.editText);
                mStorageRef = FirebaseStorage.getInstance().getReference();

                fileName = editText.getText().toString();

                if( "Image".equals(selectionThree)) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, GALLERY_INTENT);
                }
                else if( "Document".equals(selectionThree) ) {
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
                    Toast.makeText(AdminActivity.this, "Uploaded.", Toast.LENGTH_SHORT).show();

                    if(sfour == 0)
                        databasefun(""+fileName,""+url);
                    else
                        databaseStudentFun(""+fileName,""+url);
                    //databasefun("fileName","url");

                    //Log.d("data123",fileName+" "+url);
                    //databaseReference.setValue(""+url);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AdminActivity.this, "Failure", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(AdminActivity.this, "Uploaded.", Toast.LENGTH_SHORT).show();

                    if(sfour == 0)
                        databasefun(""+fileName,""+url);
                    else
                        databaseStudentFun(""+fileName,""+url);
                    //databasefun("fileName","url");

                    //Log.d("data123",fileName+" "+url);
                    //databaseReference.setValue(""+url);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AdminActivity.this, "Failure", Toast.LENGTH_SHORT).show();
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
        DatabaseReference myRef = database.getReference("subjects").child(selectionOne).child(selectionTwo);

        String id = myRef.push().getKey();

        DatabaseClass databaseClass = new DatabaseClass(""+name,""+url);

        myRef.child(id).setValue(databaseClass);


        Toast.makeText(AdminActivity.this, "Database", Toast.LENGTH_SHORT).show();
    }

    public void databaseStudentFun(String name,String url)
    {
        Log.d("data123","S: "+name+" "+url);
        /*
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("url");
        databaseReference.getParent().child("aa"+name).setValue("aa"+url);
*/
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("students").child(selectionFour).child(selectionTwo);

        String id = myRef.push().getKey();

        DatabaseClass databaseClass = new DatabaseClass(""+name,""+url);

        myRef.child(id).setValue(databaseClass);


        Toast.makeText(AdminActivity.this, "Database", Toast.LENGTH_SHORT).show();
    }









































    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.addUserbutton:
                //Log.d("adasdasdad","SIGN OUT BUTTON");
                startActivity(new Intent(AdminActivity.this,SignUpActivity.class));
                return true;
            case R.id.logoutButton:
                finish();
                /*
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                 */
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
