package com.example.tuionf.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PopupWindowActivity extends AppCompatActivity implements View.OnClickListener{

    private CustomPopWindow mCustomPopWindow,mListPopWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window);

        findViewById(R.id.left).setOnClickListener(this);
        findViewById(R.id.right).setOnClickListener(this);
        findViewById(R.id.top).setOnClickListener(this);
        findViewById(R.id.bottom).setOnClickListener(this);
        findViewById(R.id.listview).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left:
                //TODO implement
                break;
            case R.id.right:
//                showPopListView();
                break;
            case R.id.top:
                View contentView = LayoutInflater.from(this).inflate(R.layout.popup_left_or_right,null);
                //处理popWindow 显示内容
                handleLogic(contentView);
                //创建并显示popWindow
                mCustomPopWindow= new CustomPopWindow.PopupWindowBuilder(this)
                        .setView(contentView)
                        .create()
                        .showAsDropDown(findViewById(R.id.top),0,20);
                break;
            case R.id.bottom:
                showDownPop(view);
                break;
            case R.id.listview:
                showPopListView();
                break;
        }
    }

    private void showPopListView(){
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_list,null);
        //处理popWindow 显示内容
        handleListView(contentView);
//        handleLogic(contentView);
        //创建并显示popWindow
        mListPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .size(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)//显示大小
                .create()
                .showAsDropDown(findViewById(R.id.listview),0,20);
    }

    private void handleListView(View contentView){
        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        MyAdapter adapter = new MyAdapter();
        adapter.setData(mockData());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(PopupWindowActivity.this, "---"+position+"---", Toast.LENGTH_SHORT).show();
                mListPopWindow.dissmiss();
            }
        });
    }

    private List<String> mockData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("--"+i);
        }
        return list;
    }

    //向下弹出
    public void showDownPop(View view) {

        CustomPopWindow popWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(R.layout.popup_left_or_right)//显示的布局，还可以通过设置一个View
                //     .size(600,400) //设置显示的大小，不设置就默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .create()//创 建PopupWindow
                .showAsDropDown(view,0,10);//显示PopupWindow


    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     * @param contentView
     */
    private void handleLogic(View contentView){

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCustomPopWindow!=null){
                    mCustomPopWindow.dissmiss();
                }
                
                switch(v.getId()){
                    case R.id.tv_like:
                        Toast.makeText(PopupWindowActivity.this, "赞一个", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tv_hate:
                        Toast.makeText(PopupWindowActivity.this, "踩一下", Toast.LENGTH_SHORT).show();
                        break;

                     default:
                        break;   
                }
            }
        };

        contentView.findViewById(R.id.tv_like).setOnClickListener(listener);
        contentView.findViewById(R.id.tv_hate).setOnClickListener(listener);

    }
}
