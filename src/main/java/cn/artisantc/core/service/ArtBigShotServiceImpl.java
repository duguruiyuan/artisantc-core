package cn.artisantc.core.service;

import cn.artisantc.core.persistence.entity.ArtBigShot;
import cn.artisantc.core.persistence.entity.User;
import cn.artisantc.core.persistence.entity.UserProfile;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.persistence.repository.ArtBigShotRepository;
import cn.artisantc.core.persistence.repository.MyFansRepository;
import cn.artisantc.core.persistence.repository.UserRepository;
import cn.artisantc.core.persistence.specification.ArtBigShotSpecification;
import cn.artisantc.core.util.PageUtil;
import cn.artisantc.core.web.rest.v1_0.vo.APIErrorResponse;
import cn.artisantc.core.web.rest.v1_0.vo.ArtBigShotView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ArtBigShotViewPaginationList;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * “ArtBigShotService”接口的实现类。
 * Created by xinjie.li on 2016/12/30.
 *
 * @author xinjie.li
 * @since 2.1
 */
@Service
@Transactional
public class ArtBigShotServiceImpl implements ArtBigShotService {

    private static final Logger LOG = LoggerFactory.getLogger(ArtBigShotServiceImpl.class);

    private ArtBigShotRepository artBigShotRepository;

    private MyFansRepository fansRepository;

    private UserRepository userRepository;

    private MessageSource messageSource;

    @Autowired
    public ArtBigShotServiceImpl(ArtBigShotRepository artBigShotRepository, MyFansRepository fansRepository, UserRepository userRepository,
                                 MessageSource messageSource) {
        this.artBigShotRepository = artBigShotRepository;
        this.fansRepository = fansRepository;
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    @Override
    public List<ArtBigShotView> getRecommendArtBigShot() {
        List<ArtBigShotView> artBigShotViews = new ArrayList<>();

        // 查询数据 todo：需要使用一种算法，来获得数据
        PageUtil pageUtil = new PageUtil(PageUtil.ART_BIG_SHOT_RECOMMEND_PAGE_SIZE);
        int page = pageUtil.getPageForPageable(PageUtil.FIRST_PAGE);

        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());
        Page<ArtBigShot> artBigShotPage = artBigShotRepository.findAll(ArtBigShotSpecification.findAllWithDefaultOrder(), pageable);
        List<ArtBigShot> artBigShots = artBigShotPage.getContent();

        if (null != artBigShots && !artBigShots.isEmpty()) {
            for (ArtBigShot artBigShot : artBigShots) {
                artBigShotViews.add(this.getArtBigShotView(artBigShot));
            }
        }

        return artBigShotViews;
    }

    @Override
    public ArtBigShotViewPaginationList findArtBigShots(int page) {
        ArtBigShotViewPaginationList paginationList = new ArtBigShotViewPaginationList();

        // 查询数据
        PageUtil pageUtil = new PageUtil(PageUtil.ART_BIG_SHOT_PAGE_SIZE);
        page = pageUtil.getPageForPageable(page);

        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());
        Page<ArtBigShot> artBigShotPage = artBigShotRepository.findAll(ArtBigShotSpecification.findAllWithDefaultOrder(), pageable);
        List<ArtBigShot> artBigShots = artBigShotPage.getContent();

        paginationList.setTotalPages(String.valueOf(artBigShotPage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(artBigShotPage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = artBigShotPage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (artBigShotPage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("artBigShotPage.getNumber(): {}", artBigShotPage.getNumber());
        LOG.debug("artBigShotPage.getNumberOfElements(): {}", artBigShotPage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));

        List<ArtBigShotView> artBigShotViews = new ArrayList<>();
        if (null != artBigShots && !artBigShots.isEmpty()) {
            for (ArtBigShot artBigShot : artBigShots) {
                artBigShotViews.add(this.getArtBigShotView(artBigShot));
            }
            paginationList.getArtBigShotViews().addAll(artBigShotViews);
        }

        return paginationList;
    }

    @Override
    public void upgradeToArtBigShot(String userId, String overview, String introduce) {
        // 校验“userId”
        if (StringUtils.isBlank(userId) || !NumberUtils.isParsable(userId)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010031.getErrorCode());
        }

        // 校验“overview”
        if (StringUtils.isBlank(overview) || overview.length() > APIErrorResponse.MAX_ART_BIG_SHOT_OVERVIEW_LENGTH) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010013.getErrorCode());
        }

