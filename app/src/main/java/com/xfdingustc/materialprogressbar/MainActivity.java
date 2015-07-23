package com.xfdingustc.materialprogressbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  RecyclerView mRecyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initViews();

  }

  private void initViews() {
    setContentView(R.layout.activity_main);
    mRecyclerView = (RecyclerView)findViewById(R.id.rvContentList);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    List<String> strList = new ArrayList<>();

    for (int i = 0; i < 100; i++) {
      strList.add(new String("" + i));
    }

    SimpleAdapter adapter = new SimpleAdapter(this, strList);
    mRecyclerView.setAdapter(adapter);
  }


}
