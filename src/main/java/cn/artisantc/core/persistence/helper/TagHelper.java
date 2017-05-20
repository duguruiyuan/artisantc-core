package cn.artisantc.core.persistence.helper;

import cn.artisantc.core.persistence.entity.Tag;
import cn.artisantc.core.service.TagService;
import cn.artisantc.core.web.rest.v1_0.vo.APIErrorResponse;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * “Tag”的帮助类。
 * Created by xinjie.li on 2017/1/16.
 *
 * @author xinjie.li
 * @since 2.3
 */
public class TagHelper {

    private TagHelper() {
    }

    /**
     * 校验指定“标签名称”做为“主要标签”。
     *
     * @param primaryTagName 标签名称
     * @param tagService     TagService
     * @return “标签名称”对应的“主要标签”信息
     */
    public static Tag validatePrimaryTag(String primaryTagName, TagService tagService) {
        assert null != tagService;
        if (StringUtils.isBlank(primaryTagName)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010058.getErrorCode());
        }
        return tagService.findByName(primaryTagName);
    }

    /**
     * 校验指定“标签名称”做为“次要标签”。
     *
     * @param secondaryTagNames 标签名称，数组
     * @param tagService        TagService
     * @return “标签名称”对应的“次要标签”信息
     */
    public static List<Tag> validateSecondaryTags(String[] secondaryTagNames, TagService tagService) {
        List<Tag> secondaryTags = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(secondaryTagNames)) {
            List<String> availableSecondaryTagNames = new ArrayList<>();
            for (String secondaryTagName : secondaryTagNames) {
                if (StringUtils.isNotBlank(secondaryTagName)) {
                    availableSecondaryTagNames.add(secondaryTagName);
                }
            }
            if (secondaryTagNames.length > APIErrorResponse.MAX_INFORMATION_SECONDARY_TAGS_LENGTH) {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010057.getErrorCode());
            }
            for (String secondaryTagName : availableSecondaryTagNames) {
                Tag secondaryTag = tagService.findByName(secondaryTagName);
                secondaryTags.add(secondaryTag);
            }
        }
        return secondaryTags;
    }
}
