package com.example.bhimanshu_kalra.managementapp2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class temp extends AppCompatActivity {

    EditText editText1,editText2;
    Button button;
    String string1,string2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText4);

        button = findViewById(R.id.button5);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                string1 = editText1.getText().toString();
                string2 = editText2.getText().toString();

                Toast.makeText(temp.this, string1+"  "+string2, Toast.LENGTH_SHORT).show();


            }
        });

    }

}
