package cn.artisantc.core.persistence.helper;


import cn.artisantc.core.exception.UploadAdvertisementImageFailureException;
import cn.artisantc.core.persistence.entity.User;
import cn.artisantc.core.util.ImageUtil;
import cn.artisantc.core.web.rest.v1_0.vo.APIErrorResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * “Information”的帮助类。
 * Created by xinjie.li on 2016/11/14.
 *
 * @author xinjie.li
 * @since 1.1
 */
public class InformationHelper {

    private InformationHelper() {
    }

    /**
     * 默认的“资讯来源”。
     */
    public static final String DEFAULT_SOURCE = "艺匠说";

    /**
     * 校验待新增或更新的“资讯”。
     *
     * @param title   资讯标题
     * @param content 资讯内容
     * @param source  资讯来源
     * @param user    资讯作者
     */
    public static void validateInformation(String title, String content, String source, User user) {
        // 校验“标题”
        if (StringUtils.isBlank(title) || title.length() > APIErrorResponse.MAX_INFORMATION_TITLE_LENGTH) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010300.getErrorCode());
        }

        // 校验“来源”
        if (StringUtils.isNotBlank(source) && source.length() > APIErrorResponse.MAX_INFORMATION_SOURCE_LENGTH) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010301.getErrorCode());
        }

        // 校验“内容”
        String toCheckContent = content.replaceAll("<p>", "").replaceAll("</p>", "").replaceAll("<br>", "");// 如果没有输入任何内容，则此处会显示的内容为“<p><br></p>”，这是由于编辑器造成的，因此这里要特殊处理
        if (StringUtils.isBlank(toCheckContent)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010302.getErrorCode());
        }

        // 校验“作者”
        if (null == user) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010303.getErrorCode());
        }
    }

    /**
     * 对“资讯内容”进行包装处理。
     *
     * @param content 资讯内容
     * @return 包装处理后的“资讯内容”
     */
    public static String getWrapperContent(String content) {
        if (StringUtils.isNotBlank(content) && content.endsWith("<p><br></p>")) {
            // 针对前端编辑器自动输入的内容的特殊处理，编辑器会在输入内容的结尾自动补全“<p><br></p>”代码，
            // 这样会造成一个单独的换行，因此这里做处理将这段代码过滤掉后，再存入到数据库
            content = StringUtils.substringBeforeLast(content, "<p><br></p>");
        }
        return content;
    }

    /**
     * 校验待上传的“封面图片”。
     *
     * @param coverImage 待上传的“封面图片”
     * @return 通过校验后的待上传的“封面图片”
     */
    public static MultipartFile validateInformationCoverImage(MultipartFile[] coverImage) {

        if (null == coverImage) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010053.getErrorCode());
        } else {
            List<MultipartFile> uploadedFiles = isImage(coverImage);// 通过校验准备上传的图片列表
            // 校验“上传图片”的个数，目前只允许上传1个图片
            if (uploadedFiles.size() > APIErrorResponse.MAX_INFORMATION_COVER_IMAGE_LENGTH) {
                APIErrorResponse.ErrorCode.E990032.setArgs(new String[]{String.valueOf(APIErrorResponse.MAX_INFORMATION_COVER_IMAGE_LENGTH)});
                throw new UploadAdvertisementImageFailureException(APIErrorResponse.ErrorCode.E990032.getErrorCode());
            }
            if (uploadedFiles.isEmpty()) {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010053.getErrorCode());
            }
            return uploadedFiles.iterator().next();
        }
    }

    /**
     * 校验待上传的“资讯内容图片”。
     *
     * @param images 待上传的“资讯内容图片”
     * @return 通过校验后的待上传的“资讯内容图片”
     */
    public static List<MultipartFile> validateInformationImages(MultipartFile[] images) {
        List<MultipartFile> uploadedFiles = new ArrayList<>();// 通过校验准备上传的图片列表
        if (null != images) {
            uploadedFiles = isImage(images);
        }
        return uploadedFiles;
    }

    private static List<MultipartFile> isImage(MultipartFile[] images) {
        List<MultipartFile> uploadedFiles = new ArrayList<>();
        for (MultipartFile file : images) {
            if (!file.isEmpty()) {
                if (!ImageUtil.isPicture(file)) {
                    throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990031.getErrorCode());
                }
                uploadedFiles.add(file);
            }
        }
        return uploadedFiles;
    }
}
