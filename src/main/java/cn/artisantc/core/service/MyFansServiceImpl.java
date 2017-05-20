package cn.artisantc.core.service;

import cn.artisantc.core.persistence.entity.MyFans;
import cn.artisantc.core.persistence.entity.MyFollow;
import cn.artisantc.core.persistence.entity.User;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.persistence.repository.MyFansRepository;
import cn.artisantc.core.persistence.repository.MyFollowRepository;
import cn.artisantc.core.persistence.repository.OAuth2Repository;
import cn.artisantc.core.persistence.specification.MyFansSpecification;
import cn.artisantc.core.util.LoginUserUtil;
import cn.artisantc.core.util.PageUtil;
import cn.artisantc.core.web.rest.v1_0.vo.MyFansView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.MyFansPaginationList;
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
import java.util.List;

/**
 * “MyFansService”接口的实现类。
 * Created by xinjie.li on 2016/9/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Service
@Transactional
public class MyFansServiceImpl implements MyFansService {

    private static final Logger LOG = LoggerFactory.getLogger(MyFansServiceImpl.class);

    private ConversionService conversionService;

    private OAuth2Repository oAuth2Repository;

    private MyFansRepository fansRepository;

    private MyFollowRepository followRepository;

    @Autowired
    public MyFansServiceImpl(ConversionService conversionService, OAuth2Repository oAuth2Repository,
                             MyFansRepository fansRepository, MyFollowRepository followRepository) {
        this.conversionService = conversionService;
        this.oAuth2Repository = oAuth2Repository;
        this.fansRepository = fansRepository;
        this.followRepository = followRepository;
    }

    @Override
    public MyFansPaginationList findByPage(int page) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，获取粉丝列表操作终止！请尝试重新登录后再进行。");
        }

        // 查询数据
        PageUtil pageUtil = new PageUtil(PageUtil.MY_FANS_PAGE_SIZE);
        page = pageUtil.getPageForPageable(page);
        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());

        Page<MyFans> fansPage = fansRepository.findAll(MyFansSpecification.findAllByMomentId(LoginUserUtil.getLoginUsername()), pageable);
        List<MyFans> fans = fansPage.getContent();

        // 重新组装数据
        MyFansPaginationList paginationList = new MyFansPaginationList();
        paginationList.setTotalPages(String.valueOf(fansPage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(fansPage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = fansPage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (fansPage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("fansPage.getNumber(): {}", fansPage.getNumber());
        LOG.debug("fansPage.getNumberOfElements(): {}", fansPage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));

        // 遍历查询结果列表，并进行类型转换
        if (null != fans) {
            List<MyFansView> followResponses = new ArrayList<>();
            for (MyFans fan : fans) {
                MyFansView fansResponse = conversionService.convert(fan, MyFansView.class);// 类型转换

                List<MyFollow> follows = followRepository.findByI_idAndFollow_id(user.getId(), fan.getFans().getId());
                if (null != follows && !follows.isEmpty()) {
                    fansResponse.setFollowed(Boolean.TRUE);// 已关注该粉丝
                }

                followResponses.add(fansResponse);
            }
            paginationList.getFansResponses().addAll(followResponses);
        }
        return paginationList;
    }
}
