package com.huyingbao.module.wan.module;

import com.huyingbao.core.arch.scope.ActivityScope;
import com.huyingbao.core.arch.store.RxStore;
import com.huyingbao.core.arch.store.RxStoreKey;
import com.huyingbao.module.gan.ui.category.store.CategoryStore;
import com.huyingbao.module.gan.ui.category.view.CategoryActivity;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Module其实是一个简单工厂模式，Module里面的方法基本都是创建类实例的方法。
 * Created by liujunfeng on 2017/12/7.
 */
@Module
public abstract class WanModule {
    /**
     * 创建类实例有2个维度可以创建：
     * 1:通过Module中的Provide注解或者Binds等注解来创建（以下简称Module维度）优先
     * 2:通过用Inject注解标注的构造函数来创建（以下简称Inject维度）
     * <p>
     * Qualifier注解用来区分同一纬度下两种不同的创建实例的方法，类似MapKey
     * <p>
     * Provider需要写明具体的实现，
     * Binds只是告诉Dagger2谁是谁实现的，
     * <p>
     * Dagger2会根据这些信息自动生成一个关键的Map。
     * key为ViewModel的Class，
     * value则为提供ViewModel实例的Provider对象，
     * 通过provider.get()方法就可以获取到相应的ViewModel对象。
     *
     * @param randomStore
     * @return
     */
    @Singleton
    @Binds//当参数和返回值类型相同时，将方法写成抽象方法，用Binds注解。
    @IntoMap//可以让Dagger2将多个元素依赖注入到Map之中
    @RxStoreKey(CategoryStore.class)
    abstract RxStore bindCategoryStore(CategoryStore randomStore);

    /**
     * ContributesAndroidInjector注解帮助我们生成方法的返回值类型（RandomActivity）的注射器
     * 自动生成注射器AndroidInjector子类RandomActivitySubcomponent
     * <p>
     * ContributesAndroidInjector用来简化Subcomponent的书写
     * <p>
     * 在2.11之前，每一个Fragment或者Activity都需要有自己的Component
     * 然后在Component中声明inject方法，
     * 最后在Fragment或者Activity中进行注入
     * <p>
     * ContributesAndroidInjector注解标注的方法必须是抽象方法
     * 并且返回值是具体的android framework的类型（比如Activity或者Fragment），
     * 此外，这个方法不能有参数
     * <p>
     * ActivityScope标注生成的Subcomponet生命周期跟随Activity
     * ContributesAndroidInjector指定DaggerAndroidActivity的Subcomponent中需要安装的Module
     * <p>
     * dagger.android会根据@ContributesAndroidInjector生成需要注入对应对象的SubComponent，
     * 并添加到map中
     *
     * @return
     */
    @ActivityScope
    @ContributesAndroidInjector(modules = WanActivityModule.class)
    abstract CategoryActivity injectCategoryActivity();
}
