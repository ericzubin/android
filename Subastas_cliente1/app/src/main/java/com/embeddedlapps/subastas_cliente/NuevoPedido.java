package com.embeddedlapps.subastas_cliente;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NuevoPedido extends ActionBarActivity {
	
	
	
	//AlertDialog
		final Context context = this;
	//Fuentes
	private Typeface roboto;
	
	private TextView txtSalInit;
	private TextView txtSelOp;
	//private TextView txtNomPieza;
	private TextView txtMarca;
	private TextView txtModelo;
	private TextView txtAno;
	private TextView txtAgFoto;
	private TextView txtDescPieza;

	// Progress Dialog
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	// url to create new product
	private static String url_create_product = "http://embeddedlapps.com/subastas/create_product.php";
	private static String url_fotos_piezas="http://embeddedlapps.com/subastas/up.php";
	private static String carpeta="fotos";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	
	public String piezaName =null;
	public String IdUsr;
	public File currentDirectory;
	
	private EditText editPieza;
	private EditText editDesc;
	private Button btnEnviarSol;
	//private CheckBox checkPiezas;
	private Spinner ItemsSel;
	private Spinner Items;
	private Spinner ItemsModelo;
	private Spinner ItemsAn;
	private Spinner SpinImagen;
	private TextView textImagen;
	private Bitmap bitmap;
	private byte[] dato;
	private String Nompieza;
	public String UsrName =null;
	public String pedido_temp;
	
	
	public int precionado=0;
	public List<String> SpinnerArrayImagen =  new ArrayList<String>();
	private String foto;
	private static int TAKE_PICTURE = 1;
	double aleatorio = 0;
	
	
	public File file;
	public int next_img;  
	private static final int SELECT_PICTURE = 2;
	private String selectedImagePath;
	public String nuevop;
	private ArrayAdapter<String> adapterModelo;
	private List<String> SpinnerModelo;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nuevo_pedido);
		
		
		
		
		//se abre la base de datos sqlite para llenar los spinners de vehiculos 
		final DBAdapter db = new DBAdapter(NuevoPedido.this);
	     db.open();
		Log.d("bd","regresode la bd");
		
		
		//Asignar fuente a Roboto
		roboto = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Medium.ttf");
		//Cambiar fuente a todos los textView
		
		txtSalInit =(TextView) findViewById(R.id.saludoinicial);
		txtSalInit.setTypeface(roboto);
		txtSelOp =(TextView) findViewById(R.id.txtElgOpc);
		txtSelOp.setTypeface(roboto);
		//txtNomPieza =(TextView) findViewById(R.id.txtNomPieza);
		//txtNomPieza.setTypeface(roboto);
		txtMarca =(TextView) findViewById(R.id.txtMarca);
		txtMarca.setTypeface(roboto);
		txtModelo =(TextView) findViewById(R.id.txtModelo);
		txtModelo.setTypeface(roboto);
		txtAno =(TextView) findViewById(R.id.txtAno);
		txtAno.setTypeface(roboto);
		txtAgFoto =(TextView) findViewById(R.id.txtAgFoto);
		txtAgFoto.setTypeface(roboto);
		textImagen =(TextView) findViewById(R.id.txtNoImg);
		textImagen.setTypeface(roboto);
		txtDescPieza =(TextView) findViewById(R.id.txtDescPieza);
		txtDescPieza.setTypeface(roboto);
		
		//checkPiezas = (CheckBox)findViewById(R.id.check_piezasnuevas);
		//checkPiezas.setTypeface(roboto);
		
		btnEnviarSol = (Button)findViewById(R.id.buttonComprar);
		btnEnviarSol.setTypeface(roboto);
		
		
		//se asignan las vistas por el id 
		
		//btnEnviarSol=(Button) findViewById(R.id.btnSendSol);
		editPieza=(EditText) findViewById(R.id.editNombrepieza);
		editPieza.setTypeface(roboto);
		editDesc=(EditText) findViewById(R.id.editDescrip);
		editDesc.setTypeface(roboto);
		
	   
		
		
		
		SharedPreferences preferencias = getSharedPreferences("Datos", MODE_PRIVATE);
		IdUsr=preferencias.getString("IdUsr",""); // obtiene el usuario
		UsrName=preferencias.getString("UsrNombre",""); // obtiene el usuario
		txtSalInit.setText("Bienvenido "+UsrName);
		
		Intent i = getIntent();	
		next_img = i.getIntExtra("next_img",1);
		nuevop=i.getStringExtra("nuevop");
		
		
		aleatorio = new Double(Math.random() * 100).intValue();
		foto = Environment.getExternalStorageDirectory() + "/img"+next_img+".jpg";
		
		/////// para el spinner refacciones o vehiculo completo
		List<String> SpinnerArraySel =  new ArrayList<String>();
	    SpinnerArraySel.add("Refaccion");
	    SpinnerArraySel.add("Vehiculo Completo");
	    ArrayAdapter<String> adapterSel = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SpinnerArraySel);
	    adapterSel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    ItemsSel = (Spinner) findViewById(R.id.spinRefacciones);
	    ItemsSel.setAdapter(adapterSel);

	    ItemsSel.setOnItemSelectedListener(new OnItemSelectedListener()
	    {
	    	@Override
	    	public void onItemSelected(AdapterView<?> parentView, View SelectedItemView, int position, long id)
	    	{
	    		pedido_temp=editPieza.getText().toString();
	    		Log.d("sad",parentView.getItemAtPosition(position).toString());
	    		if(parentView.getItemAtPosition(position).toString()=="Refaccion")
	    		{
	    			editPieza.setText("");
	    			editPieza.setEnabled(true);
	    		}
	    		else
	    		{
	    			editPieza.setText("Compro Auto");
	    			editPieza.setEnabled(false);
	    			//checkPiezas.setEnabled(false);
	    			
	    		}
	    		
	    	}
	    
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
	    });

	    
	    llenarSpinerImagen();
	    ArrayAdapter<String> adapterImagen = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SpinnerArrayImagen);
	    adapterImagen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    SpinImagen = (Spinner) findViewById(R.id.spinImagen);
	    SpinImagen.setAdapter(adapterImagen);    	
    	 SpinImagen.setOnItemSelectedListener(new OnItemSelectedListener()
 	    {
 	    	@Override
 	    	public void onItemSelected(AdapterView<?> parentView, View SelectedItemView, int position, long id)
 	    	{
 	    		pedido_temp=editPieza.getText().toString();
 	    		Log.d("sad",parentView.getItemAtPosition(position).toString());
 	    		if(parentView.getItemAtPosition(position).toString()=="Camara")
 	    		{
 	    			precionado=1;
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
 	    			precionado=1;
 	    			Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,"Seleccione su imagen"), SELECT_PICTURE);
 	    		}
 	    		parentView.setSelection(0);
 	    		//if(precionado==1) llenarSpinerImagen();
 	    		
 	    		
 	    	}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
	    });

				//////////// para el spinner de la marca
    	Cursor c =db.getAllMarcas();
    	Log.d("bd",c.getCount()+"**");
		List<String> SpinnerArray =  new ArrayList<String>();
		if(c.moveToFirst())
        {
        	do
        	{
        		SpinnerArray.add(c.getString(1).toString());
        	}while(c.moveToNext());
        }
			
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SpinnerArray);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    Items = (Spinner) findViewById(R.id.spinMarca);
	    Items.setAdapter(adapter);
	    

	    Items.setOnItemSelectedListener(new OnItemSelectedListener()
	    {
	    	@Override
	    	public void onItemSelected(AdapterView<?> parentView, View SelectedItemView, int position, long id)
	    	{
	    		String marcaSel =parentView.getItemAtPosition(position).toString();
	    		Log.d("cambio",marcaSel);
	    		//db.open();
	        	Cursor c =db.getAllModel(marcaSel);
	    		List<String> SpinnerModelo =  new ArrayList<String>();
	    		Log.d("cambio",parentView.isPressed()+" "+parentView.isFocused());
	    		//if(position!=0)
	    		{
	    		startManagingCursor(c);
	    		if(c.moveToFirst())
	            {
	            	do
	            	{
	            		SpinnerModelo.add(c.getString(1).toString());
	            	}while(c.moveToNext());
	            }
	    		}
	    		// db.close();
	    		ActualizarModelos(SpinnerModelo);
	    		
	    	}
	    
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
	    });
	    
	    /////////  Para el spiner de la modelo
	    SpinnerModelo =  new ArrayList<String>();
	    c =db.getAllModel("Acura"); //extrae los modelos del acura opcion por default
    	Log.d("bd",c.getCount()+"**");
		if(c.moveToFirst())
        {
        	do
        	{
        		SpinnerModelo.add(c.getString(1).toString());
        	}while(c.moveToNext());
        }
			
	    adapterModelo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SpinnerModelo);
	    adapterModelo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    ItemsModelo = (Spinner) findViewById(R.id.spinModelo);
	    ItemsModelo.setAdapter(adapterModelo);

	    /////////  Para el spiner de la anno
	    List<String> SpinnerAn =  new ArrayList<String>();
	    for(int anio=2014;anio>=1980;anio--)
	    {
	    	SpinnerAn.add(String.valueOf(anio));
	    }
	    
	    ArrayAdapter<String> adapterAn = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SpinnerAn);
	    adapterAn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    ItemsAn = (Spinner) findViewById(R.id.spinAno);
	    ItemsAn.setAdapter(adapterAn);
	    
	  //  db.close();
		
	}
    protected void ActualizarModelos(List<String> spinnerModelo) {
    	
    	int reg=spinnerModelo.size();
    	Log.d("entro a actualizar spin","spin modelo "+reg);
    	SpinnerModelo.clear();
    	
    	for(int i=0;i<reg;i++)
    	{
    		SpinnerModelo.add(spinnerModelo.get(i).toString());
    	}
    	ItemsModelo.setAdapter(adapterModelo);//para cautualizar los campos del spinner
	}
	/**
     * helper to retrieve the path of an image URI
     */
    public String getPath(Uri uri) {
            // just some safety built in 
            if( uri == null ) {
                // TODO perform some logging or show user feedback
                return null;
            }
            
            // try to retrieve the image from the media store first
            // this will only work for images selected from gallery
            String[] projection = { MediaStore.Images.Media.DATA };
            Cursor cursor = managedQuery(uri, projection, null, null, null);
            if( cursor != null ){
                int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            }
            // this is our fallback here
            return uri.getPath();
    }
    
    public boolean validaNombrePieza()
	{
		Nompieza=editPieza.getText().toString();
		if(Nompieza.matches("")) return false;	
		return true;	
	}
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		//verifica imagen seleccionada		
		 if (resultCode == RESULT_OK) {
			 editPieza.setText(pedido_temp);
	            if (requestCode == SELECT_PICTURE) {
	                Uri selectedImageUri = data.getData();
	                selectedImagePath = getPath(selectedImageUri);
	                foto=selectedImagePath;
	            }
	        
		 
		 	bitmap = BitmapFactory.decodeFile(foto);
			Bitmap bmpCompressed = Bitmap.createScaledBitmap(bitmap, 640, 480, true);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			
			bmpCompressed.compress(CompressFormat.PNG, 100, bos);
			dato = bos.toByteArray();
		 
		 	file = new File(foto);
			if (file.exists())	textImagen.setText(file.getName());
			//else Toast.makeText(getApplicationContext(), "No se encontro la imagen", Toast.LENGTH_SHORT).show();
		 }
  	  }
	
	
	public void llenarSpinerImagen()
	{
		SpinnerArrayImagen.clear();
	//if(precionado==0)	
		SpinnerArrayImagen.add("Seleccione una imagen");		
	SpinnerArrayImagen.add("Camara");
    SpinnerArrayImagen.add("Galeria");    
	}
	
	public void buscarImagen(View view)
	{
		Log.d("hloa","paso a buscar");
		File file[] = Environment.getExternalStorageDirectory().listFiles();// .listFiles();  
		for(int i=0;i<file.length;i++){
		Log.d("hloa",file[i].toString());// .getAbsolutePath().toString());
		}
		//File files2[]=file[5].listFiles();
		Log.d("obtubo",file[8].toString());
		File dir=(File)file[8];
		File files2[]=dir.listFiles();
		for(int i=0;i<files2.length;i++){
			Log.d("hloa",files2[i].toString());// .getAbsolutePath().toString());
			}
	}
	
	public void Send_Solicitud(View vieww) //declarado en la vista del boton en el archivo xml
	{
		if(validaNombrePieza())
		{
		
			if(isOnline()) new CreateNewProduct().execute();
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
		
		
		}else Toast.makeText(getApplicationContext(), "se requiere de un nombre de la pieza", Toast.LENGTH_SHORT).show();
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
	/**
	 * Background Async Task to Create new product
	 * */
	class CreateNewProduct extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(NuevoPedido.this);
			pDialog.setMessage("Generando la solicitud espere un momento..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {
			String imagen = " ";
			Log.d("accion", "anteselse");
			//file = new File(foto);
			Log.d("accion", "textImagen.getText().toString()");
			if(textImagen.getText().toString().equals("No hay imagen"))
			{
				Log.d("accion", "iff");
				imagen = "No hay imagen";
				//Toast.makeText(getApplicationContext(), "No se agregara imagen", Toast.LENGTH_SHORT).show();
			}
			else
			{
				Log.d("accion", "existe1");
				if (file.exists()) 
				{
					Log.d("accion", "existe");
					imagen = "fotos/"+IdUsr+"/"+IdUsr+"_"+next_img+".jpg";
				} 
			}
			
			String pieza = editPieza.getText().toString();
			if(pieza.toUpperCase().equals("VEHICULO")) pieza="Compro auto";
			if(!editPieza.isEnabled()) pieza="Compro auto";
			
			
			Log.d("accion", "datosstring");
			String solicitante=IdUsr.toString();
			
			String marca = Items.getSelectedItem().toString();
			String modelo = ItemsModelo.getSelectedItem().toString();;
			String anno = ItemsAn.getSelectedItem().toString();
			String nueva = "0";
			String descripcion = editDesc.getText().toString();
			String id_solicitante = IdUsr;
			
			//verifica si solo se requieren piezas nuevas
			//if(checkPiezas.isChecked()) nueva="1";
			Log.d("Create Response", "paso datos");

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("pieza", pieza));
			params.add(new BasicNameValuePair("marca", marca));
			params.add(new BasicNameValuePair("modelo", modelo));
			params.add(new BasicNameValuePair("anno", anno));
			params.add(new BasicNameValuePair("imagen", imagen));
			params.add(new BasicNameValuePair("nueva", nueva));
			params.add(new BasicNameValuePair("descripcion", descripcion));
			params.add(new BasicNameValuePair("solicitante", solicitante));
			params.add(new BasicNameValuePair("id_solicitante", id_solicitante));
			
			
			Log.d("Create Response", "paso lista");

			// getting JSON Object
			// Note that create product url accepts POST method
			if(nuevop.equals("yes"))  //verifica que se haya pasado por elboton nuevo
			{
				nuevop="yes1";
				JSONObject json = jsonParser.makeHttpRequest(url_create_product,"POST", params);
				
				// check log cat fro response
				Log.d("Create Response", json.toString());
	
				// check for success tag
				try {
					int success = json.getInt(TAG_SUCCESS);
	
					if (success == 1) {
						// successfully created product
					//	Intent i = new Intent("com.embeddedLapps.MisSubastas");
					//	startActivity(i);					
						// closing this screen
					//	finish();
					} else {
						// failed to create product
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			pDialog.dismiss();

			if(textImagen.getText().toString().equals("No hay imagen"))
			{
				Log.d("accion", "no hay imagen que subir");
				Intent i = new Intent("com.embeddedLapps.MisSubastas");
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);

//				finish();
				//Toast.makeText(getApplicationContext(), "No se agregara imagen", Toast.LENGTH_SHORT).show();
			}else
			{
				if (file.exists())
				{
					Log.d("accion", "existe");
					UploaderFoto nuevaTarea = new UploaderFoto(NuevoPedido.this); //se envia el context
					String nameImag=IdUsr+"_"+Integer.toString(next_img);
					if(nuevop.equals("yes1")) //si
					{
						nuevop="no";
						nuevaTarea.execute(dato,url_fotos_piezas.getBytes(),nameImag.getBytes(),carpeta.getBytes(),IdUsr.getBytes(),foto.getBytes());
					}
					else
						{

						//finish();
						Intent i = new Intent("com.embeddedLapps.MisSubastas");
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(i);
						}
				
				}
			}
			// dismiss the dialog once done
			
		}

	}

/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/

	
}
