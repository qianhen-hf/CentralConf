package com.huang.centralconf.client.config;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings("ALL")
public class LongPolling {

    private final ExecutorService executorService=  Executors.newSingleThreadExecutor();

    public void startLongPolling(){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                
            }
        });
    }


}
