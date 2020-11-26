package org.mp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class API {
    private boolean isRunning = false;
    private List<Observer> observers = new LinkedList<>();
    private URL url;

    public API (URL url){
        if(url != null){
            this.url = url;
        }
    }
    private String GETAPI(URL urlGet) throws IOException {
        StringBuffer responseString;
        String readLine = null;
        HttpURLConnection connection = (HttpURLConnection) urlGet.openConnection();
        connection.setRequestMethod("GET");
        int response = connection.getResponseCode();

        if(response == HttpURLConnection.HTTP_OK){
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            responseString = new StringBuffer();
            while((readLine = in.readLine())!= null){
                responseString.append(readLine);
            }
            in.close();
        }
        else{
            return String.format("GET NOT WOKING");
        }
        return responseString.toString();
    }
    public void addObserver(Observer observer){
        if(observer == null){
            throw new IllegalArgumentException("Observer");
        }
        if(observers.contains(observer) == false){
            observers.add(observer);
        }
    }
    public void removeObserver(Observer observer){
        if(observer == null){
            throw new IllegalArgumentException("Observer");
        }
        if(observers.contains(observer)){
            observers.remove(observer);
        }
    }
    public void APIStart(){
        if(isRunning == false){
            isRunning = true;
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(isRunning){
                        for(Observer observer: observers){
                            try {
                                observer.notify(observer, GETAPI(url));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            t.setDaemon(true);
            t.start();
        }
    }
    public void APIStop(){
        isRunning = false;
    }
}
