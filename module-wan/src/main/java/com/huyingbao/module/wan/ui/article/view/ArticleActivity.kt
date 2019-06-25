package com.huyingbao.module.wan.ui.article.view

import android.os.Bundle
import androidx.fragment.app.Fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.base.activity.BaseRxFragActivity
import com.huyingbao.core.common.module.CommonContants
import com.huyingbao.module.wan.app.WanAppStore
import com.huyingbao.module.wan.ui.article.action.ArticleAction
import com.huyingbao.module.wan.ui.article.store.ArticleStore
import com.huyingbao.module.wan.ui.friend.view.FriendFragment

import org.greenrobot.eventbus.Subscribe

import javax.inject.Inject

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Route(path = CommonContants.Address.ArticleActivity)
class ArticleActivity : BaseRxFragActivity<ArticleStore>() {
    @Inject
    lateinit var wanAppStore: WanAppStore

    override fun createFragment(): Fragment? {
        return ArticleListFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {}

    @Subscribe(tags = [ArticleAction.TO_FRIEND], sticky = true)
    fun toFriend(rxChange: RxChange) {
        addFragmentHideExisting(FriendFragment.newInstance())
    }

    @Subscribe(tags = [ArticleAction.TO_BANNER], sticky = true)
    fun toBanner(rxChange: RxChange) {
        addFragmentHideExisting(BannerFragment.newInstance())
    }
}
