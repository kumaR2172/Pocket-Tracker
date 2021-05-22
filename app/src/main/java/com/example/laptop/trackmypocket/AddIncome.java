package com.example.laptop.trackmypocket;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trackmypocket.R;

import java.util.ArrayList;
import java.util.Calendar;

public class AddIncome extends AppCompatActivity {
    EditText editSelectDate,editAmount,editExtraNotes;
    Spinner spinnerCategory;
    ArrayList listCategoryName=new ArrayList();
    ArrayList listId=new ArrayList();
    Database database;
    DatePickerDialog picker;
    Button btnAddIncome;
    String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);

        editSelectDate=(EditText)findViewById(R.id.editSelectDate);
        editAmount=(EditText)findViewById(R.id.editAmount);
        editExtraNotes=(EditText)findViewById(R.id.editExtraNotes);
        btnAddIncome=(Button)findViewById(R.id.btnAddIncome);
        //spinnerCategory=(Spinner)findViewById(R.id.spinnerCategory);
        database=new Database(this);

        Intent i=getIntent();
        Bundle b=i.getExtras();
        UserId=b.getString("UserId");
        /* Get Categories */

        Cursor c=database.getCategoriesReleatedTOIncome("I",1);
        while (c.moveToNext())
        {
            listCategoryName.add(c.getString(1));
            listId.add(c.getString(0));
        }
        CustomAdapterSpinner customAdapterSpinner=new CustomAdapterSpinner(getApplicationContext(),listCategoryName,listId);
        spinnerCategory.setAdapter(customAdapterSpinner);

        editSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AddIncome.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                editSelectDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }


        });

        btnAddIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtCategoryId= (TextView) spinnerCategory.findViewById(R.id.txtCategoryId);
                //Toast.makeText(getApplicationContext(),txtCategoryId.getText().toString(),Toast.LENGTH_SHORT).show();
                boolean result=database.InsertTransaction(Float.parseFloat(editAmount.getText().toString()),
                        Integer.parseInt(txtCategoryId.getText().toString()),"I"
                        ,editSelectDate.getText().toString(),editExtraNotes.getText().toString(),Integer.parseInt(UserId));
                if(result==true)
                {
                    editSelectDate.setText("");
                    editAmount.setText("");
                    CustomAdapterSpinner customAdapterSpinnerr=new CustomAdapterSpinner(getApplicationContext(),listCategoryName,listId);
                    spinnerCategory.setAdapter(customAdapterSpinnerr);
                    editExtraNotes.setText("");
                    Toast.makeText(getApplicationContext(),"Income is successfully saved.",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Income is not successfully saved.",Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
}
