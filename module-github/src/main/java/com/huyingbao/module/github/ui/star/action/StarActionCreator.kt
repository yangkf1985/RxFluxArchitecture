package com.huyingbao.module.github.ui.star.action

import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.scope.ActivityScope
import retrofit2.Retrofit
import javax.inject.Inject

@ActivityScope
class StarActionCreator @Inject constructor(
        rxDispatcher: RxDispatcher,
        rxActionManager: RxActionManager,
        private val retrofit: Retrofit
) : RxActionCreator(rxDispatcher, rxActionManager), StarAction {
}
