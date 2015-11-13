package com.xfdingustc.materialprogressbar_sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xfdingustc.materialprogressbar.FABProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

  @InjectView(R.id.rvContentList)
  RecyclerView mRecyclerView;

  @InjectView(R.id.fabProgressBar)
  FABProgressBar mFabProgressBar;

  @OnClick(R.id.fab)
  public void onFabClicked() {
    mFabProgressBar.show();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initViews();

  }

  private void initViews() {
    setContentView(R.layout.activity_main);
    ButterKnife.inject(this);

    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    List<String> strList = new ArrayList<>();

    for (int i = 0; i < 100; i++) {
      strList.add(new String("" + i));
    }

    SimpleAdapter adapter = new SimpleAdapter(this, strList);
    mRecyclerView.setAdapter(adapter);


  }


}
