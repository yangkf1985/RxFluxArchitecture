package com.huyingbao.module.gan.ui.category.view;

import android.os.Bundle;

import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.common.view.CommonRxActivity;
import com.huyingbao.module.gan.ui.category.action.CategoryAction;
import com.huyingbao.module.gan.ui.category.store.CategoryStore;

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
public class CategoryActivity extends CommonRxActivity<CategoryStore> {
    @Inject
    Lazy<CategoryListFragment> mCategoryListFragmentLazy;
    @Inject
    Lazy<CategoryFragment> mCategoryCategoryFragment;

    @Nullable
    @Override
    public CategoryStore getRxStore() {
        return ViewModelProviders.of(this, mViewModelFactory).get(CategoryStore.class);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
    }

    @Override
    protected Fragment createFragment() {
        return mCategoryListFragmentLazy.get();
    }

    /**
     * 接收RxChange，粘性
     */
    @Override
    @Subscribe(sticky = true)
    public void onRxChanged(@NonNull RxChange rxChange) {
        super.onRxChanged(rxChange);
        switch (rxChange.getTag()) {
            case CategoryAction.TO_RANDOM_LIST:
//                startActivity(RandomActivity.newIntent(this, getRxStore().getCategory()));
                addFragmentHideExisting(mCategoryCategoryFragment.get());
                break;
        }
    }
}