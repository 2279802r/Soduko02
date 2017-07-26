package com.soduko02.soduko02;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

/**
 * Created by liulianqing on 17/7/16.
 */

//该类用于实现Dialog，实现自定义的对话框功能
public class KeyDialog extends Dialog{
    //用来存放代表对话框当中按钮的对话
    private final View keys[] = new View[9];
    private final int used[];

    private sodukoView sodukoView;

    //构造函数的第二个参数当中保存这当前单元格已经使用过的数字
    public KeyDialog(Context context, int[] used, sodukoView sodukoView){
        super(context);
        this.used = used;
        this.sodukoView = sodukoView;
    }

    //当一个Dialog第一次显示的时候，会调用其onCreate方法
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTitle("KeyDialog");//设置对话框标题
        setContentView(R.layout.keypad);//用于为该Dialog设置布局文件
        findView();
        //判断可用或不可用数字
        //遍历整个used数组，
        for (int i = 0; i < used.length; i++){
            if (used[i] != 0){
                keys[used[i]-1].setVisibility(View.INVISIBLE);//不可见
            }
        }
        //为对话框当中所有的按钮设置监听器
        setListeners();
    }

    //找到9个按钮对象，以方便设置监听器
    private void findView(){
        keys[0]=findViewById(R.id.keypad_1);
        keys[1]=findViewById(R.id.keypad_2);
        keys[2]=findViewById(R.id.keypad_3);
        keys[3]=findViewById(R.id.keypad_4);
        keys[4]=findViewById(R.id.keypad_5);
        keys[5]=findViewById(R.id.keypad_6);
        keys[6]=findViewById(R.id.keypad_7);
        keys[7]=findViewById(R.id.keypad_8);
        keys[8]=findViewById(R.id.keypad_9);
    }

    //通知sodukoView对象，刷新整个九宫格显示的数据
    //调用sodukoView对象的setSelectedNum方法
    private void returnResult(int temp){
        sodukoView.setSelectedTile(temp);
        //取消对话框的显示
        dismiss();
    }

    private void setListeners(){
        //遍历整个keys数组，为每一个keys对象设置监听器
        for (int i = 0; i < keys.length; i++){
            final int selectedNum = i + 1;
            keys[i].setOnClickListener(new View.OnClickListener() {
                //在监听器当中调用returnResult方法
                @Override
                public void onClick(View view) {
                    returnResult(selectedNum);
                }
            });
        }
    }
}
