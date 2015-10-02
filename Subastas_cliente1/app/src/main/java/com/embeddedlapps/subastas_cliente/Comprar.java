package com.embeddedlapps.subastas_cliente;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.embeddedlapps.subastas_cliente.NuevoPedido.CreateNewProduct;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.ColorDrawable;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class Comprar extends ActionBarActivity {
	
	private Date fechaActual;
	
	//AlertDialog
	final Context context = this;
	//Fuentes
		private Typeface roboto;
		
		private TextView txtSalInit;
		private TextView txtCantiDepo;
		private TextView txtRealPago;
		private TextView txtBanco;
		private TextView txtNomBanco;
		private TextView txtPerso;
		private TextView txtNomPer;
		private TextView txtNumCuenta;
		private TextView txtCuenta;
		private TextView txtClabe;
		private TextView txtClabeBanco;
		private TextView txtAnexCom;
		private TextView txtRFC;
		private TextView txtFacDirec;
		private TextView txtEmail;
		private TextView txtDatosEntrega;
		private TextView txtConfirPago;
		private TextView txtTel1;
		private TextView txtTel2;
		private TextView txtYaRealizoPago;
		
	
	
	
	private ProgressDialog pDialog;
	JSONParser jParser = new JSONParser();
	// url to create new product
	private static String url_all_products = "http://embeddedlapps.com/subastas/get_all_products.php";
	private static String url_datos_clientes = "http://embeddedlapps.com/subastas/get_datos_clientes.php";
	
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCTS = "products";
	private static final String TAG_PID = "pid";
	
	public String IdUsr;
	public String pid;
	public String precio;
	public String pieza;
	public String nameImag;
	public String subiImg="no";
	public int cambio=0;
	public UploaderFoto nuevaTarea;
	//products JSONArray
	JSONArray products = null;
	
	public List<String> SpinnerArrayImagen =  new ArrayList<String>();
	public SharedPreferences preferencias;
	public String UsrName =null;
	public static final String usuario_VALUE_KEY = "Usuario";
	private TextView texPrecio;
	private TextView texPieza;
	private TextView textRecibo;
	private Button btnComprar;
	//private Button btnExaminar;
	//private CheckBox chkAnticipo;
//	private CheckBox chkDirEntrega;
	private CheckBox chck_pagoTotal;
	private CheckBox chkFactura;
	private EditText editRFC;
	private EditText editDireFiscal;
	private EditText editEmail;
	private EditText editDirEntrega;
	private Spinner SpinImagenR;
	public int precionado;
	private String foto;
	private static int TAKE_PICTURE = 1;
	double aleatorio = 0;
	private static final int SELECT_PICTURE = 2;
	private String selectedImagePath;
	public File file;
	private float adelanto;
	private Bitmap bitmap;
	private byte[] dato;
	
	private String Dir_entrega_a;
	public static boolean subirImagen = false;

	
	JSONParser jsonParser = new JSONParser();
	// url to create new product
	private static String url_compra_product = "http://embeddedlapps.com/subastas/compra_product.php";
	private static String url_recibos_pago="http://embeddedlapps.com/subastas/up.php";
	private static String carpeta="recibos";
	private int reinicio=0;
	
	private int validarCampos=0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recibo_pago);
		Log.d("entro a","recibo de pago");
		
		fechaActual = new Date();
	
		
		
		//Asignar fuente a Roboto
		roboto = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Medium.ttf");
		//Cambiar fuente a todos los textView
		
		txtSalInit =(TextView) findViewById(R.id.saludoinicial);
		txtSalInit.setTypeface(roboto);
		
		//se asignan las vistas por el id 
		btnComprar=(Button) findViewById(R.id.buttonComprar);
		btnComprar.setTypeface(roboto);
		//btnExaminar=(Button) findViewById(R.id.btnExaminarR);
		texPieza=(TextView) findViewById(R.id.textNpiezaR);
		texPieza.setTypeface(roboto);
		texPrecio=(TextView) findViewById(R.id.textCostoR);
		texPrecio.setTypeface(roboto);
	//	chkAnticipo=(CheckBox) findViewById(R.id.check_anticipo);
	//	chkAnticipo.setTypeface(roboto);
	//	chkDirEntrega=(CheckBox) findViewById(R.id.ChkDatosR);
	//	chkDirEntrega.setTypeface(roboto);
		chkFactura=(CheckBox) findViewById(R.id.chkFactR);
		chkFactura.setTypeface(roboto);
		editDireFiscal=(EditText) findViewById(R.id.editDirFiscR);
		editDireFiscal.setTypeface(roboto);
		editRFC=(EditText) findViewById(R.id.editRFC);
		editRFC.setTypeface(roboto);
		editEmail=(EditText) findViewById(R.id.editEmailR);
		editEmail.setTypeface(roboto);
		editDirEntrega=(EditText) findViewById(R.id.editDirEntregaR);
		editDirEntrega.setTypeface(roboto);
		SpinImagenR = (Spinner) findViewById(R.id.spinExaminarR);
		textRecibo=(TextView) findViewById(R.id.textRecibo);
		textRecibo.setTypeface(roboto);
		chck_pagoTotal=(CheckBox) findViewById(R.id.check_pagoTotal);
		chck_pagoTotal.setTypeface(roboto);
		txtConfirPago = (TextView) findViewById(R.id.txtConfirPago);
		txtConfirPago.setTypeface(roboto);
		txtTel1 = (TextView) findViewById(R.id.txtTel1);
		txtTel1.setTypeface(roboto);
		txtTel2 = (TextView) findViewById(R.id.txtTel2);
		txtTel2.setTypeface(roboto);
		txtYaRealizoPago = (TextView) findViewById(R.id.txtYaRealizoPago);
		txtYaRealizoPago.setTypeface(roboto);
		
		//Cambiar fuente a todos los textviews :(
		
		//txtCantiDepo=(TextView) findViewById(R.id.txtCantiDepo);
		txtRealPago=(TextView) findViewById(R.id.txtRealPago);
		txtBanco=(TextView) findViewById(R.id.txtBanco);
		txtNomBanco=(TextView) findViewById(R.id.txtNomBanco);
		txtPerso=(TextView) findViewById(R.id.txtPerso);
		txtNomPer=(TextView) findViewById(R.id.txtNomPer);
		txtNumCuenta=(TextView) findViewById(R.id.txtNumCuenta);
		txtCuenta=(TextView) findViewById(R.id.txtCuenta);
		txtClabe=(TextView) findViewById(R.id.txtClabe);
		txtClabeBanco=(TextView) findViewById(R.id.txtClabeBanco);
		txtAnexCom=(TextView) findViewById(R.id.txtAnexCom);
		txtRFC=(TextView) findViewById(R.id.txtRFC);
		txtFacDirec=(TextView) findViewById(R.id.txtFacDirec);
		txtEmail=(TextView) findViewById(R.id.txtEmail);
		txtDatosEntrega=(TextView) findViewById(R.id.txtDatosEntrega);
		
		//txtCantiDepo.setTypeface(roboto);
		txtRealPago.setTypeface(roboto);
		txtBanco.setTypeface(roboto);
		txtNomBanco.setTypeface(roboto);
		txtPerso.setTypeface(roboto);
		txtNomPer.setTypeface(roboto);
		txtNumCuenta.setTypeface(roboto);
		txtCuenta.setTypeface(roboto);
		txtClabe.setTypeface(roboto);
		txtClabeBanco.setTypeface(roboto);
		txtAnexCom.setTypeface(roboto);
		txtRFC.setTypeface(roboto);
		txtFacDirec.setTypeface(roboto);
		txtEmail.setTypeface(roboto);
		txtDatosEntrega.setTypeface(roboto);
		
		
		//ocultando datos de rfc
		txtRFC.setVisibility(View.GONE);
		txtEmail.setVisibility(View.GONE);
		txtFacDirec.setVisibility(View.GONE);
		editRFC.setVisibility(View.GONE);
		editDireFiscal.setVisibility(View.GONE);
		editEmail.setVisibility(View.GONE);

		
		//Colocar nombre de la persona
		SharedPreferences preferencias = getSharedPreferences("Datos", MODE_PRIVATE);
		IdUsr=preferencias.getString("IdUsr",""); // obtiene el usuario
		UsrName=preferencias.getString("UsrNombre",""); // obtiene el usuario
		txtSalInit.setText("Bienvenido "+UsrName);

		
		Intent i = getIntent();	
		pid = i.getStringExtra("pid");
		precio = i.getStringExtra("precio");
		pieza = i.getStringExtra("pieza");
		IdUsr=i.getStringExtra("id_usr");
		
		texPieza.setText(pieza);
		texPrecio.setText("Cantidad a depositar $ "+precio);
		adelanto=Float.parseFloat(precio);
		//adelanto=(float) (adelanto*.3);
		//adelanto=Math.round(adelanto);
	//	chkAnticipo.setText(":$"+adelanto);
		
		//desactivando elementos 
		SpinImagenR.setEnabled(false);
		//editRFC.setEnabled(false);
		//editDireFiscal.setEnabled(false);
		//editEmail.setEnabled(false);

		
		foto = Environment.getExternalStorageDirectory() + "/recibo"+pid+".jpg";
		
		//para el spiner de las imagenes
		llenarSpinerImagen();
	    ArrayAdapter<String> adapterImagen = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SpinnerArrayImagen);
	    adapterImagen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);	    
	    SpinImagenR.setAdapter(adapterImagen);   
    	 SpinImagenR.setOnItemSelectedListener(new OnItemSelectedListener()
 	    {
    		 

 	    	@Override
 	    	public void onItemSelected(AdapterView<?> parentView, View SelectedItemView, int position, long id)
 	    	{
 	    		Log.d("sad",parentView.getItemAtPosition(position).toString()); 	    		 	    		
 	    		if( parentView.getItemAtPosition(position).toString()=="Camara" )
 	    		{
 	    			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
 					/*
 					 * Creamos un fichero donde guardaremos la foto
 					 */
 					Uri output = Uri.fromFile(new File(foto));
 					intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
 					/*
 					 * Lanzamos el intenta y recogemos el resultado en onActivityResult
 					 */		
 					startActivityForResult(intent, TAKE_PICTURE); // 1 para la camara, 2 para la galeria
 					
 	    		}
 	    		if(parentView.getItemAtPosition(position).toString()=="Galeria")
 	    		{ 	    			
 	    			Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,"Seleccione su imagen"), SELECT_PICTURE);
 	    		}
 	    		parentView.setSelection(0);  //regresamos el spiner a la opcion seleccione una opcion
 	    		
 	    	}
 	    	
 	    	
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
	    });
	}

	
	public void facturar(View view)
	{
		if(chkFactura.isChecked())
		{
			txtRFC.setVisibility(View.VISIBLE);
			txtEmail.setVisibility(View.VISIBLE);
			txtFacDirec.setVisibility(View.VISIBLE);
			
			editRFC.setVisibility(View.VISIBLE);
			editDireFiscal.setVisibility(View.VISIBLE);
			editEmail.setVisibility(View.VISIBLE);
			editRFC.setEnabled(true);
			editDireFiscal.setEnabled(true);
			editEmail.setEnabled(true);
		}
		else
		{
			txtRFC.setVisibility(View.GONE);
			txtEmail.setVisibility(View.GONE);
			txtFacDirec.setVisibility(View.GONE);

			editRFC.setVisibility(View.GONE);
			editDireFiscal.setVisibility(View.GONE);
			editEmail.setVisibility(View.GONE);
			editRFC.setEnabled(false);
			editDireFiscal.setEnabled(false);
			editEmail.setEnabled(false);
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		Log.d("debuj",String.valueOf(resultCode) );
		//verifica imagen seleccionada		
		 if (resultCode == RESULT_OK) {
	            if (requestCode == SELECT_PICTURE) {
	                Uri selectedImageUri = data.getData();
	                selectedImagePath = getPath(selectedImageUri);
	                foto=selectedImagePath;
	                
	            }
		 
		 	bitmap = BitmapFactory.decodeFile(foto);
			Bitmap bmpCompressed = Bitmap.createScaledBitmap(bitmap, 640, 480, true);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			
			bmpCompressed.compress(CompressFormat.JPEG, 100, bos);
			dato = bos.toByteArray();
			
		 	file = new File(foto);
			if (file.exists())	textRecibo.setText(file.getName());
		 }
		 
			SpinImagenR.setEnabled(true);	 		
		//	if(chck_pagoTotal.isChecked())	cambio=2;//establece que se pago el total
		//	if(chkAnticipo.isChecked()) cambio=1;   //establece que solo pago parcial realiado
		 
	  }
	
	public void llenarSpinerImagen()
	{
	SpinnerArrayImagen.clear();
	SpinnerArrayImagen.add("Seleccione una imagen");		
	SpinnerArrayImagen.add("Camara");
    SpinnerArrayImagen.add("Galeria");    
	}
	
	
	public String getPath(Uri uri) {
		Log.d("entro","10");
        // just some safety built in 
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        Log.d("entro","11");
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        Log.d("entro","12");
        if( cursor != null ){
            int column_index = cursor
            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            Log.d("entro","14");
            return cursor.getString(column_index);
        }
        Log.d("entro","13");
        // this is our fallback here
        return uri.getPath();
}
	
/*	public void usarDatosEntrega(View view)
	{
		if(chkDirEntrega.isChecked())
		{
			new ObtenerDatosAlmacenados().execute();
			//editDirEntrega.setText("esta es mi direccion");
			editDirEntrega.setEnabled(false);
		}
		else
		{
			editDirEntrega.setText(" ");
			editDirEntrega.setEnabled(true);
		}
	}
*/	
	public void pagoTotalRelaizado(View view)
	{
		if(chck_pagoTotal.isChecked()) {
			textRecibo.setVisibility(View.VISIBLE);
			txtAnexCom.setVisibility(View.VISIBLE);
			SpinImagenR.setVisibility(View.VISIBLE);
			SpinImagenR.setEnabled(true);
		}
		else  {
			textRecibo.setVisibility(View.GONE);
			txtAnexCom.setVisibility(View.GONE);
			SpinImagenR.setVisibility(View.GONE);
			SpinImagenR.setEnabled(false);
		}
		//chkAnticipo.setChecked(false);
		
		//Toast.makeText(getApplicationContext(), "Elija la imagen del pago total", Toast.LENGTH_SHORT).show();
		textRecibo.setText("No hay imagen");
	}
	
	/*public void pagoRealizado(View view)
	{
		if(chkAnticipo.isChecked())SpinImagenR.setEnabled(true);
		else SpinImagenR.setEnabled(false);
		chck_pagoTotal.setChecked(false);
		Toast.makeText(getApplicationContext(), "Elija la imagen del anticipo", Toast.LENGTH_SHORT).show();
		textRecibo.setText("No hay imagen");
	}*/
	
	public void Enviar_DatosCompra(View vieww) //declarado en la vista del boton en el archivo xml
	{
		
		Log.d("m","holasa"+editDirEntrega.getText().toString());
		Log.d("m","hola"+editEmail.getText().toString());
		Log.d("m","hol"+editDireFiscal.getText().toString());
		
		if(textRecibo.getText().toString().equals("No hay imagen") )
		{
			Toast.makeText(getApplicationContext(), "Debe enviar un comprobante", Toast.LENGTH_SHORT).show();
			validarCampos=4;
		}
		
		/*if(chkDirEntrega.isChecked()){
			Log.d("m","holasa"+editDirEntrega.getText().toString());
			
		}
		else
		{
			if(editDirEntrega.getText().toString().equals(""))
			{
				Log.d("m","edt1"+editDirEntrega.getText().toString());
				Toast.makeText(getApplicationContext(), "Ingrese la direccion para entrega", Toast.LENGTH_SHORT).show();	
				validarCampos=1;
			}
			else
			{
				Log.d("m","edt2"+editDirEntrega.getText().toString());
			}
			
		}
		*/
		if(chkFactura.isChecked())
		{
			
			if(editRFC.getText().toString().equals(""))
			{
				Toast.makeText(getApplicationContext(), "Ingrese su RFC", Toast.LENGTH_SHORT).show();
				validarCampos=3;
			}

			Log.d("m","edt1fiscal"+editDireFiscal.getText().toString());
			if(editDireFiscal.getText().toString().equals(""))
			{
				Toast.makeText(getApplicationContext(), "Ingrese su direccion fiscal", Toast.LENGTH_SHORT).show();
				validarCampos=2;
			}
			Log.d("m","edt1fiscal"+editRFC.getText().toString());
			Log.d("m","edt1email"+editEmail.getText().toString());
			// valida email para envio de factura electronica
			if(editEmail.getText().toString()==" ")
			{
				Toast.makeText(getApplicationContext(), "Ingrese su Email ", Toast.LENGTH_SHORT).show();	
			}
			
		}
		
	
		
		if(validarCampos==0)
		{
			if(isOnline()) new Subir_recibos().execute();
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
		validarCampos=0; //reiniciando valores
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnected()) {
	        return true;
	    }
	    return false;
	}
	
class Subir_recibos extends AsyncTask<String, String, String> {
		
		private ProgressDialog pDialog;
		
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				pDialog = new ProgressDialog(Comprar.this);
				pDialog.setMessage("Generando la solicitud espere un momento..");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			}

			/**
			 * Creating product
			 * */
			protected String doInBackground(String... args) {
				
				
				//verifica cual de las opciones esta seleccionada
				if(chck_pagoTotal.isChecked())
				{
					
					
				nameImag =IdUsr+"_"+fechaActual.toString();
				//if(cambio==1) nameImag =nameImag+"_adelanto";
				//if(cambio==2) 
					nameImag =nameImag+"_pagado";
				}

				//file = new File(foto);
				if (file.exists()) {
					//nuevaTarea = new UploaderFoto();	//se crea y se envia el context para pdialog
					//Log.d("subiendo","recibo" );
					//nuevaTarea.execute(url_recibos_pago,foto,nameImag,carpeta,IdUsr);
					
					
					//subiImg=nuevaTarea.execute(url_recibos_pago,foto,nameImag,carpeta,IdUsr);
				}
				else Toast.makeText(getApplicationContext(), "No se agregara imagen", Toast.LENGTH_SHORT).show();
				
				String anticipo=String.valueOf(adelanto);
				String rfc ="";
				String dirFiscal = "";
				String email = "";
				String dirEntrega = editDirEntrega.getText().toString();
				String rutaImag = "recibos/"+IdUsr+"/"+nameImag+".jpg";
				String facturar="0";
				
				

				if(chkFactura.isChecked())
					{
					facturar="1";
					rfc =  editRFC.getText().toString();
					dirFiscal = editDireFiscal.getText().toString();
					email=editEmail.getText().toString();
					}
				Log.d("subiendo","factura" );	
				Log.d("onse", "paso datos");

				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("id_solicitante", IdUsr));
				params.add(new BasicNameValuePair("pieza", pieza));
				params.add(new BasicNameValuePair("IdSol", pid));
				params.add(new BasicNameValuePair("costo", precio));
				params.add(new BasicNameValuePair("anticipo",anticipo));
				params.add(new BasicNameValuePair("imagen", rutaImag));
				params.add(new BasicNameValuePair("d_entrega", dirEntrega));
				params.add(new BasicNameValuePair("d_fiscal", dirFiscal));
				params.add(new BasicNameValuePair("d_rfc", rfc));
				params.add(new BasicNameValuePair("email_fiscal", email));
				params.add(new BasicNameValuePair("facturar", facturar));
				
				//Quitar toast porque da error al ejecutar
				//Toast.makeText(getApplicationContext(), "Sus datos seran almacenado para futuros pedidos", Toast.LENGTH_SHORT).show();
				
				Log.d("subiendo","parametros" );
				Log.d("Creat", "paso lista");
				// getting JSON Object
				// Note that create product url accepts POST method
				JSONObject json = jsonParser.makeHttpRequest(url_compra_product,"POST", params);
				Log.d("regreo ","regeso del proceso");
				// check log cat fro response
				Log.d("Create Response", json.toString());

				// check for success tag
				try {
					int success = json.getInt(TAG_SUCCESS);

					if (success == 1) {
						
						// successfully created product
						//Intent i = new Intent("com.embeddedLapps.MisSubastas");
						//startActivity(i);
						
						// closing this screen
						//finish();
					} else {
						// failed to create product
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

				
				Log.d("fin ","fin del proceso");
				return null;
			}

			/**
			 * After completing background task Dismiss the progress dialog
			 * **/
			protected void onPostExecute(String file_url) {
				// dismiss the dialog once done
				pDialog.dismiss();

				Log.d("subiendo","recibo"+url_recibos_pago.getBytes() );
				Log.d("subiendo","recibo"+new String(url_recibos_pago.getBytes()) );
				nuevaTarea = new UploaderFoto(Comprar.this);	//se crea y se envia el context para pdialog
				
				//nuevaTarea.execute(url_recibos_pago,foto,nameImag,carpeta,IdUsr);
				nuevaTarea.execute(dato,url_recibos_pago.getBytes(),nameImag.getBytes(),carpeta.getBytes(),IdUsr.getBytes(),foto.getBytes());
				
	
			}
 }




class ObtenerDatosAlmacenados extends AsyncTask<String, String, String> {
	
	private ProgressDialog pDialog;
	
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Comprar.this);
			pDialog.setMessage("Obteniendo sus datos espere un momento..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {
			
			String datos;
			
			Log.d("onse", "paso datos");

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("IdUsr", IdUsr));			
			Log.d("subiendo",IdUsr);

			JSONObject json = jsonParser.makeHttpRequest(url_datos_clientes,"POST", params);
			Log.d("regreo ",json.toString());
			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);
				Log.d("regreo ",String.valueOf(success));
				
				
				if (success == 1) {
					
					
					
					JSONArray productObj = json.getJSONArray("products"); // JSON Array
					Log.d("paso","pasohhhh");
					// get first product object from JSON Array
					JSONObject product = productObj.getJSONObject(0);
					Log.d("paso","pasohhhh");
									
					
					//editDirEntrega.setEnabled(true);
					String dir_e=product.getString("dir_entrega").toString();
					Log.d("paso",dir_e);
					Dir_entrega_a=product.getString("dir_entrega");
					
			//		editEmail.setText("hola"); //.setText(product.getString("dir_entrega"));
					//editDirEntrega.setEnabled(false);
					
				} else {
					Dir_entrega_a="No hay direccion";
					// failed to create product
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			
			Log.d("fin ","fin del proceso");
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();

			//se estable el valor de la direccion de entrega almacenado
			editDirEntrega.setText(Dir_entrega_a);

		}
}




}
