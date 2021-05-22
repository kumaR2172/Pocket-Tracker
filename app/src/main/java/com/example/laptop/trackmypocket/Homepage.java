package com.example.laptop.trackmypocket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.trackmypocket.AddBalance;
import com.example.trackmypocket.R;
import com.example.trackmypocket.RemoveCategory;

public class Homepage extends AppCompatActivity {
    CardView addinc,addex,addcat,remcat,rep,bal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        addinc = findViewById(R.id.card1);
        addex=findViewById(R.id.card2);
        addcat=findViewById(R.id.card3);
        remcat=findViewById(R.id.card4);
        rep=findViewById(R.id.card5);
        bal=findViewById(R.id.card6);

        addinc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Homepage.this, AddIncome.class);
                startActivity(intent);
            }
        });
        addex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Homepage.this, AddExpense.class);
                startActivity(intent);
            }
        });
        addcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Homepage.this, AddCategory.class);
                startActivity(intent);
            }
        });
        remcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Homepage.this, RemoveCategory.class);
                startActivity(intent);

            }
        });
        rep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Homepage.this,Reports.class);
                startActivity(intent);
            }
        });
        bal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Homepage.this, AddBalance.class);
                startActivity(intent);
            }
        });
    }
}

