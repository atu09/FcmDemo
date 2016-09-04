package com.atirek.alm.fcmdemo.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atirek.alm.fcmdemo.PojoClass.PersonRow;
import com.atirek.alm.fcmdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alm on 7/6/2016.
 */
public class FirebaseAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<PersonRow> personRow;
    ArrayList<PersonRow> arraylist;

    public FirebaseAdapter(Context context, List<PersonRow> personRow) {
        this.context = context;
        this.personRow = personRow;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<PersonRow>();
        this.arraylist.addAll(personRow);
    }

    class ViewHolder {
        TextView tv_name, tv_address;
    }

    @Override
    public int getCount() {
        return personRow.size();
    }

    @Override
    public Object getItem(int i) {
        return personRow.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.firebase_row, null);

            holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            holder.tv_address = (TextView) view.findViewById(R.id.tv_address);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.tv_name.setText(personRow.get(i).getName());
        holder.tv_address.setText(personRow.get(i).getAddress());

        return view;
    }
}
