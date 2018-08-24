package com.example.debug.bqjp;
/*
 * @author xy
 * @emil 384813636@qq.com
 * create at 2018/8/24
 * description:
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;

import sj.keyboard.EmoticonsKeyBoardPopWindow;
import sj.keyboard.adpater.PageSetAdapter;
import sj.keyboard.data.PageSetEntity;
import sj.keyboard.utils.EmoticonsKeyboardUtils;
import sj.keyboard.widget.EmoticonsFuncView;
import sj.keyboard.widget.EmoticonsIndicatorView;
import sj.keyboard.widget.EmoticonsToolBarView;

public class PopWindow extends EmoticonsKeyBoardPopWindow implements EmoticonsFuncView.OnEmoticonsPageViewListener, EmoticonsToolBarView.OnToolBarItemClickListener {
    private Context mContext;
    protected EmoticonsFuncView mEmoticonsFuncView;
    protected EmoticonsIndicatorView mEmoticonsIndicatorView;
    protected EmoticonsToolBarView mEmoticonsToolBarView;

    public PopWindow(Context context) {
        //super(context, (AttributeSet)null);
        super(context);
        this.mContext = context;
        @SuppressLint("WrongConstant") LayoutInflater inflater = (LayoutInflater)context.getSystemService("layout_inflater");
        View mConentView = inflater.inflate(com.keyboard.view.R.layout.view_func_emoticon, (ViewGroup)null);
        this.setContentView(mConentView);
        this.setWidth(EmoticonsKeyboardUtils.getDisplayWidthPixels(this.mContext));
        this.setHeight(EmoticonsKeyboardUtils.getDefKeyboardHeight(this.mContext));
        this.setAnimationStyle(com.keyboard.view.R.style.PopupAnimation);
        this.setOutsideTouchable(true);
        this.update();
        ColorDrawable dw = new ColorDrawable(0);
        this.setBackgroundDrawable(dw);
        this.updateView(mConentView);
    }

    private void updateView(View view) {
        this.mEmoticonsFuncView = (EmoticonsFuncView)view.findViewById(com.keyboard.view.R.id.view_epv);
        this.mEmoticonsIndicatorView = (EmoticonsIndicatorView)view.findViewById(com.keyboard
                .view.R.id.view_eiv);
        this.mEmoticonsToolBarView = (EmoticonsToolBarView)view.findViewById(com.keyboard.view.R.id.view_etv);
        this.mEmoticonsFuncView.setOnIndicatorListener(this);
        this.mEmoticonsToolBarView.setOnToolBarItemClickListener(this);
    }

    public void setAdapter(PageSetAdapter pageSetAdapter) {
        if (pageSetAdapter != null) {
            ArrayList<PageSetEntity> pageSetEntities = pageSetAdapter.getPageSetEntityList();
            if (pageSetEntities != null) {
                Iterator i$ = pageSetEntities.iterator();

                while(i$.hasNext()) {
                    PageSetEntity pageSetEntity = (PageSetEntity)i$.next();
                    this.mEmoticonsToolBarView.addToolItemView(pageSetEntity);
                }
            }
        }

        this.mEmoticonsFuncView.setAdapter(pageSetAdapter);
    }

    public void showPopupWindow() {
        View rootView = EmoticonsKeyboardUtils.getRootView((Activity)this.mContext);
        if (this.isShowing()) {
            this.dismiss();
        } else {
           // EmoticonsKeyboardUtils.closeSoftKeyboard(this.mContext);
            this.showAtLocation(rootView, 80, 0, 0);
        }

    }

    public void emoticonSetChanged(PageSetEntity pageSetEntity) {
        this.mEmoticonsToolBarView.setToolBtnSelect(pageSetEntity.getUuid());
    }

    public void playTo(int position, PageSetEntity pageSetEntity) {
        this.mEmoticonsIndicatorView.playTo(position, pageSetEntity);
    }

    public void playBy(int oldPosition, int newPosition, PageSetEntity pageSetEntity) {
        this.mEmoticonsIndicatorView.playBy(oldPosition, newPosition, pageSetEntity);
    }

    public void onToolBarItemClick(PageSetEntity pageSetEntity) {
        this.mEmoticonsFuncView.setCurrentPageSet(pageSetEntity);
    }
}
