package com.example.eric.notifpus;

import android.net.ParseException;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SignUpCallback;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the Parse SDK.

        Parse.initialize(this, "G5klpHjAC8xFVfOdKoFiIKusHPKguAl2npqIyp9t", "ZIsavOsIzqsuYex5Jsk5xQOSS29Q3AC228XpqEwO");
        ParseInstallation.getCurrentInstallation().saveInBackground();



        //Se crea el usuario aqui
        String name="2";
        String passw="12345";
        String usuario=name;
        String pass=passw;

        String email="e1@example.com";
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


        //Esta parte nos vicula nuestro dispositivo con nuestra cuenta
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("Usuarios", ParseUser.getCurrentUser());
        installation.saveInBackground();
    /*
    Esta parte debe de tener un usuario que exista o si no crashea
// Create our Installation query;
    ParseQuery pushQuery = ParseInstallation.getQuery();
    pushQuery.whereEqualTo("Usuarios", "EL usuario al que le mandaremos el push ObjectID");

// Send push notification to query
    ParsePush push = new ParsePush();
    push.setQuery(pushQuery); // Set our Installation query
    push.setMessage("Willie Hayes injured by own pop fly.");
    push.sendInBackground();

*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
