package com.example.debug.bqjp;
/*
 * @author xy
 * @emil 384813636@qq.com
 * create at 2018/8/23
 * description:
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Iterator;

import sj.keyboard.EmoticonsKeyBoardPopWindow;
import sj.keyboard.adpater.PageSetAdapter;
import sj.keyboard.data.PageSetEntity;
import sj.keyboard.interfaces.EmoticonClickListener;
import sj.keyboard.utils.EmoticonsKeyboardUtils;
import sj.keyboard.widget.EmoticonsEditText;
import sj.keyboard.widget.EmoticonsFuncView;
import sj.keyboard.widget.EmoticonsIndicatorView;
import sj.keyboard.widget.EmoticonsToolBarView;

public class DialogActivity extends Activity implements EmoticonsFuncView.OnEmoticonsPageViewListener, EmoticonsToolBarView.OnToolBarItemClickListener {
    protected int activityCloseEnterAnimation;
    protected int activityCloseExitAnimation;


    private EmoticonsEditText etContent;
    private EmoticonsKeyBoardPopWindow mKeyBoardPopWindow;
    private ImageView iv_face;

    private EmoticonsFuncView mEmoticonsFuncView;
    private EmoticonsIndicatorView mEmoticonsIndicatorView;
    private EmoticonsToolBarView mEmoticonsToolBarView;

    private RelativeLayout rl;
    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[] {android.R.attr.windowAnimationStyle});
        int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);
        activityStyle.recycle();
        activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new int[] {android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation});
        activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);
        activityCloseExitAnimation = activityStyle.getResourceId(1, 0);
        activityStyle.recycle();

        //设置布局在底部
        getWindow().setGravity(Gravity.BOTTOM);
        //设置布局填充满宽度
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(layoutParams);

        etContent=findViewById(R.id.et_content);
        //keyboard=findViewById(R.id.keyboard);
        //LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(keyboard.getLayoutParams());
        //params.height= EmoticonsKeyboardUtils.getDefKeyboardHeight(this);
        //keyboard.setLayoutParams(params);

        mEmoticonsFuncView = (EmoticonsFuncView)findViewById(com.keyboard.view.R.id.view_epv);
        mEmoticonsIndicatorView = (EmoticonsIndicatorView)findViewById(com.keyboard
                .view.R.id.view_eiv);
        mEmoticonsToolBarView = (EmoticonsToolBarView)findViewById(com.keyboard.view.R.id.view_etv);
        mEmoticonsFuncView.setOnIndicatorListener(this);
        mEmoticonsToolBarView.setOnToolBarItemClickListener(this);

        rl=findViewById(R.id.rl);
        //RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(rl.getLayoutParams());
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(rl.getLayoutParams());
        params.height= EmoticonsKeyboardUtils.getDefKeyboardHeight(this);
        rl.setLayoutParams(params);
        iv_face=findViewById(R.id.iv_face);




        iv_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



               /* if (mKeyBoardPopWindow != null) {
                    if(mKeyBoardPopWindow.isShowing()) {
                        mKeyBoardPopWindow.dismiss();
                    }else{
                        //keyboard.setVisibility(View.VISIBLE);
                        mKeyBoardPopWindow.showPopupWindow();
                    }
                } else {
                    initKeyBoardPopWindow();
                    //keyboard.setVisibility(View.VISIBLE);
                    mKeyBoardPopWindow.showPopupWindow();
                }*/
            }
        });
        initEmoticonsEditText();
        initKeyBoardPopWindow();
    }
    private void initEmoticonsEditText() {
        SimpleCommonUtils.initEmoticonsEditText(etContent);
        etContent.setFocusable(true);
        etContent.setFocusableInTouchMode(true);
        etContent.requestFocus();
    }
    private void initKeyBoardPopWindow() {
     /*   mKeyBoardPopWindow = new EmoticonsKeyBoardPopWindow(DialogActivity.this);
        EmoticonClickListener emoticonClickListener = SimpleCommonUtils.getCommonEmoticonClickListener(etContent);
        PageSetAdapter pageSetAdapter = new PageSetAdapter();
        SimpleCommonUtils.addEmojiPageSetEntity(pageSetAdapter, this, emoticonClickListener);
        SimpleCommonUtils.addXhsPageSetEntity(pageSetAdapter, this, emoticonClickListener);
        mKeyBoardPopWindow.setAdapter(pageSetAdapter);*/
        mKeyBoardPopWindow = new EmoticonsKeyBoardPopWindow(DialogActivity.this);
        mKeyBoardPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //keyboard.setVisibility(View.GONE);
            }
        });
       // mKeyBoardPopWindow.setAnimationStyle(R.anim.anim_pop);
        EmoticonClickListener emoticonClickListener = SimpleCommonUtils.getCommonEmoticonClickListener(etContent);
        PageSetAdapter pageSetAdapter = new PageSetAdapter();
        SimpleCommonUtils.addEmojiPageSetEntity(pageSetAdapter, this, emoticonClickListener);
        //SimpleCommonUtils.addXhsPageSetEntity(pageSetAdapter, this, emoticonClickListener);
       // mKeyBoardPopWindow.setAdapter(pageSetAdapter);
        this.setAdapter(pageSetAdapter);
    }
    @Override
    public void finish() {
        super.finish();
        //finish时调用退出动画
        overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
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

    @Override
    public void emoticonSetChanged(PageSetEntity pageSetEntity) {
        mEmoticonsToolBarView.setToolBtnSelect(pageSetEntity.getUuid());
    }

    @Override
    public void playTo(int i, PageSetEntity pageSetEntity) {
        mEmoticonsIndicatorView.playTo(i, pageSetEntity);
    }

    @Override
    public void playBy(int i, int i1, PageSetEntity pageSetEntity) {
        mEmoticonsIndicatorView.playBy(i, i1, pageSetEntity);
    }

    @Override
    public void onToolBarItemClick(PageSetEntity pageSetEntity) {
        mEmoticonsFuncView.setCurrentPageSet(pageSetEntity);
    }
}

