package com.unikom.asktech.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import  com.unikom.asktech.Model.Images;
import com.unikom.asktech.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter  extends  RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


    private Context mContext;
    private ArrayList<Images> imageList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public RecyclerAdapter(Context context, ArrayList<Images> imageList) {
        this.mContext = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycleview_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Images CurrentItem = imageList.get(position);

        String imageUrl = CurrentItem.getUrl();
        String title = CurrentItem.getTitle();
        String description = CurrentItem.getDescription();

        holder.tvDescription.setText(description);
        holder.tvTitle.setText(title);
        Picasso.with(mContext).load(imageUrl)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

      public   ImageView imageView;
      public   TextView  tvTitle;
      public   TextView  tvDescription;


        public ViewHolder(View itemView) {
            super(itemView);
            imageView =  itemView.findViewById(R.id.imageView);
            tvTitle =  itemView.findViewById(R.id.tittle);
            tvDescription = itemView.findViewById(R.id.description);

            imageView.setOnClickListener(new View.OnClickListener() {
               @Override
                public void onClick(View v) {
                     if(mListener != null) {
                        int position = getAdapterPosition();
                         if (position != RecyclerView.NO_POSITION) {
                             mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
