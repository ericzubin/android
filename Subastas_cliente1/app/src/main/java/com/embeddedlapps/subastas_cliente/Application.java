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
        Parse.initialize(this, "g0p79lQxI51ZETZtwKTCZuqjf1YyxQUZn8Qy1kQb", "tUKgzMsZ09LbLIIdwSZyIH1PVcu24aEcMpxcWH4A");

        // Specify an Activity to handle all pushes by default.
        PushService.setDefaultPushCallback(this, MisSubastas.class);
        // Save the current Installation to Parse.
        ParseInstallation.getCurrentInstallation().saveInBackground();





        //Se crea el usuario aqui
        String name="usuario2";
        String passw="pass";
        String usuario=name;
        String pass=passw;

        String email="usuario2f@example.com";
        ParseUser user = new ParseUser();
        user.setUsername(usuario);
        user.setPassword(pass);
        user.setEmail(email);
// other fields can be set just like with ParseObject
        // user.put("phone", "650-253-0090");

        user.signUpInBackground(new SignUpCallback() {



            public void done(com.parse.ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });

try
{
    //Esta parte nos vicula nuestro dispositivo con nuestra cuenta
    ParseInstallation installation = ParseInstallation.getCurrentInstallation();
    installation.put("Usuarios", ParseUser.getCurrentUser());
    installation.saveInBackground();
}catch (Exception e)
{

}




// Find users near a given location
    ParseQuery userQuery = ParseUser.getQuery();
    userQuery.whereEqualTo("username", "usuario1");

// Find devices associated with these users
    ParseQuery pushQuery = ParseInstallation.getQuery();
    pushQuery.whereMatchesQuery("Usuarios", userQuery);

// Send push notification to query
    ParsePush push = new ParsePush();
    push.setQuery(pushQuery); // Set our Installation query
    push.setMessage("Free hotdogs at the Parse concession stand!");
    push.sendInBackground();


    }


}