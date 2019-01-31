package com.huyingbao.module.kotlin.ui.article.store

import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.module.kotlin.action.KotlinResponse
import com.huyingbao.module.kotlin.ui.article.action.ArticleAction
import com.huyingbao.module.kotlin.ui.article.model.Article
import com.huyingbao.module.kotlin.ui.article.model.Banner
import com.huyingbao.module.kotlin.ui.article.model.Page

import org.greenrobot.eventbus.Subscribe

import java.util.ArrayList

import javax.inject.Inject
import javax.inject.Singleton

import androidx.lifecycle.MutableLiveData

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
class ArticleStore @Inject
constructor(rxDispatcher: RxDispatcher) : RxActivityStore(rxDispatcher) {
    val articleLiveData = MutableLiveData<List<Article>>()
    val bannerLiveData = MutableLiveData<List<Banner>>()
    var nextRequestPage = 1//列表页数

    /**
     * 当所有者Activity销毁时,框架调用ViewModel的onCleared（）方法，以便它可以清理资源。
     */
    override fun onCleared() {
        super.onCleared()
        nextRequestPage = 1
        articleLiveData.value = null
        bannerLiveData.value = null
    }

    /**
     * 接收RxAction
     * 处理RxAction携带的数据
     * 发送RxChange通知RxView
     *
     * @param rxAction
     */
    @Subscribe
    override fun onRxAction(rxAction: RxAction) {
        when (rxAction.tag) {
            ArticleAction.GET_ARTICLE_LIST -> {
                val articleResponse = rxAction.getResponse<KotlinResponse<Page<Article>>>()
                if (articleLiveData.value == null) {
                    articleLiveData.setValue(articleResponse.data!!.datas)
                } else {
                    articleLiveData.value!!.addAll(articleResponse.data!!.datas!!)
                    articleLiveData.setValue(articleLiveData.value)
                }
                nextRequestPage++
            }
            ArticleAction.GET_BANNER_LIST -> {
                val bannerResponse = rxAction.getResponse<KotlinResponse<ArrayList<Banner>>>()
                bannerLiveData.setValue(bannerResponse.data)
            }
            ArticleAction.TO_BANNER, ArticleAction.TO_FRIEND, ArticleAction.TO_LOGIN -> postChange(RxChange.newRxChange(rxAction.tag))
        }
    }
}