package com.example.laptop.trackmypocket;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trackmypocket.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

public class Index extends AppCompatActivity {
    GridView simpleList;
    String UserId;
    ArrayList List=new ArrayList<>();
    Database database;
    String Datee,Monthh,Yearr,Dayss,Monthss,Yearss;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.file,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String value=item.getTitle().toString();

        if(value.equals("Change Password"))
        {
            final Dialog dialog=new Dialog(Index.this);
            dialog.setContentView(R.layout.changepassword);
            dialog.setTitle("Change Password");
            final EditText editNewPassword=(EditText)dialog.findViewById(R.id.editPassword);
            final EditText editNewConfirmPassword=(EditText)dialog.findViewById(R.id.editNewConfirmPassword);
            Button btnChangePassword=(Button)dialog.findViewById(R.id.btnChangePassword);
            Button btnCancel=(Button)dialog.findViewById(R.id.btnCancel);
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.backgroundcolor);
            dialog.show();



            btnChangePassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(editNewPassword.getText().toString().isEmpty() && editNewConfirmPassword.getText().toString().isEmpty())
                    {
                        Toast.makeText(getApplicationContext(),"Please fill all the fields.",Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        if(editNewPassword.getText().toString().equals(editNewConfirmPassword.getText().toString()))
                        {
                            String Password=editNewPassword.getText().toString();
                            boolean result=database.UpdateUserPassword(Integer.parseInt(UserId),Password);
                            if(result==true)
                            {
                                Toast.makeText(getApplicationContext(),"Password is updated successfully.",Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Password is not updated.",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Your password is not matched.",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


        }
        else if(value.equals("Logout"))
        {
            boolean result=database.Logout(Integer.parseInt(UserId));
            if(result==true)
            {
                Intent i=new Intent(getApplicationContext(),Login.class);
                //finishActivity();
                startActivity(i);
            }
            else
            {

            }
        }
        else
        {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        database=new Database(this);

        Intent i=getIntent();
        Bundle b=i.getExtras();
        UserId=b.getString("UserId");

        simpleList = (GridView) findViewById(R.id.gridView);
        List.add(new Item("Add Income",R.drawable.addincome));
        List.add(new Item("Add Expense",R.drawable.addexpenditure));
        List.add(new Item("Add Category",R.drawable.addcategory));
        List.add(new Item("Reports",R.drawable.reports));
        List.add(new Item("Balance",R.drawable.rupees));
        List.add(new Item("Default Month",R.drawable.calendarr));
        MyAdapter myAdapter=new MyAdapter(this,R.layout.grid_view_items,List);
        simpleList.setAdapter(myAdapter);

        /*Get Default date of user*/
        Cursor c=database.GetDefaultDate(Integer.parseInt(UserId));
        if(c.moveToNext())
        {
            String Datee=c.getString(0);
            StringTokenizer st=new StringTokenizer(Datee,"/");
            st.nextToken();
            Monthh=st.nextToken();
            Yearr=st.nextToken();

        }


        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Item=((TextView)view.findViewById(R.id.textView)).getText().toString();

                //Toast.makeText(getApplicationContext(),Item,Toast.LENGTH_SHORT).show();
                //String Item=textItem.getText().toString();
                if(Item.equals("Add Income")) {
                    Intent i = new Intent(getApplicationContext(), AddIncome.class);
                    i.putExtra("UserId",UserId);
                    startActivity(i);
                }
                else if(Item.equals("Add Expense"))
                {
                    Intent i = new Intent(getApplicationContext(), AddExpense.class);
                    i.putExtra("UserId",UserId);
                    startActivity(i);
                }
                else  if(Item.equals("Add Category"))
                {
                    Intent i = new Intent(getApplicationContext(), AddCategory.class);
                    i.putExtra("UserId",UserId);
                    startActivity(i);
                }
                else  if(Item.equals("Reports"))
                {
                    Intent i = new Intent(getApplicationContext(), Reports.class);
                    i.putExtra("UserId",UserId);
                    i.putExtra("Monthh",Monthh);
                    i.putExtra("Yearr",Yearr);
                    startActivity(i);
                }
                else if(Item.equals("Balance"))
                {
                    double income=0,expense=0,saving=0;
                    StringTokenizer st;
                    Cursor c=database.GetBalance(Integer.parseInt(UserId));
                    while (c.moveToNext())
                    {
                        st=new StringTokenizer(c.getString(2).toString(),"/");
                        Dayss=st.nextToken();
                        Monthss=st.nextToken();
                        Yearss=st.nextToken();
                        //Log.d("Date---------->",Monthh+"/"+Yearr+" ---  "+Monthss+"-"+Yearss);
                        if(Monthh.equals(Monthss) && Yearr.equals(Yearss))
                        {

                            if(c.getString(1).equals("I"))
                            {
                                income=income+Float.parseFloat(c.getString(0));
                            }
                            else
                            {
                                expense=expense+Float.parseFloat(c.getString(0));
                            }
                        }

                    }
                    saving=income-expense;

                    // Create custom dialog object
                    final Dialog dialog = new Dialog(Index.this);
                    // Include dialog.xml file
                    dialog.setContentView(R.layout.dialog);
                    // Set dialog title
                    dialog.getWindow().setBackgroundDrawableResource(R.drawable.backgroundcolor);
                    dialog.setTitle("Total balance of "+Monthh+"/"+Yearr);

                    // set values for custom dialog components - text, image and button
                    TextView txtIncomeBalance = (TextView) dialog.findViewById(R.id.txtIncomeBalance);
                    TextView txtExpenseBalance=(TextView)dialog.findViewById(R.id.txtExpenseBalance);
                    TextView txtSavingBalance=(TextView)dialog.findViewById(R.id.txtSavingBalance);
                    txtIncomeBalance.setText(" Income Rs "+income+"/-");
                    txtExpenseBalance.setText(" Expense Rs "+expense+"/-");
                    txtSavingBalance.setText(" Saving Rs "+ saving+"/-");
                    /*ImageView image = (ImageView) dialog.findViewById(R.id.imageDialog);
                    image.setImageResource(R.drawable.addincome);*/

                    dialog.show();

                    Button declineButton = (Button) dialog.findViewById(R.id.declineButton);
                    // if decline button is clicked, close the custom dialog
                    declineButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Close dialog
                            dialog.dismiss();
                        }
                    });

                }
                else if(Item.equals("Default Month"))
                {
                    // Create custom dialog object
                    final Dialog dialog = new Dialog(Index.this);
                    // Include dialog.xml file
                    dialog.setContentView(R.layout.dialogdefautmonth);
                    // Set dialog title
                    dialog.getWindow().setBackgroundDrawableResource(R.drawable.backgroundcolor);
                    dialog.setTitle("Select Default Month and Year");

                    // set values for custom dialog components - text, image and button
                    final DatePicker datePicker=(DatePicker)dialog.findViewById(R.id.datePicker);
                    Datee = new SimpleDateFormat("d", Locale.getDefault()).format(new Date());
                    datePicker.init(Integer.parseInt(Yearr),Integer.parseInt(Monthh)-1,Integer.parseInt(Datee),null);
                    //  datePicker.updateDate(Integer.parseInt(Yearr),Integer.parseInt(Monthh),Integer.parseInt(Datee));
                    dialog.show();

                    Button btnUpdateDefaultDate = (Button) dialog.findViewById(R.id.btnUpdateDefaultDate);
                    // if decline button is clicked, close the custom dialog
                    btnUpdateDefaultDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Close dialog
                            int   day  = datePicker.getDayOfMonth();
                            int   month= datePicker.getMonth()+1;
                            int   year = datePicker.getYear();
                            String NewDate=day+"/"+month+"/"+year;
                            boolean result=database.UpdateDefaultDate(NewDate,UserId);
                            if(result==true)
                            {
                                Toast.makeText(getApplicationContext(),"Date is updated successfully.",Toast.LENGTH_SHORT).show();
                                Datee=Integer.toString(day);
                                Monthh=Integer.toString(month);
                                Yearr=Integer.toString(year);
                                datePicker.updateDate(year,month,day);
                            }

                            dialog.dismiss();
                        }
                    });
                }
                else
                {

                }
            }
        });



    }
}
