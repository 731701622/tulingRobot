package test.wkx.com.tulingrobot.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import test.wkx.com.tulingrobot.Activity.BaseApplication;
import test.wkx.com.tulingrobot.Layout.nav_bar;
import test.wkx.com.tulingrobot.R;

public class HostFragment extends BaseFragment
{
    @Nullable
    @BindView(R.id.h_back)
    ImageView hBack;
    @BindView(R.id.h_head)
    ImageView hHead;
    @BindView(R.id.user_line)
    ImageView userLine;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_val)
    TextView userVal;
    @BindView(R.id.history)
    nav_bar history;
    @BindView(R.id.collect)
    nav_bar collect;
    @BindView(R.id.version)
    nav_bar version;
    @BindView(R.id.about)
    nav_bar about;
    private Unbinder bind;
    private View rootView;;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_host, container, false);
        }
        bind = ButterKnife.bind(rootView);
        return rootView;
    }


    @Override
    protected int getLayoutId()
    {

        return R.layout.fragment_host;
    }

    @Override
    protected void initData()
    {

    }


}
