package com.hsallajo.gradle.makeajoke;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import java.io.IOException;
import static com.hsallajo.gradle.networkutils.NetworkUtils.isOnline;
import com.hsallajo.gradle.makeajoke.backend.jokesApi.JokesApi;

public class WorkerFragment extends Fragment {

    interface JokeBgTaskCallbacks {
        void onProgressUpdate(int percent);
        void onCancelled();
        void onPostExecute(String s);
    }

    private Context mParentActivity;
    private JokesAsyncTask mTask;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mParentActivity = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mParentActivity = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this fragment across configuration changes.
        setRetainInstance(true);
    }
    
    public void loadJoke(){
        if(!isOnline(getContext())){
            ((JokeBgTaskCallbacks)mParentActivity).onCancelled();
        }

        mTask = new JokesAsyncTask();
        mTask.execute(new Pair<>(mParentActivity, ""));
    }

     private class JokesAsyncTask extends AsyncTask<Pair<Context, String>, Integer, String> {

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
                return mJokeApiService.tellJoke("name").execute().getData();
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
}
