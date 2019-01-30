package com.hsallajo.gradle.makeajoke;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.hsallajo.gradle.disputils.DisplayJokeActivity;
import com.hsallajo.gradle.makeajoke.backend.jokesApi.JokesApi;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();
        hideProgressBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        JokesEndpointsTask asyncTask = new JokesEndpointsTask();
        showProgressBar();
        asyncTask.execute();
    }

    private void showProgressBar(){
        ProgressBar p = findViewById(R.id.progress_have_a_joke);
        p.setVisibility(View.VISIBLE);
        Button b = findViewById(R.id.btn_tell_joke);
        b.setVisibility(View.GONE);
    }

    private void hideProgressBar(){
        ProgressBar p = findViewById(R.id.progress_have_a_joke);
        p.setVisibility(View.GONE);
        Button b = findViewById(R.id.btn_tell_joke);
        b.setVisibility(View.VISIBLE);
    }

    public void showJoke(String aJoke){
        Intent i = new Intent(this, DisplayJokeActivity.class);
        i.putExtra(DisplayJokeActivity.EXTRA_JOKE_CONTENTS, aJoke);
        startActivity(i);
    }

    class JokesEndpointsTask extends AsyncTask<Void, Integer, String>{

        public final String TAG = JokesEndpointsTask.class.getSimpleName();
        private JokesApi mJokeApiService = null;

        @Override
        protected String doInBackground(Void... voids) {

            if (mJokeApiService == null){
                JokesApi.Builder builder = new JokesApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                                request.setDisableGZipContent(true);
                            }
                        });
                // end options for devappserver

                mJokeApiService = builder.build();

                /*try {
                    Thread.sleep(8000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

                Log.d(TAG, "devappserver initialized");

            }


            try {
                return mJokeApiService.tellJoke("foo").execute().getData();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            showJoke(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

}
