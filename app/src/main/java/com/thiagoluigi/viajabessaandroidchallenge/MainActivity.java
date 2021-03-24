package com.thiagoluigi.viajabessaandroidchallenge;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button1, button2, button3, fetchButton;
    NetworkImageView networkImageView1, networkImageView2, networkImageView3;
    TextView packageName1, packageName2, packageName3, packageValue1, packageValue2, packageValue3;
    ArrayList<Package> mPackages = new ArrayList<>();
    String BASE_URL = "https://private-9ad56-viajabessa83.apiary-mock.com";
    int numberOfRequestsCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        fetchButton = findViewById(R.id.fetch);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        fetchButton.setOnClickListener(this);

        packageName1 = findViewById(R.id.packageName1);
        packageName2 = findViewById(R.id.packageName2);
        packageName3 = findViewById(R.id.packageName3)
        ;
        packageValue1 = findViewById(R.id.packageValue1);
        packageValue2 = findViewById(R.id.packageValue2);
        packageValue3 = findViewById(R.id.packageValue3);

        networkImageView1 = findViewById(R.id.NetworkImageView1);
        networkImageView2 = findViewById(R.id.NetworkImageView2);
        networkImageView3 = findViewById(R.id.NetworkImageView3);

        networkImageView1.setDefaultImageResId(R.mipmap.ic_launcher);
        networkImageView2.setDefaultImageResId(R.mipmap.ic_launcher);
        networkImageView3.setDefaultImageResId(R.mipmap.ic_launcher);

        packagesFetch(BASE_URL);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.fetch:
                packagesFetch(BASE_URL);
                break;
            case R.id.button1:
                Log.d("package", mPackages.get(0).toString());
                startActivity(new Intent(MainActivity.this, DetailsViewActivity.class).putExtra("package", mPackages.get(0)));
                break;
            case R.id.button2:
                startActivity(new Intent(MainActivity.this, DetailsViewActivity.class).putExtra("package", mPackages.get(1)));
                break;
            case R.id.button3:
                startActivity(new Intent(MainActivity.this, DetailsViewActivity.class).putExtra("package", mPackages.get(2)));
                break;
            default:
                break;
        }
    }

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            if (error instanceof NetworkError) {
                Toast.makeText(getApplicationContext(), getString(R.string.no_network), Toast.LENGTH_LONG).show();
            }
        }
    };

    private void packagesFetch(String BASE_URL) {
        mPackages.clear();
        numberOfRequestsCompleted = 0;
        VolleyLog.DEBUG = true;
        RequestQueue queue = Request.getInstance(getApplicationContext()).getRequestQueue();
        String uri_packages = BASE_URL + "/pacotes";
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, uri_packages, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals(null)) {
                    Gson mGson = new Gson();
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        Log.d("responseJson", jsonArray.toString());
                        for(int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Package mPackage = mGson.fromJson(jsonObject.toString(), Package.class);
                                mPackages.add(mPackage);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ++numberOfRequestsCompleted;
                }
            }
        }, errorListener) {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
            @Override
            public Priority getPriority() {
                return Priority.IMMEDIATE;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Version", String.valueOf(Build.VERSION.SDK_INT));
                params.put("Manufacturer", Build.MANUFACTURER);
                params.put("Model", Build.MODEL);
                return params;
            }
        };

        queue.add(stringRequest);
        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {

            @Override
            public void onRequestFinished(com.android.volley.Request<Object> request) {
                if (numberOfRequestsCompleted == 1) {
                    numberOfRequestsCompleted = 0;
                    setImage();
                    setName();
                    setValue();
                }
            }
        });
    }

    private void setImage() {
        imageLoader(mPackages.get(0).getImageLink(), networkImageView1);
        imageLoader(mPackages.get(1).getImageLink(), networkImageView2);
        imageLoader(mPackages.get(2).getImageLink(), networkImageView3);
    }

    private void setName() {
        packageName1.setText(mPackages.get(0).getName());
        packageName2.setText(mPackages.get(1).getName());
        packageName3.setText(mPackages.get(2).getName());
    }

    private void setValue() {
        packageValue1.setText(mPackages.get(0).getValueTxt());
        packageValue2.setText(mPackages.get(1).getValueTxt());
        packageValue3.setText(mPackages.get(2).getValueTxt());
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

}