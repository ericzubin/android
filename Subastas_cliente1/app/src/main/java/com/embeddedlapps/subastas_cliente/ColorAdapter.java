package com.embeddedlapps.subastas_cliente;

import java.util.ArrayList;
import java.util.HashMap;

import com.embeddedlapps.subastas_cliente.MisSubastas.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ColorAdapter extends BaseAdapter {
	
	//Fuentes
	private Typeface roboto;
	
	
	private int[] colors = new int[] { 0xAA95c11f, 0xAAe30613 };
	private LayoutInflater mInflater;
	 
	//The variable that will hold our text data to be tied to list.
	private ArrayList<HashMap<String, String>> data;
	
	
	 
	/*public ColorAdapter(Context context, String[] items) {
	    mInflater = LayoutInflater.from(context);
	    this.data = items;
	}*/

	public ColorAdapter(Context context, ArrayList<HashMap<String, String>> productsList, int listItem,	String[] strings, int[] is) {
		  
		mInflater = LayoutInflater.from(context);
		    this.data = productsList;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();// .length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		 return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		 return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		 
		if (convertView == null) {
		    convertView = mInflater.inflate(R.layout.list_item, null);
		 
		    holder = new ViewHolder();
		    holder.text = (TextView) convertView.findViewById(R.id.pieza);
		    convertView.setTag(holder);
		} else {
		    holder = (ViewHolder) convertView.getTag();
		}

		roboto = Typeface.createFromAsset(convertView.getContext().getAssets(),"fonts/Roboto-Medium.ttf");
		
		//estableciendo la informacion en cada uno de los registros
		    TextView titleView = (TextView) convertView.findViewById(R.id.pieza);
		    titleView.setTypeface(roboto);
		    titleView.setText(data.get(position).get("pieza").toString());
		    TextView fecha = (TextView) convertView.findViewById(R.id.fecha);
		    fecha.setTypeface(roboto);
		    fecha.setText(data.get(position).get("fecha").toString());
		    TextView id = (TextView) convertView.findViewById(R.id.id);
		    id.setText(data.get(position).get("pid").toString());
		    TextView activo = (TextView) convertView.findViewById(R.id.activo);
		    activo.setText(data.get(position).get("activo").toString());

		    int actColor=Integer.valueOf(data.get(position).get("activo").toString());
		    Log.d("activoColor",String.valueOf(actColor));
		    //Set the background color depending of  odd/even colorPos result
		    int colorPos = position % colors.length;
		    convertView.setBackgroundColor(colors[actColor]);
		 
		   return convertView;
		}

}
