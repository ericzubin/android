package com.embeddedlapps.subastas_cliente;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MisSubastas extends ActionBarActivity  {
	
	
	private ListView lv;
	
	
	//AlertDialog
	final Context context = this;
	
	
	//Fuentes
			private Typeface roboto;
			private Typeface robotoCondensed;
	
	
	private ProgressDialog pDialog;
	JSONParser jParser = new JSONParser();
	// url to create new product												
	private static String url_all_products = "http://embeddedlapps.com/subastas/get_all_products.php";
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCTS = "products";
	private static final String TAG_PID = "pid";
	private static final String TAG_NAME = "name";
	private static final String TAG_PIEZA = "pieza";
	private static final String TAG_FECHA = "fecha";
	private static final String TAG_ACTIVO = "activo";
	private int next_img;
	
	public String IdUsr;
	
	//products JSONArray
	JSONArray products = null;
	
	ArrayList<HashMap<String, String>> productsList;
	
	public SharedPreferences preferencias;
	public String UsrName =null;
	public static final String usuario_VALUE_KEY = "Usuario";
	private TextView textName;
	private TextView textPedidosRealizados;
	private Button btnNuevaSol;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("entro a lis","listas1");
		setContentView(R.layout.mis_pedidos);
		Log.d("entro a lis","listas");
		productsList = new ArrayList<HashMap<String, String>>();
		
		
		lv = (ListView) findViewById(R.id.listview1);
		
		
		//se asignan las vistas por el id 
		
		roboto = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Medium.ttf");
		robotoCondensed = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Regular.ttf");
		
		textName=(TextView) findViewById(R.id.saludoinicial);
		textName.setTypeface(roboto);
		textPedidosRealizados = (TextView) findViewById(R.id.PedidosRealizados);
		textPedidosRealizados.setTypeface(robotoCondensed);
		
		
		
		
		
		//textName=(TextView) findViewById(R.id.saludoinicial);
		//TextView sinconexion=(TextView)findViewById(R.id.PedidosRealizados);	
		
		//uso de SharedPreferences para gurdar datos y que permanezcan al reabrir la aplicacion
		SharedPreferences preferencias = getSharedPreferences("Datos", MODE_PRIVATE);
		Log.d("entro a lis","listas2");
		UsrName=preferencias.getString("UsrNombre",""); // obtiene el usuario
		textName.setText("Bienvenido "+UsrName);
		
		IdUsr=preferencias.getString("IdUsr","");
		
		if(isOnline()) new LoadAllProducts().execute();
		else 
			{
			
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(
					context);
			alertDialog.setMessage("Comprueba tu conexion a internet");
			alertDialog.setNegativeButton("Continuar",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					dialog.cancel();
				}
			});
			alertDialog.show();
			//textPedidosRealizados.setText("Verifica tu conexion a INTERNET");
			//btnNuevaSol.setEnabled(false);
			
			}
		
		
		
		// Get listview
		// lv = getListView().setAdapter(adapter);;
		//lv.setBackgroundColor(Color.RED);
		
				
		lv.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {			

				if(isOnline())
				{
					//textPedidosRealizados.setText("Verifica tu conexion a INTERNET");
					//btnNuevaSol.setEnabled(false);
					
				// getting values from selected ListItem
				String pid = ((TextView) view.findViewById(R.id.id)).getText().toString();
				String activo = ((TextView) view.findViewById(R.id.activo)).getText().toString();

				Intent in = new Intent("com.embeddedLapps.finSubastas");
				
				// sending pid to next activity
				in.putExtra(TAG_PID, pid);		
				in.putExtra("id_usr", IdUsr);
				in.putExtra(TAG_ACTIVO, activo);
				

				startActivityForResult(in, 100);
				}
				else 
				{
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
					alertDialog.setMessage("Comprueba tu conexion a internet");
					alertDialog.setNegativeButton("Continuar",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					}
				});
				alertDialog.show();
				}
			}
		});
	}
	
	static class ViewHolder {
	    TextView text;
	}
	
	/*public void Nueva_Solicitud(View vieww) //declarado en la vista del boton en el archivo xml
	{
		Intent i = new Intent("com.embeddedLapps.NuevoPedido");

		if(products==null){	next_img=1;
		}
//		else 
//			next_img=products.length()+1;
		i.putExtra("next_img",next_img );
		i.putExtra("nuevop","yes" );
		startActivity(i); //mod a--> forResult
	}*/
	
	@Override
	public void onBackPressed() {
		Log.d("back","preciono regreso");

		moveTaskToBack(true);// borra el stack de retroceso
		finish();
	}
	
	
	

	//Menu Action Bar & Go to New Activity
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        //CreateMenu(menu);
    	getMenuInflater().inflate(R.menu.main, menu);
        return true;
	}
	
	
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item)
	    {    
	         return MenuChoice(item);    
	    }    
	   
	 
		
	/*	@SuppressLint("NewApi")
		private void CreateMenu(Menu menu)
	    {

	        MenuItem mnu1 = menu.add(0, 1, 1, "Item New");
	        {         
	            mnu1.setIcon(R.drawable.ic_action_new); 
	            //mnu1.setShowAsAction(
	              //    	 MenuItem.SHOW_AS_ACTION_IF_ROOM);
	        }
	        
	        MenuItem mnu2 = menu.add(0, 0, 0, "Item refresh");
	        {         
	            mnu2.setIcon(R.drawable.ic_action_refresh); 
	            //mnu2.setShowAsAction(
	              //    	 MenuItem.SHOW_AS_ACTION_IF_ROOM);
	        }
			
	    }
	    
	    */

	    private boolean MenuChoice(MenuItem item)
	    {        
	    	
	        switch (item.getItemId()) {
	        
	        case  R.id.action_nuevo:      
	        	
	        	if(isOnline()) {
	        	Intent i = new Intent("com.embeddedLapps.NuevoPedido");

	    		if(products==null){	next_img=1;
	    		}
//	    		else 
//	    			next_img=products.length()+1;
	    		i.putExtra("next_img",next_img );
	    		i.putExtra("nuevop","yes" );
	    		startActivity(i); //mod a--> forResult
	        	}
	        	else 
    			{
	        		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
	    					context);
	    			alertDialog.setMessage("Comprueba tu conexion a internet");
	    			alertDialog.setNegativeButton("Continuar",new DialogInterface.OnClickListener() {
	    				public void onClick(DialogInterface dialog,int id) {
	    					dialog.cancel();
	    				}
	    			});
	    			alertDialog.show();
    			}
	        	
	            return true;  
	            
	            
	        case R.id.action_refresh:
	        	if(isOnline()) {
	        		Intent i = new Intent("com.embeddedLapps.MisSubastas");
	        		startActivity(i);
	        	
	        	}
	    		else 
	    			{
	    			AlertDialog.Builder alertDialog = new AlertDialog.Builder(
	    					context);
	    			alertDialog.setMessage("Comprueba tu conexi�n a internet");
	    			alertDialog.setNegativeButton("Continuar",new DialogInterface.OnClickListener() {
	    				public void onClick(DialogInterface dialog,int id) {
	    					dialog.cancel();
	    				}
	    			});
	    			alertDialog.show();
	    			
	    			}
	        	return true;
	        }
	        
	        
	        return false;
	    }    
	
	
	
	
	public boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnected()) {
	        return true;
	    }
	    return false;
	}
	
	
	
	/**
	 * Background Async Task to Load all product by making HTTP Request
	 * */
	class LoadAllProducts extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			Log.d("paso","entro al pdialog");
			super.onPreExecute();
			pDialog = new ProgressDialog(MisSubastas.this);
			pDialog.setMessage("Analizando datos. Espere porfavor...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All products from url
		 * */
		protected String doInBackground(String... args) {
			
			
			String id_solicitante = IdUsr;
			Log.d("All Products: ", "paso al background");
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id_solicitante", id_solicitante));
			Log.d("All Products: ", "agrego params"+url_all_products);
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_all_products,"POST",params);
			Log.d("All Products: ", "agrego params"+url_all_products);
			// Check your log cat for JSON reponse
			Log.d("All Products: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// products found
					// Getting Array of Products
					products = json.getJSONArray(TAG_PRODUCTS);

					// looping through All Products
					for (int i = 0; i < products.length(); i++) {
						JSONObject c = products.getJSONObject(i);

						// Storing each json item in variable
						String id = c.getString(TAG_PID);
						String pieza = c.getString(TAG_PIEZA);
						String fecha = c.getString(TAG_FECHA);
						//Log.d("nexImg",c.getString("nextimg"));
						String activo =c.getString(TAG_ACTIVO);
						next_img=Integer.valueOf(c.getString("nextimg").toString());

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(TAG_PID, id);
						map.put(TAG_PIEZA, pieza);
						map.put(TAG_FECHA, fecha);
						map.put(TAG_ACTIVO, activo);

						// adding HashList to ArrayList
						productsList.add(map);
					}
				} else {
					// No hay productos
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
			/*		ListAdapter adapter = new SimpleAdapter(
							MisSubastas.this, productsList,
							R.layout.list_item, new String[] { TAG_PID,
									TAG_PIEZA, TAG_FECHA},
							new int[] { R.id.id, R.id.pieza,R.id.fecha});
					// updating listview
					setListAdapter(adapter);
				*/
					
					ListAdapter adapter = new ColorAdapter(
							MisSubastas.this, productsList,
							R.layout.list_item, new String[] { TAG_PID,
									TAG_PIEZA, TAG_FECHA,TAG_ACTIVO},
							new int[] { R.id.id, R.id.pieza,R.id.fecha,R.id.activo});
					// updating listview
					lv.setAdapter(adapter);
					
					
				}
			
			});
			
			

		}

	}
	
	
	
	

}
