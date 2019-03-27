package com.app.topservices;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Model.Condominio;

public class CustomAdapter extends ArrayAdapter<Condominio> implements View.OnClickListener {

    private ArrayList<Condominio> dataSet;
    Context mContext;

    private static class ViewHolder{
        TextView txtName;
        TextView txtType;
        TextView txtVersion;
        ImageView info;
    }

    public CustomAdapter(ArrayList<Condominio> data, Context context){
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext = context;
    }


    @Override
    public void onClick(View view) {
        int position = (Integer) view.getTag();
        Object object = getItem(position);
        Condominio condominio = (Condominio)object;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Condominio condominio = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.type);
            viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.version_number);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);

            result = convertView;

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        lastPosition = position;

        viewHolder.txtName.setText(condominio.getNomeCondominio());
        viewHolder.txtVersion.setText(condominio.getNomeResponsavel());
        viewHolder.txtType.setText(condominio.getEmail());
        viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);

        return convertView;
    }
}
