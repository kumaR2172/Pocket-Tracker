package com.example.laptop.trackmypocket;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.trackmypocket.R;

import java.util.ArrayList;


public class ReportsCustomAdapter extends BaseAdapter {
    Context context;
    ArrayList ListId;
    ArrayList ListDatee;
    ArrayList ListCategory;
    ArrayList ListAmount;
    ArrayList ListStatus;
    LayoutInflater inflter;

    public ReportsCustomAdapter(Context applicationContext, ArrayList ListId,ArrayList ListDatee,ArrayList categoryList,ArrayList ListAmount,ArrayList ListStatus) {
        this.context = context;
        this.ListId = ListId;
        this.ListDatee=ListDatee;
        this.ListCategory = categoryList;
        this.ListAmount=ListAmount;
        this.ListStatus=ListStatus;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return ListCategory.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.activity_reportslistview, null);
        TextView txtDatee = (TextView) view.findViewById(R.id.txtDatee);
        TextView txtCategory = (TextView) view.findViewById(R.id.txtCategoryName);
        TextView txtAmount=(TextView)view.findViewById(R.id.txtAmount);
        TextView txtId=(TextView)view.findViewById(R.id.txtId);
        String Status=(String) ListStatus.get(i).toString();

        if(Status.equalsIgnoreCase("I"))
        {
           txtAmount.setTextColor(Color.parseColor("#008000"));
        }
        else
        {
            txtAmount.setTextColor(Color.parseColor("#ff0000"));
        }

        txtDatee.setText(ListDatee.get(i).toString());
        txtCategory.setText(ListCategory.get(i).toString());
        txtAmount.setText(ListAmount.get(i).toString());
        txtId.setText(ListId.get(i).toString());
        return view;
    }
}