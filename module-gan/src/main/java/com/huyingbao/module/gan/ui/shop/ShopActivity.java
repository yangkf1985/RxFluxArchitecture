package com.huyingbao.module.gan.ui.shop;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.huyingbao.core.store.RxStore;
import com.huyingbao.core.store.RxStoreChange;
import com.huyingbao.core.util.ActivityUtils;
import com.huyingbao.module.gan.GanModuleActivity;
import com.huyingbao.module.gan.ui.shop.module.ShopStore;
import com.huyingbao.module.gan.R;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public class ShopActivity extends GanModuleActivity {
    private static final String SHOP_ID = "shopId";
    @Inject
    Lazy<ShopFragment> mShopFragmentLazy;

    private ShopStore mStore;

    public static Intent newIntent(Context context, int shopId) {
        Intent intent = new Intent(context, ShopActivity.class);
        intent.putExtra(SHOP_ID, shopId);
        return intent;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mStore = ViewModelProviders.of(this, mViewModelFactory).get(ShopStore.class);
        if (getSupportFragmentManager().findFragmentById(R.id.fl_content) == null)
            ActivityUtils.addFragment(getSupportFragmentManager(), mShopFragmentLazy.get(), R.id.fl_content);
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
    }

    @Nullable
    @Override
    public List<RxStore> getLifecycleRxStoreList() {
        return Collections.singletonList(mStore);
    }
}
