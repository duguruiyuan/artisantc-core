package cn.artisantc.core.service;

import cn.artisantc.core.exception.ItemNotFoundException;
import cn.artisantc.core.persistence.entity.Item;
import cn.artisantc.core.persistence.entity.MyFavoriteItem;
import cn.artisantc.core.persistence.entity.User;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.persistence.repository.ItemRepository;
import cn.artisantc.core.persistence.repository.MyFavoriteItemRepository;
import cn.artisantc.core.persistence.repository.OAuth2Repository;
import cn.artisantc.core.util.DateTimeUtil;
import cn.artisantc.core.util.PageUtil;
import cn.artisantc.core.web.rest.v1_0.vo.ItemView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.MyFavoriteItemPaginationList;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * “MyFavoriteItemService”接口的实现类。
 * Created by xinjie.li on 2016/10/14.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Service
@Transactional
public class MyFavoriteItemServiceImpl implements MyFavoriteItemService {

    private static final Logger LOG = LoggerFactory.getLogger(MyFavoriteItemServiceImpl.class);

    private OAuth2Repository oAuth2Repository;

    private MyFavoriteItemRepository favoriteItemRepository;

    private ItemRepository itemRepository;

    private ItemService itemService;

    @Autowired
    public MyFavoriteItemServiceImpl(OAuth2Repository oAuth2Repository, MyFavoriteItemRepository favoriteItemRepository,
                                     ItemRepository itemRepository, ItemService itemService) {
        this.oAuth2Repository = oAuth2Repository;
        this.favoriteItemRepository = favoriteItemRepository;
        this.itemRepository = itemRepository;
        this.itemService = itemService;
    }

    @Override
    public MyFavoriteItemPaginationList findByPage(int page) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，获取屏蔽列表操作终止！请尝试重新登录后再进行。");
        }

        // 查询数据
        PageUtil pageUtil = new PageUtil(PageUtil.MY_FAVORITE_PAGE_SIZE);
        page = pageUtil.getPageForPageable(page);
        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());

        Page<MyFavoriteItem> favoritePage = favoriteItemRepository.findByUser_idOrderByIdDesc(user.getId(), pageable);
        List<MyFavoriteItem> favorites = favoritePage.getContent();

        // 重新组装数据
        MyFavoriteItemPaginationList paginationList = new MyFavoriteItemPaginationList();
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
            List<ItemView> views = new ArrayList<>();
            for (MyFavoriteItem favorite : favorites) {
                views.add(itemService.getItemViewFromItem(favorite.getItem(), Boolean.TRUE));
            }
            paginationList.getFavoriteItemViews().addAll(views);
        }
        return paginationList;
    }

    @Override
    public void favorite(String itemId) {
        // 校验“拍品ID”
        if (StringUtils.isBlank(itemId)) {
            throw new ItemNotFoundException("");
        } else if (!NumberUtils.isParsable(itemId)) {
            throw new ItemNotFoundException("没有找到指定“itemId”对应的资源：" + itemId);
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，获取点赞列表操作终止！请尝试重新登录后再进行。");
        }

        // 加载店铺
        Item item = itemRepository.findOne(NumberUtils.toLong(itemId));
        if (null == item) {
            // 没有找到给定“itemId”的拍品
            throw new ItemNotFoundException("没有找到指定“itemId”对应的资源：" + itemId);
        } else {
            if (Item.Status.PENDING_REVIEW == item.getStatus() || Item.Status.REJECTED == item.getStatus()) {
                // 不允许收藏“待审核”和“审核不通过”的拍品
                throw new ItemNotFoundException("没有找到指定“itemId”对应的资源：" + itemId);
            }
        }

        // 查找是否已经收藏
        List<MyFavoriteItem> favoriteItems = favoriteItemRepository.findByUser_idAndItem_id(user.getId(), item.getId());
        if (null == favoriteItems || favoriteItems.isEmpty()) {
            // 没有收藏，执行“收藏”操作
            MyFavoriteItem favorite = new MyFavoriteItem();
            Date date = new Date();// 生成时间，保证该数据需要用到时间的地方的数据一致性
            favorite.setUpdateDateTime(date);
            favorite.setCreateDateTime(date);
            favorite.setItem(item);
            favorite.setUser(user);

            favoriteItemRepository.save(favorite);
        } else {
            // 已经收藏，执行“取消收藏”操作
            favoriteItemRepository.delete(favoriteItems);// 取消收藏
        }
    }

    @Override
    public MyFavoriteItemPaginationList findMyUpcomingBiddingItems(int page) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，获取屏蔽列表操作终止！请尝试重新登录后再进行。");
        }

        // 查询数据
        PageUtil pageUtil = new PageUtil(PageUtil.MY_FAVORITE_PAGE_SIZE);
        page = pageUtil.getPageForPageable(page);
        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());

        // 获取时间范围
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startDateTime = calendar.getTime();// 今天的开始时间
        LOG.debug("startDateTime: {}", DateFormatUtils.format(startDateTime, DateTimeUtil.DATE_FORMAT_ALL));

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endDateTime = calendar.getTime();// 今天的结束时间
        LOG.debug("endDateTime: {}", DateFormatUtils.format(endDateTime, DateTimeUtil.DATE_FORMAT_ALL));

        // 查询数据：找到“我收藏的拍品”中，处于“Item.Status.PREVIEW”的拍品
        Page<MyFavoriteItem> favoritePage = favoriteItemRepository.findByUser_idAndItem_statusAndItem_StartDateTimeBetween(user.getId(), Item.Status.PREVIEW, startDateTime, endDateTime, pageable);
        List<MyFavoriteItem> favorites = favoritePage.getContent();

        // 重新组装数据
        MyFavoriteItemPaginationList paginationList = new MyFavoriteItemPaginationList();
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
            List<ItemView> views = new ArrayList<>();
            for (MyFavoriteItem favorite : favorites) {
                views.add(itemService.getItemViewFromItem(favorite.getItem(), Boolean.TRUE));
            }
            paginationList.getFavoriteItemViews().addAll(views);
        }
        return paginationList;
    }
}
