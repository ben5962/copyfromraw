package com.example.utilisateur.copyfromraw;

import android.content.res.Resources;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class MainActivity extends Activity {
    private Button bton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         bton = (Button) findViewById( R.id.premierBt);
        bton.setEnabled(true);
        //BoutonEnvoyer = (Button)findViewById(R.id.BtonTransfert);
        //BoutonEnvoyer.setEnabled(true);

    }

    public void ActionEnvoyer(View view){
        bton.setEnabled(false);
        Toast.makeText(getApplicationContext(),"Envoi en cours", Toast.LENGTH_SHORT).show();
                File chemin = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES);
        File fichierdest = new File(chemin, "pouetdest.txt");
        try {
            chemin.mkdirs();
            InputStream lecture_flux = getResources().openRawResource(R.raw.pouetfromraw);
            OutputStream ecriture_flux = new FileOutputStream(fichierdest);
            byte[] donnees = new byte[lecture_flux.available()];
            lecture_flux.read(donnees);
            ecriture_flux.write(donnees);
            lecture_flux.close();
            ecriture_flux.close();
            MediaScannerConnection.scanFile(this,
                    new String[]{fichierdest.toString()},
                    null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String chemin, Uri uri) {
                            Log.i("externalstorage", "scanned" + chemin + ":");
                            Log.i("externalstorage", "-> uri="  + uri);
                        }

                    });
        } catch (IOException e ) {
            Log.w("external storage", "error writing" + fichierdest, e);
                }
        bton.setEnabled(true);
        }


// ajouter les actions que je veux lier au bouton ici.

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
