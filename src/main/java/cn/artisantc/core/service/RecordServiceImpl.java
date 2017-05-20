package cn.artisantc.core.service;

import cn.artisantc.core.exception.MomentNotFoundException;
import cn.artisantc.core.persistence.entity.ArtMoment;
import cn.artisantc.core.persistence.entity.ArtMomentReportRecord;
import cn.artisantc.core.persistence.entity.User;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.persistence.repository.ArtMomentReportRecordRepository;
import cn.artisantc.core.persistence.repository.ArtMomentRepository;
import cn.artisantc.core.persistence.repository.OAuth2Repository;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * “RecordService”接口的实现类。
 * Created by xinjie.li on 2016/9/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Service
@Transactional
public class RecordServiceImpl implements RecordService {

    private ArtMomentReportRecordRepository momentReportRecordRepository;

    private ArtMomentRepository artMomentRepository;

    private OAuth2Repository oAuth2Repository;

    @Autowired
    public RecordServiceImpl(ArtMomentReportRecordRepository momentReportRecordRepository, ArtMomentRepository artMomentRepository,
                             OAuth2Repository oAuth2Repository) {
        this.momentReportRecordRepository = momentReportRecordRepository;
        this.artMomentRepository = artMomentRepository;
        this.oAuth2Repository = oAuth2Repository;
    }

    @Override
    public void reportMoment(String momentId) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，布操作终止！请尝试重新登录后再进行。");
        }

        // 校验“momentId”
        if (!NumberUtils.isParsable(momentId)) {
            throw new MomentNotFoundException("没有找到指定“momentId”对应的资源：" + momentId);
        }

        // 查找转发的“原文”
        ArtMoment artMoment = artMomentRepository.findOne(NumberUtils.toLong(momentId));
        if (null == artMoment) {
            // 没有找到给定“momentId”的艺文
            throw new MomentNotFoundException("没有找到指定“momentId”对应的资源：" + momentId);
        }

        ArtMomentReportRecord reportRecord = new ArtMomentReportRecord();
        reportRecord.setArtMoment(artMoment);
        reportRecord.setUser(user);

        Date date = new Date();
        reportRecord.setCreateDateTime(date);
        reportRecord.setUpdateDateTime(date);

        momentReportRecordRepository.save(reportRecord);
    }
}
