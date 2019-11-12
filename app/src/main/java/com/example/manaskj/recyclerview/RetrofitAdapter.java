package com.example.manaskj.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RetrofitAdapter extends RecyclerView.Adapter<RetrofitAdapter.MyViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<ModelRecycler> modelRecyclers;

    public RetrofitAdapter(MainActivity layoutInflater, ArrayList<ModelRecycler> modelRecyclers) {
        this.layoutInflater = LayoutInflater.from(layoutInflater);
        this.modelRecyclers = modelRecyclers;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v = layoutInflater.inflate(R.layout.retro_item,parent,false);
      MyViewHolder mh = new MyViewHolder(v);
      return mh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load(modelRecyclers.get(position).getImgURL()).into(holder.iv);
        holder.name.setText(modelRecyclers.get(position).getName());
        holder.country.setText(modelRecyclers.get(position).getCountry());
        holder.city.setText(modelRecyclers.get(position).getCity());

    }

    @Override
    public int getItemCount() {
      return  modelRecyclers.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView country, name, city;
        ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);

            country = (TextView) itemView.findViewById(R.id.country);
            name = (TextView) itemView.findViewById(R.id.name);
            city = (TextView) itemView.findViewById(R.id.city);
            iv = (ImageView) itemView.findViewById(R.id.iv);
        }

    }
}
