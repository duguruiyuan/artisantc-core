package cn.artisantc.core.service;

import cn.artisantc.core.web.rest.v1_0.vo.AdvertisementDetailView;
import cn.artisantc.core.web.rest.v1_0.vo.AdvertisementImageView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.AdvertisementViewPaginationList;
import org.springframework.web.multipart.MultipartFile;

/**
 * 支持“广告”操作的服务接口。
 * Created by xinjie.li on 2016/11/4.
 *
 * @author xinjie.li
 * @since 1.0
 */
public interface AdvertisementService {

    /**
     * 获得“广告列表”。
     *
     * @param page     分页
     * @param pageSize 分页记录数
     * @return 广告列表
     */
    AdvertisementViewPaginationList findByPage(int page, int pageSize);

    /**
     * 获得指定“广告ID”的广告详情。
     *
     * @param id 广告ID
     * @return 指定“广告ID”的广告详情
     */
    AdvertisementDetailView findById(String id);

    /**
     * 发表一篇新的“广告ID”。
     *
     * @param title       广告的标题
     * @param imageName   广告的图片名称
     * @param imageWidth  广告的图片宽度
     * @param imageHeight 广告的图片高度
     */
    void create(String title, String imageName, String imageWidth, String imageHeight);

    /**
     * 上传“广告图片”。
     *
     * @param images 待上传的图片
     * @return 上传成功后的广告图片的URL地址
     */
    AdvertisementImageView uploadAdvertisementImage(MultipartFile[] images);

    /**
     * 从已上传的“广告图片”中删除指定文件名的图片文件。
     *
     * @param fileName 待删除的文件名
     */
    void deleteAdvertisementImage(String fileName);
}
