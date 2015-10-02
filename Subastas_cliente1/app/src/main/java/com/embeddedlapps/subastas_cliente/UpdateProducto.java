package com.embeddedlapps.subastas_cliente;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONException;
import org.json.JSONObject;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;




import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



class UpdateProducto extends AsyncTask<String, String, String> {
	finSubastas act;
	Context ctx;
	ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	

	// url to create new product
	private static String url_update_product="http://embeddedlapps.com/subastas/update_product.php";
	//private static String url_fotos_piezas="http://embeddedlapps.com/subastas/up.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	
	public UpdateProducto(finSubastas finSubastas) {
		ctx=finSubastas;
		act=finSubastas;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Before starting background thread Show Progress Dialog
	 * */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = new ProgressDialog(ctx);
		pDialog.setMessage("Eliminando de su lista espere un momento..");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
		pDialog.show();
	}

	/**
	 * Creating product
	 * */
	protected String doInBackground(String... params2) {
		
		Log.d("Create Response", "paso11");	
		for(int i=0;i<params2.length;i++)
		{
			Log.d("params"+i,params2[i]);
		}
		String UsrId=params2[0];
		String pId=params2[1];
		String campo=params2[2];
		
		// Building Parameters
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("usrid",UsrId.toString()));
		params.add(new BasicNameValuePair("pid", pId.toString()));
		params.add(new BasicNameValuePair("campo", campo.toString()));
		
		
		Log.d("Create Response", "paso lista");

		// getting JSON Object
		// Note that create product url accepts POST method
		final JSONObject json = jsonParser.makeHttpRequest(url_update_product,"POST", params);
		
		
		// check log cat fro response
		Log.d("Create Response", json.toString());

		// check for success tag
		try {
			int success = json.getInt(TAG_SUCCESS);

			if (success == 1) {
				// successfully created product
				//Intent i = new Intent("com.embeddedLapps.MisSubastas");
				//act.startActivity(i);					
				// closing this screen
				//act.finish();
			} else {
				// failed to create product
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
		
		pDialog.dismiss();

		Intent i = new Intent("com.embeddedLapps.MisSubastas");
		act.startActivity(i);					
		act.finish();

	}

}



