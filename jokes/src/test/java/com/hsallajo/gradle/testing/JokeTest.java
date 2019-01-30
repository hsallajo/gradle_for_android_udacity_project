package com.hsallajo.gradle.testing;

import com.hsallajo.gradle.jokes.Jokes;
import org.junit.Assert;
import org.junit.Test;

public class JokeTest {

    @Test
    public void JokeTest() {
        String res = Jokes.getJoke();
        Assert.assertTrue(res != "" && !res.isEmpty());
    }
}
