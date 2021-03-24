package com.thiagoluigi.viajabessaandroidchallenge;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Request {
    private static Request mInstance;
    private Context mContext;
    private RequestQueue mRequestQueue;

    private Request(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized Request getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Request(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if(mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }
}
