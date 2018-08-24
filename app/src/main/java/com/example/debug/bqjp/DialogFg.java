package com.example.debug.bqjp;
/*
 * @author xy
 * @emil 384813636@qq.com
 * create at 2018/8/24
 * description:
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Iterator;

import me.shaohui.bottomdialog.BaseBottomDialog;
import sj.keyboard.EmoticonsKeyBoardPopWindow;
import sj.keyboard.adpater.PageSetAdapter;
import sj.keyboard.data.PageSetEntity;
import sj.keyboard.interfaces.EmoticonClickListener;
import sj.keyboard.utils.EmoticonsKeyboardUtils;
import sj.keyboard.widget.EmoticonsEditText;
import sj.keyboard.widget.EmoticonsFuncView;
import sj.keyboard.widget.EmoticonsIndicatorView;
import sj.keyboard.widget.EmoticonsToolBarView;

public class DialogFg extends BaseBottomDialog implements EmoticonsFuncView.OnEmoticonsPageViewListener, EmoticonsToolBarView.OnToolBarItemClickListener {

    private EmoticonsEditText etContent;
    private ImageView iv_face;

    private EmoticonsFuncView mEmoticonsFuncView;
    private EmoticonsIndicatorView mEmoticonsIndicatorView;
    private EmoticonsToolBarView mEmoticonsToolBarView;

    private RelativeLayout rl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Black_NoTitleBar);
       // setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_dialog;
    }

    @Override
    public void bindView(View v) {
        etContent=v.findViewById(R.id.et_content);
       // initEmoticonsEditText();
        rl=v.findViewById(R.id.rl);
        //ll=v.findViewById(R.id.ll);
        //RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ll.getLayoutParams());
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(rl.getLayoutParams());
        params.height= EmoticonsKeyboardUtils.getDefKeyboardHeight(getActivity());
        rl.setLayoutParams(params);
        iv_face=v.findViewById(R.id.iv_face);
       // keyboard=v.findViewById(R.id.keyboard);
        iv_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleInput(getActivity());
            }
        });
        mEmoticonsFuncView = (EmoticonsFuncView)v.findViewById(R.id.view_epv);
        mEmoticonsIndicatorView = (EmoticonsIndicatorView)v.findViewById(R.id.view_eiv);
        mEmoticonsToolBarView = (EmoticonsToolBarView)v.findViewById(R.id.view_etv);
        mEmoticonsFuncView.setOnIndicatorListener(this);
        mEmoticonsToolBarView.setOnToolBarItemClickListener(this);

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
      /*  mKeyBoardPopWindow = new EmoticonsKeyBoardPopWindow(getActivity());
        mKeyBoardPopWindow.getContentView().measure(0, 0);
        int height = mKeyBoardPopWindow.getContentView().getMeasuredHeight();
        mKeyBoardPopWindow.showAtLocation(etContent,Gravity.BOTTOM,0,-height);
*/
        EmoticonClickListener emoticonClickListener = SimpleCommonUtils.getCommonEmoticonClickListener(etContent);

        PageSetAdapter pageSetAdapter = new PageSetAdapter();
        SimpleCommonUtils.addEmojiPageSetEntity(pageSetAdapter, getActivity(), emoticonClickListener);
       // SimpleCommonUtils.addXhsPageSetEntity(pageSetAdapter, getActivity(), emoticonClickListener);
        this.setAdapter(pageSetAdapter);

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
    private void toggleInput(Context context){
        InputMethodManager inputMethodManager =
                (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
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
