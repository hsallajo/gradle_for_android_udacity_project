package com.hsallajo.gradle.makeajoke;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import com.hsallajo.gradle.disputils.DisplayJokeActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements WorkerFragment.JokeBgTaskCallbacks {

    private static final String TAG_AD_FRAGMENT = "tag_ad_fragment";
    private static final String TAG_WORKER_FRAGMENT = "tag_worker_fragment";
    private WorkerFragment mWorkerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        AdFragment adFragment;

        if(getApplicationContext().getResources().getLayout(R.layout.fragment_ad) != null){

            adFragment = (AdFragment) fm.findFragmentByTag(TAG_AD_FRAGMENT);

            if(adFragment == null){
                adFragment = new AdFragment();
                fm.beginTransaction().add(R.id.ad_fragment_container, adFragment, TAG_AD_FRAGMENT).commit();
            }

        }

        mWorkerFragment = (WorkerFragment) fm.findFragmentByTag(TAG_WORKER_FRAGMENT);

        if(mWorkerFragment == null){
            mWorkerFragment = new WorkerFragment();
            fm.beginTransaction().add(mWorkerFragment, TAG_WORKER_FRAGMENT).commit();
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        hideProgressBar();
    }


    public void tellJoke(View view) {
        showProgressBar();
        mWorkerFragment.loadJoke();
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

    private void showJoke(String aJoke){
        Intent i = new Intent(this, DisplayJokeActivity.class);
        i.putExtra(DisplayJokeActivity.EXTRA_JOKE_CONTENTS, aJoke);
        startActivity(i);
    }

    @Override
    public void onCancelled() {
        hideProgressBar();
        android.widget.Toast.makeText(this
                , getApplicationContext().getString(R.string.str_no_network)
                , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostExecute(String s) {
        showJoke(s);
    }
}
