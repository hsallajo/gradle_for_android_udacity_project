package com.hsallajo.gradle.makeajoke;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import java.io.IOException;
import java.lang.ref.WeakReference;

import static com.hsallajo.gradle.networkutils.NetworkUtils.isOnline;
import com.hsallajo.gradle.makeajoke.backend.jokesApi.JokesApi;

/*
 A class used to retains asyncTask across configuration changes.
* */
public class WorkerFragment extends Fragment {

    interface JokeBgTaskCallbacks {
        void onCancelled();
        void onPostExecute(String s);
    }

    private Context mCallingActivity;
    private JokesAsyncTask mTask;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallingActivity = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallingActivity = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this fragment across configuration changes.
        setRetainInstance(true);
    }
    
    public void loadJoke(){
        if(!isOnline(getContext())){
            ((JokeBgTaskCallbacks) mCallingActivity).onCancelled();
        }

        mTask = new JokesAsyncTask(mCallingActivity);
        mTask.execute();
    }

     private static class JokesAsyncTask extends AsyncTask<Void, Integer, String> {

        private static final String ROOT_URL = "http://10.0.2.2:8080/_ah/api/";
        private final String TAG = JokesAsyncTask.class.getSimpleName();
        private JokesApi mJokeApiService = null;
        private WeakReference<Context> contextRef;

        JokesAsyncTask(Context c){
            contextRef = new WeakReference<>(c);
        }


        protected final String doInBackground(Void... params) {

            if (mJokeApiService == null){
                JokesApi.Builder builder = new JokesApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        .setRootUrl(ROOT_URL)
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> request) {
                                request.setDisableGZipContent(true);
                            }
                        });


                mJokeApiService = builder.build();
            }

            try {
                return mJokeApiService.tellJoke().execute().getData();
            } catch (IOException e) {
                Log.e(TAG, "doInBackground error: IOException e: " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {

            if(contextRef.get() == null){
                Log.e(TAG, "onPostExecute error: null context");
                return;
            }

            if(s == null){
                ((WorkerFragment.JokeBgTaskCallbacks)contextRef.get()).onCancelled();
            } else {
                ((WorkerFragment.JokeBgTaskCallbacks)contextRef.get()).onPostExecute(s);
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
