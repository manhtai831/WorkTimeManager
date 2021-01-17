package com.manhtai.calendartime.adapter;

import android.content.Context;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.manhtai.calendartime.R;
import com.manhtai.calendartime.model.About;
import com.manhtai.calendartime.until1.CallBackAboutRecyclerView;

import java.util.List;

public class AboutAdapter extends RecyclerView.Adapter<AboutAdapter.AboutHolder> {

    Context context;
    List<About> abouts;
    CallBackAboutRecyclerView callBackAboutRecyclerView;
    public AboutAdapter(Context context, List<About> abouts) {
        this.context = context;
        this.abouts = abouts;
    }

    @NonNull
    @Override
    public AboutHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        callBackAboutRecyclerView = (CallBackAboutRecyclerView) context;
        return new AboutHolder(LayoutInflater.from(context).inflate(R.layout.item_about,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AboutHolder holder, int position) {
        About about = abouts.get(position);
        holder.tvItemAboutTitle.setText(about.getTitle());
        holder.tvItemInfoInfo.setText(about.getInfo());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackAboutRecyclerView.getItemPosition(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return abouts.size();
    }

    protected class AboutHolder extends RecyclerView.ViewHolder {
        private TextView tvItemAboutTitle;
        private TextView tvItemInfoInfo;

        public AboutHolder(@NonNull View itemView) {
            super(itemView);
            tvItemAboutTitle = itemView.findViewById(R.id.tv_item_about_title);
            tvItemInfoInfo = itemView.findViewById(R.id.tv_item_info_info);
        }
    }
}
