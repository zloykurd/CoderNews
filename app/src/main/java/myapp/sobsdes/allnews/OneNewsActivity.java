package myapp.sobsdes.allnews;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class OneNewsActivity extends AppCompatActivity {
    ImageView setimage;
    TextView settextv1;
    TextView settextv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        setimage = (ImageView) findViewById(R.id.newsBigImage);
        settextv1 = (TextView) findViewById(R.id.newsText);
        settextv2 = (TextView) findViewById(R.id.newsBigDate);


        Intent intent = getIntent();
        //String inimg = intent.getStringExtra("img");
        String intitle = intent.getStringExtra("n_title");
        String indate = intent.getStringExtra("n_date");
        String news_source = intent.getStringExtra("n_source");
        //setimage.setImageDrawable(inimg);
        getSupportActionBar().setTitle(intitle);
        settextv1.setText(news_source);
        settextv2.setText(indate);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
