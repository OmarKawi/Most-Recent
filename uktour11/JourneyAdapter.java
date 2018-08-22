package com.example.omar.uktour11;

/**
 * Created by omar on 1/6/2018.
 */
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class JourneyAdapter  extends RecyclerView.Adapter<JourneyAdapter.MyViewHolder>{
    private List<Journey> journeys;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameOfJourney, dateOfJourney, timeOfJourney, lcoationOfJourney;
        RelativeLayout join;

        public MyViewHolder(View view) {
            super(view);

            nameOfJourney = (TextView) view.findViewById(R.id.titleOfJourneyList);
            dateOfJourney = (TextView) view.findViewById(R.id.dateList);
            timeOfJourney = (TextView) view.findViewById(R.id.timeList);
            lcoationOfJourney = (TextView) view.findViewById(R.id.locationlist);
            join = (RelativeLayout)  view.findViewById(R.id.join_jounrey);
        }
    }

    public JourneyAdapter(List<Journey> journey111, Context mContext) {
        this.journeys = journey111;
        this.mContext = mContext;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.journey_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Journey jouny = journeys.get(position);
        holder.nameOfJourney.setText(jouny.getName());
        holder.dateOfJourney.setText(jouny.getDate());
        holder.timeOfJourney.setText(jouny.getDuration());
        holder.lcoationOfJourney.setText(jouny.getLocation());
        holder.join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                alertDialog.setTitle("Join Journey");
                alertDialog.setMessage("Do you want to join this journey?");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, int which) {
                                StringRequest stringRequest = new StringRequest(POST, Url.JOIN_JOURNEY, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        // response
                                        try {
                                            JSONObject obj = new JSONObject(response);
                                            if (obj.getInt("success") == 1) {
                                                dialog.dismiss();
                                            } else {
                                                Toast.makeText(mContext, obj.has("Error") ? obj.getString("Error") : "Try again later", Toast.LENGTH_SHORT).show();
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
                                        params.put("userId",UserProfile.USER_ID );
                                        params.put("journeyId",jouny.getId());
                                        return params;
                                    }
                                };
                                RequestQueue queue = Volley.newRequestQueue(mContext);

                                queue.add(stringRequest);

                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return journeys.size();
    }
}


