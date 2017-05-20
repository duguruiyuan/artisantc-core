package cn.artisantc.core.service;

import cn.artisantc.core.persistence.entity.Tag;
import cn.artisantc.core.persistence.repository.TagRepository;
import cn.artisantc.core.web.rest.v1_0.vo.APIErrorResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * “TagService”接口的实现类。
 * Created by xinjie.li on 2017/1/9.
 *
 * @author xinjie.li
 * @since 2.2
 */
@Service
@Transactional
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag findByName(String name) {
        if (StringUtils.isBlank(name) || name.length() > APIErrorResponse.MAX_TAG_LENGTH) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010052.getErrorCode());
        }
        Tag tag = tagRepository.findByName(name);
        if (null == tag) {
            tag = new Tag();
            tag.setName(name);

            Date date = new Date();
            tag.setCreateDateTime(date);
            tag.setCreateDateTime(date);

            tag = tagRepository.save(tag);
        }

        return tag;
    }
}
