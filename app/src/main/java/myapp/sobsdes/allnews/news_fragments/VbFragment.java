package myapp.sobsdes.allnews.news_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import myapp.sobsdes.allnews.News;
import myapp.sobsdes.allnews.NewsDatabase;
import myapp.sobsdes.allnews.OneNewsActivity;
import myapp.sobsdes.allnews.R;
import myapp.sobsdes.allnews.imageloader.LazyImageLoadNewsAdapter;

/**
 * Created by dimsob on 29.06.16.
 */

public class VbFragment extends ListFragment {
    LazyImageLoadNewsAdapter adapter;
    ListView list;

    public VbFragment() {
    }


    final String LOG_TAG = "NewsLog";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        list = (ListView) getActivity().findViewById(myapp.sobsdes.allnews.R.id.listView);

        adapter = new LazyImageLoadNewsAdapter(getActivity(), getData(),
                getActivity().getApplicationContext());// mStrings
        list.setAdapter(adapter);


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);

        Intent i = new Intent(getActivity(), OneNewsActivity.class);


        i.putExtra("mPosition", position);
        startActivity(i);

    }

    private ArrayList<News> getData() {
        NewsDatabase db = new NewsDatabase(getActivity()
                .getApplicationContext());
        final ArrayList<News> stringItems = new ArrayList<News>();

        ArrayList<News> pr = (ArrayList<News>) db.getAllNews();

        for (News p : pr) {
            stringItems.add(p);
        }

        Log.d("News24Fragment", stringItems.toString());

        return stringItems;

    }
}