package com.example.laptop.trackmypocket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trackmypocket.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SignUp extends AppCompatActivity {

    EditText editUserName,editMobileNumber,editPassword,editConfirmPassword;
    CheckBox ckConditions;
    Button btnSignUp;
    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editUserName=(EditText)findViewById(R.id.editUserName);
        editMobileNumber=(EditText)findViewById(R.id.editMobileNumber);
        editPassword=(EditText)findViewById(R.id.editPassword);
        editConfirmPassword=(EditText)findViewById(R.id.editConfirmPassword);
        ckConditions=(CheckBox)findViewById(R.id.ckConditions);
        btnSignUp=(Button)findViewById(R.id.btnSignUp);
        database=new Database(this);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editPassword.getText().toString().equals(editConfirmPassword.getText().toString()))
                {
                    if(ckConditions.isChecked())
                    {
                        String UserId=database.InsertUserInformation(editUserName.getText().toString(),editMobileNumber.getText().toString(),editPassword.getText().toString());
                        if(UserId!="-1")
                        {
                            String date = new SimpleDateFormat("d/M/yyyy", Locale.getDefault()).format(new Date());

                            /* Insert Default categories */
                            ArrayList<String> Categories=new ArrayList<String>();
                            Categories.add("Salary");
                            Categories.add("Interest");
                            Categories.add("Other");
                            Categories.add("Rent");
                            Categories.add("Credit");
                            database.InsertDefaultCategories(Categories,Integer.parseInt(UserId),"I");

                            ArrayList<String> Categories1=new ArrayList<String>();
                            Categories1.add("Business Expenses");
                            Categories1.add("Education");
                            Categories1.add("Finance & Investment");
                            Categories1.add("Giving");
                            Categories1.add("Health & Medical");
                            Categories1.add("Home & Utilities");
                            Categories1.add("Rent");
                            Categories1.add("Restaurant & Dining");
                            database.InsertDefaultCategories(Categories1,Integer.parseInt(UserId),"E");


                            database.InsertDefaultDate(date,Integer.parseInt(UserId));
                            Intent i=new Intent(getApplicationContext(),Index.class);
                            i.putExtra("UserId",UserId);
                            startActivity(i);
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Please agree terms and conditions.",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Confirm password is not matched.",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
