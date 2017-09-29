package com.example.diler.englishmusictube;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    List<Info> arrayList;
    MyAdapter myAdapter;
    EditText email, password;
    Button b;
    FirebaseAuth mAuth;
    NativeExpressAdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        arrayList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recylerView);
        if(databaseReference==null) {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Hindi");
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        myAdapter = new MyAdapter(arrayList,databaseReference,this);
        recyclerView.setAdapter(myAdapter);

    }





}
