package com.udacity.gradle.builditbigger.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;
import com.hsallajo.gradle.jokes.Jokes;

/** An endpoint class we are exposing */
@Api(
        name = "jokesApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.gradle.udacity.com",
                ownerName = "backend.builditbigger.gradle.udacity.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /** Endpoint method*/
    @ApiMethod(name = "tellJoke")
    public MyBean tellJoke(@Named("name") String name) {
        MyBean response = new MyBean();
        response.setData(Jokes.getJoke());

        return response;
    }

}
