package com.example.administrator.listviewlearn;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView listview;
    private List<Map<String,?>> list;
    private Map<String,Object> map;
    private ArrayAdapter listAdapter;
    private PullToRefreshListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mListView = (PullToRefreshListView) findViewById(R.id.list_refresh);
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        listview = mListView.getRefreshableView();
        list = new ArrayList<Map<String, ?>>();
        for(int i = 0;i<5;i++)
        {
            map = new HashMap<String,Object>();
            map.put("Text","Text"+i);
            map.put("Button","Button"+i);
            map.put("img",R.drawable.test);
            list.add(map);
        }

        listAdapter = new MyAdapter(this,R.layout.list_item);
        for(int i = 0;i<5;i++) {
            listAdapter.add(new User("Text"+i, "button"+i, R.drawable.test));
        }
        listview.setAdapter(listAdapter);

        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                new GetDataTask().execute();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new GetDataTask().execute();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class GetDataTask extends AsyncTask<Void,Void,String>
    {

        @Override
        protected String doInBackground(Void... voids) {
            try
            {
                Thread.sleep(2000);
            }catch(InterruptedException e)
            {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            listAdapter.add(new User("Text","button",R.drawable.test));
            listAdapter.notifyDataSetChanged();
            mListView.onRefreshComplete();
        }

    }

    class MyAdapter extends ArrayAdapter<User>
    {
        private int  mResouseId;
        public MyAdapter(Context context, int resource) {
            super(context, resource);
            mResouseId = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            User user = getItem(position);
            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(mResouseId, null);
            TextView textView = (TextView) view.findViewById(R.id.text1);
            Button button = (Button) view.findViewById(R.id.button1);
            ImageView img = (ImageView) view.findViewById(R.id.img1);
            textView.setText(user.getText());
            button.setText(user.getButton());
            img.setImageResource(user.getImg());

            return  view;
        }
    }
    class User
    {
        private String mText;
        private String mButton;
        private  int  mImg;
        public User(String text,String button,int img)
        {
            mText=text;
            mButton=button;
            mImg=img;
        }
        public String getText()
        {
            return  mText;
        }
        public String getButton()
        {
            return  mButton;
        }

        public int getImg()
        {
            return mImg;
        }


    }

}
