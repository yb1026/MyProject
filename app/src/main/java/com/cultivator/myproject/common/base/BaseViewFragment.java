
package com.cultivator.myproject.common.base;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.cultivator.myproject.R;


/**
 * @author Simon.xin
 * @TTOD 为Fragment中子视图，包含在BaseView中,可对当前视图管理
 */
public class BaseViewFragment extends FrameLayout {

    public final static int TYPE_EMPTY = 1;
    public final static int TYPE_LOADING = 2;
    public final static int TYPE_DATA = 3;
    private int mEmptyType = TYPE_DATA;
    private String mEmptyMessage;
    private Context mContext;
    private boolean mShowEmptyButton = true;
    private View mDataView;
    private ViewStub mEmptyView;
    private TextView emptyTextView;
    private Button emptyViewButton;
    private ImageView emptyImageView;
    private TopBarManager actionBar;
    private View rootView;

    public BaseViewFragment(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public BaseViewFragment(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView () {
        rootView = this;
    }

    @Override
    public void setFitsSystemWindows (boolean fitSystemWindows) {
        super.setFitsSystemWindows(false);
    }

    public void addDataView (View view) {
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mDataView = view;
        addView(mDataView);
    }

    /**
     * 初始化View
     */
    private void changeEmptyType () {
        // change empty type
        switch (mEmptyType) {
            case TYPE_DATA:
                if (mDataView != null) {
                    mDataView.setVisibility(View.VISIBLE);
                }
                if (mEmptyView != null) {
                    mEmptyView.setVisibility(View.GONE);
                }
                break;
            case TYPE_EMPTY:
                if (mEmptyView != null) {
                    mEmptyView.setVisibility(View.VISIBLE);
                }
                if (mDataView != null) {
                    mDataView.setVisibility(View.GONE);
                }
                if (emptyTextView != null && mEmptyMessage!=null) {
                    emptyTextView.setText(mEmptyMessage);
                }
                break;
            case TYPE_LOADING:
                if (mEmptyView != null)
                    mEmptyView.setVisibility(View.GONE);
                if (mDataView != null)
                    mDataView.setVisibility(View.GONE);
                break;
        }
    }

    public View viewById(int resId){
        return rootView.findViewById(resId);
    }

    private void initEmptyView () {
        if (mEmptyView == null) {
            mEmptyView = new ViewStub(mContext);
            mEmptyView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mEmptyView.setLayoutResource(R.layout.view_empty);
            addView(mEmptyView);
            mEmptyView.inflate();
            emptyViewButton = ((Button) viewById(R.id.empty_refresh_btn));
            emptyTextView = ((TextView) viewById(R.id.empty_tv));
            emptyImageView = ((ImageView) viewById(R.id.empty_iv));
        }
        if (mShowEmptyButton && emptyViewButton!=null) {
            emptyViewButton.setVisibility(View.VISIBLE);
        } else {
            emptyViewButton.setVisibility(View.GONE);
        }
    }

    private static Animation getRotateAnimation () {
        final RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF,
                .5f);
        rotateAnimation.setDuration(1500);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        return rotateAnimation;
    }


    /**
     *
     * @param emptyMessage  错误消息
     * @param emptyButtonClickListener  刷新按钮数据
     * @param resId     错误图片icon
     */
    public void setEmpty (String emptyMessage, OnClickListener emptyButtonClickListener, int resId) {
        this.mEmptyMessage = emptyMessage;
        this.mEmptyType = TYPE_EMPTY;
        initEmptyView();
        if (emptyButtonClickListener != null) {
            setEmptyButtonClickListener(emptyButtonClickListener);
        }
        if (resId != 0 && emptyImageView!=null) {
            emptyImageView.setImageResource(resId);
        }
        changeEmptyType();
    }

    public void setEmpty (String emptyMessage,OnClickListener emptyButtonClickListener) {
        setEmpty(emptyMessage, emptyButtonClickListener, 0);
    }
    public void setEmpty (OnClickListener emptyButtonClickListener) {
        setEmpty(null, emptyButtonClickListener, 0);
    }

    public void setEmpty (String emptyMessage) {
        setEmpty(emptyMessage, null, 0);
    }

    public void setEmpty (String emptyMessage, int errorImgResId) {
        setEmpty(emptyMessage, null, errorImgResId);
    }

    public void showData () {
        this.mEmptyType = TYPE_DATA;
        changeEmptyType();
    }

    public void setEmptyType (int emptyType) {
        this.mEmptyType = emptyType;
        changeEmptyType();
    }


    public void setEmptyButtonClickListener (OnClickListener emptyButtonClickListener) {
        if (emptyViewButton != null) {
            emptyViewButton.setOnClickListener(emptyButtonClickListener);
        }
    }

    public void onDestroy () {
        removeAllViews();
        removeAllViewsInLayout();
    }

    @Override
    protected void onRestoreInstanceState (Parcelable state) {
        try {
            super.onRestoreInstanceState(state);
        } catch (Exception e) {
        }
        state = null;
    }
}
