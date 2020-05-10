package com.unikom.asktech.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.squareup.picasso.Picasso;
import com.unikom.asktech.R;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import static com.unikom.asktech.View.MainActivity.EXTRA_DESCRIPTION;
import static com.unikom.asktech.View.MainActivity.EXTRA_TITTLE;
import static com.unikom.asktech.View.MainActivity.EXTRA_URL;

public class DetailActivity extends AppCompatActivity {
    private ImageButton mImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String title = intent.getStringExtra(EXTRA_TITTLE);
        String description = intent.getStringExtra(EXTRA_DESCRIPTION);

        ImageView imageView = findViewById(R.id.DetailimageView);
        TextView titleView = findViewById(R.id.Detailtittle);
        TextView descriptionView = findViewById(R.id.Detaildescription);


        Picasso.with(this).load(imageUrl).fit().centerInside().into(imageView);
        titleView.setText(title);
        descriptionView.setText(description);
        mImageButton = findViewById(R.id.ImageDetailButtonLeft);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }
    public void back(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
