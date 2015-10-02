package com.embeddedlapps.subastas_cliente;

import java.util.ArrayList;
import java.util.HashMap;
 
import com.embeddedlapps.subastas_cliente.Model;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class MyAdapter extends ArrayAdapter<Model> {
 
        private final Context context;
        private final ArrayList<Model> modelsArrayList;
 
        public MyAdapter(Context context, ArrayList<Model> modelsArrayList) {
 
            super(context, R.layout.list_item, modelsArrayList);
 
            this.context = context;
            this.modelsArrayList = modelsArrayList;
        }
 
      

		@Override
        public View getView(int position, View convertView, ViewGroup parent) {
 
            // 1. Create inflater 
            LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
            // 2. Get rowView from inflater
 
            View rowView = null;
            rowView = inflater.inflate(R.layout.list_item, parent, false);
            TextView titleView = (TextView) rowView.findViewById(R.id.pieza);
            titleView.setText("modelsArrayList.get(position).getTitle()");
 
 
            // 5. retrn rowView
            return rowView;
        }
}