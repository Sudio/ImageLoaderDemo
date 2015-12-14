package xiaoxiao.imageloaderdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Flim on 2015/12/12.
 */
public class ImageLoderWithOutCache {

    private Handler mHandler;

    public void showImageByThread(final ImageView imgview,final String url){
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                ImgHolder holder = (ImgHolder) msg.obj;
                if(holder.imageView.getTag().equals(holder.url)){
                    holder.imageView.setImageBitmap(holder.bitmap);
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = getBitmapFromUrl(url);
                Message message = Message.obtain();
                message.obj = new ImgHolder(bitmap,imgview,url);
                mHandler.sendMessage(message);
            }
        }).start();
    }

    public void showImageByAsync(ImageView imageView,String url){
        ASyncDownLoader task = new ASyncDownLoader(imageView,url);
        task.execute(url);
    }

    private static Bitmap getBitmapFromUrl(String urlString){
        Bitmap bitmap;
        InputStream is = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            connection.disconnect();
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(is != null)
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }

    private class ImgHolder{
        public Bitmap bitmap;
        public ImageView imageView;
        public String url;

        public ImgHolder(Bitmap bitmap, ImageView imageView, String url) {
            this.bitmap = bitmap;
            this.imageView = imageView;
            this.url = url;
        }
    }
    private class ASyncDownLoader extends AsyncTask<String,Void,Bitmap>{
        private ImageView image;
        private String url;

        public ASyncDownLoader(ImageView image, String url) {
            this.image = image;
            this.url = url;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return getBitmapFromUrl(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(image.getTag().equals(url)){
                image.setImageBitmap(bitmap);
            }
        }
    }
}
