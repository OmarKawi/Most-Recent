package com.example.omar.uktour11;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

/**
 * Created by omar on 12/24/2017.
 */

public class HistoricPlacesDetails extends AppCompatActivity  implements OnMapReadyCallback {
    RatingBar rate;
    TextView details, name;
    ImageView img;
    private RequestQueue queue;
    private String id;
    private GoogleMap mMap;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.histroic_places_details);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        name = (TextView) findViewById(R.id.HistoricPlaceName_details);
        rate = (RatingBar) findViewById(R.id.ratingBar);
        details = (TextView) findViewById(R.id.detailsOfHistoricPlaces);
        img = (ImageView) findViewById(R.id.imageofHistoryDetails);
        queue = Volley.newRequestQueue(getApplicationContext());
       // rate.setEnabled(false);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.historicPlace_map);
        mapFragment.getMapAsync(this);

        StringRequest stringRequest = new StringRequest(POST, Url.HISTORIC_PLACE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray jsonArray = obj.getJSONArray("result");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject historicJsonObject = (JSONObject) jsonArray.get(i);
                        name.setText(historicJsonObject.getString("Name"));
                        rate.setRating(Float.parseFloat(historicJsonObject.getDouble("AverageRate") + ""));
                        img.setImageBitmap(new Base64Converter().convertStringToBitMap(historicJsonObject.getString("Image")));
                        details.setText(historicJsonObject.getString("Detail"));


                        LatLng locationfinal = new LatLng(Double.parseDouble(historicJsonObject.getString("Latitude")),
                                Double.parseDouble(historicJsonObject.getString("Longitude")));
                        mMap.addMarker(new MarkerOptions().position(locationfinal));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(locationfinal));
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
                params.put("id", id);
                return params;
            }
        };
        queue.add(stringRequest);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }
}
