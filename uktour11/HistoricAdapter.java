package com.example.omar.uktour11;

/**
 * Created by omar on 1/6/2018.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;
public class HistoricAdapter extends RecyclerView.Adapter<HistoricAdapter.MyViewHolder> {
    private List<Historic> historics;
    private Context context;
    public class MyViewHolder extends  RecyclerView.ViewHolder
    {
        public ImageView img;
        public TextView nameOf;
        public RatingBar avgRate;
        RelativeLayout gotoHistoric;


        public MyViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.imageOfHistoric);
            nameOf = (TextView) view.findViewById(R.id.nameOfHistoricPlaceList);
            avgRate = (RatingBar) view.findViewById(R.id.ratingBarList);
            gotoHistoric = (RelativeLayout) view.findViewById(R.id.relativeOne);


    }

    }
    public HistoricAdapter(List<Historic> historics , Context context)
    {
        this.historics = historics;
        this.context=context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.historic_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Historic historic = historics.get(position);
        holder.nameOf.setText(historic.getNameOfHistoric());
        holder.avgRate.setRating(historic.getRate());

       holder.img.setImageBitmap(new Base64Converter().convertStringToBitMap(historic.getImage()));
        holder.gotoHistoric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, HistoricPlacesDetails.class);
                i.putExtra("id", historic.getId());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return historics.size();
    }
}

