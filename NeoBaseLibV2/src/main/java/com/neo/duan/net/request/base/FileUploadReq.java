package com.neo.duan.net.request.base;

/**
 * @author : neo.duan
 * @date : 	 2016/10/12
 * @desc : 文件上传
 */
public class FileUploadReq extends BaseRequest{
    private String filePath;
    public FileUploadReq(String filePath) {
        setFilePath(filePath);
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getApi() {
        return "Upload";
    }

//    @Override
//    public Class<? extends BaseInfo> getResponseClazz() {
//        return null;
//    }
}
