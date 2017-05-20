package cn.artisantc.core.service;

import cn.artisantc.core.persistence.entity.MyFans;
import cn.artisantc.core.persistence.entity.MyFollow;
import cn.artisantc.core.persistence.entity.User;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.persistence.repository.MyFansRepository;
import cn.artisantc.core.persistence.repository.MyFollowRepository;
import cn.artisantc.core.persistence.repository.OAuth2Repository;
import cn.artisantc.core.persistence.repository.UserRepository;
import cn.artisantc.core.persistence.specification.MyFollowSpecification;
import cn.artisantc.core.util.LoginUserUtil;
import cn.artisantc.core.util.PageUtil;
import cn.artisantc.core.web.rest.v1_0.vo.APIErrorResponse;
import cn.artisantc.core.web.rest.v1_0.vo.MyFollowView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.MyFollowPaginationList;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * “MyFollowService”接口的实现类。
 * Created by xinjie.li on 2016/9/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Service
@Transactional
public class MyFollowServiceImpl implements MyFollowService {

    private static final Logger LOG = LoggerFactory.getLogger(MyFollowServiceImpl.class);

    private ConversionService conversionService;

    private UserRepository userRepository;

    private MyFollowRepository followRepository;

    private MyFansRepository fansRepository;

    private OAuth2Repository oAuth2Repository;

    @Autowired
    public MyFollowServiceImpl(ConversionService conversionService, UserRepository userRepository, MyFollowRepository followRepository,
                               MyFansRepository fansRepository, OAuth2Repository oAuth2Repository) {
        this.conversionService = conversionService;
        this.userRepository = userRepository;
        this.followRepository = followRepository;
        this.fansRepository = fansRepository;
        this.oAuth2Repository = oAuth2Repository;
    }

    @Override
    public MyFollowPaginationList findByPage(int page) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，获取关注列表操作终止！请尝试重新登录后再进行。");
        }

        // 查询数据
        PageUtil pageUtil = new PageUtil(PageUtil.MY_FOLLOW_PAGE_SIZE);
        page = pageUtil.getPageForPageable(page);
        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());

        Page<MyFollow> followPage = followRepository.findAll(MyFollowSpecification.findAllByMobile(user.getId()), pageable);
        List<MyFollow> follows = followPage.getContent();

        // 重新组装数据
        MyFollowPaginationList paginationList = new MyFollowPaginationList();
        paginationList.setTotalPages(String.valueOf(followPage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(followPage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = followPage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (followPage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("followPage.getNumber(): {}", followPage.getNumber());
        LOG.debug("followPage.getNumberOfElements(): {}", followPage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));

        // 遍历查询结果列表，并进行类型转换
        if (null != follows) {
            List<MyFollowView> followResponses = new ArrayList<>();
            for (MyFollow follow : follows) {
                followResponses.add(conversionService.convert(follow, MyFollowView.class));// 类型转换
            }
            paginationList.getFollowViews().addAll(followResponses);
        }
        return paginationList;
    }

    @Override
    public void follow(String followUserId) {
        // 校验“followUserId”，并查找要关注的用户是否存在
        if (StringUtils.isBlank(followUserId) || !NumberUtils.isParsable(followUserId)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010031.getErrorCode());
        }
        User followUser = userRepository.findOne(NumberUtils.toLong(followUserId));
        if (null == followUser) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010031.getErrorCode());
        }

        // 校验当前登录用户信息
        User i = userRepository.findByMobile(LoginUserUtil.getLoginUsername());
        if (null == i) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，关注/取消关注操作终止！请尝试重新登录后再进行。");
        }

        // 校验当前用户和要“关注/取消关注”的用户是否是同一人
        if (i.getId() == followUser.getId()) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010032.getErrorCode());
        }

        Date date = new Date();// 生成时间，保证该数据需要用到时间的地方的数据一致性
        // 查找是否已经关注
        List<MyFollow> follows = followRepository.findByI_idAndFollow_id(i.getId(), NumberUtils.toLong(followUserId));
        if (null == follows || follows.isEmpty()) {
            // 没有关注，执行“关注”操作
            MyFollow follow = new MyFollow();
            follow.setCreateDateTime(date);
            follow.setUpdateDateTime(date);
            follow.setI(i);
            follow.setFollow(followUser);
            followRepository.save(follow);// 关注

            // 把当前执行“关注”操作的用户添加到“被关注的用户”的“粉丝列表”里
            MyFans fans = new MyFans();
            fans.setCreateDateTime(date);
            fans.setUpdateDateTime(date);
            fans.setI(followUser);
            fans.setFans(i);
            fansRepository.save(fans);// 成为粉丝
        } else {
            // 已经关注，则执行“取消关注”操作
            followRepository.delete(follows);// 取消关注

            // 把当前执行“取消关注”操作的用户从“被关注的用户”的“粉丝列表”里删除
            List<MyFans> fans = fansRepository.findByI_mobileAndFans_id(followUser.getMobile(), i.getId());
            fansRepository.delete(fans);
        }
    }

    @Override
    public void followArtBigShots(String[] followUserIds) {
        if (ArrayUtils.isNotEmpty(followUserIds)) {
            List<String> availableFollowUserIdList = new ArrayList<>();// “合法有效的大咖用户的ID”列表
            List<String> followUserIdList = new ArrayList<>();// “待关注的大咖用户的ID”列表
            for (String followUserId : followUserIds) {
                if (StringUtils.isNotBlank(followUserId) && NumberUtils.isParsable(followUserId)) {
                    availableFollowUserIdList.add(followUserId);
                    followUserIdList.add(followUserId);
                }
            }

            // 校验当前登录用户信息
            User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
            if (null == user) {
                return;// 终止操作
            }

            // 查询“我关注的用户”
            List<MyFollow> myFollows = followRepository.findByI_id(user.getId());
            for (String followUserId : availableFollowUserIdList) {
                for (MyFollow myFollow : myFollows) {
                    // 若“我关注的用户”中已经包含了“待关注的大咖”，则从“待关注的大咖用户的ID”列表中移除该“大咖用户”
                    if (myFollow.getFollow().getId() == NumberUtils.toLong(followUserId)) {
                        followUserIdList.remove(followUserId);
                        break;
                    }
                }
            }

            // 执行“关注”
            for (String followUserId : followUserIdList) {
                try {
                    this.follow(followUserId);
                } catch (IllegalArgumentException e) {
                    String errorCode = e.getMessage();
                    if (APIErrorResponse.ErrorCode.E010032.getErrorCode().equals(errorCode)) {
                        LOG.info("用户关注了身为大咖的“自己”！忽略用户的行为。");
                    } else {
                        throw e;
                    }
                }
            }
        }
    }
}
