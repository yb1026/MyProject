package com.cultivator.myproject.common.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cultivator.myproject.common.log.MyLog;
import com.cultivator.myproject.common.net.CallServer;
import com.cultivator.myproject.common.net.ConnectionController;
import com.cultivator.myproject.common.net.HttpListener;


/**
 * @author: xin.wu
 * @create time: 2016/4/7 14:42
 * @TODO: BaseFragment
 */
public abstract class BaseFragment extends Fragment implements HttpListener, ViewListener {

    private BaseViewFragment mBaseView;
    public BaseActivity mContext;
    private ConnectionController controller;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyLog.d(getClass(), "onCreate...");
        mContext = (BaseActivity) getActivity();
        mBaseView = new BaseViewFragment(mContext);
        controller = new ConnectionController(getContext(), this, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        addView(inflater.inflate(getLayoutId(), null));
        MyLog.d(getClass(), "onCreateView...");
        initUI();
        return mBaseView;
    }


    @Override
    public void onShowDataView() {
        showDataView();
    }

    public BaseActivity getBaseActivity() {
        return mContext;
    }

    public BaseViewFragment getBaseView() {
        return mBaseView;
    }

    public TopBarManager getToolBar() {
        return getBaseActivity().getToolBar();
    }

    private void addView(View view) {
        if (view != null) {
            mBaseView.addDataView(view);
        }
    }


    public View findViewById(int id) {
        return mBaseView.findViewById(id);
    }

    /**
     * 现实数据视图
     */
    public void showDataView() {
        if (mBaseView != null) {
            mBaseView.showData();
        }
    }

    /**
     * @param emptyMessage
     * @TODO: 异常视图
     */
    public void showEmptyView(String emptyMessage) {
        if (mBaseView != null) {
            mBaseView.setEmpty(emptyMessage, null, 0);
        }
    }

    /**
     * @param emptyMessage
     * @param errorImgResId
     */
    public void showEmptyView(String emptyMessage, int errorImgResId) {
        if (mBaseView != null) {
            mBaseView.setEmpty(emptyMessage, null, errorImgResId);
        }
    }

    /**
     * @param emptyMessage
     * @param emptyButtonClickListener
     */
    public void showEmptyView(String emptyMessage, View.OnClickListener emptyButtonClickListener) {
        if (mBaseView != null) {
            mBaseView.setEmpty(emptyMessage, emptyButtonClickListener);
        }
    }

    public void showEmptyView(View.OnClickListener emptyButtonClickListener) {
        if (mBaseView != null) {
            mBaseView.setEmpty(emptyButtonClickListener);
        }
    }

    /**
     * @param emptyMessage
     * @param emptyButtonClickListener
     * @param errorImgResId
     */
    public void showEmptyView(String emptyMessage, View.OnClickListener emptyButtonClickListener, int errorImgResId) {
        if (mBaseView != null) {
            mBaseView.setEmpty(emptyMessage, emptyButtonClickListener, errorImgResId);
        }
    }


    @Override
    public void
    onDestroyView() {
        super.onDestroyView();
        destoryAll();
    }

    private void destoryAll() {
        MyLog.d(getClass(), "fragment destoryAll...");
        if (mBaseView != null) {
            mBaseView.onDestroy();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        MyLog.d(getClass(), "onResume...");
    }






    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        CallServer.getRequestInstance().cancelAll();
    }

    protected void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    public void startActivity(String pAction, Bundle pBundle) {
        Intent intent = new Intent(pAction);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }





    /**
     * @return http
     */
    public ConnectionController getConnection() {
        return controller;
    }

    /***************************************
     * http end
     ********************************************/

    public abstract int getLayoutId();

    public abstract void initUI();

    /**
     * @TODO Fragment+ViewPager中多个Fragment切换加载数据()，此方法需手动调用。
     */
    public void onLoadData() {
        MyLog.d(getClass(), "onLoadData...");
    }
}
