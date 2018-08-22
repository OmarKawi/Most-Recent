package com.example.omar.uktour11;

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

/**
 * Created by omar on 12/25/2017.
 */

public class EditProfile extends Fragment {
    Button button;
    EditText username;
    EditText Email, Passowrd , confirmpas , Age ;
    ImageView addimage, camera , profile;
    Uri imageUri;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_profile_fragment, container, false);
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 34);
        }
        final RequestQueue queue = Volley.newRequestQueue(getContext());
        button = (Button) view.findViewById(R.id.update_button_final);
        username = (EditText) view.findViewById(R.id.username_update_final);
        username.setText(UserProfile.USERNAME);
        username.setEnabled(false);
        Email = (EditText) view.findViewById(R.id.email_update_final);
        Email.setEnabled(false);
        Email.setText(UserProfile.EMAIL);
       Passowrd = (EditText) view.findViewById(R.id.password_update_final);
     confirmpas = (EditText) view.findViewById(R.id.confirmpass_update_final);
        Age = (EditText) view.findViewById(R.id.age_update_final);
        Age.setText(UserProfile.AGE);
        addimage = (ImageView) view.findViewById(R.id.addnewimage_update_final);
        camera = (ImageView) view.findViewById(R.id.camera_update_final) ;
        profile = (ImageView) view.findViewById(R.id.ImageProfile_update_final);
        profile.setImageBitmap(new Base64Converter().convertStringToBitMap(UserProfile.IMAGE));
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

        StringRequest stringRequest = new StringRequest(POST, Url.My_Profile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // response
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getInt("success") == 1) {
                        UserProfile.USERNAME = obj.getString("Username");
                        username.setText(UserProfile.USERNAME);
                        UserProfile.EMAIL = obj.getString("Email");
                        Email.setText(UserProfile.EMAIL);
                        UserProfile.AGE = obj.getString("Age");
                        Age.setText(UserProfile.AGE);
                        UserProfile.IMAGE = obj.getString("Photo");
                        profile.setImageBitmap(new Base64Converter().convertStringToBitMap(UserProfile.IMAGE));

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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate())
                {
                    StringRequest stringRequest = new StringRequest(POST, Url.UPDATE_PROFILE, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // response
                            try {
                                JSONObject obj = new JSONObject(response);
                                if (obj.getInt("success") == 1) {
                                    Intent i = new Intent(getContext(), TappedView.class);
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
                            params.put("age", Age.getText().toString());
                            params.put("password", Passowrd.getText().toString());
                            Bitmap bitmap = ((BitmapDrawable)profile.getDrawable()).getBitmap();
                            params.put("photo", new Base64Converter().convertBitMapToString(bitmap));
                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }



                else
                    Toast.makeText(getActivity(),"Password is not the same try again!",Toast.LENGTH_SHORT).show();







                ((MainActivitySecond)getActivity()).setViewPager(0);
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
        startActivityForResult(takePicture, 0);//zero can be replaced with any action code

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






