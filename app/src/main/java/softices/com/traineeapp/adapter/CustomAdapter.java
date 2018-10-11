package softices.com.traineeapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import softices.com.traineeapp.R;
import softices.com.traineeapp.activity.ContactActivity;
import softices.com.traineeapp.Database.DataModel;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<DataModel> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewName1;
        TextView textViewContact;
        TextView textViewContact1;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName1 = (TextView) itemView.findViewById(R.id.textViewName1);
            this.textViewContact1 = (TextView) itemView.findViewById(R.id.textViewContact1);
        }
    }

    public CustomAdapter(ArrayList<DataModel> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);

        //MyViewHolder myViewHolder = new MyViewHolder(view);
        return null;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName1 = holder.textViewName1;
        TextView textViewVersion1 = holder.textViewContact1;

        textViewName1.setText(dataSet.get(listPosition).getName());
        textViewVersion1.setText(dataSet.get(listPosition).getVersion());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}