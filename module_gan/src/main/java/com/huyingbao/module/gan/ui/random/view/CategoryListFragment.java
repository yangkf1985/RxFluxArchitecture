package com.huyingbao.module.gan.ui.random.view;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.huyingbao.core.arch.scope.ActivityScope;
import com.huyingbao.core.common.R2;
import com.huyingbao.core.common.view.CommonRxFragment;
import com.huyingbao.module.gan.R;
import com.huyingbao.module.gan.action.GanConstants;
import com.huyingbao.module.gan.ui.main.action.MainAction;
import com.huyingbao.module.gan.ui.main.action.MainActionCreator;
import com.huyingbao.module.gan.ui.random.adapter.CategoryAdapter;
import com.huyingbao.module.gan.ui.main.store.MainStore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * 内容类型列表展示页面
 * Created by liujunfeng on 2017/12/7.
 */
@ActivityScope
public class CategoryListFragment extends CommonRxFragment<MainStore> {
    @Inject
    MainActionCreator mActionCreator;
    @BindView(R2.id.rv_content)
    RecyclerView mRvContent;

    private List<String> mDataList = new ArrayList();
    private BaseQuickAdapter mAdapter = new CategoryAdapter(mDataList);

    @Inject
    public CategoryListFragment() {
    }

    @Nullable
    @Override
    public MainStore getRxStore() {
        return ViewModelProviders.of(this, mViewModelFactory).get(MainStore.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.common_fragment_base_list;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        initRecyclerView();
        showData();
    }

    /**
     * 实例化RecyclerView,并设置adapter
     */
    private void initRecyclerView() {
        mRvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvContent.setHasFixedSize(true);
        mRvContent.setAdapter(mAdapter);
        mRvContent.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//硬件加速
        mRvContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                mActionCreator.postLocalAction(MainAction.TO_WAN_MODULE,
                        GanConstants.Key.CATEGORY, mDataList.get(position));
            }
        });
    }

    /**
     * 显示数据
     */
    private void showData() {
        mDataList.addAll(Arrays.asList(getResources().getStringArray(R.array.gan_array_category)));
        mAdapter.notifyDataSetChanged();
    }
}