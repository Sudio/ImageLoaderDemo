package xiaoxiao.imageloaderdemo;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Flim on 2015/12/14.
 */
public class MyAdapterWithCache extends BaseAdapter implements AbsListView.OnScrollListener{
    private List<String> mDatas;
    private LayoutInflater inflater;

    public MyAdapterWithCache(List<String> mDatas, Context context) {
        this.mDatas = mDatas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        String url = mDatas.get(position);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.listview_item,null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(viewHolder);
        }
        viewHolder.imageView.setTag(url);
        viewHolder.imageView.setImageResource(android.R.drawable.ic_delete);
        //加载图片
        return convertView;

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
            if(scrollState == SCROLL_STATE_IDLE){

            }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    class ViewHolder{
        ImageView imageView;
    }
}
