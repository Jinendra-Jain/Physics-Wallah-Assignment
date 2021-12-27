package com.example.physicswallahassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public TextView vTextView1,vTextView2,vTextView3,vTextView4,vTextView5,vTextView6;
    public TextView[] textViews1,textViews2;
    public Button vJsonFetcherButton;
    public RadioButton vRadioButton,vRadioButton2,vRadioButton3,vRadioButton4,vRadioButton5,vRadioButton6,vRadioButton7,vRadioButton8,vRadioButton9;
    public RadioButton[][] radioButtons;
    public String[][][] exclusion_details;
    public JSONObject[] facilities,exclusion_objects;
    public JSONObject[][] options_details;
    public JSONArray[] options,exclusion;
    public String[] facility_id;
    public String[] facility_name;
    public String[][][] option_details_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vTextView1 = findViewById(R.id.textView1);
        vTextView2 = findViewById(R.id.textView2);
        vTextView3 = findViewById(R.id.textView3);
        vTextView4 = findViewById(R.id.textView4);
        vTextView5 = findViewById(R.id.textView5);
        vTextView6 = findViewById(R.id.textView6);
        textViews1 = new TextView[]{vTextView1,vTextView3,vTextView5};
        textViews2 = new TextView[]{vTextView2,vTextView4,vTextView6};
        vRadioButton = findViewById(R.id.radioButton);
        vRadioButton2 = findViewById(R.id.radioButton2);
        vRadioButton3 = findViewById(R.id.radioButton3);
        vRadioButton4 = findViewById(R.id.radioButton4);
        vRadioButton5 = findViewById(R.id.radioButton5);
        vRadioButton6 = findViewById(R.id.radioButton6);
        vRadioButton7 = findViewById(R.id.radioButton7);
        vRadioButton8 = findViewById(R.id.radioButton8);
        vRadioButton9 = findViewById(R.id.radioButton9);
        radioButtons = new RadioButton[][]{{vRadioButton,vRadioButton2,vRadioButton3,vRadioButton4},{vRadioButton5,vRadioButton6},{vRadioButton7,vRadioButton8,vRadioButton9}};
        vJsonFetcherButton = findViewById(R.id.jsonFetcherButton);

        vJsonFetcherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "https://my-json-server.typicode.com/ricky1550/pariksha/db";
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
//                            int l;
                            JSONArray arr2 = response.getJSONArray("exclusions");
                            exclusion = new JSONArray[arr2.length()];

                            for (int l = 0; l < arr2.length(); l++) {

                                exclusion[l] = arr2.getJSONArray(l);
                                exclusion_objects = new JSONObject[exclusion[l].length()];
                                for (int m = 0; m < exclusion[l].length(); m++) {

                                    exclusion_objects[m] = exclusion[l].getJSONObject(m);

                                    String facility_identity = exclusion_objects[m].getString("facility_id");
                                    String options_identity = exclusion_objects[m].getString("options_id");

                                    exclusion_details = new String[arr2.length()][exclusion[l].length()][exclusion_objects[m].length()];
                                    exclusion_details[l][m][0] = facility_identity;
                                    exclusion_details[l][m][1] = options_identity;

                                }

                            }

                            JSONArray arr1 = response.getJSONArray("facilities");
                            for (int o = 0; o < arr1.length(); o++){

                                // Instantiating JSONObject Array of length 3
                                facilities = new JSONObject[arr1.length()];
                                facilities[o] = arr1.getJSONObject(o);

                                facility_id = new String[arr1.length()];
                                facility_id[o] = facilities[o].getString("facility_id");
                                textViews1[o].setText(facility_id[o]);

                                facility_name = new String[arr1.length()];
                                facility_name[o] = facilities[o].getString("name");
                                textViews2[o].setText(facility_name[o]);

                                options = new JSONArray[arr1.length()];
                                options[o] = facilities[o].getJSONArray("options");


                                for (int p = 0; p < options[o].length(); p++){

//                                    Log.d("TAG",options[o].getString(p));

                                    options_details = new JSONObject[arr1.length()][options[o].length()];
                                    options_details[o][p] = options[o].getJSONObject(p);

                                    option_details_value = new String[arr1.length()][options[o].length()][options_details[o][p].length()];
                                    option_details_value[o][p][0] = options_details[o][p].getString("name");
                                    option_details_value[o][p][1] = options_details[o][p].getString("icon");
                                    option_details_value[o][p][2] = options_details[o][p].getString("id");

                                    radioButtons[o][p].setText("Name: " + option_details_value[o][p][0] + " Icon: " + option_details_value[o][p][1]);

                                }

                            }

                        } catch (JSONException e) {

                            e.printStackTrace();

                        }

                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(getApplicationContext(), "This button is tapped", Toast.LENGTH_SHORT).show();

                            }
                        }

                );

                requestQueue.add(jsonObjectRequest);

            }
        });

    }
}