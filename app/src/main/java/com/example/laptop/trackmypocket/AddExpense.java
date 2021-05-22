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

//import android.support.v7.app.AppCompatActivity;

public class AddExpense extends AppCompatActivity {
    EditText editSelectDate,editAmount,editExtraNotes;
    Spinner spinnerCategory;
    ArrayList listCategoryName=new ArrayList();
    ArrayList listId=new ArrayList();
    Database database;
    DatePickerDialog picker;
    Button btnAddExpense;
    String UserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        editSelectDate=(EditText)findViewById(R.id.editSelectDate);
        editAmount=(EditText)findViewById(R.id.editAmount);
        editExtraNotes=(EditText)findViewById(R.id.editExtraNotes);
        btnAddExpense=(Button)findViewById(R.id.btnAddExpense);
        //spinnerCategory=(Spinner)findViewById(R.id.spinnerCategory);
        database=new Database(this);

        Intent i=getIntent();
        Bundle b=i.getExtras();
        UserId=b.getString("UserId");


        /* Get Categories */

        Cursor c=database.getCategoriesReleatedTOIncome("E",1);
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
                picker = new DatePickerDialog(AddExpense.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                editSelectDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }


        });

        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtCategoryId= (TextView) spinnerCategory.findViewById(R.id.txtCategoryId);
                //Toast.makeText(getApplicationContext(),txtCategoryId.getText().toString(),Toast.LENGTH_SHORT).show();
                boolean result=database.InsertTransaction(Float.parseFloat(editAmount.getText().toString()),
                        Integer.parseInt(txtCategoryId.getText().toString()),"E"
                        ,editSelectDate.getText().toString(),editExtraNotes.getText().toString(),Integer.parseInt(UserId));
                if(result==true)
                {
                    editSelectDate.setText("");
                    editAmount.setText("");
                    CustomAdapterSpinner customAdapterSpinnerr=new CustomAdapterSpinner(getApplicationContext(),listCategoryName,listId);
                    spinnerCategory.setAdapter(customAdapterSpinnerr);
                    editExtraNotes.setText("");
                    Toast.makeText(getApplicationContext(),"Expense is successfully saved.",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Expense is not successfully saved.",Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
}
