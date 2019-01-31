package com.hsallajo.gradle.makeajoke;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;

import static com.hsallajo.gradle.networkutils.NetworkUtils.isOnline;

public class WorkerFragment extends Fragment {

    public static final String TAG = WorkerFragment.class.getSimpleName();

    interface JokeBgTaskCallbacks {
        void onPreExecute();
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
        mTask.execute(new Pair<Context, String>(mParentActivity, ""));
    }
}
