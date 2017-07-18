package com.dmspro.trieungo.hackernewapi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dmspro.trieungo.hackernewapi.Adapter.AdapterPost;
import com.dmspro.trieungo.hackernewapi.DTO.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public List listIdOf500Stories;
    public List<Post> mListPosts;
    public RecyclerView recyclerView;
    public AdapterPost adapterPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new readJSONTop500Post().execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty");
            }
        });



    }

    private void initView() {
        listIdOf500Stories = new ArrayList();
        mListPosts = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_home);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    public class readJSONTop500Post extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            String chuoi = getXMLFromURL(params[0]);
            try {
                JSONArray itemArray = new JSONArray(chuoi);
                Log.d("jsonasd ", String.valueOf(itemArray.length()));

                for (int i = 0; i < 30; i++) {
                    int value = itemArray.getInt(i);
                    listIdOf500Stories.add(value);

                    getJSONByID(value);

                    Log.d("json500ID ", i + " = " + value);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return chuoi;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("checklast ", "da hoan thanh");
        }

    }

    public void getJSONByID(int value) {
        final String url = "https://hacker-news.firebaseio.com/v0/item/" + value + ".json?print=pretty";
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new readJSONByIdPost().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
            }
        });
    }

    public class readJSONByIdPost extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            String post = getXMLFromURL(params[0]);
            return post;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject root = new JSONObject(s);

                String id = root.getString("id");
                String title = root.getString("title");
                String author = root.getString("by");
                String time = root.getString("time");
                String type = root.getString("type");
                String url = root.getString("url");
                String descendants = root.getString("descendants");
                String score = root.getString("score");


                Post post = new Post( id,  title,  author,  score,  time,  type,  url,  descendants);
                mListPosts.add(post);

                adapterPost = new AdapterPost(getApplicationContext(), mListPosts);
                recyclerView.setAdapter(adapterPost);

                Log.d("Title : ",  id + " -- " + title + " -- " + author  + " -- "
                        + time + " -- " + type + " -- " + url + " -- " + descendants);
                Log.d("size mlistpostsize : ", String.valueOf(mListPosts.size()) + " -- " + mListPosts.get(0).getmTitle());

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private String getXMLFromURL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
