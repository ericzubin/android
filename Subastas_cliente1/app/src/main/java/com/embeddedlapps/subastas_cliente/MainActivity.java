package com.embeddedlapps.subastas_cliente;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {

	public SharedPreferences preferencias;
	public String UsrName =null;
	public String UsrCel =null;
	public String UsrEmail =null;
	public String IdUsr=null;
	
	public static final String usuario_VALUE_KEY = "Usuario";
	private EditText editName;
	private EditText editCel;
	private EditText editEmail;
	private CheckBox politicas;
	private ImageButton btnIngresar;
	private Toast Msg_acepPoliticas;
	private Toast Msg_camposObli_tel;
	private Toast Msg_camposObli_nom;
	int request_Code = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.d("Paso0", "onCreateView");
		//se asignan las vistas por el id 
		btnIngresar=(ImageButton) findViewById(R.id.btnIngresar);
		editName=(EditText) findViewById(R.id.editNombre);
		editCel=(EditText) findViewById(R.id.editCel);
		editEmail=(EditText) findViewById(R.id.editEmail);
		politicas= (CheckBox) findViewById(R.id.checkPoliticas);
		
		Msg_acepPoliticas=Toast.makeText(this,"Se requiere aceptar las politicas de privacidad",Toast.LENGTH_LONG);
		Msg_camposObli_tel=Toast.makeText(this," Se requiere de un numer celular 10 digitos",Toast.LENGTH_SHORT);
		Msg_camposObli_nom=Toast.makeText(this," Se requiere un nombre valid mayor a 3 caracteres",Toast.LENGTH_SHORT);
				
		//uso de SharedPreferences para gurdar datos y que permanezcan al reabrir la aplicacion
		SharedPreferences preferencias = getSharedPreferences("Datos", MODE_PRIVATE);
		//	preferencias.edit().clear().commit(); //limpia los valores guardados
		//Log.d("Paso2", "numero"+getPhoneNumber());
		UsrName=preferencias.getString("UsrNombre",""); // obtiene el usuario
		Log.d("Paso2", "hola"+UsrName);
		if(UsrName.matches("")) //si no existe nombre no hace nada 
			{
			  //obtiene el numero del cel
		//	editName.setText(getPhoneNumber(), TextView.BufferType.EDITABLE);
			//editCel.setEnabled(false);
			}
		else{ //si existe algun nombre ira a la vista MisSubastas
			
			Log.d("Paso1", "onCreateView");
			iraMisSubastas();
			}
		Log.d("Paso8", "onCreateView");
	}
	
	public void IngresarSistema(View vieww) //declarado en la vista del boton en el archivo xml
	{
		//verifica se hayan ingresado los datos correctamente
		if(validaNombre())
		{		
			//if(validaEmail())
			//{
				if(validaCel())
				{
					if(validaPoliticas())
					{
						guardarDatos();
						iraMisSubastas();
					} else Msg_acepPoliticas.show(); //muestra mensaje de faltan politicas
				}else Msg_camposObli_tel.show();//muestra mensaje campos obligatorios
			//}else Msg_camposObli.show(); //muestra mensaje campos obligatorios 
		}else Msg_camposObli_nom.show(); //muestra mensaje campos obligatorios
	}
	
	public boolean validaNombre()
	{
		UsrName=editName.getText().toString();
		String UsrNameTemp ="no se ingreso nada correctamente MOyaCoNradO";
		String UsrNameTemp_o=UsrNameTemp;
		int i=0,letras=0;
		
		if(UsrName.matches("")) 
		{
			return false;
			//UsrName	=UsrNameTemp;
		}
		else
		{
			//valida cada caracterer
			for(i=0;i<UsrName.length()-1;i++)
			{
				int val=(int)UsrName.charAt(i); //convierte al valor en codigo asccii
				int val_sig=(int)UsrName.charAt(i+1);
				if(validaChar(val)) //si el caracter es valido				
				{
					//si el caracter proviene de espacios iniciales en blanco o si es el primer caracter
					if(i==0 || letras==0) UsrNameTemp=String.valueOf(UsrName.charAt(i));
					else UsrNameTemp=UsrNameTemp+UsrName.charAt(i); //se forma el nuevo String
					letras=1;  // se activa que ya hubo letras validas
					
				}
				else
				{
					if(validaChar(val_sig))  //se verifica si el siguiente caracter es caracter valido
					{
						//si ya hay letras previas se cambia el caracter invalido por un _
						 if(letras==1) UsrNameTemp=UsrNameTemp+'_'; //verifica que haya un caracter valido
					}
				}
				//Log.d("char "+i+": ",UsrName.charAt(i)+" "+"--"+UsrNameTemp+"  ascii: "+val);
			}
			
			//verificando que el ultimo caracter sea uno valido
			if( validaChar(UsrName.charAt(i)))
			{
				UsrNameTemp=UsrNameTemp+UsrName.charAt(i);
			}
			
			UsrName=UsrNameTemp; //se asigna el valor del string resultante a UsrName
		}
		
		// se verifica que la cadena valida sea mayor a 3 letras
		if(UsrName==UsrNameTemp_o) return false;
		else{
			if(UsrName.length()<3) return false;
			else return true;
		}
		
		
	}
	
	//valida si el caracter es valido
	public boolean validaChar(int c)
	{
		int val=c;
		
		if((val>47 && val<58)  || (val>64 && val<91)   ||  (val>96 && val<123)  || 
				val==241 ||  val==225 || val==233 || val==237 || val==243 || val==250   ||  //minuscuas  y vocales acentuadas a-u
				val==209  || val==193 || val==201 || val==205 || val==211 || val==218)
		{
			return true;
		}
		else return false;
	}
	
	
	public boolean validaCel()
	{
		
		UsrCel=editCel.getText().toString();
		
		int i=0;
		int val;
		if(UsrCel.matches("")) return false;
		else
		{
			if(UsrCel.length()>10 || UsrCel.length()<10)
			{
			  	return false;
			}
			else
			{
			 for(i=0;i<UsrCel.length();i++)
			 	{
					val=(int)UsrCel.charAt(i); //convierte al valor en codigo asccii
					if((val>47 && val<58))
					{}
					else return false;
			    }
			return true;
			}
		}
		
	}
	
	public boolean validaEmail()
	{
		Log.d("PasoVal", "validaEmail");
		UsrEmail=editEmail.getText().toString();
		if(UsrEmail.matches("")) return false;
		return true;
	}
	
	public boolean validaPoliticas()
	{
		Log.d("PasoVal", "validaPol");
		if(politicas.isChecked()) return true;
		return false;
	}


	public void guardarDatos()
	{
		
		//Guarda el Saldo Restante
		preferencias = getSharedPreferences("Datos", MODE_PRIVATE);
		SharedPreferences.Editor editor = preferencias.edit();
		editor.putString("UsrNombre",UsrName);
		editor.putString("UsrCel",UsrCel);
		editor.putString("UsrEmail",UsrEmail);
		editor.putString("IdUsr",UsrName+UsrCel);
		editor.commit();
	
	}
	
	public void iraMisSubastas(){
		Log.d("fua asubastas","fue");
	 Intent i = new Intent("com.embeddedLapps.MisSubastas");
	 startActivity(i);
	}
	
	public String getPhoneNumber(){
		Log.d("entro","cel");
		  TelephonyManager mTelephonyManager;
		  mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
	
		  // System.out.println(mTelephonyManager.getAllCellInfo()); 
		  //Log.("cel:",mTelephonyManager.getAllCellInfo());	  
		  return mTelephonyManager.getLine1Number();
		}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
		SharedPreferences preferencias = getSharedPreferences("Datos", MODE_PRIVATE);
		UsrName=preferencias.getString("UsrNombre",""); // obtiene el usuario
		Log.d("Paso2", "hola"+UsrName);
		if(UsrName.matches("")) //si no existe nombre no hace nada 
			{
			
			}
		else{ //si existe algun nombre ira a la vista MisSubastas
			Log.d("PasoFin", "finalizar en resume");
			super.finish();
			}	
	}
	
	

	
}
