package org.esiea.tassevil_mierzynski.app;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.NotificationCompat;
import android.widget.EditText;
import android.content.Context;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    String urlMusic = null;
    String singerstring = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context context = this;

        ImageButton IB = (ImageButton) findViewById(R.id.DLTask);
        // Show toast message and launch new activity when button is clicked
        IB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Affichage du toast
                Toast.makeText(getApplicationContext(), getString(R.string.toast), Toast.LENGTH_LONG).show();

                EditText singer = (EditText) findViewById(R.id.Research);
                singerstring = singer.getText().toString();
                new DLTask().execute("http://api.deezer.com/search?q=" + singerstring);
            }
        });
    }

    public void onClickTest(View v){
        EditText singer = (EditText) findViewById(R.id.Research);
        singerstring = singer.getText().toString();
        new DLTask().execute("http://api.deezer.com/search?q=" + singerstring);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu) {
            //new AlertDialog.Builder(this).setMessage("WOW").create().show();

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setView(R.layout.dialog).setMessage("WOW").setTitle("Titre").create().show();
        }
        else if (id == R.id.notif) {
            showNotification();
        }
        else if (id == R.id.Toasteur){
            Toast.makeText(getApplicationContext(),getString(R.string.toast),Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void showNotification() {

        Resources r = getResources();
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.ic_menu_report_image)
                        .setContentTitle(r.getString(R.string.notification_title))
                        .setContentText(r.getString(R.string.notification_text));

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

    private class DLTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground (String... urls){
            try {
                return downloadUrl(urls[0]);
            }catch(IOException e){
                return getString(R.string.WrongUrl);
            }
        }
        @Override
        protected void onPostExecute(String result){
            try {
                JSONObject js = new JSONObject(result);
                urlMusic = js.getJSONArray("data").getJSONObject(0).getString("preview");
                String titrechanson = js.getJSONArray("data").getJSONObject(0).getString("title");
                String titrealbum = js.getJSONArray("data").getJSONObject(0).getJSONObject("album").getString("title");
                String jacket = js.getJSONArray("data").getJSONObject(0).getJSONObject("album").getString("cover");
                String jstexttotal = getString(R.string.songTitle) + titrechanson +"\n"+ getString(R.string.AlbumTitle)+ titrealbum +"\n"+getString(R.string.cover)+ jacket;
                ((TextView) findViewById(R.id.DLTaskText)).setText(jstexttotal);
                try {

                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setDataSource(urlMusic);
                    mediaPlayer.prepare();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private String downloadUrl(String myurl) throws IOException{
        InputStream is = null;

        try{
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            is = conn.getInputStream();

            return readIt(is, 80000);
        } finally {
            if (is != null){
                is.close();
            }
        }
    }

    private String readIt(InputStream is, int i)throws IOException{
        Reader reader;
        reader = new InputStreamReader(is, "UTF-8");
        char[] buffer = new char[i];
        reader.read(buffer);
        return new String(buffer);
    }

    public void playMusic(View v){
        mediaPlayer.start();
    }

    public void pauseMusic(View v){
        mediaPlayer.pause();
    }
}
