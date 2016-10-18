package com.demo.maat.hello_rxjava;

import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.demo.maat.hello_rxjava.common.logger.Log;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class SchedulerFragment extends Fragment {


    static final String TAG = "SchedulerFragment";

    String[] data = {"1", "2", "3"};
    @BindView(R.id.btn_no_map)
    Button mBtnNoMap;
    @BindView(R.id.btn_just)
    Button mBtnJust;
    @BindView(R.id.btn_just_one)
    Button mBtnJustone;
    @BindView(R.id.btn_just_two)
    Button mBtnJusttwo;
    @BindView(R.id.btn_just_three)
    Button mBtnJustthree;
    @BindView(R.id.btn_just_four)
    Button mBtnJustfour;
    @BindView(R.id.btn_just_five)
    Button mBtnJustfive;
    @BindView(R.id.btn_just_six)
    Button mBtnJustsix;
    @BindView(R.id.btn_one_map)
    Button mBtnOneMap;
    @BindView(R.id.btn_two_map)
    Button mBtnTwoMap;
    @BindView(R.id.btn_long_operation)
    Button mBtnLongOperation;
    @BindView(R.id.progress_operation_running)
    ProgressBar mProgressOperationRunning;
    @BindView(R.id.progress_operation_two_running)
    ProgressBar mProgressOperationTwoRunning;
    private Subscription baseSubscription;
    private Subscription oneMapSubscription;
    private Subscription twoMapSubscription;
    private Subscription longOpeSubscription;
    private CompositeSubscription mCompositeSubscription;
    ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scheduler_fragment, container, false);
        ButterKnife.bind(this, view);
        mCompositeSubscription = new CompositeSubscription();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @OnClick({R.id.btn_just, R.id.btn_just_one, R.id.btn_just_two,
            R.id.btn_just_three, R.id.btn_just_four, R.id.btn_just_five,
            R.id.btn_just_six, R.id.btn_no_map, R.id.btn_one_map, R.id.btn_two_map, R.id.btn_long_operation})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_just:
                doJust();
                break;
            case R.id.btn_just_one:
                doJustOne();
                break;
            case R.id.btn_just_two:
                doJustTwo();
                break;
            case R.id.btn_just_three:
                doJustThree();
                break;
            case R.id.btn_just_four:
                doJustFour();
                break;
            case R.id.btn_just_five:
                doJustFive();
                break;
            case R.id.btn_just_six:
                doJustSix();
                break;
            case R.id.btn_no_map:
                doBaseOperation();
                break;
            case R.id.btn_one_map:
                doOnemapOperation();
                break;
            case R.id.btn_long_operation:
                doLongOperation();
                break;
            case R.id.btn_two_map:
                doTwomapWithLongOperation();
                break;
        }
    }


    private void myCode() {

        // TODO: 2016/10/18    如果一个Observable没有任何的的Subscriber，那么这个Observable是不会发出任何事件的。

        // TODO: 2016/10/18  1. 创建一个Observable(被观察者)对象，直接调用Observable.create即可
        Observable<String> myObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> sub) {
                // TODO: 2016/10/18   2.这里定义的Observable对象仅仅发出一个Hello World字符串，然后就结束了。
                sub.onNext("Hello, world!");
                sub.onCompleted();
            }
        });

// TODO: 2016/10/18 3.创建一个观察者对象
        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) {
                System.out.println(s);
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }
        };

        // TODO: 2016/10/18 4. 订阅关系
        myObservable.subscribe(mySubscriber);

        // TODO: 2016/10/18  说明:  一旦mySubscriber订阅了myObservable，myObservable就是调用mySubscriber对象的onNext和onComplete方法，mySubscriber就会打印出Hello World！


        // TODO: 2016/10/18 更简洁的代码

        // TODO: 2016/10/18 简化Observable对象的创建过程
        //todo  Observable.just就是用来创建只发出一个事件就结束的Observable对象
        Observable<String> observable = Observable.just("Hello, world!");


        // TODO: 2016/10/18  简化Subscriber
        // TODO: 2016/10/18 并不关心OnComplete和OnError，我们只需要在onNext的时候做一些处理，这时候就可以使用Action1类

        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        };
        myObservable.subscribe(onNextAction);


        // TODO: 2016/10/18 重载版本 myObservable.subscribe(onNextAction, onErrorAction, onCompleteAction);
        // TODO: 2016/10/18 接受三个Action1类型的参数，分别对应OnNext，OnComplete， OnError函数

// TODO: 2016/10/18 最终版本

        Observable.just("Hello, world!")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                });

        // TODO: 2016/10/18 其实认清了对象就好办了
        // TODO: 2016/10/18 中心就是 Observable对象.subscribe(Subscriber对象)  肯定少不了.subscribe
        // TODO: 2016/10/18  Observable对象创建: 1.Observable.create  2.Observable.just(T)
        // TODO: 2016/10/18  Subscriber对象创建: 1. new Subscriber<Object> 2. new Action1<Object>


