package xiaoxiao.imageloaderdemo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Flim on 2015/12/11.
 */
public class MyAdapter extends BaseAdapter{
    private List<String> uris;
    private LayoutInflater inflater;
    private ImageLoderWithOutCache loderWithOutCache;
    private Context context;

    public MyAdapter(Context context, List<String> uris) {
        this.uris = uris;
        inflater = LayoutInflater.from(context);
        loderWithOutCache = new ImageLoderWithOutCache();
        this.context = context;
    }

    @Override
    public int getCount() {
        return uris.size();
    }

    @Override
    public Object getItem(int position) {
        return uris.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {

    }*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String url = uris.get(position);
        ViewHolder viewHolder = null;
        Log.d("URLS", url);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listview_item, null);
            //fuck!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.img);
            //fuck!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.imageView.setTag(url);
        viewHolder.imageView.setImageResource(android.R.drawable.ic_delete);
//        loderWithOutCache.showImageByAsync(viewHolder.imageView, url);
        Picasso.with(context).load(url).into(viewHolder.imageView);
        Log.d("Picasso","picasso");
        return convertView;
    }


    public class ViewHolder {
        public ImageView imageView;
    }
}
