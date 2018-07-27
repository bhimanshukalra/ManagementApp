package com.example.bhimanshu_kalra.managementapp2;

import android.os.PatternMatcher;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    EditText userEditText,passEditText;
    String user,pass;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    int type;
    Spinner spinnerOne;
    ArrayAdapter<CharSequence> adapterOne;
    String selectionOne;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userEditText = findViewById(R.id.signUpUser);
        passEditText = findViewById(R.id.signUpPass);
        progressBar = findViewById(R.id.signUpProgressBar);

        mAuth = FirebaseAuth.getInstance();


        findViewById(R.id.signUpSubmitButton).setOnClickListener(new View.OnClickListener() {
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
                    passEditText.setError("Username is required");
                    passEditText.requestFocus();
                    return;
                }



                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("users");

                String id = myRef.push().getKey();

                DatabaseTeacherClass databaseTeacherClass = new DatabaseTeacherClass(""+user,""+pass,""+selectionOne);

                myRef.child(id).setValue(databaseTeacherClass);

                Toast.makeText(SignUpActivity.this, "New user added", Toast.LENGTH_SHORT).show();




            }
        });

        spinnerOne=(Spinner)findViewById(R.id.spinner);
        adapterOne = ArrayAdapter.createFromResource(this,R.array.subjectsStrings,android.R.layout.simple_spinner_item);
        adapterOne.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOne.setAdapter(adapterOne);
        spinnerOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectionOne=String.valueOf(spinnerOne.getSelectedItem());
                //Toast.makeText(getBaseContext(),adapterView.getItemAtPosition(i)+" selected",Toast.LENGTH_LONG).show();
               // Toast.makeText(SignUpActivity.this,selectionOne+" selected",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
}
