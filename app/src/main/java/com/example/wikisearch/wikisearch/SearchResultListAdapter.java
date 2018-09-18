package com.example.wikisearch.wikisearch;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;


public class SearchResultListAdapter extends ArrayAdapter {

    private final Activity context;
    private final SearchResultModel searchResultModel;

    public SearchResultListAdapter(Activity context, SearchResultModel searchResultModelParam){

        super(context,R.layout.search_result_view,searchResultModelParam.query.pages);
        this.context=context;
        this.searchResultModel = searchResultModelParam;
    }

    public View getView(int position, View view, ViewGroup parent) {

        try {
            LayoutInflater inflater = context.getLayoutInflater();
            View searchResultRow = inflater.inflate(R.layout.search_result_view, parent, false);

            //this code gets references to objects in the search_result_view.xml file
            TextView title = searchResultRow.findViewById(R.id.title);
            TextView description = searchResultRow.findViewById(R.id.description);
            NetworkImageView thumbnailImage = searchResultRow.findViewById(R.id.thumbnail_image);

            //this code sets the values of the objects to values from the arrays
            if (searchResultModel.query.pages[position] != null) {
                title.setText(searchResultModel.query.pages[position].title);

                if (searchResultModel.query.pages[position].terms != null) {
                    description.setText(searchResultModel.query.pages[position].terms.description[0]);
                } else {
                    description.setText(searchResultModel.query.pages[position].title);
                }

                thumbnailImage.setDefaultImageResId(R.drawable.default_image);

                //load the thumbnail image
                if (searchResultModel.query.pages[position].thumbnail != null) {
                    imageLoader(thumbnailImage, searchResultModel.query.pages[position].thumbnail.source);
                }
            }

            return searchResultRow;
        }
        catch (Exception ex)
        {
            Log.e(ex.getMessage(),ex.getStackTrace().toString());
        }

        return null;
    };


    private void imageLoader(NetworkImageView networkImageView,String imageUrl) {
        RequestQueue mRequestQueue = SingletonRequestQueue.getInstance(context).getRequestQueue();

        ImageLoader imageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache mCache = new LruCache(10);

            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }

            public Bitmap getBitmap(String url) {
                return (Bitmap) mCache.get(url);
            }
        });

        networkImageView.setImageUrl(imageUrl, imageLoader);
        imageLoader.get(imageUrl, ImageLoader.getImageListener(
                networkImageView, R.drawable.default_image, R.drawable.default_image));
    }

}
