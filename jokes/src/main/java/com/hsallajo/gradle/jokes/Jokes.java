package com.hsallajo.gradle.jokes;

import java.util.Date;

public class Jokes {

    public static final String REALLY_GOOD_JOKE = "It's a perfect day for a good joke. :)";

    public static String getJoke(){

        Date date = new Date();
        String s = "Today is " + date + ". " + REALLY_GOOD_JOKE;
        return s;
    }
}