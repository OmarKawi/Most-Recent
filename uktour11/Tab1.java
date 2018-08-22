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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

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

public class Tab1 extends Fragment {
    private List<Historic> HistoricLists = new ArrayList<>();
    private RecyclerView recyclerView;
    private HistoricAdapter mAdapter;
    private RequestQueue queue;
    RelativeLayout relativeLayout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1fragement, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycletab1);
        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.relativeOne);
        queue = Volley.newRequestQueue(getContext());


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        preparHistoricData(recyclerView);

        return rootView;
    }

    private void preparHistoricData(final RecyclerView recyclerView) {
        StringRequest stringRequest = new StringRequest(POST, Url.HISTORIC_PLACE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray jsonArray = obj.getJSONArray("result");
                    for (int i =0; i< jsonArray.length(); i++){
                        JSONObject historicJsonObject = (JSONObject) jsonArray.get(i);
                        Historic xx = new Historic();
                        xx.setNameOfHistoric(historicJsonObject.getString("Name"));
                        xx.setRate(historicJsonObject.getInt("AverageRate"));
                        xx.setImage(historicJsonObject.getString("Image"));
                        xx.setId(historicJsonObject.getString("Id"));
                        HistoricLists.add(xx);
                    }
                    recyclerView.setAdapter(new HistoricAdapter(HistoricLists , getContext()));
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







