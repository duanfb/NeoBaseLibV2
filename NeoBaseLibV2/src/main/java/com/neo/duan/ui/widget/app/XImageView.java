package com.neo.duan.ui.widget.app;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.neo.duan.AppBaseApplication;
import com.neo.duan.R;
import com.neo.duan.utils.LogUtils;
import com.neo.duan.utils.StringUtils;


/**
 * @author : neo.duan
 * @date : 	 2016/7/25 0025
 * @desc : app应用内使用的TextView,方便日后维护
 */
public class XImageView extends SimpleDraweeView {
    protected final String TAG = getClass().getSimpleName();

    //setTag：key需要一个资源id，不然报错
    private static final int URL_TAG = R.id.url_tag;

    private GenericDraweeHierarchy mHierarchy;

    private ScalingUtils.ScaleType mDefaultScaleType = ScalingUtils.ScaleType.CENTER_INSIDE;
    private ScalingUtils.ScaleType mDefaultImageScaleType = ScalingUtils.ScaleType.CENTER_CROP;
    private int mDefaultFailureResId = R.drawable.ic_launcher;
    private int mDefaultPlaceholderResId = R.drawable.ic_launcher;
    private int mDefaultDuration = 300;

    private int mScreenWidth = AppBaseApplication.getInstance().getDeviceInfo().screenWith;
    private int mTwoItemWidth; //2item，单个item宽
    private int mFourItemWidth; //4item，单个item宽
    protected int mWidth;
    protected int mHeight;
    private int mDefaultWidth = mScreenWidth / 2; //默认宽为屏幕宽
    private int mDefaultHeight = mDefaultWidth; //默认高为宽度1/2


    private boolean isLoaderSuccess; //图片是否加载成功

    public XImageView(Context context) {
        this(context, null);
    }

    public XImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mHierarchy = this.getHierarchy();

        mTwoItemWidth = mScreenWidth / 2;
        mFourItemWidth = mScreenWidth / 4;

