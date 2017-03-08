package org.live.module.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.live.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private ListView mDemoListView = null;
    private static final String[] mItemTitles = {"推流界面"}; // 列表项名称
    private static final String[] mItemActivityNames = {"org.live.module.publish.view.impl.PublishActivity"}; // 定义目标界面Activity
    private static final String[] mParams = {"rtmp://123.207.19.234/live/stream01"}; // 定义需要携带至目标界面Activity的参数
    private ListItemClickListener listener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listener = new ListItemClickListener(); // 初始化监听类
        initUIElements();
    }

    /**
     * 初始化控件
     */
    private void initUIElements() {
        mDemoListView = (ListView) findViewById(R.id.lv_demo);

        SimpleAdapter adapter = new SimpleAdapter(this,
                getData(),//数据源
                R.layout.item_listview_demo,//显示布局
                new String[]{"content"}, //数据源的属性字段
                new int[]{R.id.tv_demo_item_name}); //布局里的控件id
        //添加并且显示
        mDemoListView.setAdapter(adapter);
        mDemoListView.setOnItemClickListener(listener);
    }

    /**
     * 获取数据
     *
     * @return
     */
    private List<HashMap<String, String>> getData() {

        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < mItemTitles.length; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("content", mItemTitles[i]);
            list.add(map);
        }
        return list;
    }

    /**
     * 列表项监听类
     */
    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            String className = mItemActivityNames[position]; // 目标Activity类名
            String rtmpUrl = mParams[position]; // 目标Activity所需要的参数
            try {
                Class<?> clazz = Class.forName(className);
                Object object = clazz.newInstance();
                Intent intent = new Intent(MainActivity.this, object.getClass());
                if (rtmpUrl != null) {
                    intent.putExtra("rtmpUrl", rtmpUrl); // 携带参数
                }
                startActivity(intent); // 跳转到目标界面
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}