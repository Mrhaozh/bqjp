package com.example.debug.bqjp;
/*
 * @author xy
 * @emil 384813636@qq.com
 * create at 2018/8/24
 * description:
 */

public class AppBean {

    private int id;
    private int icon;
    private String funcName;

    public int getIcon() {
        return icon;
    }

    public String getFuncName() {
        return funcName;
    }

    public int getId() {
        return id;
    }

    public AppBean(int icon, String funcName){
        this.icon = icon;
        this.funcName = funcName;
    }
}
