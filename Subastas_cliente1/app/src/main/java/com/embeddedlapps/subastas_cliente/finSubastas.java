package com.embeddedlapps.subastas_cliente;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class finSubastas extends ActionBarActivity {
	//AlertDialog
	final Context context = this;	
	//Fuentes
	private Typeface roboto;
	
	private TextView txtSalInit;
	private TextView txtDisponibilidad;
	private TextView txtLblPrecio;
	private TextView txtSuImagen;
	private TextView txtImgVendedor;
	private TextView txtLblComVen;
	
	
	public String UsrName =null;

	// Progress Dialog
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	// url to create new product
	
	private static final String url_detalle = "http://embeddedlapps.com/subastas/get_product_details.php";
//	private static final String url_detalle = "http://embeddedlapps.com/subastas/get_all_products.php";
	

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCT = "product";
	private static final String TAG_NAME = "name";
	private static final String TAG_PRICE = "price";
	private static final String TAG_DESCRIPTION = "descripcion";
	private static final String TAG_DISPONIBLE = "disponible";
	
	public String piezaName =null;
	public String IdUsr;
	
	private TextView textPieza;
	private TextView textPrecio;
	private TextView textDisponible;
	private TextView textComent;
	public Button btnComprar;
	String pid;
	private boolean finalizar=false;
	
	private ImageView imgProducto;
	private ImageView imgVendedor;
	private String imageUrl;
	private String imageUrl_v;
	private String StrOferta="La oferta no ha terminado";
	
	private Drawable[] arrImg = new Drawable[2];

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.respuesta_pedido);
		
		
		
		
		//Asignar fuente a Roboto
				roboto = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Medium.ttf");
				//Cambiar fuente a todos los textView
				
				txtSalInit =(TextView) findViewById(R.id.saludoinicial);
				txtSalInit.setTypeface(roboto);
				txtDisponibilidad =(TextView) findViewById(R.id.txtDisponibilidad);
				txtDisponibilidad.setTypeface(roboto);
				txtLblPrecio =(TextView) findViewById(R.id.txtLblPrecio);
				txtLblPrecio.setTypeface(roboto);
				txtSuImagen =(TextView) findViewById(R.id.txtSuImg);
				txtSuImagen.setTypeface(roboto);
				txtImgVendedor =(TextView) findViewById(R.id.txtImgVendedor);
				txtImgVendedor.setTypeface(roboto);
				txtLblComVen =(TextView) findViewById(R.id.txtlblComVend);
				txtLblComVen.setTypeface(roboto);
				
				
				btnComprar = (Button)findViewById(R.id.buttonComprar);
				btnComprar.setTypeface(roboto);
		
		
			
		//se asignan las vistas por el id 
		
		textPieza=(TextView) findViewById(R.id.textPiezasol);
		textPieza.setTypeface(roboto);
		textPrecio=(TextView) findViewById(R.id.textPrecio);
		textPrecio.setTypeface(roboto);
		textDisponible=(TextView) findViewById(R.id.textDisponible);
		textDisponible.setTypeface(roboto);
		textComent=(TextView) findViewById(R.id.textComVende);
		textComent.setTypeface(roboto);
		imgProducto=(ImageView) findViewById(R.id.imageView1);
		imgVendedor=(ImageView) findViewById(R.id.ImageVendedor);
		
		//Colocar nombre de la persona
				SharedPreferences preferencias = getSharedPreferences("Datos", MODE_PRIVATE);
				IdUsr=preferencias.getString("IdUsr",""); // obtiene el usuario
				UsrName=preferencias.getString("UsrNombre",""); // obtiene el usuario
				txtSalInit.setText("Bienvenido "+UsrName);
		
		
		//limpiando campos
		textPieza.setText("");
		textPrecio.setText("");
		textComent.setText("");
		textDisponible.setText("");
		
		
		Intent i = getIntent();	
		// getting product id (pid) from intent
		pid = i.getStringExtra("pid");
		IdUsr=i.getStringExtra("id_usr");
		Log.d("activo_finsu","otro"+i.getStringExtra("activo"));

		
		if(isOnline()) new LoadProducts().execute();
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
			//textPedidosRealizados.setText("Verifica tu conexion a INTERNET");
			//btnNuevaSol.setEnabled(false);
			
			}
		


	}
	public boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnected()) {
	        return true;
	    }
	    return false;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		finalizar=true;
		//verifica imagen seleccionada		
		finish();
			
	  }
	
	public void Send_comprar(View vieww) //declarado en la vista del boton en el archivo xml
	{
		
		String opcion=btnComprar.getText().toString();
		Log.d("btnopcio",opcion);
		if(opcion.equals("Eliminar de mi lista"))
		{
			new UpdateProducto(finSubastas.this).execute(IdUsr,pid,"visible");
		}
		if(opcion.equals("Comprar"))
		{
		Log.d("sendCom","pasohhhh");
		Intent in = new Intent("com.embeddedLapps.Comprar");
		// sending pid to next activity
		in.putExtra("precio", textPrecio.getText());
		in.putExtra("pieza", textPieza.getText());
		in.putExtra("pid", pid);
		in.putExtra("id_usr", IdUsr);
		// starting new activity and expecting some response back
		//startActivityForResult(in, 100);
		startActivity(in);
		}
		if(opcion.equals("Regresar"))  // si el boton contiene la palabra regresar terminara este intent
		{
			finish();	
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	class LoadProducts extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			Log.d("paso","entro al pdialog");
			super.onPreExecute();
			pDialog = new ProgressDialog(finSubastas.this);
			if(finalizar)pDialog.setMessage("Completando acciones. Espere porfavor...");
			else pDialog.setMessage("Obteniendo datos. Espere porfavor...");

			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... args) {

			
			
						// Building Parameters
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("id", pid));
						params.add(new BasicNameValuePair("IdUsr", IdUsr));
					//	params.add(new BasicNameValuePair("id_solicitante", pid));
						// getting product details by making HTTP request
						// Note that product details url will use GET request
							
						final JSONObject json = jsonParser.makeHttpRequest(url_detalle,"POST", params);
						
						// check your log for json response
						Log.d("Single Product Details", json.toString());
					
						 runOnUiThread(new Runnable() {
						 @Override
					     public void run() {
					        	 
						try {
						// json success tag
						int success = json.getInt(TAG_SUCCESS);
						boolean regresar=false;
						if (success == 1) 
						{
							// successfully received product details
							JSONArray productObj = json.getJSONArray(TAG_PRODUCT); // JSON Array
							Log.d("paso","pasohhhh");
							// get first product object from JSON Array
							JSONObject product = productObj.getJSONObject(0);
							Log.d("paso","pasohhhh");
							
							if(product.getString("activo").toString().equals("0"))
							{
								Log.d("activo",product.getString("activo").toString());
								Toast.makeText(getApplicationContext(), "No se ha finalizado la subasta de este producto", Toast.LENGTH_SHORT).show();
								btnComprar.setText("Regresar");
								textDisponible.setText(StrOferta);
								regresar=true;
								//finalizar=true;
								//imageUrl=null;
								//finish();
							}
							
							textPieza.setText("Su pedido: "+product.getString(TAG_NAME));
							textPrecio.setText(product.getString(TAG_PRICE));
							if(product.getString(TAG_DESCRIPTION).equals("null")) textComent.setText(" ");
							else textComent.setText(product.getString(TAG_DESCRIPTION));
							
							String imagen=product.getString("imagen");//obtiene la imagen del cliente
							String imagenV=product.getString("imagen_prov"); //obtiene la imagen del vendedor
							
							if(regresar)
							{
								textComent.setText(StrOferta);
								textPrecio.setText(StrOferta);
								imagenV="No hay imagen";
							}	
							else
							{
								if(product.getString(TAG_PRICE).toString().equals("0.00") )
								{
									textDisponible.setText("No se realizo ninguna oferta para su pieza");
									btnComprar.setText("Eliminar de mi lista");
								}
								else textDisponible.setText(product.getString(TAG_DISPONIBLE));										
							
							}
							Log.d("nombre",product.getString("comprado"));				
							
							if(imagen.equals("No hay imagen")) imageUrl = "http://embeddedlapps.com/subastas/fotos/sin_imagen.jpg";
							else imageUrl = "http://embeddedlapps.com/subastas/"+imagen;
							//imagen del vendedor
							
							
							if(imagenV.equals("No hay imagen") || imagenV.equals("null")) imageUrl_v = "http://embeddedlapps.com/subastas/fotos/sin_imagen.jpg";
							else imageUrl_v = "http://embeddedlapps.com/subastas/"+imagenV;
							//String imagenProv=product.getString("imagenProv");
							//if(imagenProv.equals("No hay imagen")) imageUrl = "http://embeddedlapps.com/subastas/fotos/sin_imagen.jpg";
							//else imageUrl = "http://embeddedlapps.com/subastas/"+imagenProv;
							//imageUrl = "http://embeddedlapps.com/subastas/"+imagenProv;
							if(Integer.valueOf(product.getString("comprado"))==1)
							{
								btnComprar.setText("Eliminar de mi lista");
							}
							else{					
										Log.d("nombre","cambio");
								}
						}else{
							Log.d("paso","pas");
							// product with pid not found
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}			
						 }
					        });return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			
			Log.d("finalizando","post");
			// dismiss the dialog once got all details
			
			if(finalizar) 
			{   
				Log.d("finalizando","post");
				pDialog.dismiss();
				finish();
			}
			else
			{
				Log.d("finalizando","post1");
				if(imageUrl.length()>0)
				{					
					Log.d("finalizando","postimg");
					new DownloadImageTask().execute(imageUrl,imageUrl_v);  //para mostrar la imagen
				}
				pDialog.dismiss();
			}

			
			
		}
	}
	
	
	
	
	class DownloadImageTask extends AsyncTask<String, Void, Drawable>
    {

         final ProgressDialog progressDialog = new ProgressDialog(finSubastas.this);

           protected void onPreExecute()
           {
               progressDialog.setTitle("");
               progressDialog.setMessage("Cargando imagen...");
               progressDialog.show();
           }

           //@SuppressWarnings("null")
		protected Drawable doInBackground(String... urls)
           {
			
               Log.d("DEBUG", "drawable");
               int i=0;
               for(i=0;i<urls.length;i++)
               {
            	  // Log.d("DEBUG", "drawable2");
            	   Drawable drawable = downloadImage(urls[i]);
            	  // Log.d("DEBUG", "drawable3");
            	   arrImg[i]= drawable;
               }
               
               return arrImg[1]; 

           }

           protected void onPostExecute(Drawable imagenURL1)
           {
        	   
        	   imgProducto.setImageDrawable(arrImg[0]);
        	   imgVendedor.setImageDrawable(arrImg[1]);
               progressDialog.dismiss();
           }

	
	
	private Drawable downloadImage(String imageUrl)
    {
        try
        {
            URL url = new URL(imageUrl);
            InputStream is = (InputStream)url.getContent();
            return Drawable.createFromStream(is, "src");
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
	
	

}
