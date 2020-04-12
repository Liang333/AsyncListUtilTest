package com.example.asynclistutiltest;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AsyncListUtilActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.async_list_util);

        RecyclerView recyclerView = findViewById(R.id.rv);
        MyAsyncListUtil myAsyncListUtil = new MyAsyncListUtil(recyclerView);
        AsyncListUtilAdapter adapter = new AsyncListUtilAdapter(this, myAsyncListUtil);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
