package com.example.omar.uktour11;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.net.PasswordAuthentication;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class SignIn extends Fragment {

    Button button;
    Button singup;
    EditText username, password;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_in_fragment, container, false);
        button = (Button) view.findViewById(R.id.sign);
        username = (EditText) view.findViewById(R.id.sign_in_username);
        password = (EditText) view.findViewById(R.id.password_sign);
        final RequestQueue queue = Volley.newRequestQueue(getContext());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest stringRequest = new StringRequest(POST, Url.SIGNIN, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.getInt("success") == 1) {
                                UserProfile.USER_ID = obj.getString("Id");
                                Intent i = new Intent(getActivity(), TappedView.class);
                                startActivity(i);
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
                        params.put("username", username.getText().toString());
                        params.put("password", password.getText().toString());
                        return params;
                    }
                };
                queue.add(stringRequest);
            }
        });


        singup = (Button) view.findViewById(R.id.idonthave);

        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivityFirst) getActivity()).setViewPager(1);

            }
        });


        return view;


    }


/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_fragment);


        singup = (Button) findViewById(R.id.singup);
        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),SignUp.class);
                startActivity(i);

            }
        });
    }*/
}



