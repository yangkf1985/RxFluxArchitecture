package com.huyingbao.module.wan.ui.login.store;

import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.store.RxActivityStore;
import com.huyingbao.module.wan.action.WanResponse;
import com.huyingbao.module.wan.ui.login.action.LoginAction;
import com.huyingbao.module.wan.ui.login.model.User;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by liujunfeng on 2019/1/2.
 */
@Singleton
public class LoginStore extends RxActivityStore {
    private WanResponse<User> mUser;

    @Inject
    LoginStore(RxDispatcher rxDispatcher) {
        super(rxDispatcher);
    }

    @Override
    @Subscribe
    public void onRxAction(RxAction rxAction) {
        switch (rxAction.getTag()) {
            case LoginAction.LOGIN:
                mUser = rxAction.getResponse();
                postChange(RxChange.newRxChange(rxAction.getTag()));
                break;
            case LoginAction.REGISTER:
                break;
        }
    }
}