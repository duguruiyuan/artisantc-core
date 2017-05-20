package cn.artisantc.core.service;

import cn.artisantc.core.exception.MomentNotFoundException;
import cn.artisantc.core.persistence.entity.ArtMoment;
import cn.artisantc.core.persistence.entity.MyFavoriteArtMoment;
import cn.artisantc.core.persistence.entity.User;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.persistence.repository.ArtMomentRepository;
import cn.artisantc.core.persistence.repository.MyFavoriteArtMomentRepository;
import cn.artisantc.core.persistence.repository.OAuth2Repository;
import cn.artisantc.core.persistence.specification.MyFavoriteArtMomentSpecification;
import cn.artisantc.core.util.ConverterUtil;
import cn.artisantc.core.util.LoginUserUtil;
import cn.artisantc.core.util.PageUtil;
import cn.artisantc.core.web.rest.v1_0.vo.MyFavoriteArtMomentView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.MyFavoriteArtMomentPaginationList;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * “MyFavoriteArtMomentService”接口的实现类。
 * Created by xinjie.li on 2016/9/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Service
@Transactional
public class MyFavoriteArtMomentServiceImpl implements MyFavoriteArtMomentService {

    private static final Logger LOG = LoggerFactory.getLogger(MyFavoriteArtMomentServiceImpl.class);

    private MyFavoriteArtMomentRepository favoriteArtMomentRepository;

    private ArtMomentRepository artMomentRepository;

    private ConversionService conversionService;

    private OAuth2Repository oAuth2Repository;

    private MyFavoriteArtMomentServiceImpl(MyFavoriteArtMomentRepository favoriteArtMomentRepository, ArtMomentRepository artMomentRepository,
                                           ConversionService conversionService, OAuth2Repository oAuth2Repository) {
        this.favoriteArtMomentRepository = favoriteArtMomentRepository;
        this.artMomentRepository = artMomentRepository;
        this.conversionService = conversionService;
        this.oAuth2Repository = oAuth2Repository;
    }

    @Override
    public void favorite(String momentId) {
        // 校验“艺文ID”
        if (StringUtils.isBlank(momentId)) {
            throw new MomentNotFoundException("");
        } else if (!NumberUtils.isParsable(momentId)) {
            throw new MomentNotFoundException("没有找到指定“momentId”对应的资源：" + momentId);
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，获取点赞列表操作终止！请尝试重新登录后再进行。");
        }

        // 加载艺文
        ArtMoment artMoment = artMomentRepository.findOne(NumberUtils.toLong(momentId));
        if (null == artMoment) {
            // 没有找到给定“momentId”的艺文
            throw new MomentNotFoundException("没有找到指定“momentId”对应的资源：" + momentId);
        }

        // 查找是否已经收藏
        long id = artMoment.getId();// 待收藏的艺文的ID
        if (null != artMoment.getOriginalArtMoment()) {
            id = artMoment.getOriginalArtMoment().getId();// 查找待收藏的艺文是否有原始艺文，若有原始艺文，则收藏原始艺文
        }
        // 收藏待收藏的艺文
        List<MyFavoriteArtMoment> favoriteArtMoments = favoriteArtMomentRepository.findByArtMoment_idAndUser_id(id, user.getId());
        if (null == favoriteArtMoments || favoriteArtMoments.isEmpty()) {
            // 没有收藏，执行“收藏”操作
            MyFavoriteArtMoment favorite = new MyFavoriteArtMoment();
            Date date = new Date();// 生成时间，保证该数据需要用到时间的地方的数据一致性
            favorite.setUpdateDateTime(date);
            favorite.setCreateDateTime(date);
            favorite.setUser(user);

            ArtMoment favoriteArtMoment = new ArtMoment();
            favoriteArtMoment.setId(id);
            favorite.setArtMoment(favoriteArtMoment);

            favoriteArtMomentRepository.save(favorite);
        } else {
            // 已经收藏，执行“取消收藏”操作
            favoriteArtMomentRepository.delete(favoriteArtMoments);// 取消收藏
        }
    }

    @Override
    public MyFavoriteArtMomentPaginationList findByPage(int page) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，获取屏蔽列表操作终止！请尝试重新登录后再进行。");
        }

        // 查询数据
        PageUtil pageUtil = new PageUtil(PageUtil.MY_FAVORITE_PAGE_SIZE);
        page = pageUtil.getPageForPageable(page);
        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());

        Page<MyFavoriteArtMoment> favoritePage = favoriteArtMomentRepository.findAll(MyFavoriteArtMomentSpecification.findAllByMobile(LoginUserUtil.getLoginUsername()), pageable);
        List<MyFavoriteArtMoment> favorites = favoritePage.getContent();

        // 重新组装数据
        MyFavoriteArtMomentPaginationList paginationList = new MyFavoriteArtMomentPaginationList();
        paginationList.setTotalPages(String.valueOf(favoritePage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(favoritePage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = favoritePage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (favoritePage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("favoritePage.getNumber(): {}", favoritePage.getNumber());
        LOG.debug("favoritePage.getNumberOfElements(): {}", favoritePage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));

        // 遍历查询结果列表，并进行类型转换
        if (null != favorites) {
            List<MyFavoriteArtMomentView> favoriteResponses = new ArrayList<>();
            for (MyFavoriteArtMoment favorite : favorites) {
                MyFavoriteArtMomentView favoriteResponse = conversionService.convert(favorite, MyFavoriteArtMomentView.class);// 类型转换

                // 查询艺文所含的图片，并放到返回结果中
                favoriteResponse.setArtMomentImages(ConverterUtil.getImages(favorite.getArtMoment()));
                favoriteResponses.add(favoriteResponse);
            }
            paginationList.getFavoriteArtMomentViews().addAll(favoriteResponses);
        }
        return paginationList;
    }
}
