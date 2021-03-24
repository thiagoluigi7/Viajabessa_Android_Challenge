package com.thiagoluigi.viajabessaandroidchallenge;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.LruCache;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class DetailsViewActivity extends AppCompatActivity implements View.OnClickListener {
    Button buyButton;
    NetworkImageView networkImageView1, networkImageView2;
    TextView packageName, packageValue, packageDescription;
    Package mPackage;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        context = getApplicationContext();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buyButton = findViewById(R.id.buyButton);
        buyButton.setOnClickListener(this);

        networkImageView1 = findViewById(R.id.image);
        networkImageView2 = findViewById(R.id.imageLocal);

        networkImageView1.setDefaultImageResId(R.mipmap.ic_launcher);
        networkImageView2.setDefaultImageResId(R.mipmap.ic_launcher);

        packageName = findViewById(R.id.packageName);
        packageValue = findViewById(R.id.packageValue);
        packageDescription = findViewById(R.id.packageDescription);

        mPackage = (Package)getIntent().getSerializableExtra("package");
        setImage();
        setDescription();

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.buyButton) {
            buyClick();
        }
    }

    private void setImage() {
        imageLoader(mPackage.getImageLink(), networkImageView1);
        imageLoader(mPackage.getLocalImage(), networkImageView2);
    }

    private void setDescription() {
        packageName.setText(mPackage.getName());
        packageValue.setText(mPackage.getValueTxt());
        packageDescription.setText(mPackage.getDescription());
    }

    private void buyClick() {
        CharSequence text = "Pacote comprado!";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void imageLoader(String url, NetworkImageView networkImageView) {
        RequestQueue mRequestQueue = Request.getInstance(getApplicationContext()).getRequestQueue();
        ImageLoader imageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache()
        {
            private final LruCache<String, Bitmap> mCache = new LruCache<>(10);

            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }

            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });

        networkImageView.setImageUrl(url, imageLoader);
        imageLoader.get(url, ImageLoader.getImageListener(networkImageView, R.mipmap.ic_launcher,R.drawable.ic_launcher_foreground));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
