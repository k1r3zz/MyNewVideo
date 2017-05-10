package com.example.administrator.mynewvideo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.fragment.FirstFragment;
import com.example.administrator.fragment.SecongFragment;
import com.example.administrator.fragment.ThridFragment;


public class MenuActivity extends AppCompatActivity {
    private Button zhangmeng_but, hezi_but, mine_but;
    private FragmentManager fm;
    private Fragment NowFragment;


    private FirstFragment firstFragment = new FirstFragment();
    private SecongFragment secongFragment = new SecongFragment();
    private ThridFragment thridFragment = new ThridFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);
        initView();
        Fade fade = new Fade();
        fade.setDuration(500);
        Slide slide = new Slide(Gravity.LEFT);
        slide.setDuration(500);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);


    }

    private void initView() {
        zhangmeng_but = (Button) findViewById(R.id.zhangmeng_but);
        hezi_but = (Button) findViewById(R.id.box_but);
        mine_but = (Button) findViewById(R.id.mine_but);

        Drawable drawable = getResources().getDrawable(R.drawable.tabfire);
        drawable.setBounds(0, 7, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        zhangmeng_but.setCompoundDrawables(null, drawable, null, null);

        Drawable drawable2 = getResources().getDrawable(R.drawable.tablive);
        drawable2.setBounds(0, 7, drawable2.getMinimumWidth(),
                drawable2.getMinimumHeight());
        hezi_but.setCompoundDrawables(null, drawable2, null, null);

        Drawable drawable3 = getResources().getDrawable(R.drawable.tabfme);
        drawable3.setBounds(0, 7, drawable3.getMinimumWidth(),
                drawable3.getMinimumHeight());
        mine_but.setCompoundDrawables(null, drawable3, null, null);

        fm = getSupportFragmentManager();

        FragmentTransaction transaction = fm.beginTransaction().setCustomAnimations(R.anim.popwindow_anim, R.anim.popwindow_anim);
        transaction.add(R.id.main_framelayout, firstFragment).commit();
        NowFragment = firstFragment;
        //transaction.addToBackStack(null);
        zhangmeng_but.setSelected(true);
        zhangmeng_but.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = fm.beginTransaction().setCustomAnimations(R.anim.popwindow_anim, R.anim.popwindow_anim);
//                transaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_right_in);
                if (!firstFragment.isAdded()) {
                    transaction.hide(NowFragment).add(R.id.main_framelayout, firstFragment).commit();
                } else {
                    transaction.hide(NowFragment).show(firstFragment).commit(); // 隐藏当前的fragment，显示下一个
                }
                NowFragment = firstFragment;
                // transaction .addToBackStack(null);


                // zhangmeng_but.setFocusable(true);
                // zhangmeng_but.requestFocus();
                // zhangmeng_but.requestFocusFromTouch();
                zhangmeng_but.setSelected(true);
                mine_but.setSelected(false);
                hezi_but.setSelected(false);
            }
        });

        hezi_but.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = fm.beginTransaction().setCustomAnimations(R.anim.popwindow_anim, R.anim.popwindow_anim);
//                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                if (!secongFragment.isAdded()) {
                    transaction.hide(NowFragment).add(R.id.main_framelayout, secongFragment).commit();
                } else {
                    transaction.hide(NowFragment).show(secongFragment).commit(); // 隐藏当前的fragment，显示下一个
                }
                NowFragment = secongFragment;
                // transaction .addToBackStack(null);

                hezi_but.setSelected(true);
                zhangmeng_but.setSelected(false);
                mine_but.setSelected(false);
            }
        });
        mine_but.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = fm.beginTransaction().setCustomAnimations(R.anim.popwindow_anim, R.anim.popwindow_anim);
//                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

                if (!thridFragment.isAdded()) {
                    transaction.hide(NowFragment).add(R.id.main_framelayout, thridFragment).commit();
                } else {
                    transaction.hide(NowFragment).show(thridFragment).commit(); // 隐藏当前的fragment，显示下一个
                }
                NowFragment = thridFragment;
                //  transaction.addToBackStack(null);

                hezi_but.setSelected(false);
                zhangmeng_but.setSelected(false);
                mine_but.setSelected(true);

            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    1);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted

            } else {
                // Permission Denied
                //  displayFrameworkBugMessageAndExit();
                Toast.makeText(this, "请在应用管理中打开“相机,video”访问权限！", Toast.LENGTH_LONG).show();
                finish();
            }

        }
    }


}
