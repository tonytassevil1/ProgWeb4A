package org.esiea.tassevil_mierzynski.app;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mierzy on 08/11/2016.
 */
public class RecyclerSimpleViewAdapter extends RecyclerView.Adapter<RecyclerSimpleViewAdapter.ViewHolder> {

  private ArrayList<Model> nDataset;
  //private List<String> items;
  //private int itemLayout;

  public RecyclerSimpleViewAdapter(ArrayList items) {
    this.nDataset = items;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    // get inflater and get view by resource id itemLayout

    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
    ViewHolder vh = new ViewHolder(v);
    return vh;
    }

  @Override
  public int getItemCount() {
    return nDataset.size();
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.text.setText(nDataset.get(position).text);
    holder.text2.setText(nDataset.get(position).text2);
  }
  public static class ViewHolder extends RecyclerView.ViewHolder {
     public TextView text;
     public TextView text2;

    public ViewHolder(View itemView) {
      super(itemView);

      text = (TextView) itemView.findViewById(R.id.text);
      text2 = (TextView) itemView.findViewById(R.id.text2);
    }
  }
}