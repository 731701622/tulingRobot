package test.wkx.com.tulingrobot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseFragment extends Fragment{

    private boolean isInitData = false;//是否初始化数据
    private boolean isVisibleToUser = false;//是否可见
    private boolean isPrepareView = false;//view是否加载完成

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);

    }

    protected abstract int getLayoutId();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isPrepareView = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        lazyInitData();
    }

    private void lazyInitData() {
        if(!isInitData&&isVisibleToUser&&isPrepareView){
            isInitData = true;
            initData();
        }
    }

    protected abstract void initData();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lazyInitData();
    }

}
