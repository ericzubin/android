package com.embeddedlapps.subastas_cliente;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

class UploaderFoto extends AsyncTask<byte[], Void, Void>{

	ProgressDialog pDialog2;
	byte[] miFoto ;//= "";
	Context subir_archivo; 
	//Activity act;
	int prev=0;  
	Comprar act;
	NuevoPedido act2;
	private String foto;
	
	//public UploaderFoto(Context subir_recibos) {
		// TODO Auto-generated constructor stub
	//	subir_archivo= subir_recibos;
		
		
	//}

	public UploaderFoto(Comprar comprar) {
		subir_archivo= comprar;// TODO Auto-generated constructor stub
		act=comprar;
		prev=1;
	}

	public UploaderFoto(NuevoPedido nuevoPedido) {
		subir_archivo= nuevoPedido;// TODO Auto-generated constructor stub
		act2=nuevoPedido;
		prev=2;
	}

	protected void onPreExecute() {
		super.onPreExecute();
		pDialog2 = new ProgressDialog(subir_archivo);
        pDialog2.setMessage("Subiendo la imagen, espere." );
        pDialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog2.setCancelable(true);
        pDialog2.show();
	}
	
	protected Void doInBackground(byte[]... params) {
		miFoto = params[0];//recive la foto
		foto=new String(params[5]);//.toString();
		
		for(int i=1;i<params.length;i++)
		{
			Log.d("param_"+i, new String(params[i]));
		}
		
		try { 
			Log.d("entro","Entro");
			
			String nameImag=new String(params[2]);//recibe el  nombre del archivo
			String DirUrl=new String (params[1]);//recibe el url del archivo php donde se realizaran las operaciones
			String carpeta=new String (params[3]); ////recibe la carpeta donde guardara las imagenes
			String UsrId=new String (params[4]);
			
			HttpClient httpclient = new DefaultHttpClient();
			httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
			HttpPost httppost = new HttpPost(DirUrl);
			Log.d("entro","htpost creado url:"+DirUrl);

			//File file = new File(miFoto);
			//file.renameTo(new File (Environment.getExternalStorageDirectory() + IdUsr+ aleatorio +".jpg"));
			MultipartEntity mpEntity = new MultipartEntity();
			Log.d("entro","multipart");
			//ContentBody foto = new FileBody(file, "image/jpeg");
			//foto. .writeTo("name","hola.jpj");
			mpEntity.addPart("fotoUp", new ByteArrayBody(miFoto, foto));

			//mpEntity.addPart("fotoUp", foto);
			Log.d("entro","htpost addpart");
			//params[1]=IdUsr;
			httppost.setHeader("nombreid", nameImag+".jpg"); //envia el nombre de la imagen
			httppost.setHeader("userid", UsrId); //envia el nombre de la imagen
			httppost.setHeader("transfer", carpeta); //envia el nombre de la carpeta donde se guardara las imagenes
			Log.d("entro","htpost header");
			httppost.setEntity(mpEntity);
			Log.d("entro","htpost header");
			HttpResponse response =httpclient.execute(httppost);
			Log.d("entro","htpost execute");
			try{
			JSONObject obj = null;
			String jsonResult = inputStreamToString(response.getEntity().getContent()).toString();  
			Log.d("entro","result json");
			obj = new JSONObject(jsonResult);
			Log.d("Create Response", obj.toString());
			}
			catch (JSONException e) {
				e.printStackTrace();
			}			
			
			
			httpclient.getConnectionManager().shutdown();
			Log.d("entro","close conection imagenes");
			
			//Intent i = new Intent("com.embeddedLapps.MisSubastas");
			//startActivity(i);
			
			//subir_archivo.finish();
			//file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		pDialog2.dismiss();
		Intent i = new Intent("com.embeddedLapps.MisSubastas");
		
		if(prev==1)
		{
			act.finish();
			act.startActivity(i);
			}
		if(prev==2)
		{
			act2.startActivity(i);
			act2.finish();
		}
		
		//finish();
	}
	 
	

	private StringBuilder inputStreamToString(InputStream is) {
         String rLine = "";
         StringBuilder answer = new StringBuilder();
         BufferedReader rd = new BufferedReader(new InputStreamReader(is));

         try {
          while ((rLine = rd.readLine()) != null) {
           answer.append(rLine);
            }
         }

         catch (IOException e) {
             e.printStackTrace();
          }
         return answer;
        }

	//public void execute(byte[] dato, String url, String nameImag, String carpeta, String idUsr, String foto2) {
		// TODO Auto-generated method stub
		
	//}

	

	
}