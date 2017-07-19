package com.dmspro.trieungo.hackernewapi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dmspro.trieungo.hackernewapi.Adapter.AdapterPost;
import com.dmspro.trieungo.hackernewapi.DAL.PostDAL;
import com.dmspro.trieungo.hackernewapi.DAL.SQLiteDataAccessHelper;
import com.dmspro.trieungo.hackernewapi.DTO.PostDTO;
import com.github.pwittchen.infinitescroll.library.InfiniteScrollListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public List listIdOf500Stories;
    public String[] arrURL;
    public List<PostDTO> mListPosts;
    public RecyclerView recyclerView;
    public AdapterPost adapterPost;
    LinearLayoutManager layoutManager;

    public Button testbtn;

    private SQLiteDataAccessHelper sqliteDataAccessHelper;
    private PostDAL postDAL;

    private static final int MAX_ITEMS_PER_REQUEST = 20;
    private static final int SIMULATED_LOADING_TIME_IN_MS = 1000;
    public ProgressBar progressBar;
    private int page;

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

        postDAL.deleteAllPost();
        progressBar.setVisibility(View.VISIBLE);

        testbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mListPosts = postDAL.getAllPost();
//                PostDTO posttest = postDAL.getPostByID(Integer.parseInt(mListPosts.get(0).getmPostID()));
//                Log.d("post by id: ", posttest.getmPostID() +" -- " + posttest.getmTitle());
//                for (int i = 0; i < mListPosts.size(); i++) {
//                    Log.d("check db: ","stt: " + i + " -- " + String.valueOf(mListPosts.size()) + " -- "
//                            + mListPosts.get(i).getmPostID() + " -- " + mListPosts.get(i).getmTitle());
//                }

                Toast.makeText(MainActivity.this, "Co Gi Trong Nay Dau Ma Click :V :V :V", Toast.LENGTH_LONG).show();
            }
        });



    }

    private void initView() {
        listIdOf500Stories = new ArrayList();
        mListPosts = new ArrayList<>();
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        testbtn = (Button) findViewById(R.id.testbtn);

        sqliteDataAccessHelper = new SQLiteDataAccessHelper(getApplicationContext());
        postDAL = new PostDAL(getApplicationContext());

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_home);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


    }

    @NonNull
    private InfiniteScrollListener createInfiniteScrollListener() {
        return new InfiniteScrollListener(MAX_ITEMS_PER_REQUEST, layoutManager) {
            @Override
            public void onScrolledToEnd(final int firstVisibleItemPosition) {
                simulateLoading();

                int start = ++page * MAX_ITEMS_PER_REQUEST;
                final boolean allItemsLoaded = start >= mListPosts.size();
                if (allItemsLoaded) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    int end = start + MAX_ITEMS_PER_REQUEST;
                    final List<PostDTO> itemsLocal;

                    String[] arr20URLNext = new String[20];

//                    for(int i = start; i < end; i++)
//                    {
//                        arr20URLNext[i] = arrURL[i];
//                    }

                    if(end <= mListPosts.size())
                    {
                         itemsLocal = getItemsToBeLoaded(start, end);
                    }
                    else
                    {
                         itemsLocal = getItemsToBeLoaded(start, mListPosts.size());
                    }

                    refreshView(recyclerView, new AdapterPost(itemsLocal), firstVisibleItemPosition);
                }
            }
        };
    }

    @NonNull
    private List<PostDTO> getItemsToBeLoaded(int start, int end) {
        List<PostDTO> newItems = mListPosts.subList(start, end);
        final List<PostDTO> oldItems = ((AdapterPost) recyclerView.getAdapter()).getItems();
        final List<PostDTO> itemsLocal = new LinkedList<>();
        itemsLocal.addAll(oldItems);
        itemsLocal.addAll(newItems);
        return itemsLocal;
    }

    private void simulateLoading() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(SIMULATED_LOADING_TIME_IN_MS);
                } catch (InterruptedException e) {
                    Log.e("MainActivity", e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void param) {
                progressBar.setVisibility(View.GONE);
            }
        }.execute();
    }



    public class readJSONTop500Post extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            String chuoi = getXMLFromURL(params[0]);
            try {
                JSONArray itemArray = new JSONArray(chuoi);
                Log.d("jsonasd ", String.valueOf(itemArray.length()));

                postDAL.deleteAllPost();
                for (int i = 0; i < itemArray.length(); i++) {
                    int value = itemArray.getInt(i);
                    listIdOf500Stories.add(value);

                    Log.d("Array 500 ID --", i + " = " + value);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return chuoi;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            arrURL = new String[listIdOf500Stories.size()];
            String[] arrURLFirst = new String[50];

            for(int i = 0; i < arrURL.length; i++)
            {
                arrURL[i] = "https://hacker-news.firebaseio.com/v0/item/" + listIdOf500Stories.get(i) + ".json?print=pretty";
                if(i < 50)
                {
                    arrURLFirst[i] = arrURL[i];
                }
            }


            getJSONByID(arrURLFirst);
        }

    }

    public void getJSONByID(final String[] arrURL) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new readJSONByIdPost().execute(arrURL);
            }
        });
    }

    public class readJSONByIdPost extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {


            for(int i = 0; i < params.length; i++)
            {
                String postJSON = getXMLFromURL(params[i]);
                try {
                    JSONObject root = new JSONObject(postJSON);

                    String id = root.optString("id");
                    String title = root.optString("title");
                    String author = root.optString("by");
                    String time = root.optString("time");
                    String type = root.optString("type");
                    String url = root.optString("url");
                    String descendants = root.optString("descendants");
                    String score = root.optString("score");

                    PostDTO post = new PostDTO(id, title, author, score, time, type, url, descendants);
                    postDAL.doInsertPost(post);

                    Log.d("Object: ", id + " -- " + title + " -- " + author + " -- "
                            + time + " -- " + type  + " -- " + descendants + " -- " + url);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return "OO";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressBar.setVisibility(View.GONE);

            mListPosts = postDAL.getAllPost();
            if(mListPosts.size() != 0)
            {
                recyclerView.setAdapter(new AdapterPost(mListPosts.subList(page, MAX_ITEMS_PER_REQUEST)));
            }
            recyclerView.addOnScrollListener(createInfiniteScrollListener());

//            adapterPost = new AdapterPost(MainActivity.this, mListPosts);
//            recyclerView.setAdapter(adapterPost);

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
