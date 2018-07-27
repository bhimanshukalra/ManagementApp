package com.example.bhimanshu_kalra.managementapp2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by bhimanshu_kalra on 15/4/18.
 */

public class listViewAdapter extends ArrayAdapter {

    private int LayoutResource;

    public listViewAdapter(@NonNull Context context, int resource, @NonNull List objects)
    {
        super(context, resource, objects);
        LayoutResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View view = convertView;

        if(view == null)
        {view = LayoutInflater.from(getContext()).inflate(LayoutResource,null);}

        ImageView image = (ImageView) view.findViewById(R.id.listViewImageView);
        TextView title = (TextView) view.findViewById(R.id.listViewTitle);

        listClass list_class = (listClass) getItem(position);

        image.setImageResource(list_class.getMimageId());
        title.setText(list_class.getmTitle());

        return view;
    }

}
