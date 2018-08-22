package com.example.omar.uktour11;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

/**
 * Created by omar on 12/23/2017.
 */

public class MyProfile extends Fragment{
    private List<Journey> Journeys = new ArrayList<>();
    private RecyclerView recyclerView;
    private JourneyAdapter mAdapter;
    private RequestQueue queue;

    ImageView img , propic ,addimage, camera;
    TextView usrname, age, gender, email;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_profile_fragment, container , false);
        img = (ImageView) view.findViewById(R.id.edit_final_myprofile);
        propic = (ImageView)view.findViewById(R.id.Profile_final_myprofile);
        usrname = (TextView) view.findViewById(R.id.username_final_myprofile);
        age = (TextView) view.findViewById(R.id.age_final_myprofile);
        gender = (TextView) view.findViewById(R.id.gender_final_myprofile);
        email = (TextView) view.findViewById(R.id.Email_final_myprofile);
        email.setEnabled(false);

        recyclerView = (RecyclerView) view.findViewById(R.id.profile_journeys);
        queue = Volley.newRequestQueue(getContext());

        mAdapter = new JourneyAdapter(Journeys, getActivity());
        RecyclerView.LayoutManager Layout = new LinearLayoutManager(getContext()); /// or getActivity ???
        recyclerView.setLayoutManager(Layout);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);



        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivitySecond)getActivity()).setViewPager(1);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        StringRequest stringRequest = new StringRequest(POST, Url.My_Profile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // response
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getInt("success") == 1) {
                        UserProfile.USERNAME = obj.getString("Username");
                        usrname.setText(UserProfile.USERNAME);
                        UserProfile.EMAIL = obj.getString("Email");
                        email.setText(UserProfile.EMAIL);
                        gender.setText(obj.getString("Gender"));
                        UserProfile.AGE = obj.getString("Age");
                        age.setText(UserProfile.AGE);
                        UserProfile.IMAGE = obj.getString("Photo");
                        propic.setImageBitmap(new Base64Converter().convertStringToBitMap(UserProfile.IMAGE));
                        JSONArray jsonArray = obj.getJSONArray("Journeys");
                        for (int i =0; i< jsonArray.length(); i++){
                            JSONObject JourneyJSONObejct = (JSONObject) jsonArray.get(i);
                            Journey JO = new Journey();
                            JO.setDate(JourneyJSONObejct.getString("Date"));
                            JO.setDuration(JourneyJSONObejct.getString("Duration"));
                            JO.setLocation(JourneyJSONObejct.getString("Location"));
                            JO.setName(JourneyJSONObejct.getString("Name"));

                            Journeys.add(JO);
                        }
                        recyclerView.setAdapter(new JourneyAdapter(Journeys, getActivity()));
                    } else {
                        Toast.makeText(getContext(), obj.has("Error") ? obj.getString("Error") : "Try again later", Toast.LENGTH_SHORT).show();
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
                params.put("id", UserProfile.USER_ID);
                return params;
            }
        };
        queue.add(stringRequest);
    }
}