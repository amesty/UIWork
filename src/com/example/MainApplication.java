package com.example;

import java.net.CookieHandler;
import java.net.CookieManager;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.example.core.DatabaseHelper;
import com.example.core.Session;
import com.example.model.Answer;
import com.example.model.Appointment;
import com.example.model.Article;
import com.example.model.CommentTest;
import com.example.model.Counselor;
import com.example.model.FollowCounselor;
import com.example.model.Problem;
import com.example.model.Question;
import com.example.model.Reply;
import com.example.model.Test;
import com.example.model.TestAnswer;
import com.example.model.TestReply;
import com.example.model.TestScore;
import com.example.model.User;
import com.example.model.UserInfo;
import com.example.uiwork.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public class MainApplication extends Application {

    private static final String TAG = "MainApplication";
	public static MainActivity mainActivity;
    private Bus mBus;
    private Session mSession;
    public static DatabaseHelper mDatabaseHelper;

    private Handler mHandler = new Handler();
    public static MainApplication INSTANCE;

	// login user name
	public final String PREF_USERNAME = "username";
	
	
    /**
     * 应用程序被创建时的入口
     */
    @Override
    public void onCreate() {	
        Log.d(TAG, "Application OnCreate....");
        super.onCreate();
        // 注册应用程序对象
        AVObject.registerSubclass(Reply.class);
        AVObject.registerSubclass(Answer.class);
        AVObject.registerSubclass(UserInfo.class);
        AVObject.registerSubclass(FollowCounselor.class);
        AVObject.registerSubclass(Appointment.class);
        AVUser.alwaysUseSubUserClass(User.class);
        //AVObject.registerSubclass(User.class);
        AVObject.registerSubclass(Problem.class);
        AVObject.registerSubclass(Counselor.class);
        AVObject.registerSubclass(Article.class);
        AVObject.registerSubclass(Test.class);
        AVObject.registerSubclass(Question.class);
        AVObject.registerSubclass(TestAnswer.class);
        AVObject.registerSubclass(TestScore.class);
        AVObject.registerSubclass(TestReply.class);
        AVObject.registerSubclass(CommentTest.class);
        AVOSCloud.useAVCloudCN();
        AVOSCloud.setDebugLogEnabled(true);
        AVOSCloud.initialize(this, "p1fhHotE3pljLKHr6YSNyVhb-gzGzoHsz", "YJT0XErLqVVwCK4ltAoHijn4");
        INSTANCE = this;
        
        // 注册事件监听
        mBus = new Bus(ThreadEnforcer.MAIN);
        mBus.register(this);

        // 内存缓存信息
        mSession = new Session(this);
        // 数据管理对象
        //mDatabaseHelper = new DatabaseHelper(this);
        
        
        // 初始化Android-Universal-Image-Loader三大组件
        initImageLoader();

        //XXX enable cookie, otherwise HttpUrlConnection doesn't support cookie
        //让网络连接使用Cookie
        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        
    }
    
    /**
     * 取得Session
     * @return
     */
    public Session getSession() {
        return mSession;
    }
    
    public DatabaseHelper getDatabaseHelper(){
    	return mDatabaseHelper;
    }
    
    /**
     * 程序终止之前执行的操作
     * 将环信进程退出
     */
    @Override
    public void onTerminate() {
        Log.d(TAG, "onTerminate()");
        super.onTerminate();
    }


    public MainActivity getMainActivity(){
    	return mainActivity;
    }
    
    /**
     * 框架要求
     * @return
     */
    public Bus getBus() {
        return mBus;
    }

    //注册事件，框架使用
    public void registerEvent(Object object) {
        mBus.register(object);
    }

    //撤销事件注册，框架使用
    public void unregisterEvent(Object object) {
        mBus.unregister(object);
    }
 
  
    /**
     * 发布事件
     * @param event
     */
    public void postEvent(final Object event) {
        //NOTE: make sure the event is post in main thread
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                getBus().post(event);
            }
        });
    }

    /**
     * 显示Toast通知
     * @param message
     */
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    
    /**
     * 显示Toast通知
     * @param message
     */
    public void showMessage(int messageResouceId) {
        Toast.makeText(this, getResources().getString(messageResouceId), Toast.LENGTH_SHORT).show();
    }


    /**
     * 初始化图片下载对象
     * Android-Universal-Image-Loader三大组件DisplayImageOptions、ImageLoaderConfiguration、ImageLoader
     */
    private void initImageLoader() {
    	/*显示图像选项参数设置
    	 *用于指导每一个Imageloader根据网络图片的状态（空白、下载错误、正在下载）显示对应的图片，
    	 *是否将缓存加载到磁盘上，下载完后对图片进行怎么样的处理
    	 */
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
//                .showImageOnLoading(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.default_image)
                .showImageOnFail(R.drawable.default_image)
                .build();
        
        int memoryCacheSize = (int) (Runtime.getRuntime().maxMemory() / 4);
        int threadPoolSize = 2;
        Log.i(TAG, " MAX HEAP SIZE = " + Runtime.getRuntime().maxMemory() / 1024 / 1024 + "m" + " threadpoolsize=" + threadPoolSize);
        
        /*ImageLoaderConfiguration是针对图片缓存的全局配置，
         * 主要有线程类、缓存大小、磁盘大小、图片下载与解析、日志方面的配置
         */
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY + 2)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .defaultDisplayImageOptions(defaultOptions)
//              .writeDebugLogs() // Not necessary in common
                .diskCache(new UnlimitedDiscCache(StorageUtils.getIndividualCacheDirectory(this)))
                .denyCacheImageMultipleSizesInMemory()
                .threadPoolSize(threadPoolSize)
                .memoryCache(new LruMemoryCache(memoryCacheSize))
                .imageDownloader(new BaseImageDownloader(this, BaseImageDownloader.DEFAULT_HTTP_CONNECT_TIMEOUT * 4, BaseImageDownloader.DEFAULT_HTTP_READ_TIMEOUT * 2))
                .build();
        
        /*ImageLoader是具体下载图片，缓存图片，显示图片的具体执行类，
         * 它有两个具体的方法displayImage(...)、loadImage(...)，但是其实最终他们的实现都是displayImage(...)
         */
        ImageLoader.getInstance().init(config);
    }
}
