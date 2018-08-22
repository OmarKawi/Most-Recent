package com.example.omar.uktour11;

/**
 * Created by omar on 12/20/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.POST;


public class Tab2 extends Fragment {
Button button;
    private List<Journey> Journeys = new ArrayList<>();
    private RecyclerView recyclerView;
    private JourneyAdapter mAdapter;
    private RequestQueue queue;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
                View rootView = inflater.inflate(R.layout.tab2fragement, container, false);
        button = (Button) rootView.findViewById(R.id.AddNewJourny);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.journyes_recycle);
        queue = Volley.newRequestQueue(getContext());

        mAdapter = new JourneyAdapter(Journeys, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        preparejourneyData();








        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),AddNewJourney.class);
                startActivity(i);
            }
        });


        return rootView;
    }
    private void preparejourneyData() {
        StringRequest stringRequest = new StringRequest(POST, Url.Journyes_All, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray jsonArray = obj.getJSONArray("result");
                    for (int i =0; i< jsonArray.length(); i++){
                        JSONObject JourneyJSONObejct = (JSONObject) jsonArray.get(i);
                        Journey JO = new Journey();
                        JO.setDate(JourneyJSONObejct.getString("Date"));
                        JO.setDuration(JourneyJSONObejct.getString("Duration"));
                        JO.setLocation(JourneyJSONObejct.getString("Location"));
                        JO.setName(JourneyJSONObejct.getString("Name"));
                        JO.setId(JourneyJSONObejct.getInt("Id") + "");
                        Journeys.add(JO);
                    }
                    recyclerView.setAdapter(new JourneyAdapter(Journeys, getActivity()));
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
        );
        queue.add(stringRequest);




    }

}
