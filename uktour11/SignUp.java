package com.example.omar.uktour11;

/**
 * Created by omar on 12/23/2017.
 */

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.android.volley.Request.Method.POST;

public class SignUp extends Fragment {
    Button button;
    EditText username;
    EditText Email, Passowrd , confirmpas , Age ;
    ImageView addimage, camera , profile;
    Uri imageUri;
    String gender;
    String image64;
    Bitmap bitmap;
    RadioGroup radioGroup;



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_fragment, container, false);
        button = (Button) view.findViewById(R.id.sign_up_button_final);

        username = (EditText) view.findViewById(R.id.username_signup_final);
        Email = (EditText) view.findViewById(R.id.email_signup_final);
        Passowrd = (EditText) view.findViewById(R.id.password_signup_final);
        confirmpas = (EditText) view.findViewById(R.id.confirmpass_singup_final);
        Age = (EditText) view.findViewById(R.id.age_signup_final);
        addimage = (ImageView) view.findViewById(R.id.addnewimage_singup_final);
        camera = (ImageView) view.findViewById(R.id.camera_signup_final) ;
        profile = (ImageView) view.findViewById(R.id.ImageProfile_signup) ;
        radioGroup = (RadioGroup) view.findViewById(R.id.gendergroup);

        final RequestQueue queue = Volley.newRequestQueue(getContext());

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 34);
        }
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton rb = (RadioButton) group.findViewById(checkedId);
                    if (null != rb && checkedId > -1) {
                        gender= rb.getText().toString();
                    }

                }
            });



        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opencamera();
            }
        });
        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate())
                {
                    StringRequest stringRequest = new StringRequest(POST, Url.SIGN_UP, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // response
                            try {
                                JSONObject obj = new JSONObject(response);
                                if (obj.getInt("success") == 1) {
                                    ((MainActivityFirst) getActivity()).setViewPager(0);
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
                            params.put("username",username.getText().toString() );
                            params.put("password",Passowrd.getText().toString());
                            params.put("email", Email.getText().toString());
                            params.put("gender",gender);
                            params.put("age", Age.getText().toString());
                            Bitmap bitmap = ((BitmapDrawable)profile.getDrawable()).getBitmap();
                            params.put("photo", new Base64Converter().convertBitMapToString(bitmap));
                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }



                else
                    Toast.makeText(getActivity(),"Password is not the same try again!",Toast.LENGTH_SHORT).show();



            }
        });


        return view;
    }
    private boolean validate() {
        boolean temp=true;
        String pass=Passowrd.getText().toString();
        String cpass=confirmpas.getText().toString();

        if(!pass.equals(cpass)){
            temp=false;
        }
        return temp;
    }
    private void opencamera()
    {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, 0);

    }

    private void openGallery()
    {
        Intent gallery = new Intent(Intent.ACTION_PICK , MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, 1);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap thePic = data.getExtras().getParcelable("data");
                        profile.setImageBitmap(thePic);
                    }
                }
                break;
            case 1:
                if(resultCode == RESULT_OK){
                    imageUri = data.getData();
                    profile.setImageURI(imageUri);
                }
                break;
        }
    }



    @Override
    public void onRequestPermissionsResult(final int requestCode, final @NonNull String[] permissions, final @NonNull int[] grantResults) {
        switch (requestCode) {
            case 34: {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    /*Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();*/
                }
                break;
            }
        }
    }
}





