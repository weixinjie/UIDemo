package will.github.com.uidemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import will.github.com.uidemo.custom_refresh.CustomRefreshView;


public class MainActivity extends AppCompatActivity {

    ListView lv_content;
    ArrayList<String> catalogs;
    ArrayAdapter<String> adapter;
    private CustomRefreshView refreshView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_content = findViewById(R.id.lv_content);
        refreshView = findViewById(R.id.refreshView);

        init();
    }

    private void init() {
        catalogs = new ArrayList<String>();
        // 为主页目录添加条目，以后每多写一个例子，在这里添加一个条目就可以了。
        // 不像以前MainActivity中每多写一个例子，需要添加一个按钮，还要写相应的点击事件。
        catalogs.add("weixinjie");
        catalogs.add("zhangrui");
        catalogs.add("hehehe");
        catalogs.add("weixinjie");
        catalogs.add("zhangrui");
        catalogs.add("hehehe");
        catalogs.add("weixinjie");
        catalogs.add("zhangrui");
        catalogs.add("hehehe");
        catalogs.add("weixinjie");
        catalogs.add("zhangrui");
        catalogs.add("hehehe");
        catalogs.add("weixinjie");
        catalogs.add("zhangrui");
        catalogs.add("hehehe");
        catalogs.add("weixinjie");
        catalogs.add("zhangrui");
        catalogs.add("hehehe");
        catalogs.add("weixinjie");
        catalogs.add("zhangrui");
        catalogs.add("hehehe");
        catalogs.add("weixinjie");
        catalogs.add("zhangrui");
        catalogs.add("hehehe");
        catalogs.add("weixinjie");
        catalogs.add("zhangrui");
        catalogs.add("hehehe");
        catalogs.add("weixinjie");
        catalogs.add("zhangrui");
        catalogs.add("hehehe");
        catalogs.add("weixinjie");
        catalogs.add("zhangrui");
        catalogs.add("hehehe");
        catalogs.add("weixinjie");
        catalogs.add("zhangrui");
        catalogs.add("hehehe");

        // 这里ListView的适配器选用ArrayAdapter，ListView中每一项的布局选用系统的simple_list_item_1。
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, catalogs);
        lv_content.setAdapter(adapter);

        // 通过一个实现OnItemClickListener接口的匿名类的onItemClick方法来处理ListView中每一项的点击事件。
        lv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("---------", "用户正在点击");
            }
        });


        refreshView.setOnRefreshListener(new CustomRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getBaseContext(), "onRefresh", Toast.LENGTH_SHORT).show();
                lv_content.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshView.finishComplete();
                        adapter.notifyDataSetChanged();
                    }
                }, 3000);
            }

            @Override
            public void onLoadMore() {
                Toast.makeText(getBaseContext(), "onLoadMore", Toast.LENGTH_SHORT).show();
                lv_content.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        catalogs.add("我是新加入的测试数据");
                        adapter.notifyDataSetChanged();
                        refreshView.finishComplete();
                    }
                }, 3000);
            }
        });

    }
}