// TODO: 2016/10/18  //使用map操作来完成类型转换
//TODO   String >"Hello, world!"转换成 Integer >s.hashCode()
        // TODO: 2016/10/18  用到的方法是map(new Func1<>())
        Observable.just("Hello, world!")
                .map(s -> {
                    return s.hashCode() + 11; //s.hashCode() 是一个Integer对象
                })
                .subscribe(i -> {
                    System.out.println(Integer.toString(i));
                });
        //简化写法  直接操作数据时的写法  获取abc的哈希值然后打印出来
        observable.just("abc").map(String::hashCode).subscribe(System.err::println);
    }


    private void myCode2() {
        // TODO: 2016/10/18 map操作符的使用  线程调度
        // TODO: 2016/10/18 线程调度只有subscribeOn（）和observeOn（）两个方法

        // TODO: 2016/10/18 subscribeOn（）只作用于被观察者创建阶段,指示Observable的创建线程。只能指定一次
        // TODO: 2016/10/18 observeOn（）可指定多次，每次指定完都在下一步生效。指定在事件传递（加工变换 map）和最终被处理（观察者 subscriber）的发生的线程。
        Observable.just("abc").  //创建Observable对象
                subscribeOn(Schedulers.newThread()).//Observable对象在子线程创建 subscribeOn
                observeOn(Schedulers.io()).  //将接下来执行的线程环境指定为io线程,进行耗时操作 observeOn下一步执行
                map(s -> s.hashCode() + 11).  //类型转换 String转integer 耗时操作
                observeOn(AndroidSchedulers.mainThread()).  //在主线程运行  observeOn 下一步执行
                subscribe(integer -> {                       //订阅
            android.util.Log.e("操作integer", integer.toString());  //操作integer 在主线程执行
        });

        Observable.just("bcd").
                subscribeOn(Schedulers.newThread()).observeOn(Schedulers.io()).

                flatMap(new Func1<String, Observable<File>>() {
                    @Override
                    public Observable<File> call(String s) {
                        if (s.equals("bcd")) {
                            File f = new File("abc");
                            return listFiles(f);
                        }
                        return null;
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(File::toString);
    }

    private Observable<File> listFiles(File f) {
        if (f.isDirectory()) {
            return Observable.from(f.listFiles()).flatMap(new Func1<File, Observable<File>>() {
                @Override
                public Observable<File> call(File file) {
                    return listFiles(f);
                }
            });
        } else {
            return Observable.just(f);
        }
    }


    private void doJust() {


// TODO: 2016/10/18 默认都在主线程
        Observable.just(1, 2, 3)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        printLog("Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        printLog("onError");

                    }

                    @Override
                    public void onNext(Integer integer) {
                        printLog("Next " + integer + " ");
                    }
                });
    }


    private void doJustOne() {
        // TODO: 2016/10/18 不在主线程
        Observable.just(1, 2, 3)
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        printLog("Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        printLog("onError");
                    }
                    @Override
                    public void onNext(Integer integer) {
                        printLog("Next " + integer + " ");
                    }
                });
    }


    private void doJustTwo() {
        // TODO: 2016/10/18 主线程
        Observable.just(1, 2, 3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        printLog("Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        printLog("onError");
                    }
                    @Override
                    public void onNext(Integer integer) {
                        printLog("Next " + integer + " ");
                    }
                });
    }


    private void doJustThree() {
        // TODO: 2016/10/18 非主线程 map类型转换
        Observable.just(1, 2, 3)
                .subscribeOn(Schedulers.io())
                .map(integer -> {
                    printLog("map  " + integer + "a");
                    return integer + "a";
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        printLog("Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        printLog("onError");

                    }

                    @Override
                    public void onNext(String s) {
                        printLog("Next " + s);

                    }

                });
    }


    private void doJustFour() {
// TODO: 2016/10/18 两个map
        Observable.just(1, 2, 3)
                .subscribeOn(Schedulers.io())
                .map(integer -> {
                    printLog("map1 " + integer + "a");
                    return integer + "a";
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(s -> {
                    printLog("map2 " + s + "b ");
                    return s + "b";
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        printLog("Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        printLog("onError");
                    }

                    @Override
                    public void onNext(String s) {
                        printLog("Next " + s);
                    }

                });
    }


    private void doJustFive() {
        // TODO: 2016/10/18 3个map 方法执行顺序有什么规律吗?
        Observable.just(1, 2, 3)
                .subscribeOn(Schedulers.io())
                .map(integer -> {
                    printLog("map1 " + integer + "a");
                    return integer + "a";
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(s -> {
                    printLog("map2 " + s + "b ");
                    return s + "b";
                })
                .observeOn(Schedulers.io())
                .map(s -> {
                    printLog("map3 " + s + "c ");
                    return s + "c";
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        printLog("Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        printLog("onError");

                    }

                    @Override
                    public void onNext(String s) {
                        printLog("Next " + s);
                    }

                });
    }


    private void doJustSix() {
        // TODO: 2016/10/18 订阅者在非主线程
        Observable.just(1, 2, 3)
                .subscribeOn(AndroidSchedulers.mainThread())
                .map(integer -> {
                    printLog("map1 " + integer + "a");
                    return integer + "a";
                })
                .observeOn(Schedulers.io())
                .map(s -> {
                    printLog("map2 " + s + "b ");
                    return s + "b";
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(s -> {
                    printLog("map3 " + s + "c ");
                    return s + "c";
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        printLog("Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        printLog("onError");
                    }

                    @Override
                    public void onNext(String s) {
                        printLog("Next " + s);
                    }

                });
    }


    /**
     * Subscriber加OnSubscribe基本使用,没有线程切换,没有过滤等操作
     */

    private void doBaseOperation() {
        baseSubscription = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                printLog("onStart in OnSubscribe");
                subscriber.onStart();
                int N = data.length; //{1,2,3}
                for (int i = 0; i < N; i++) {
                    printLog("onNext " + data[i] + " in OnSubscribe");
                    subscriber.onNext(data[i]);

                }
                printLog("OnCompleted in OnSubscribe");
                subscriber.onCompleted();

            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                printLog("OnCompleted in Subscriber");
            }

            @Override
            public void onError(Throwable e) {
                printLog("onError in Subscriber");
            }

            @Override
            public void onNext(String s) {
                printLog("onNext " + s + " in Subscriber");
            }
        });
        mCompositeSubscription.add(baseSubscription);

    }

    /**
     * 对数据做一次map操作,给每一个数据后面加一个"a",没有线程调度;
     */
    private void doOnemapOperation() {
        oneMapSubscription = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                printLog("onStart in OnSubscribe");
                subscriber.onStart();
                int N = data.length;
                for (int i = 0; i < N; i++) {
                    printLog("onNext " + data[i] + " in OnSubscribe");
                    subscriber.onNext(data[i]); // TODO: 2016/10/18 这里进行map操作 ,然后是next
                }
                printLog("OnCompleted in OnSubscribe");
                subscriber.onCompleted();
            }
        }).map(s -> {
            String result = s + "a";
            printLog(result);
            return result;
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                printLog("OnCompleted in Subscriber");
            }

            @Override
            public void onError(Throwable e) {
                printLog("onError in Subscriber");
            }

            @Override
            public void onNext(String s) {
                printLog("onNext " + s + " in Subscriber");
            }
        });
        mCompositeSubscription.add(oneMapSubscription);
    }


    /**
     * 模拟阻操作,如网络请求,文件读取,再加上scheduler,切换线程。
     */
    private void doLongOperation() {
        mProgressOperationRunning.setVisibility(View.VISIBLE);
        longOpeSubscription = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                printLog("onStart in OnSubscribe");
                subscriber.onStart();
                int N = data.length;
                for (int i = 0; i < N; i++) {
                    dosomethingBlockThread();  // TODO: 2016/10/18 延时操作
                    printLog("onNext" + data[i] + " in OnSubscribe");
                    subscriber.onNext(data[i]);
                }
                printLog("OnCompleted in OnSubscribe");
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        printLog("OnCompleted in Subscriber");
                        mProgressOperationRunning.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        printLog("onError in Subscriber");
                        mProgressOperationRunning.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onNext(String s) {
                        printLog("onNext " + s + " in Subscriber");

                    }
                });
        mCompositeSubscription.add(longOpeSubscription);
    }

    /**
     * 对数据进行两次map操作并加上线程调度,第一次map操作每个数据后面+"a",第二次map操作每个数据后面+"b".
     */
    private void doTwomapWithLongOperation() {
        mProgressOperationTwoRunning.setVisibility(View.VISIBLE);
        twoMapSubscription = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                printLog("onStart in OnSubscribe");
                subscriber.onStart();
                int N = data.length;
                for (int i = 0; i < N; i++) {
                    dosomethingBlockThread();
                    printLog("onNext " + data[i] + " in OnSubscribe");
                    subscriber.onNext(data[i]);

                }
                printLog("OnCompleted in OnSubscribe");
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(s -> {
                    String result = s + "a";
                    printLog("Map1 " + result);
                    return result;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(s -> {
                    String result = s + "b";
                    printLog("Map2 " + result);
                    return result;
                })
//                你可以把上面的注释了,使用这个.observeOn(AndroidSchedulers.mainThread())看看map2有什么变化
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        printLog("OnCompleted in Subscriber");
                        mProgressOperationTwoRunning.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        printLog("onError in Subscriber");
                        mProgressOperationTwoRunning.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onNext(String s) {
                        printLog("onNext " + s + " in Subscriber");

                    }
                });
        mCompositeSubscription.add(twoMapSubscription);
    }

    private void dosomethingBlockThread() {
        printLog("blocking....");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void printLog(String s) {
        Log.i(TAG, s + getThreadMessage());
    }

    private String getThreadMessage() {
        if (Looper.myLooper() == Looper.getMainLooper())
            return " MainThread";
        else return " Not-MainThread";
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.unsubscribe();
    }


}


