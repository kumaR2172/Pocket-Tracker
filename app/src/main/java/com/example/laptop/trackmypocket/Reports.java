package com.example.laptop.trackmypocket;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trackmypocket.R;

import java.util.ArrayList;
import java.util.StringTokenizer;

//import android.support.v7.app.AppCompatActivity;

public class Reports extends AppCompatActivity {
    ListView simpleList;
    TextView txtTotalAmount,txtIncome,txtExpense;
    ArrayList ListId=new ArrayList();
    ArrayList ListDatee=new ArrayList();
    ArrayList ListCategory=new ArrayList();
    ArrayList ListAmount=new ArrayList();
    ArrayList ListStatus=new ArrayList();
    Database database;
    String Monthh,Yearr,Dayss,Monthss,Yearss,UserId;
    StringTokenizer st;
    double saving=0,income=0,expense=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        txtTotalAmount=(TextView)findViewById(R.id.txtSaving);
        txtIncome=(TextView)findViewById(R.id.txtIncome);
        txtExpense=(TextView)findViewById(R.id.txtExpense);
        simpleList=(ListView)findViewById(R.id.listView);
        database=new Database(this);

        Intent i=getIntent();
        Bundle b=i.getExtras();
        UserId=b.getString("UserId");
        Monthh=b.getString("Monthh");
        Yearr=b.getString("Yearr");

          /*Get Categories releated to income*/

        Cursor c= database.GetTransactionDetails(Integer.parseInt(UserId));
        while (c.moveToNext())
        {
            st=new StringTokenizer(c.getString(5).toString(),"/");
            Dayss=st.nextToken();
            Monthss=st.nextToken();
            Yearss=st.nextToken();

            if(Monthh.equals(Monthss) && Yearr.equals(Yearss))
            {
                ListId.add(c.getString(0));
                ListDatee.add(" "+c.getString(1));
                ListCategory.add(" "+c.getString(2));
                ListAmount.add(" "+c.getString(3));
                ListStatus.add(c.getString(4));

                if(c.getString(4).equals("I"))
                {
                    income=income+Float.parseFloat(c.getString(3));
                }
                else
                {
                    expense=expense+Float.parseFloat(c.getString(3));
                }
            }
        }
        saving=income-expense;
        txtTotalAmount.setText("Saving:- Rs "+saving +"/-");
        txtIncome.setText("Income:-  Rs "+income +"/-");
        txtExpense.setText("Expense:- Rs "+expense+"/-");


        Log.d("Value--------",ListStatus.toString());
        final ReportsCustomAdapter customAdapter = new ReportsCustomAdapter(getApplicationContext(),ListId,ListDatee,ListCategory,ListAmount,ListStatus);
        simpleList.setAdapter(customAdapter);
    }
}
