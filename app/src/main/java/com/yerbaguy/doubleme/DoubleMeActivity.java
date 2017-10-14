package com.yerbaguy.doubleme;

import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.Buffer;

public class DoubleMeActivity extends AppCompatActivity {

    //EditText inputValue = null;
    EditText inputValue;
    String value;
    Integer doubledValue = 0;
    Button doubleMe;
    TextView textViewDoubleMe;
    TextView textViewSecond;
    //public static String request = "lkjasdf";
    public static String engword = "lkjasdf";
    //public static final String URL = ("http://10.0.2.2:8080/PracticeWordsServlet/DoubleMeServlet?param1=" + request);
    public static final String URL = ("http://10.0.2.2:8080/PracticeExercise/rest/json/metallica/post");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_me);

        inputValue = (EditText) findViewById(R.id.inputNum);
        doubleMe = (Button) findViewById(R.id.doubleMe);
        textViewDoubleMe = (TextView) findViewById(R.id.textViewDoubleMe);
        textViewSecond = (TextView) findViewById(R.id.textViewSecond);


        doubleMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                       value = inputValue.getText().toString();
                textViewDoubleMe.setText(value);

                JSONObject post_dict = new JSONObject();

                try {
                    post_dict.put("value", value);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                GetXMLTask getxmlTask = new GetXMLTask();
                //getxmlTask.execute(new String [] { URL})

                getxmlTask.execute(String.valueOf(post_dict));


            }
        });

    }


    //class GETXMLTask extends AsyncTask<String, Void, String> {
    class GetXMLTask extends AsyncTask<String, String, String> {

        @Override
        //protected String doInBackground(String... urls) {
        protected String doInBackground(String... params) {

            String JSONResponse = null;
            String JSONData = params[0];

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL("http://10.0.2.2:8080/PracticeExercise/rest/json/metallica/post");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");


                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                Log.d("JSONData",JSONData);
                writer.write("kljasdf");
                writer.flush(); //added
                writer.close();
                urlConnection.connect(); //added

              //  OutputStream os = urlConnection.getOutputStream();
              //  BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
              //  writer.write(JSONData);
              //  writer.flush();
              //  writer.close();
              //  os.close();

              //  urlConnection.connect();


                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer stringBuffer = new StringBuffer();

                if (stringBuffer == null) {

                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String inputLine;

                while (( inputLine = reader.readLine()) != null)

                    stringBuffer.append(inputLine + "\n");

                    if (stringBuffer.length() == 0) {
                        return null;
                    }

                    JSONResponse = stringBuffer.toString();

                String TAG = "";
                Log.i(TAG, JSONResponse);

                return JSONResponse;

                //return null;


                //return stringBuffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


            //   String output = null;

         //   for (String url: urls) {

         //       output = getOutputFromUrl(url);
         //   }

         //   return output;
            return null;
        }

        private String getOutputFromUrl(String url) {

            StringBuffer output = new StringBuffer("");

            try {

                InputStream stream = getHttpConnection(url);
                BufferedReader buffer = new BufferedReader(
                        new InputStreamReader(stream));


                String s = "";

                while (( s = buffer.readLine()) != null) {
                    output.append(s);
                }


            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return output.toString();
          }



          private InputStream getHttpConnection(String urlString) throws IOException {

            InputStream  stream = null;
              URL url = new URL(urlString);

              URLConnection connection = url.openConnection();

              try {

                  HttpURLConnection httpConnection = (HttpURLConnection) connection;
                  httpConnection.setRequestMethod("POST");
                  httpConnection.connect();

                  if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                      stream = httpConnection.getInputStream();
                  }

              } catch (Exception ex) {
                  ex.printStackTrace();
              }

              return stream;
          }

          @Override
          protected void onPostExecute(String output) {
             // outputText.setText(output);
              //textViewDoubleMe.setText(output);
              textViewSecond.setText(output);
          }


    }







}