        // 校验“introduce”
        if (StringUtils.isBlank(introduce) || introduce.length() > APIErrorResponse.MAX_ART_BIG_SHOT_INTRODUCE_LENGTH) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010014.getErrorCode());
        }

        // 查找对应的“用户信息”
        User user = userRepository.findOne(NumberUtils.toLong(userId));
        if (null == user) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010031.getErrorCode());
        }

        // 查找对应的“大咖信息”
        ArtBigShot artBigShot = artBigShotRepository.findByUser_id(user.getId());
        // 没有对应的“大咖信息”表示该用户不是大咖，执行“成为大咖”的流程
        if (null == artBigShot) {
            artBigShot = new ArtBigShot();
            artBigShot.setOverview(overview);
            artBigShot.setIntroduce(introduce);
            artBigShot.setUser(user);

            Date date = new Date();
            artBigShot.setCreateDateTime(date);
            artBigShot.setUpdateDateTime(date);

            artBigShotRepository.save(artBigShot);
        }
    }

    @Override
    public ArtBigShotView findArtBigShotById(String artBigShotId) {
        // 校验“artBigShotId”
        if (StringUtils.isBlank(artBigShotId) || !NumberUtils.isParsable(artBigShotId)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010015.getErrorCode());
        }

        // 查询“大咖”
        ArtBigShot artBigShot = artBigShotRepository.findOne(NumberUtils.toLong(artBigShotId));
        if (null == artBigShot) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010015.getErrorCode());
        }

        return this.getArtBigShotView(artBigShot);
    }

    @Override
    public void cancelArtBigShot(String artBigShotId) {
        // “artBigShotId”合法的情况下进行操作
        if (StringUtils.isNotBlank(artBigShotId) && NumberUtils.isParsable(artBigShotId)) {
            ArtBigShot artBigShot = artBigShotRepository.findOne(NumberUtils.toLong(artBigShotId));
            if (null != artBigShot) {
                artBigShotRepository.delete(artBigShot);
            }
        }
    }

    private ArtBigShotView getArtBigShotView(ArtBigShot artBigShot) {
        ArtBigShotView view = new ArtBigShotView();
        User user = artBigShot.getUser();

        view.setAvatarFileUrl(UserHelper.getAvatar3xUrl(user));
        view.setFans(String.valueOf(fansRepository.countByI_id(user.getId())));
        view.setIntroduce(artBigShot.getIntroduce());
        view.setOverview(artBigShot.getOverview());
        view.setId(String.valueOf(user.getId()));
        view.setNickname(user.getProfile().getNickname());
        view.setSerialNumber(user.getSerialNumber());
        view.setArgBigShot(null != artBigShotRepository.findByUser_id(user.getId()));
        view.setArtBigShotId(String.valueOf(artBigShot.getId()));
        if (null != user.getProfile().getSex()) {
            view.setSex(user.getProfile().getSex().name());
            view.setMale(user.getProfile().getSex() == UserProfile.UserSex.MALE);
            view.setFemale(user.getProfile().getSex() == UserProfile.UserSex.FEMALE);

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            view.setSexValue(messageSource.getMessage(user.getProfile().getSex().getMessageKey(), null, request.getLocale()));
        }
        // “头像”地址
        if (StringUtils.isNotBlank(user.getProfile().getAvatar3x())) {
            view.setAvatarFileUrl(UserHelper.getAvatar3xUrl(user));
        }

        return view;
    }
}
