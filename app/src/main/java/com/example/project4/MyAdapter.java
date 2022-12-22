package com.example.project4;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    /*
    passing in the data and the listener defined in the main activity
     */
    public ViewHolder viewHolder;
    public MyAdapter(){
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View listView = inflater.inflate(R.layout.item, parent, false);
        viewHolder = new ViewHolder(listView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 10;
    }


    /*
        This class creates a wrapper object around a view that contains the layout for
         an individual item in the list. It also implements the onClickListener so each ViewHolder in the list is clickable.
        It's onclick method will call the onClick method of the RVClickListener defined in
        the main activity.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        Button b0;
        Button b1;
        Button b2;
        Button b3;
        Button b4;
        Button b5;
        Button b6;
        Button b7;
        Button b8;
        Button b9;
        private View itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            b0 = (Button) itemView.findViewById(R.id.b0);
            b1 = (Button) itemView.findViewById(R.id.b1);
            b2 = (Button) itemView.findViewById(R.id.b2);
            b3 = (Button) itemView.findViewById(R.id.b3);
            b4 = (Button) itemView.findViewById(R.id.b4);
            b5 = (Button) itemView.findViewById(R.id.b5);
            b6 = (Button) itemView.findViewById(R.id.b6);
            b7 = (Button) itemView.findViewById(R.id.b7);
            b8 = (Button) itemView.findViewById(R.id.b8);
            b9 = (Button) itemView.findViewById(R.id.b9);
            this.itemView = itemView;
        }

        public void changeColor() {
            b0.setBackgroundColor(Color.RED);
        }
    }
}
