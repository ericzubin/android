package com.example.eric.notifpus;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SignUpCallback;

public class Application extends android.app.Application {

  public Application() {
  }

  @Override
  public void onCreate() {


	// Initialize the Parse SDK.

          Parse.initialize(this, "G5klpHjAC8xFVfOdKoFiIKusHPKguAl2npqIyp9t", "ZIsavOsIzqsuYex5Jsk5xQOSS29Q3AC228XpqEwO");
          ParseInstallation.getCurrentInstallation().saveInBackground();



    //Se crea el usuario aqui
    String name="1";
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
}