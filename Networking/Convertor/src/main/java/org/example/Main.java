package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Classes.*;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.HttpURLConnection;


public class Main {


   static class User {

        String name;
        String surname;
        String email;

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", surname='" + surname + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }

    }

    public static void main(String[] args) throws Exception {


       // First Task
        var json="{\n"+
                " \"name\": \"Joe\",\n"+
                " \"surname\": \"Biden\",\n"+
                " \"email\": \"potus@gmail.com\"\n"+
                "}";
       User user= toObject(json,User.class);
       System.out.println("Getted user from json has next values of properties:\n"+user.toString());


        // Second Task


        HttpURLConnection connection;

        int maxSize=0;
        String maxImageURL = "";

        String apiUrl="https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=10&api_key=DEMO_KEY";

        URL url = new URL(apiUrl);
        ObjectMapper objectMapper = new ObjectMapper();

        MarsPhotosResponse response = objectMapper.readValue(url, MarsPhotosResponse.class);
        Object[] photos = (Object[])response.getPhotos().toArray();


        for(int i=0;i<photos.length;i++){

            String newRequestURL=((Photo)photos[i]).getImg_src().replace("http:","https:");

            url = new URL(newRequestURL);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if(responseCode>=400){
                System.out.println("Server can't execute client request");
                return;
            }


            int contentLength=connection.getContentLength();

            System.out.println("URL "+url.toString());
            System.out.println("Content length "+contentLength);


            if(contentLength>maxSize){
                maxSize=contentLength;
                maxImageURL=((Photo)photos[i]).getImg_src();
            }

            connection.disconnect();

        }

        System.out.println("Image with max size have url : "+ maxImageURL + "\nSize "+maxSize);



   }
    public static<T> T toObject(String json, Class<T> obj) throws  Exception{

        Class<?> classVar = Class.forName(obj.getName());

        Object object= classVar.getDeclaredConstructor().newInstance();

        json=json.replaceAll("[{}\\n \"]","");

        String data[]= json.split(",");


       for(String val:data){

           String []row=val.split(":");

           String fieldName = row[0];
           Object fieldValue = row[1];

           Field field = classVar.getDeclaredField(fieldName);

           field.set(object, fieldValue);

       }


       return (T)object;
    }



}
