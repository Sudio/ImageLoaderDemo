package xiaoxiao.imageloaderdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> mDatas;
    private ListView mListView;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listview);

        mDatas = Arrays.asList(ImageUtils.IMAGES);

        myAdapter = new MyAdapter(this, mDatas);
        mListView.setAdapter(myAdapter);
    }


}
