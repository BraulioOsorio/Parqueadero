package com.example.app_parqueadero.core;


import android.content.Context;
import android.content.res.Resources;

import com.example.app_parqueadero.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    String apiHost;

    public Config(Context context){
        Resources res = context.getResources();
        InputStream inputStream = res.openRawResource(R.raw.config);
        // Lee las propiedades del archivo
        try {
            Properties properties = new Properties();
            properties.load(inputStream);

            apiHost = properties.getProperty("api_host");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getApiHost(){
        return apiHost;
    }

    public String getEndPoint(String endpoint){
        if (endpoint != null && endpoint.startsWith("/")) endpoint = endpoint.substring(1);
        return apiHost + (apiHost.endsWith("/") ? "" : "/") + (endpoint != null ? endpoint : "");
    }
}
