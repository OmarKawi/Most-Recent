package com.example.omar.uktour11;

/**
 * Created by omar on 12/24/2017.
 */

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;


public class AddNewJourney extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    Button b1;
    int day, month, year, hour, minute;
    int dayFinal, yearFinal, monthFunal, hourFinal, minuteFinal;
    Button add;
    EditText name, location, duration;
    String DateFullFormt, TimeFormat;
    DateFormat dateFormat;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_journey);
        b1 = (Button) findViewById(R.id.dateTime_final_addnewjourney);
        add = (Button) findViewById(R.id.addto_final_addnewjourney);
        name = (EditText) findViewById(R.id.nameOfPlace_final_addnewjourney);
        location = (EditText) findViewById(R.id.location_final_addnewjourney);
        duration = (EditText) findViewById(R.id.duration_final_addnewjourney);

        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext()); ///****


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewJourney.this, AddNewJourney.this, year, month, day);
                datePickerDialog.show();

            }
        });
        //  dateFormat = dayFinal + "/" + monthFunal + "/" + yearFinal;
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(POST, Url.ADD_NEW_JOURNEY, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.getInt("success") == 1) {
                                Intent i = new Intent(getApplicationContext(), TappedView.class);
                                startActivity(i);

                            } else {
                                Toast.makeText(getApplicationContext(), obj.has("Error") ? obj.getString("Error") : "Try again later", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Throwable t) {
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Log.d("Error.Response", error.getMessage());
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("name", name.getText().toString());
                        params.put("location", location.getText().toString());
                        params.put("duration", duration.getText().toString());
                        String dateAndTime = yearFinal + "-" + monthFunal + "-" + dayFinal + " " + hourFinal + ":" + minuteFinal;
                        params.put("date", dateAndTime.toString());
                        return params;
                    }
                };
                queue.add(stringRequest);
            }

        });


    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        yearFinal = year;
        monthFunal = month + 1;
        dayFinal = day;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddNewJourney.this, AddNewJourney.this,
                hour, minute, android.text.format.DateFormat.is24HourFormat(this));
        timePickerDialog.show();

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hourFinal = hourOfDay;
        minuteFinal = minute;
    }
}

