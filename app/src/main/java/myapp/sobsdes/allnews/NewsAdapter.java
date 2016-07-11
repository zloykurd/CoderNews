package myapp.sobsdes.allnews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZloykurD on 11.07.2016.
 */
public class NewsAdapter extends BaseAdapter {

    List<News> rData = new ArrayList<News>();
    private Activity activity;
    private LayoutInflater inflater;
    Context mContext;

    public NewsAdapter(Activity a, ArrayList<News> rD,
                       Context context) {
        this.mContext = context;
        activity = a;
        rData = rD;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return 0;
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    public static class ViewHolder {
        public ImageView image;
        public TextView textv1;
        public TextView textv2;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;
        if (convertView == null) {
            vi = inflater.inflate(R.layout.newslistadapter, null);
            holder = new ViewHolder();
            holder.image = (ImageView) vi.findViewById(R.id.newsBigImage);
            holder.textv1 = (TextView) vi.findViewById(R.id.newsText);
            holder.textv2 = (TextView) vi.findViewById(R.id.newsBigDate);
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }
        News item = rData.get(position);
        Context context = parent.getContext();
        holder.image.setImageResource(R.drawable.ic_menu_share);
        holder.textv1.setText(item.getNews_title());
        holder.textv2.setText(item.getNews_date());

        vi.setOnClickListener(new OnItemClickListener(position, item));
        // vi.setOnLongClickListener(new View.OnLongClickListener(position, item));
        return vi;
    }
   /* private class OnItemClickListener implements View.OnClickListener {
        private int mPosition;
        private News n;

        OnItemClickListener(int position, News news) {
            mPosition = position;
            n = news;
        }

        public void onClick(View arg0) {
            Intent qIntent = new Intent(mContext, OneNewsActivity.class);
            int flag = 1;
            //qIntent.putExtra("img", String.valueOf(flag));
            qIntent.putExtra("title", String.valueOf(flag));
            qIntent.putExtra("date", String.valueOf(flag));;
            qIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(qIntent);
        }
    }*/
   private class OnItemClickListener implements View.OnClickListener {
       private int mPosition;
       private News n;

       OnItemClickListener(int position, News news) {
           mPosition = position;
           n = news;

       }

       @Override
       public void onClick(View arg0) {
           Intent myIntent = new Intent(mContext, OneNewsActivity.class);
           int flag = 1;
           myIntent.putExtra("flag_place", String.valueOf(flag));
           myIntent.putExtra("title", n.getNews_title());
           myIntent.putExtra("date", n.getNews_date());
           myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           mContext.startActivity(myIntent);


       }

   }

}
