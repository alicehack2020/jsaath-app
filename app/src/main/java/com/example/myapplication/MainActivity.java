package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.myapplication.model.Todo;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView register;
    private static final String TAG = "MainActivity";
  Button logout;
    ApiInterface apiInterface;
  RecyclerView recycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler=findViewById(R.id.recycler);

        logout=findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        if(FirebaseAuth.getInstance().getCurrentUser()==null)
        {
            Intent intent=new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        };
        apiInterface = ApiClient.getClient().create((ApiInterface.class));
        getData();

    }







    private void getData()
    {
        Call<List<Todo>> call = apiInterface.getTodos();
        call.enqueue(new Callback<List<Todo>>() {
            @Override
            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                Log.e(TAG, "onResponse: " + response.body());
                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
                recycler.setLayoutManager(linearLayoutManager1);
                recycler.setAdapter(new NewsAdapter(response.body(), MainActivity.this));

            }

            @Override
            public void onFailure(Call<List<Todo>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }
}

