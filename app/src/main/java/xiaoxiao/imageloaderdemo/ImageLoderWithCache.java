package xiaoxiao.imageloaderdemo;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Flim on 2015/12/13.
 */
public class ImageLoderWithCache {
    private Set<AsyncDownloadImage> mTasks;
    private LruCache<String,Bitmap> mMemoryCache;
    private ListView mListview;
    public ImageLoderWithCache(ListView listView){
        this.mListview = listView;
        mTasks = new HashSet<>();
        int maxMemory = (int) (Runtime.getRuntime().maxMemory()/10);
        mMemoryCache = new LruCache<String,Bitmap>(maxMemory){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public void showImage(String url,ImageView imageView){
        Bitmap bitmap = getBitmapFromMemoryCaches(url);
        if(bitmap == null){
            imageView.setImageResource(android.R.drawable.ic_delete);
        }else{
            imageView.setImageBitmap(bitmap);
        }
    }

    public void addBitmapToCache(String url,Bitmap bitmap){
        if(mMemoryCache.get(url) == null){
            mMemoryCache.put(url, bitmap);
        }
    }

    public Bitmap getBitmapFromMemoryCaches(String url){
        return mMemoryCache.get(url);
    }

    public void cancelAllTasks(){
        if(mTasks != null){
            for(AsyncDownloadImage task:mTasks){
                task.cancel(false);
            }
        }
    }

    class AsyncDownloadImage extends AsyncTask<String,Void,Bitmap>{
        String url;

        public AsyncDownloadImage(String url) {
            this.url = url;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            url = params[0];
            Bitmap bitmap = getBitmapFromMemoryCaches(url);
            if(bitmap !=null){
                addBitmapToCache(url,bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView = (ImageView) mListview.findViewWithTag(url);
            if(imageView !=null &&bitmap == null){
                imageView.setImageBitmap(bitmap);
            }
            mTasks.remove(this);
        }
    }
}
