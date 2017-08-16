package com.example.tuionf.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by tuion on 2017/8/1.
 */

public class CustomDialog extends Dialog implements View.OnClickListener{

    private Context mContext;
    private String content,positiveName,negativeName,title;
    private int positiveColor,negativeColor;
    private OnCloseListener listener;

    private TextView titleTv;
    private TextView contentTv;
    private TextView cancelTv;
    private TextView submitTv;

    public CustomDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public CustomDialog(Context context, String content) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.content = content;
    }

    public CustomDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }

    public CustomDialog(Context context, int themeResId, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    public CustomDialog(Context context, String content, OnCloseListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    protected CustomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public CustomDialog setTitle(String title){
        this.title = title;
        return this;
    }

    public CustomDialog setPositiveButton(String name){
        this.positiveName = name;
        return this;
    }

    public CustomDialog setPositiveButtonColor(int positiveColor){
        this.positiveColor = positiveColor;
        return this;
    }

    public CustomDialog setNegativeButton(String name){
        this.negativeName = name;
        return this;
    }

    public CustomDialog setNegativeButtonColor(int negativeColor){
        this.negativeColor = negativeColor;
        return this;
    }

    public CustomDialog setPosition(int gravity, float alpha){
        Window window = this.getWindow();
        window.setGravity(gravity);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = alpha;
        window.setAttributes(lp);
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_commom);
        setCanceledOnTouchOutside(false);
        initView();
    }


    private void initView() {
        titleTv = (TextView) findViewById(R.id.title);
        contentTv = (TextView) findViewById(R.id.content);
        cancelTv = (TextView) findViewById(R.id.cancel);
        submitTv = (TextView) findViewById(R.id.submit);

        contentTv.setText(content);
        if(!TextUtils.isEmpty(positiveName)){

            submitTv.setTextColor(positiveColor);
            submitTv.setText(positiveName);
        }

        if(!TextUtils.isEmpty(negativeName)){
            cancelTv.setText(negativeName);
            cancelTv.setTextColor(negativeColor);
        }

        if(!TextUtils.isEmpty(title)){
            titleTv.setText(title);
        }

        submitTv.setOnClickListener(this);
        cancelTv.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel:
                if(listener != null){
                    listener.onClick(this, false);
                }
                this.dismiss();
                break;
            case R.id.submit:
                if(listener != null){
                    listener.onClick(this, true);
                }
                break;
        }
    }

    public interface OnCloseListener{
        void onClick(Dialog dialog, boolean confirm);
    }


}
