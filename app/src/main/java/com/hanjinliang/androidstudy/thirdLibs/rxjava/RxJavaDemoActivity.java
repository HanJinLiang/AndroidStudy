package com.hanjinliang.androidstudy.thirdLibs.rxjava;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.hanjinliang.androidstudy.R;


import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.operators.observable.ObservableGroupBy;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.schedulers.Schedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * RxJava学习
 */
public class RxJavaDemoActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;



    List<AppInfo> appInfos = new ArrayList<AppInfo>();
    AppAdapter mAppAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_demo);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        mAppAdapter=new AppAdapter(R.layout.item_app_list,appInfos);

        mRecyclerView.setAdapter(mAppAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadApp();
            }
        });

        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                loadApp();
            }
        },100);


    }

    public class AppAdapter extends BaseQuickAdapter<AppInfo, BaseViewHolder> {
        public AppAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, AppInfo item) {
            helper.setText(R.id.app_name, item.getAppName());
            helper.setImageDrawable(R.id.app_logo, item.getImage());
        }
    }


    private void loadApp() {
        mAppAdapter.getData().clear();
        Observable.create(new ObservableOnSubscribe<AppInfo>() {
            @Override
            public void subscribe(ObservableEmitter<AppInfo> emitter) throws Exception {
                List<PackageInfo> packageInfos = RxJavaDemoActivity.this.getPackageManager().getInstalledPackages(0);
                for (int i = 0; i < packageInfos.size(); i++) {
                    PackageInfo packageInfo = packageInfos.get(i);
                    //过滤掉系统app
//            if ((ApplicationInfo.FLAG_SYSTEM & packageInfo.applicationInfo.flags) != 0) {
//                continue;
//            }
                    for (double j = 0; j < 10000000; j++) {
                        double x = j * i;
                    }

                    AppInfo appInfo = new AppInfo();
                    appInfo.setAppName(packageInfo.packageName);
                    if (packageInfo.applicationInfo.loadIcon(RxJavaDemoActivity.this.getPackageManager()) == null) {
                        continue;
                    }
                    appInfo.setImage(packageInfo.applicationInfo.loadIcon(RxJavaDemoActivity.this.getPackageManager()));
                    emitter.onNext(appInfo);
                }
                emitter.onComplete();
            }
        })
//
//                .take(5)//只取5条
//                .repeat(3)
                .map(new Function<AppInfo, AppInfo>() {
                    @Override
                    public AppInfo apply(AppInfo appInfo) throws Exception {
                        appInfo.setAppName(appInfo.getAppName().toUpperCase() );
                        return appInfo;
                    }
                })
//                .scan(new BiFunction<AppInfo, AppInfo, AppInfo>() {
//                    @Override
//                    public AppInfo apply(AppInfo appInfo, AppInfo appInfo2) throws Exception {
//                        if(appInfo.getAppName().length()>appInfo2.getAppName().length()){
//                           return appInfo;
//                        }else{
//                            return appInfo2;
//                        }
//
//                    }
//                })
//                .groupBy(new Function<AppInfo, String>() {
//                    @Override
//                    public String apply(AppInfo appInfo) throws Exception {
//                        return appInfo.getAppName().substring(8);
//                    }
//                })
                .distinct()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AppInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(AppInfo appInfo) {
                        LogUtils.e(appInfo.toString());
                        mAppAdapter.addData(appInfo);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        LogUtils.e("onComplete---" + appInfos.size());
//                mAppAdapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
        
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        });
    }


}

