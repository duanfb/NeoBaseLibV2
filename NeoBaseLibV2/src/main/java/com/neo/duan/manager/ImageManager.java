package com.neo.duan.manager;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.neo.duan.utils.StringUtils;

/**
 * @author : neo.duan
 * @date : 	 2016/8/13
 * @desc : 图片管理器:用于管理加载图片，目前用的是Facebook出品Fresco
 * see ： see:http://fresco-cn.org/docs/index.html
 */
public class ImageManager {
    private final String TAG = "ImageManager";

    private ImageManager() {
    }

    public static final ImageManager getInstance() {
        return ImageManagerHolder.instance;
    }
    private static class ImageManagerHolder {
        private static final ImageManager instance = new ImageManager();
    }

    /**
     * 初始化配置缓存策略
     */
    public void init(Context context) {
//        以下为配置项，目前使用默认 see:http://fresco-cn.org/docs/index.html

//        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context)
//                .setBitmapMemoryCacheParamsSupplier(bitmapCacheParamsSupplier)
//                .setCacheKeyFactory(cacheKeyFactory)
//                .setDownsampleEnabled(true)
//                .setWebpSupportEnabled(true)
//                .setEncodedMemoryCacheParamsSupplier(encodedCacheParamsSupplier)
//                .setExecutorSupplier(executorSupplier)
//                .setImageCacheStatsTracker(imageCacheStatsTracker)
//                .setMainDiskCacheConfig(mainDiskCacheConfig)
//                .setMemoryTrimmableRegistry(memoryTrimmableRegistry)
//                .setNetworkFetchProducer(networkFetchProducer)
//                .setPoolFactory(poolFactory)
//                .setProgressiveJpegConfig(progressiveJpegConfig)
//                .setRequestListeners(requestListeners)
//                .setSmallImageDiskCacheConfig(smallImageDiskCacheConfig)
//                .build();
//        Fresco.initialize(context, config);
        Fresco.initialize(context);
    }


    /**
     * 检查图片是否已缓存在内存中，非disk
     * @param url 图片url
     * @return
     */
    public boolean isMemoryCache(String url) {
        if (StringUtils.isBlank(url)) {
            return false;
        }
        ImagePipeline pipeline = Fresco.getImagePipeline();
        return pipeline.isInBitmapMemoryCache(Uri.parse(url));
    }

    /**
     * 检查图片是否已缓存在disk中
     * @param url 图片url
     * @return
     */
    public boolean isInDiskCache(String url) {
        if (StringUtils.isBlank(url)) {
            return false;
        }
        ImagePipeline pipeline = Fresco.getImagePipeline();
        return pipeline.isInDiskCacheSync(Uri.parse(url));
    }

    /**
     * 清除缓存中某个图片
     * @param url 图片url
     */
    public void clearFromCache(String url) {
        ImagePipeline pipeline = Fresco.getImagePipeline();
        pipeline.evictFromCache(Uri.parse(url));
    }


    /**
     * 清除全部缓存
     */
    public void clearCache() {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearMemoryCaches();
        imagePipeline.clearDiskCaches();
        //或者 imagePipeline.clearCaches();
    }

    /**
     * 清除全部disk上图片缓存：一般用于用户手动清除缓存
     */
    public void clearDiskCaches() {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearDiskCaches();
    }

    /**
     * 清除全部disk上图片缓存：一般用于退出应用
     */
    public void clearMemoryCaches() {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearDiskCaches();
    }

    /**
     * 获取缓存大小
     * @return
     */
    public long getCacheSize() {
        return Fresco.getImagePipelineFactory().getMainFileCache().getSize();
    }

}
