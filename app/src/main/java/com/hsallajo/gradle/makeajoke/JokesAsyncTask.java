package com.hsallajo.gradle.makeajoke;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.hsallajo.gradle.makeajoke.backend.jokesApi.JokesApi;

import java.io.IOException;

public class JokesAsyncTask extends AsyncTask<Pair<Context, String>, Integer, String> {

    private static final String ROOT_URL = "http://10.0.2.2:8080/_ah/api/";
    private final String TAG = JokesAsyncTask.class.getSimpleName();
    private JokesApi mJokeApiService = null;
    private Context context;

    @Override
    protected String doInBackground(Pair<Context, String>... params) {

        context = params[0].first;

        if (mJokeApiService == null){
            JokesApi.Builder builder = new JokesApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(ROOT_URL)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                            request.setDisableGZipContent(true);
                        }
                    });


            mJokeApiService = builder.build();
        }

        try {
            return mJokeApiService.tellJoke("foo").execute().getData();
        } catch (IOException e) {
            Log.e(TAG, "doInBackground: IOException e: " + e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {

        if(s == null){
            ((WorkerFragment.JokeBgTaskCallbacks)context).onCancelled();
        } else {
            ((WorkerFragment.JokeBgTaskCallbacks)context).onPostExecute(s);
        }

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }
}
