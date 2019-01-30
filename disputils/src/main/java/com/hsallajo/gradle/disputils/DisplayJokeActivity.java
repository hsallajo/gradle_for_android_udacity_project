package com.hsallajo.gradle.disputils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class DisplayJokeActivity extends AppCompatActivity {

    public final static String EXTRA_JOKE_CONTENTS = "com.hsallajo.gradle.disputils.EXTRA_JOKE_CONTENTS";
    private String aJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {

            aJoke = savedInstanceState.getString(EXTRA_JOKE_CONTENTS, "");

        } else {

            Bundle args = getIntent().getExtras();

            if (args != null) {
                if (args.containsKey(EXTRA_JOKE_CONTENTS)) {
                    aJoke = args.getString(EXTRA_JOKE_CONTENTS);
                }
            }
        }

        setContentView(R.layout.activity_display_joke);
        TextView tv = findViewById(R.id.tv_joke_content);
        tv.setText(aJoke);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(EXTRA_JOKE_CONTENTS, aJoke);
        super.onSaveInstanceState(outState);
    }
}
