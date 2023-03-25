package com.example.myapplication;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import android.view.GestureDetector;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{

    private WebView mWebView;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = findViewById(R.id.web_view);

        // 开启JavaScript支持
        mWebView.getSettings().setJavaScriptEnabled(true);

        // 设置可以获取焦点
        mWebView.setFocusable(true);
        mWebView.setFocusableInTouchMode(true);

        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        // 加载网页
        mWebView.loadUrl("https://keycode-visualizer.netlify.app");

        // 初始化手势识别器
        initGestureDetector();
    }


    //触摸手势识别
    private void initGestureDetector()
    {
        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onDown(MotionEvent e)
            {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
            {
                float distanceX = e2.getX() - e1.getX();
                float distanceY = e2.getY() - e1.getY();
                if (Math.abs(distanceX) > Math.abs(distanceY))
                {
                    if (distanceX > 0)
                    {
                        // 右滑动，模拟按下D键
                        onKeyDown(KeyEvent.KEYCODE_D, null);
                    } else
                    {
                        // 左滑动，模拟按下A键
                        onKeyDown(KeyEvent.KEYCODE_A, null);
                    }
                } else
                {
                    if (distanceY > 0)
                    {
                        // 下滑动，模拟按下S键
                        onKeyDown(KeyEvent.KEYCODE_S, null);
                    } else
                    {
                        // 上滑动，模拟按下W键
                        onKeyDown(KeyEvent.KEYCODE_W, null);
                    }
                }
                return true;
            }
        });

        // 设置WebView的触摸事件处理器
        mWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (mWebView != null)
        {
            switch (keyCode)
            {
                case KeyEvent.KEYCODE_W:
                    mWebView.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_UP));
                    mWebView.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_UP));
                    return true;
                case KeyEvent.KEYCODE_S:
                    mWebView.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN));
                    mWebView.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_DOWN));
                    return true;
                case KeyEvent.KEYCODE_A:
                    mWebView.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_LEFT));
                    mWebView.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_LEFT));
                    return true;
                case KeyEvent.KEYCODE_D:
                    mWebView.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT));
                    mWebView.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_RIGHT));
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}