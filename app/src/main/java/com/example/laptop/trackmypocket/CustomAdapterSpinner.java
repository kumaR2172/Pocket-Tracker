package com.example.laptop.trackmypocket;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.trackmypocket.R;

import java.util.ArrayList;

public class CustomAdapterSpinner extends BaseAdapter {
    Context context;
    ArrayList categoryList;
    ArrayList Id;
    LayoutInflater inflter;

    public CustomAdapterSpinner(Context applicationContext, ArrayList categoryList, ArrayList Id) {
        this.context = context;
        this.categoryList = categoryList;
        this.Id = Id;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return categoryList.size();
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
        view = inflter.inflate(R.layout.activity_listviewspinner, null);
        TextView country = (TextView) view.findViewById(R.id.txtCategoryName);
        TextView txtCategoryId = (TextView) view.findViewById(R.id.txtCategoryId);
        country.setText(categoryList.get(i).toString());
        txtCategoryId.setText(Id.get(i).toString());
        return view;
    }
}
