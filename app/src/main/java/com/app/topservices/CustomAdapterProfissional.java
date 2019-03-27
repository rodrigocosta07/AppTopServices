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


import Model.Profissional;

public class CustomAdapterProfissional extends ArrayAdapter<Profissional> implements View.OnClickListener {

    private ArrayList<Profissional> dataSet;
    Context mContext;

    private static class ViewHolder{
        TextView txtName;
        TextView txtType;
        TextView txtVersion;
        ImageView info;
    }

    public CustomAdapterProfissional(ArrayList<Profissional> data, Context context){
        super(context, R.layout.row_item_profissional, data );
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object = getItem(position);
        Profissional profissional = (Profissional) object;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Profissional profissional = getItem(position);
        CustomAdapterProfissional.ViewHolder viewHolder;

        final View result;

        if(convertView == null){
            viewHolder = new CustomAdapterProfissional.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.type);
            viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.version_number);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);

            result = convertView;

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (CustomAdapterProfissional.ViewHolder) convertView.getTag();
            result = convertView;
        }

        lastPosition = position;

        viewHolder.txtName.setText(profissional.getNome());
        viewHolder.txtVersion.setText(profissional.getTelefone());
        viewHolder.txtType.setText(profissional.getEmail());
        viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);

        return convertView;
    }
}
