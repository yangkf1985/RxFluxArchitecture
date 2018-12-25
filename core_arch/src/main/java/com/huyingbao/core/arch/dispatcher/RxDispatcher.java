package com.huyingbao.core.arch.dispatcher;

import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.model.RxError;
import com.huyingbao.core.arch.store.RxActionDispatch;
import com.huyingbao.core.arch.view.RxFluxView;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Dispatcher不会被直接使用，
 * 而是通过通过一个ActionCreator来封装Dispatcher，
 * 并提供便捷的方法来分发View中产生的事件，
 * 消息的传递通过Action（Action是一个普通的POJO类）来封装。
 * Created by liujunfeng on 2017/12/7.
 */
@Singleton
public class RxDispatcher {
    @Inject
    RxDispatcher() {
    }

    /**
     * 将store注册到EventBus中
     *
     * @param rxStore
     * @param <T>     实现RxActionDispatch的RxStore
     */
    public <T extends RxActionDispatch> void subscribeRxStore(final T rxStore) {
        EventBus.getDefault().register(rxStore);
    }

    /**
     * 将view注册到EventBus中
     *
     * @param rxView
     * @param <T>
     */
    public <T extends RxFluxView> void subscribeRxView(final T rxView) {
        EventBus.getDefault().register(rxView);
    }

    /**
     * 解除rxStore的注册
     *
     * @param rxStore
     * @param <T>
     */
    public <T extends RxActionDispatch> void unsubscribeRxStore(final T rxStore) {
        EventBus.getDefault().unregister(rxStore);
    }

    /**
     * 将view解除注册
     *
     * @param rxView
     * @param <T>
     */
    public <T extends RxFluxView> void unsubscribeRxView(final T rxView) {
        EventBus.getDefault().unregister(rxView);
    }

    /**
     * 判断是否注册
     *
     * @param object
     * @param <T>
     * @return {@code true}
     */
    public <T> boolean isSubscribe(final T object) {
        return EventBus.getDefault().isRegistered(object);
    }

    /**
     * 解除所有的注册
     */
    public synchronized void unsubscribeAll() {
        EventBus.clearCaches();
        EventBus.getDefault().removeAllStickyEvents();
    }

    /**
     * 1:发送RxAction,通知所有订阅的RxStore
     *
     * @param action
     */
    public void postRxAction(final RxAction action) {
        EventBus.getDefault().post(action);
    }

    /**
     * 2:发送RxChange,通知所有订阅的RxView,粘性通知
     *
     * @param rxChange
     */
    public void postRxChange(final RxChange rxChange) {
        EventBus.getDefault().postSticky(rxChange);
    }

    /**
     * 3:发送RxError,到所有订阅的RxView,粘性通知
     *
     * @param rxError
     */
    public void postRxError(final RxError rxError) {
        EventBus.getDefault().postSticky(rxError);
    }
}