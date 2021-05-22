package com.example.laptop.trackmypocket;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trackmypocket.R;

import java.util.ArrayList;

public class AddCategory extends AppCompatActivity {
    ListView simpleList;
    EditText editCategoryName;
    RadioButton rdbtnIncome,rdbtnExpence;
    Button btnAddCategory;
    Database database;
    ArrayList categoryList = new  ArrayList();
    ArrayList  Id=new ArrayList();
    String UserId;
    Dialog dialogg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        simpleList = (ListView) findViewById(R.id.listView);

        editCategoryName=(EditText)findViewById(R.id.editCategoryName);
        rdbtnIncome=(RadioButton)findViewById(R.id.rdbntIncome);
        rdbtnExpence=(RadioButton)findViewById(R.id.rdbtnExpense);
        btnAddCategory=(Button)findViewById(R.id.btnAddCategory);
        database=new Database(this);

        Intent i=getIntent();
        Bundle b=i.getExtras();
        UserId=b.getString("UserId");

        /*Get Categories releated to income*/
        rdbtnIncome.setChecked(true);
        rdbtnExpence.setChecked(false);
        Cursor c= database.getCategoriesReleatedTOIncome("I",Integer.parseInt(UserId));
        while (c.moveToNext())
        {
            Id.add(c.getString(0));
            categoryList.add(c.getString(1));
        }
        final CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), categoryList, Id);
        simpleList.setAdapter(customAdapter);

        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Status;
                if(rdbtnIncome.isChecked())
                {
                    Status="I";
                }
                else
                {
                    Status="E";
                }
                boolean result=database.insertCategories(editCategoryName.getText().toString(),Status,UserId);
                if(result==true)
                {
                    editCategoryName.setText("");
                    simpleList.clearTextFilter();
                    editCategoryName.setText("");
                    Cursor c= database.getCategoriesReleatedTOIncome(Status,Integer.parseInt(UserId));
                    while (c.moveToNext())
                    {
                        Id.add(c.getString(0));
                        categoryList.add(c.getString(1));
                    }
                    CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), categoryList, Id);
                    simpleList.setAdapter(customAdapter);
                    Toast.makeText(getApplicationContext(),"Your new category is successfully saved.",Toast.LENGTH_SHORT).show();

                }
                else
                {

                    Toast.makeText(getApplicationContext(),"Your new category is not saved.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        rdbtnIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rdbtnIncome.isChecked())
                {
                    rdbtnExpence.setChecked(false);
                    categoryList.clear();
                    Id.clear();
                    CustomAdapter customAdapter;
                    customAdapter= new CustomAdapter(getApplicationContext(), categoryList, Id);
                    simpleList.setAdapter(customAdapter);

                    Cursor c= database.getCategoriesReleatedTOIncome("I",Integer.parseInt(UserId));
                    while (c.moveToNext())
                    {
                        Id.add(c.getString(0));
                        categoryList.add(c.getString(1));
                    }
                    customAdapter = new CustomAdapter(getApplicationContext(), categoryList, Id);
                    simpleList.setAdapter(customAdapter);
                }
            }
        });

        rdbtnExpence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rdbtnExpence.isChecked())
                {
                    rdbtnIncome.setChecked(false);
                    categoryList.clear();
                    Id.clear();
                    CustomAdapter customAdapter;
                    customAdapter= new CustomAdapter(getApplicationContext(), categoryList, Id);
                    simpleList.setAdapter(customAdapter);

                    Cursor c= database.getCategoriesReleatedTOIncome("E",Integer.parseInt(UserId));
                    while (c.moveToNext())
                    {
                        Id.add(c.getString(0));
                        categoryList.add(c.getString(1));
                    }
                    customAdapter = new CustomAdapter(getApplicationContext(), categoryList, Id);
                    simpleList.setAdapter(customAdapter);
                }
            }
        });

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final TextView txtCategory=(TextView)view.findViewById(R.id.txtCategoryId);
                TextView txtcategoryNames=(TextView)view.findViewById(R.id.txtCategoryName);
                Log.d("Categor Id=",txtCategory.getText().toString());
                final Dialog dialogg=new Dialog(AddCategory.this);

                dialogg.setContentView(R.layout.dialogcategory);
                dialogg.getWindow().setBackgroundDrawableResource(R.drawable.backgroundcolor);
                dialogg.setTitle("Category");
                dialogg.show();

                final EditText editNewcategory=(EditText)dialogg.findViewById(R.id.editNewcategory);
                Button btnNewCategoryUpdate=(Button)dialogg.findViewById(R.id.btnNewCategoryUpdate);
                Button btnCancel=(Button)dialogg.findViewById(R.id.btnCancel);
                Button btnDeleteCategory=(Button)dialogg.findViewById(R.id.btnDeleteCategory);

                editNewcategory.setText(txtcategoryNames.getText().toString());

                btnNewCategoryUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         database.UpdateNewCategory(Integer.parseInt(txtCategory.getText().toString()),editNewcategory.getText().toString(),Integer.parseInt(UserId));
                         dialogg.dismiss();

                        /*Get Categories releated to income*/
                        rdbtnIncome.setChecked(true);
                        rdbtnExpence.setChecked(false);
                        Cursor c= database.getCategoriesReleatedTOIncome("I",Integer.parseInt(UserId));
                        while (c.moveToNext())
                        {
                            Id.add(c.getString(0));
                            categoryList.add(c.getString(1));
                        }
                        final CustomAdapter customAdapterr = new CustomAdapter(getApplicationContext(), categoryList, Id);
                        simpleList.setAdapter(customAdapterr);
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogg.dismiss();
                    }
                });

                btnDeleteCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean  result= database.DeleteCategory(Integer.parseInt(txtCategory.getText().toString()),Integer.parseInt(UserId));
                        if(result==true)
                        {
                            Toast.makeText(getApplicationContext(),"category is successfully deleted",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Category is use with one or more transactions",Toast.LENGTH_SHORT).show();
                        }

                        dialogg.dismiss();

                        /*Get Categories releated to income*/
                        rdbtnIncome.setChecked(true);
                        rdbtnExpence.setChecked(false);
                        Cursor c= database.getCategoriesReleatedTOIncome("I",Integer.parseInt(UserId));
                        while (c.moveToNext())
                        {
                            Id.add(c.getString(0));
                            categoryList.add(c.getString(1));
                        }
                        final CustomAdapter customAdapterrr = new CustomAdapter(getApplicationContext(), categoryList, Id);
                        simpleList.setAdapter(customAdapterrr);
                    }
                });



            }

        });



    }
}
