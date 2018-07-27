package com.example.bhimanshu_kalra.managementapp2;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.nio.channels.GatheringByteChannel;

public class UploadResourcesByAdminActivity extends AppCompatActivity {

    StorageReference mStorageRef;
    private static final int PICK_PDF_CODE = 2342;
    private static final int GALLERY_INTENT = 2;
    String fileName;
    EditText editText;
    FirebaseStorage firebaseStorage;
    String url,url2;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Uri uri;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_resources_by_admin);

        editText = findViewById(R.id.uploadEditText);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        findViewById(R.id.uploadButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fileName = editText.getText().toString();

                Intent intent = new Intent(Intent.ACTION_PICK);
                //intent.setType("application/pdf");
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,PICK_PDF_CODE);

            }
        });


        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editText = findViewById(R.id.uploadEditText);
                mStorageRef = FirebaseStorage.getInstance().getReference();

                        fileName = editText.getText().toString();

                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, GALLERY_INTENT);



            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_PDF_CODE && resultCode == RESULT_OK)
        {
            uri = data.getData();
//            StorageReference filepath = mStorageRef.child("files").child(fileName);


            StorageReference filepath = mStorageRef.child("files").child(fileName);

            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    url = downloadUrl.toString();
                    Toast.makeText(UploadResourcesByAdminActivity.this, "Uploaded.", Toast.LENGTH_SHORT).show();

                    databasefun(""+fileName,""+url);
                    //databasefun("fileName","url");

                    //Log.d("data123",fileName+" "+url);
                    //databaseReference.setValue(""+url);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UploadResourcesByAdminActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                }
            });
        }

        else if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK)
        {
            uri = data.getData();
            StorageReference filepath = mStorageRef.child("pdf").child(fileName);
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    url = downloadUrl.toString();
                    Toast.makeText(UploadResourcesByAdminActivity.this, "Uploaded.", Toast.LENGTH_SHORT).show();

                    databasefun(""+fileName,""+url);
                    //databasefun("fileName","url");

                    //Log.d("data123",fileName+" "+url);
                    //databaseReference.setValue(""+url);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UploadResourcesByAdminActivity.this, "Failure", Toast.LENGTH_SHORT).show();
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
        DatabaseReference myRef = database.getReference("url");

        String id = myRef.push().getKey();

        DatabaseClass databaseClass = new DatabaseClass(name,url);

        myRef.child(id).setValue(databaseClass);

        Toast.makeText(UploadResourcesByAdminActivity.this, "Database", Toast.LENGTH_SHORT).show();
    }


}
