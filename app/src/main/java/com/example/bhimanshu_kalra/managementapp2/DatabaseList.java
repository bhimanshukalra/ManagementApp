package com.example.bhimanshu_kalra.managementapp2;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class DatabaseList extends ArrayAdapter<DatabaseClass> {

    private Activity context;
    private List<DatabaseClass> databaseList;

    public DatabaseList(Activity context, List<DatabaseClass> databaseList)
    {
        super(context, R.layout.layout_list_view_contents,databaseList);
        this.context = context;
        this.databaseList = databaseList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_list_view_contents,null,true);

        TextView textViewTitle = (TextView) listViewItem.findViewById(R.id.listViewTextView);
        //TextView textViewSubTitle = (TextView) listViewItem.findViewById(R.id.listViewSubTitle);

        DatabaseClass databaseClass = databaseList.get(position);

        textViewTitle.setText(databaseClass.getName());
        //textViewSubTitle.setText(databaseClass.getUrl());


        return listViewItem;
//        return super.getView(position, convertView, parent);
    }
}
