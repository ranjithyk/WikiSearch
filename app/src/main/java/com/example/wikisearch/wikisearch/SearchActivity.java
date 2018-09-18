package com.example.wikisearch.wikisearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

public class SearchActivity extends BaseActivity {

    SearchResultModel searchResultModel;
    EditText seachText;
    ListView searchResultListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setTextChangeListnerForSearchText();
    }

    //Add text change listner to search text edit field
    private void setTextChangeListnerForSearchText(){

        seachText = findViewById(R.id.search_text);
        seachText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(charSequence.length() > 0) {
                    //When the text changed call wiki API and get the search result
                    getSearchResult(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void getSearchResult(String searchText) {

        //Build the url string including the entered search text
        String url = urlBuilder(searchText);

        //create a JsonObject request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        //when API returned the response add the results to the list view
                        setResultListView(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if(IsNetworkAvailable()) {
                            Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "No Internet!.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //Add JsonObject request to the request queue
        RequestQueue queue =  SingletonRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
        queue.add(jsonObjectRequest);
    }

    private void setResultListView(JSONObject response) {

        try {
            //convert the response into Model class
            GsonBuilder builder = new GsonBuilder();
            Gson mGson = builder.create();
            searchResultModel = mGson.fromJson(response.toString(), SearchResultModel.class);

            if (searchResultModel == null || searchResultModel.query == null)
                return;

            SearchResultListAdapter searchResultListAdapter = new SearchResultListAdapter(this, searchResultModel);
            searchResultListView = findViewById(R.id.search_result_list);
            searchResultListView.setAdapter(searchResultListAdapter);

            searchResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getApplicationContext(), WikiContentActivity.class);
                    intent.putExtra("PAGE_ID", searchResultModel.query.pages[position].pageid);

                    //When user clicked the list item launch the wiki page
                    startActivity(intent);
                }
            });
        }
        catch (Exception ex) {
            Log.e(ex.getMessage(),ex.getStackTrace().toString());
        }
    }

    //Build the url for the wiki API request
    private String urlBuilder(String searchText){

        String baseUrl = Constants.SEARCH_BASE_URL;
        String queryString = "action=query&format=json&prop=pageimages%7Cpageterms&generator=prefixsearch&redirects=1&formatversion=2&piprop=thumbnail&pithumbsize=50&pilimit=10&wbptterms=description&gpslimit=10&";
        queryString = queryString + "gpssearch=" + searchText;
        return baseUrl + queryString;
    }
}
