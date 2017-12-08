package com.huyingbao.module.git.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.huyingbao.core.custom.CommonToolbarActivity;
import com.huyingbao.core.store.RxStore;
import com.huyingbao.core.store.RxStoreChange;
import com.huyingbao.core.util.ActivityUtils;
import com.huyingbao.module.git.R;
import com.huyingbao.module.git.action.GitActions;
import com.huyingbao.module.git.ui.module.GitStore;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public class GitActivity extends CommonToolbarActivity {
    @Inject
    Lazy<GitRepoFragment> mGitRepoFragmentLazy;
    @Inject
    Lazy<GitUserFragment> mGitUserFragmentLazy;

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        if (getSupportFragmentManager().findFragmentById(R.id.fl_content) == null)
            ActivityUtils.addFragment(getSupportFragmentManager(), mGitRepoFragmentLazy.get(), R.id.fl_content);
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getType()) {
            case GitActions.TO_GIT_USER:
                ActivityUtils.addAndHideFragment(getSupportFragmentManager(), mGitUserFragmentLazy.get(), R.id.fl_content);
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getLifecycleRxStoreList() {
        return Collections.singletonList(ViewModelProviders.of(this, mViewModelFactory).get(GitStore.class));
    }
}