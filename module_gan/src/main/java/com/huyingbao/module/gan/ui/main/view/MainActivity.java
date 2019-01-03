package com.huyingbao.module.gan.ui.main.view;

import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.common.view.CommonRxActivity;
import com.huyingbao.module.gan.R;
import com.huyingbao.module.gan.ui.main.action.MainAction;
import com.huyingbao.module.gan.ui.main.store.MainStore;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import dagger.Lazy;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public class MainActivity extends CommonRxActivity<MainStore> {
    @Inject
    Lazy<MainFragment> mMainFragmentLazy;

    @Override
    protected Fragment createFragment() {
        return mMainFragmentLazy.get();
    }

    @Nullable
    @Override
    public MainStore getRxStore() {
        return ViewModelProviders.of(this, mViewModelFactory).get(MainStore.class);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        setTitle(R.string.gan_label_main);
    }

    /**
     * 接收RxChange，粘性
     */
    @Override
    @Subscribe(sticky = true)
    public void onRxChanged(@NonNull RxChange rxChange) {
        super.onRxChanged(rxChange);
        switch (rxChange.getTag()) {
            case MainAction.TO_GAN_MODULE:
                break;
            case MainAction.TO_WAN_MODULE:
                ARouter.getInstance().build("/wan/ArticleActivity").navigation();
                break;
        }
    }
}