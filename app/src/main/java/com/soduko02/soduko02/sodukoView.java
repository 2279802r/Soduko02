package com.soduko02.soduko02;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by liulianqing on 17/7/13.
 */

public class sodukoView extends View{

    //单元格的宽度和高度
    private float width;
    private float height;
    private Paint backgroundPaint;
    private Paint darkPaint;
    private Paint whitePaint;
    private Paint lightPaint;
    private Paint numberPaint;
    private Game game;//声明game的引用与对象
    private int selectedX;
    private int selectedY;

    public sodukoView(Context context) {
        super(context);
        backgroundPaint = new Paint();//生成用于绘制背景色的画笔
        darkPaint = new Paint();//生成用于暗色画笔
        whitePaint = new Paint();//生成用于提醒画笔
        lightPaint = new Paint();//生成用于亮色画笔
        numberPaint = new Paint();
        game = new Game();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //计算当前单元格的宽度和高度
        this.width = w / 9f;
        this.height = h / 9f;
//        super.onSizeChanged(w,h,oldw,oldh);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        backgroundPaint.setColor(getResources().getColor(R.color.soduko_background));//设置画笔颜色
        canvas.drawRect(0,0,getWidth(),getHeight(),backgroundPaint);//绘制背景色

        darkPaint.setColor(getResources().getColor(R.color.soduko_dark));

        whitePaint.setColor(getResources().getColor(R.color.soduko_white));

        lightPaint.setColor(getResources().getColor(R.color.soduko_light));

        //绘制九宫格
        //两条线相隔1像素且用不同的颜色:一深一浅，纯粹是为了好看，效果像是一条刻上去的线
        for (int i = 0; i < 9; i++){
            //以下两行代码用于绘制横向的单元格线
            canvas.drawLine(0, i * height, getWidth(), i * height, lightPaint);
            canvas.drawLine(0,i * height + 1, getWidth(), i * height + 1, whitePaint);//
            //以下两行代码用于绘制纵向的单元格线
            canvas.drawLine(i * width, 0, i * width, getWidth(), lightPaint);
            canvas.drawLine(i * width + 1, 0, i * width + 1, getWidth(), whitePaint);
        }

        //当线的行数时3的倍数是，加深颜色
        for (int i = 0; i < 9; i++){
            if(i % 3 != 0){
                continue;
            }
            canvas.drawLine(0,i * height,getWidth(),i * height,darkPaint);
            canvas.drawLine(0,i * height + 1,getWidth(),i * height + 1,whitePaint);
            canvas.drawLine(i * width,0,i * width,getHeight(),darkPaint);
            canvas.drawLine(i * width+1,0,i * width + 1,getHeight(),whitePaint);
        }

        //绘制数字
        numberPaint.setColor(Color.BLACK);
        numberPaint.setStyle(Paint.Style.STROKE);
        numberPaint.setTextSize(height * 0.75f);
        numberPaint.setTextAlign(Paint.Align.CENTER);//文字居中

        Paint.FontMetrics fm = numberPaint.getFontMetrics();
        float x = width / 2f;//x轴偏移量
        float y = height / 2 - (fm.ascent + fm.descent) / 2;//y轴偏移量

        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                canvas.drawText(game.getTitleString(i,j), i * width  + x, j * height + y, numberPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        //判断用户是否点击
        if (event.getAction() != MotionEvent.ACTION_DOWN){
            return super.onTouchEvent(event);
        }
        //得到用户点击坐标
        selectedX = (int)(event.getX() / width);
        selectedY = (int)(event.getY() / height);

        int[] used = game.getUsedTileByCoor(selectedX, selectedY);
        StringBuilder stringBuilder = new StringBuilder();//将使用过的数字存放到sb中
        for (int i = 0; i < used.length; i++){
            stringBuilder.append(String.valueOf(used[i]));
        }

        KeyDialog keyDialog = new KeyDialog(getContext(), used,this);
        keyDialog.show();

 /*     //sum：用LayoutInflater通过布局文件生成一个dialog对象
        //生成一个LayoutInflater对象
        //使用LayoutInflater对象，根据一个布局文件，生成一个View对象
        //从生成好的TextView当中，取出相应的空间
        //设置TextView的内容
        //生成一个对话框的Builder对象
        //设置对话框所要显示的内容
        //生成对话框对象，并将其显示出来
        LayoutInflater layoutInflater = LayoutInflater.from(this.getContext());
        View layoutView = layoutInflater.inflate(R.layout.content_main, null);
        TextView textView = (TextView)layoutView.findViewById(R.id.usedTextId);
        textView.setText(stringBuilder.toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setView(layoutView);
        AlertDialog dialog = builder.create();
        dialog.show();*/

        return true;
    }

    public void setSelectedTile(int tile){
        if (game.setTileIfValid(selectedX, selectedY, tile)){
            invalidate();//重新调用onDraw
        }
    }
}
