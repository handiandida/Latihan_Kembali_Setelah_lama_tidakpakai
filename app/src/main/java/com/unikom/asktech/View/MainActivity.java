package com.unikom.asktech.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.unikom.asktech.Adapter.RecyclerAdapter;
import com.unikom.asktech.R;
import androidx.annotation.NonNull;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.unikom.asktech.Model.Images;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.OnItemClickListener {
    public  static final String EXTRA_URL = "imageUrl";
    public  static final String EXTRA_TITTLE = "title";
    public  static final String EXTRA_DESCRIPTION = "description";

    private static final String TAG = "MainActivity";
    private DatabaseReference reference;
    private StorageReference mStorageRef;

    private Context mContext = MainActivity.this;
    private RecyclerView recyclerView;

    private ArrayList<Images> imagesList;
    private RecyclerAdapter recyclerAdapter;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: started");

        recyclerView = (RecyclerView) findViewById(R.id.recycleview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        reference = FirebaseDatabase.getInstance().getReference();

        imagesList = new ArrayList<>();

        init();
        mImageView = findViewById(R.id.chatBot);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              openChatBot();
            }
        });
    }

    private void init() {
        clearAll();

        Query query = reference.child("Images");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Images images = new Images();
                    images.setUrl(snapshot.child("url").getValue().toString());
                    images.setDescription(snapshot.child("description").getValue().toString());
                    images.setTitle(snapshot.child("title").getValue().toString());

                    imagesList.add(images);
                }
                recyclerAdapter = new RecyclerAdapter(mContext, imagesList);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.setOnItemClickListener(MainActivity.this);
                recyclerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void clearAll () {

        if(imagesList != null){
            imagesList.clear();

            if(recyclerAdapter != null) {
                recyclerAdapter.notifyDataSetChanged();
            }

        }
        imagesList = new ArrayList<>();
    }

    public void openChatBot(){
        Intent intent = new Intent(this, ChatBot.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        Images clickedItem = imagesList.get(position);

        detailIntent.putExtra(EXTRA_URL, clickedItem.getUrl());
        detailIntent.putExtra(EXTRA_TITTLE, clickedItem.getTitle());
        detailIntent.putExtra(EXTRA_DESCRIPTION, clickedItem.getDescription());
        startActivity(detailIntent);
    }
}