        addOnLayoutListener();
    }

    public void addOnLayoutListener() {
        //监听View的绘制
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = getWidth();
                int height = getHeight();
                //自己的控件大小取不到，取其父类控件大小
                if (width <= 10 || height <= 10) {
                    return ;
                }
                mWidth = width;
                mHeight = height;

                removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void removeOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }
        else {
            getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        }
    }

    /**
     * 初始化配置占位图等
     */
    protected void init() {
//        if (mWidth > mTwoItemWidth) {
//            mDefaultPlaceholderResId = R.drawable.ggj_occupy_pic_1_1;
//            mDefaultFailureResId = R.drawable.ggj_occupy_pic_1_1;
//        } else if (mWidth > mFourItemWidth && mWidth <= mTwoItemWidth) {
//            mDefaultPlaceholderResId = R.drawable.ggj_occupy_pic_1_2;
//            mDefaultFailureResId = R.drawable.ggj_occupy_pic_1_2;
//        } else {
//            mDefaultPlaceholderResId = R.drawable.ggj_occupy_pic_1_4;
//            mDefaultFailureResId = R.drawable.ggj_occupy_pic_1_4;
//        }

        setPlaceholderImage(mDefaultPlaceholderResId);
        setFailureImage(mDefaultFailureResId);

        //图像是缩放类型
        switch (getScaleType()) {
            case FIT_CENTER: //如果是FIT_CENTER默认的，代表未设置
                mHierarchy.setActualImageScaleType(mDefaultImageScaleType);
                break;
            case FIT_XY:
                mHierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
                break;
            case CENTER:
                mHierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER);
                break;
            case CENTER_CROP:
                mHierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
                break;
            case CENTER_INSIDE:
                mHierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE);
                break;
            default: //其他使用默认
                mHierarchy.setActualImageScaleType(mDefaultImageScaleType);
                break;
        }

        //设置动画显示渐变时间
        setFadeDuration(mDefaultDuration);
        //设置背景颜色:如果是圆形头像，设置背景为透明
        if (isRoundAsCircle()) {
            setBackgroundColor(getResources().getColor(android.R.color.transparent));
        } else {
            setBackgroundColor(Color.parseColor("#fafafa"));
        }
    }

    public void setFailureImage(int resId) {
        setFailureImage(resId, mDefaultScaleType);
    }

    /**
     * 失败占位图
     *
     * @param resId 资源id
     * @param scaleType 缩放类型
     */
    public void setFailureImage(int resId, ScalingUtils.ScaleType scaleType) {
        mHierarchy.setFailureImage(resId, scaleType);
    }

    /**
     * 加载中占位图
     *
     * @param resId 资源id
     */
    public void setPlaceholderImage(int resId) {
        setPlaceholderImage(resId, mDefaultScaleType);
    }

    public void setPlaceholderImage(int resId, ScalingUtils.ScaleType scaleType) {
        mHierarchy.setPlaceholderImage(resId, scaleType);
    }

    /**
     * 设置缩放类型
     *
     * @param scaleType 缩放类型
     */
    public void setActualImageScaleType(ScalingUtils.ScaleType scaleType) {
        mHierarchy.setActualImageScaleType(scaleType);
    }

    /**
     * 是否圆形
     *
     * @return 是否圆形
     */
    public boolean isRoundAsCircle() {
        RoundingParams roundingParams = mHierarchy.getRoundingParams();
        return roundingParams != null && roundingParams.getRoundAsCircle();
    }

    /**
     * 设置动画显示渐变时间
     *
     * @param duration 渐变时间
     */
    public void setFadeDuration(int duration) {
        mHierarchy.setFadeDuration(duration);
    }

    /*类型	Scheme	示例
    远程图片	http://, https://	HttpURLConnection 或者参考 使用其他网络加载方案
    本地文件	file://	FileInputStream
    Content provider	content://	ContentResolver
    asset目录下的资源	asset://	AssetManager
    res目录下的资源	res://	Resources.openRawResource
    res 示例:

    Uri uri = Uri.parse("res://包名(实际可以是任何字符串甚至留空)/" + R.drawable.ic_launcher);*/

    public void setImageUrl(String url) {
        if (StringUtils.isBlank(url)) {
            url = "";
        }
        setImageUrl(url, null);
    }

    public void setImageUrl(String url, ControllerListener<ImageInfo> listener) {
        LogUtils.d(TAG, "iamge url ==" + url);
        setImageURI(Uri.parse(url), listener);
    }

    public void setImageUrl(String path, int width, int height, ControllerListener listener) {
        if (path == null) {
            path = "";
        }
        this.mWidth = width;
        this.mHeight = height;
        setImageURI(Uri.parse(path), listener);
    }

    /**
     * 加载Resource资源下的图片
     *
     * @param resId 资源id
     */
    @Override
    public void setImageResource(int resId) {
        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME) // "res"
                .path(String.valueOf(resId))
                .build();
        setImageURI(uri);
    }

    @Override
    public void setImageURI(Uri uri) {
        this.setImageURI(uri, null);
    }

    public void setImageURI(Uri uri, ControllerListener listener) {
        //有宽高直接显示
        if (mWidth > 0 && mHeight > 0) {
            displayImage(uri, listener);
            return;
        }

        //先看设置的宽高
        ViewGroup.LayoutParams params = getLayoutParams();
        if (params != null) {
            mWidth = params.width > 0 ? params.width : mWidth;
            mHeight = params.height > 0 ? params.height : mHeight;
        }

        //取不到：再看绘制的宽高
        if (mWidth <= 0 ) {
            mWidth = getMeasuredWidth();
        }

        if (mHeight <= 0) {
            mHeight = getMeasuredHeight();
        }

        //取不到：再取默认
        if (mWidth <= 0 ) {
            mWidth = mDefaultWidth;
        }
        if (mHeight <= 0) {
            mHeight = mDefaultHeight;
        }

        displayImage(uri, listener);
    }

    private void displayImage(Uri uri, ControllerListener listener) {
        //校验图片上显示的图片是否为tag url，相同url则不显示
        Object tagObj = getTag(URL_TAG);
        if (tagObj == null && !TextUtils.isEmpty(uri.getPath())) {
            isLoaderSuccess = false;
            setTag(URL_TAG, uri.getPath());
        } else if (tagObj instanceof String) {
            if (tagObj.equals(uri.getPath()) && isLoaderSuccess) {
                if (listener != null) {
                    //不显示图片的时候，回调一次加载完成
                    ImageInfo info = new ImageInfo() {
                        @Override
                        public int getWidth() {
                            return mWidth;
                        }

                        @Override
                        public int getHeight() {
                            return mHeight;
                        }

                        @Override
                        public QualityInfo getQualityInfo() {
                            return getQualityInfo();
                        }
                    };
                    listener.onFinalImageSet("id好像不会用到", info, getController().getAnimatable());
                }
                return;
            } else {
                setTag(URL_TAG, uri.getPath());
            }
        }

        init();

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(mWidth, mHeight))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setImageRequest(request)
                .setTapToRetryEnabled(false)
                .setControllerListener(new ImageLoaderListener(listener))
                .setOldController(this.getController())
                .build();
        this.setController(controller);
    }

    /**
     * 再代理一次，监听到图片是否加载完成
     */
    private class ImageLoaderListener implements ControllerListener<ImageInfo> {
        private ControllerListener listener;

        public ImageLoaderListener(ControllerListener listener) {
            this.listener = listener;
        }

        @Override
        public void onSubmit(String id, Object callerContext) {
            if (listener != null) {
                listener.onSubmit(id, callerContext);
            }
        }

        @Override
        public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
            if (listener != null) {
                listener.onFinalImageSet(id, imageInfo, animatable);
            }
            isLoaderSuccess = true;
        }

        @Override
        public void onIntermediateImageSet(String id, ImageInfo imageInfo) {
            if (listener != null) {
                listener.onIntermediateImageSet(id, imageInfo);
            }
        }

        @Override
        public void onIntermediateImageFailed(String id, Throwable throwable) {
            if (listener != null) {
                listener.onIntermediateImageFailed(id, throwable);
            }
        }

        @Override
        public void onFailure(String id, Throwable throwable) {
            if (listener != null) {
                listener.onFailure(id, throwable);
            }
        }

        @Override
        public void onRelease(String id) {
            if (listener != null) {
                listener.onRelease(id);
            }
        }
    }
}
