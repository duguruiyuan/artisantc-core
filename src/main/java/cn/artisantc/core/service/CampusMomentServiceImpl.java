package cn.artisantc.core.service;

import cn.artisantc.core.persistence.entity.BaseMoment;
import cn.artisantc.core.persistence.repository.ArtMomentBrowseRecordRepository;
import cn.artisantc.core.persistence.repository.ArtMomentCommentLikeRepository;
import cn.artisantc.core.persistence.repository.ArtMomentCommentRepository;
import cn.artisantc.core.persistence.repository.ArtMomentForwardRecordRepository;
import cn.artisantc.core.persistence.repository.ArtMomentImageRepository;
import cn.artisantc.core.persistence.repository.ArtMomentLikeRepository;
import cn.artisantc.core.persistence.repository.ArtMomentRepository;
import cn.artisantc.core.persistence.repository.MyBlockRepository;
import cn.artisantc.core.persistence.repository.MyFavoriteArtMomentRepository;
import cn.artisantc.core.persistence.repository.MyFollowRepository;
import cn.artisantc.core.persistence.repository.OAuth2Repository;
import cn.artisantc.core.persistence.repository.UserRepository;
import cn.artisantc.core.persistence.repository.UserRewardOrderRepository;
import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentCommentView;
import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentGiveLikeSummaryView;
import cn.artisantc.core.web.rest.v1_0.vo.ArtMomentView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ArtMomentCommentViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ArtMomentLikesViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ArtMomentViewPaginationList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * “CampusMomentService”接口的实现类。
 * Created by xinjie.li on 2016/9/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Service
@Transactional
public class CampusMomentServiceImpl extends AbstractMomentService implements CampusMomentService {

    @Autowired
    public CampusMomentServiceImpl(ArtMomentRepository artMomentRepository, UserRepository userRepository,
                                   ArtMomentImageRepository artMomentImageRepository, ConversionService conversionService,
                                   ArtMomentCommentRepository commentRepository, ArtMomentLikeRepository likeRepository,
                                   ArtMomentBrowseRecordRepository browseRecordRepository, ArtMomentForwardRecordRepository forwardRecordRepository,
                                   MyFavoriteArtMomentRepository favoriteArtMomentRepository, MyBlockRepository blockRepository, TagService tagService,
                                   MyFollowRepository followRepository, UserRewardOrderRepository userRewardOrderRepository, OAuth2Repository oAuth2Repository,
                                   ArtMomentCommentLikeRepository commentLikeRepository) {
        super(artMomentRepository, userRepository, artMomentImageRepository, conversionService,
                commentRepository, likeRepository, browseRecordRepository, forwardRecordRepository,
                favoriteArtMomentRepository, blockRepository, tagService, followRepository, userRewardOrderRepository,
                oAuth2Repository, commentLikeRepository);
    }

    @Override
    public ArtMomentView post(String content, MultipartFile[] files, String location, String primaryTagName, String[] secondaryTagNames) {
        return super.postMoment(content, files, location, BaseMoment.Category.CAMPUS, primaryTagName, secondaryTagNames);
    }

    @Override
    public ArtMomentView forward(String content, String momentId, String location) {
        return super.forwardMoment(content, momentId, location, BaseMoment.Category.CAMPUS);
    }

    @Override
    public ArtMomentViewPaginationList findByPage(int page) {
        return super.findByCategoryAndStatusAndPage(BaseMoment.Category.CAMPUS, BaseMoment.Status.AVAILABLE, page);
    }

    @Override
    public ArtMomentView findById(String momentId) {
        return super.findOneByCategory(momentId, BaseMoment.Category.CAMPUS);
    }

    @Override
    public ArtMomentCommentView commentOn(String momentId, String parentCommentId, String comment) {
        return super.commentOnMoment(momentId, parentCommentId, comment, BaseMoment.Category.CAMPUS);
    }

    @Override
    public ArtMomentGiveLikeSummaryView giveLikeOnMomentList(String momentId) {
        return super.giveLikeForMomentList(momentId, BaseMoment.Category.CAMPUS);
    }

    @Override
    public ArtMomentGiveLikeSummaryView giveLikeOnMomentDetail(String momentId) {
        return super.giveLikeForMomentDetail(momentId, BaseMoment.Category.CAMPUS);
    }

    @Override
    public ArtMomentLikesViewPaginationList findLikesByMomentId(String momentId, int page) {
        return super.findLikesByMomentIdAndPage(momentId, BaseMoment.Category.CAMPUS, page);
    }

    @Override
    public ArtMomentCommentViewPaginationList findCommentsByMomentId(String momentId, int page) {
        return super.findCommentsByMomentIdAndPage(momentId, BaseMoment.Category.CAMPUS, page);
    }
}
