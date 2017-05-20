package cn.artisantc.core.service;


import cn.artisantc.core.persistence.entity.MyBlock;
import cn.artisantc.core.persistence.entity.User;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.persistence.repository.MyBlockRepository;
import cn.artisantc.core.persistence.repository.OAuth2Repository;
import cn.artisantc.core.persistence.repository.UserRepository;
import cn.artisantc.core.persistence.specification.MyBlockSpecification;
import cn.artisantc.core.util.LoginUserUtil;
import cn.artisantc.core.util.PageUtil;
import cn.artisantc.core.web.rest.v1_0.vo.APIErrorResponse;
import cn.artisantc.core.web.rest.v1_0.vo.MyBlockView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.MyBlockPaginationList;
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
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * “MyBlockService”接口的实现类。
 * Created by xinjie.li on 2016/9/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Service
@Transactional
public class MyBlockServiceImpl implements MyBlockService {

    private static final Logger LOG = LoggerFactory.getLogger(MyBlockServiceImpl.class);

    private ConversionService conversionService;

    private UserRepository userRepository;

    private MyBlockRepository blockRepository;

    private OAuth2Repository oAuth2Repository;

    @Autowired
    public MyBlockServiceImpl(ConversionService conversionService, UserRepository userRepository, MyBlockRepository blockRepository, OAuth2Repository oAuth2Repository) {
        this.conversionService = conversionService;
        this.userRepository = userRepository;
        this.blockRepository = blockRepository;
        this.oAuth2Repository = oAuth2Repository;
    }

    @Override
    public MyBlockPaginationList findByPage(int page) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，获取屏蔽列表操作终止！请尝试重新登录后再进行。");
        }

        // 查询数据
        PageUtil pageUtil = new PageUtil(PageUtil.MY_BLOCK_PAGE_SIZE);
        page = pageUtil.getPageForPageable(page);
        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());

        Page<MyBlock> blockPage = blockRepository.findAll(MyBlockSpecification.findAllByMomentId(user.getId()), pageable);
        List<MyBlock> blocks = blockPage.getContent();

        // 重新组装数据
        MyBlockPaginationList paginationList = new MyBlockPaginationList();
        paginationList.setTotalPages(String.valueOf(blockPage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(blockPage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = blockPage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (blockPage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("blockPage.getNumber(): {}", blockPage.getNumber());
        LOG.debug("blockPage.getNumberOfElements(): {}", blockPage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));

        // 遍历查询结果列表，并进行类型转换
        if (null != blocks) {
            List<MyBlockView> blockResponses = new ArrayList<>();
            for (MyBlock block : blocks) {
                blockResponses.add(conversionService.convert(block, MyBlockView.class));// 类型转换
            }
            paginationList.getBlockResponses().addAll(blockResponses);
        }
        return paginationList;
    }

    @Override
    public void block(String blockUserId) {
        // 校验“blockUserId”，并查找要屏蔽的用户是否存在
        if (StringUtils.isEmpty(blockUserId) || !NumberUtils.isParsable(blockUserId)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010031.getErrorCode());
        }
        User blockUser = userRepository.findOne(NumberUtils.toLong(blockUserId));
        if (null == blockUser) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010031.getErrorCode());
        }

        // 校验当前登录用户信息
        User i = userRepository.findByMobile(LoginUserUtil.getLoginUsername());
        if (null == i) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，屏蔽/取消屏蔽操作终止！请尝试重新登录后再进行。");
        }

        // 校验当前用户和要“屏蔽/取消屏蔽”的用户是否是同一人
        if (i.getId() == blockUser.getId()) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010033.getErrorCode());
        }

        Date date = new Date();// 生成时间，保证该数据需要用到时间的地方的数据一致性
        // 查找是否已经屏蔽
        List<MyBlock> blocks = blockRepository.findByI_mobileAndBlock_id(LoginUserUtil.getLoginUsername(), NumberUtils.toLong(blockUserId));
        if (null == blocks || blocks.isEmpty()) {
            // 没有屏蔽，执行“屏蔽”操作
            MyBlock block = new MyBlock();
            block.setCreateDateTime(date);
            block.setUpdateDateTime(date);
            block.setI(i);
            block.setBlock(blockUser);
            blockRepository.save(block);// 屏蔽
        } else {
            // 已经屏蔽，则执行“取消屏蔽”操作
            blockRepository.delete(blocks);// 取消屏蔽
        }
    }
}
