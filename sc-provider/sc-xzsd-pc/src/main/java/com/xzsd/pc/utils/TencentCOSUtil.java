package com.xzsd.pc.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 腾讯云对象存储COS工具类
 *
 * @author 黄瑞穆
 * @date 2020-04-11
 */
@Component
public class TencentCOSUtil {

    //商品图片文件夹名称
    public static final String GOODSIMAGEFOLDER = "goodsImage";
    //轮播图文件夹名称
    public static final String ROLLIMAGEFOLDER = "rollImage";
    //商品评论图片文件夹名称
    public static final String GOODSCOMMENTIMAGEFOLDER = "goodsCommentImage";
    //头像文件夹名称
    public static final String HEADIMAGEFOLDER = "headImage";

    //商品图片分类编号
    public static final int GOODSIMAGECATE = 1;
    //轮播图分类编号
    public static final int ROLLIMAGECATE = 2;
    //商品评论图片分类编号
    public static final int GOODSCOMMENTIMAGECATE = 3;
    //头像分类编号
    public static final int HEADIMAGECATE = 4;


    @Value("${spring.tengxun.accessKey}")
    private String accessKey;
    @Value("${spring.tengxun.secretKey}")
    private String secretKey;
    @Value("${spring.tengxun.bucket}")
    private String bucket;
    @Value("${spring.tengxun.bucketName}")
    private String bucketName;
    @Value("${spring.tengxun.path}")
    private String path;
    @Value("${spring.tengxun.area}")
    private String area;

    /**
     * 获取腾讯的COS存储服务操作对象
     * 用于上传文件
     *
     * @return 返回COSClient对象用于上传文件
     */
    public COSClient getTencentCOSClient() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);
        // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig中包含了设置region, https(默认http), 超时, 代理等set方法, 使用可参见源码或者接口文档FAQ中说明
        ClientConfig clientConfig = new ClientConfig(new Region(area));
        // 3 生成cos客户端
        COSClient cosClient = new COSClient(cred, clientConfig);
        return cosClient;
    }

    /**
     * 上传图片
     *
     * @param file 要上传的图片文件
     * @param imageFolder 图片存放的文件夹
     * @return 返回上传的图片url地址
     * @throws IOException
     */
    public String uploadImage(MultipartFile file, String imageFolder) throws IOException {
        //获取COSClient对象进行上传文件
        COSClient cosClient = getTencentCOSClient();
        // 存储桶bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
        String bucketName = getBucketName();
        // 指定要上传到 COS 上对象键
        // 对象键（Key）是对象在存储桶中的唯一标识。
        // 例如，在对象的访问域名 `bucket1-1250000000.cos.ap-beijing.myqcloud.com/mydemo.jpg` 中，对象键为 mydemo.jpg
        // 对象键key的格式为：headImage/headImage_62058671ac4343d0baadf95f6ae9ef8c.png
        String key = imageFolder + "/" + imageFolder + "_" + UUIDUtils.getUUID() + "." + file.getContentType().substring(6);
        File localFile = null;
        //将 MultipartFile 类型 转为 File 类型
        localFile = File.createTempFile("temp", null);
        file.transferTo(localFile);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        String url = getPath() + "/" + key;
        return url;
    }


    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
