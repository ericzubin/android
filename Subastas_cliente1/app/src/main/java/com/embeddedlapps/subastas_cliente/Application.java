package com.embeddedlapps.subastas_cliente;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SignUpCallback;

import org.apache.http.ParseException;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Application extends android.app.Application {

    public Application() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the Parse SDK.
        Parse.initialize(this, "FpPXHdgWc7XHoCnktm2eLHODLRKQbaq6B4hZFwcV", "fdwlJSyO9QAyapI8tLtAnJ4JFEbQ2LMEZlCJUzRu");

        // Specify an Activity to handle all pushes by default.
        PushService.setDefaultPushCallback(this, MisSubastas.class);
        // Save the current Installation to Parse.
        ParseInstallation.getCurrentInstallation().saveInBackground();





        //Se crea el usuario aqui
        String name="usuario1";
        String passw="pass";
        String usuario=name;
        String pass=passw;

        String email="usuario1f@example.com";
        ParseUser user = new ParseUser();
        user.setUsername(usuario);
        user.setPassword(pass);
        user.setEmail(email);
// other fields can be set just like with ParseObject
        // user.put("phone", "650-253-0090");

        user.signUpInBackground(new SignUpCallback() {

            @Override
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });

        try {
            //Esta parte nos vicula nuestro dispositivo con nuestra cuenta
            ParseInstallation installation = ParseInstallation.getCurrentInstallation();
            installation.put("Usuarios", ParseUser.getCurrentUser());
            installation.saveInBackground();
        }catch (Exception e)
        {

        };




//Busqueda del usuario
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", "usuario1");
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> list, com.parse.ParseException e) {
                if (e == null) {

                    Log.d("objectId ", "Retrieved " + list.get(0).getObjectId());
                    SendPushTO(list.get(0).getObjectId());
                } else {
                    Log.d("objetID", "Error: " + e.getMessage());

                }
                // idOBjet = list.get(0).getObjectId().toString();
            }


        });







    }
    public void SendPushTO(String usuario)
    {
        //  Esta parte debe de tener un usuario que exista o si no crashea
        ParseQuery pushQuery = ParseInstallation.getQuery();
        pushQuery.whereEqualTo("Usuarios", usuario);

// Send push notification to query
        ParsePush push = new ParsePush();
        push.setQuery(pushQuery); // Set our Installation query
        push.setMessage("Willie Hayes injured by own pop fly.");
        push.sendInBackground();


// Send push notification to query

    }
}