package com.xfdingustc.materialprogressbar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Xiaofei on 2015/7/23.
 */
public class SimpleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private final List<String> mStrList;
  private final Context mContext;

  public SimpleAdapter(Context context, List<String> strList) {
    this.mContext = context;
    this.mStrList = strList;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    LayoutInflater inflater = LayoutInflater.from(mContext);
    View view = inflater.inflate(R.layout.str_item, viewGroup, false);

    return new SimpleViewHolder(view);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
    String str = mStrList.get(position);

    ((SimpleViewHolder)viewHolder).mTv.setText(str);
  }

  @Override
  public int getItemCount() {
    return mStrList.size();
  }


  private class SimpleViewHolder extends RecyclerView.ViewHolder {

    TextView mTv;

    public SimpleViewHolder(View itemView) {
      super(itemView);

      mTv = (TextView) itemView.findViewById(R.id.text);
    }
  }
}



