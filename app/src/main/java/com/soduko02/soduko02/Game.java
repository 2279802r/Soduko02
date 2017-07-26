package com.soduko02.soduko02;

/**
 * Created by liulianqing on 17/7/15.
 */

public class Game {

    //数独初始化数据的基础
    private final String str = ""
            +"360000000"
            +"004230800"
            +"000004200"
            +"070460003"
            +"820000014"
            +"500013020"
            +"001900000"
            +"007048300"
            +"000000045";

    private int soduko[] = new int[9 * 9];//array将字符串拆开放置到数组中

    //用于存储每个单元格已经不可用／用过的数据
    private int used[][][] = new int[9][9][];

    public Game(){
//        soduko = fromPuzzieString(str);
        getStringToInt(str);
        calculateAllUsedTiles();
    }

    //根据九宫格当中的坐标，返回该坐标所应该填写的数字
    private int getTile(int x, int y){
        return soduko[y * 9 + x];
    }

    //根据九宫格当中的坐标，返回该坐标所填写的字符串
    public String getTitleString(int x, int y){
        int num = getTile(x,y);
        if (num == 0)
            return "";
        else
            return String.valueOf(num);
    }

/*    //根据一个字符串数据，生成一个整形数组，所谓数独游戏的初始化数据
    protected int[] fromPuzzieString (String src){
        int[] sodukoArray = new int[src.length()];
        for (int i = 0; i < sodukoArray.length; i++){
            sodukoArray[i] = src.charAt(i) - '0';//将字符转换为数字
        }
        return sodukoArray;
    }*/

    protected void getStringToInt(String str){
        for(int i=0;i<str.length();i++){
            soduko[i]=str.charAt(i)-'0';
        }
    }

    //用于计算所有单元格对应的不可用数据，调用calculateUsedNums
    public void calculateAllUsedTiles(){
        for (int x = 0; x < 9; x++){
            for (int y = 0; y < 9; y++){
                used[x][y] = calculateUsedTiles(x,y);
            }
        }
    }

    //取出某一单元格当中已经不可用的数据,调用calculateAllUsedNums
    public int[] getUsedTileByCoor(int x, int y){
        return used[x][y];
    }

    //计算某一单元格当中已经不可用的数据
    public int[] calculateUsedTiles(int x, int y){

        int used[] = new int[9];
        //纵坐标不可用数据
        for (int i = 0; i < 9; i++){
            int num = getTile(x,i);
            if (i == y)
                continue;
            if (num == 0)
                continue;

            used[num - 1] = num;
        }
        //横坐标不可用数据
        for (int i = 0; i < 9; i++){
            int num = getTile(i,y);
            if (i == x)
                continue;
            if (num == 0)
                continue;
            used[num - 1] = num;
        }
        //小型九宫格不可用数据
        int startX = (x / 3) * 3;
        int startY = (y / 3) * 3;
        for (int i = startX; i < startX + 3; i++){
            for (int j = startY; j < startY+3; j++){
                int num = getTile(i,j);
                if (i == x && j == y)
                    continue;
                if (num == 0)
                    continue;
                used[num-1] = num;
            }
        }
        //compress
        //将去掉0
        int unused = 0;
        for (int num:used){
            if (num == 0)continue;
            unused++;
        }
        int usedTile[] = new int[unused];
        unused = 0;
        for (int num:used){
            if (num != 0)
                usedTile[unused++] = num;
        }
        return usedTile;
    }

    protected boolean setTileIfValid(int x, int y, int value){
        int temp[] = getUsedTiles(x, y);
        if (value != 0){
            for (int num:temp){
                if (num == value)
                    return false;
            }
        }
        setTile(x,y,value);//将输入的数字设置到九宫格上
        calculateAllUsedTiles();//重新计算所有单元格不可用的数字
        return true;
    }

    protected int[] getUsedTiles(int x, int y){
        return used[x][y];
    }

    private void setTile(int x, int y, int value){
        soduko[y * 9 + x] = value;
    }

}
