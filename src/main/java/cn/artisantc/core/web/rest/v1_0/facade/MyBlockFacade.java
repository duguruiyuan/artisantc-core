package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.service.MyBlockService;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.MyBlockPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.request.BlockRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * “我屏蔽的用户”操作的API。
 * Created by xinjie.li on 2016/9/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api")
public class MyBlockFacade {

    private MyBlockService blockService;

    @Autowired
    public MyBlockFacade(MyBlockService blockService) {
        this.blockService = blockService;
    }

    /**
     * 根据给定的页数获取“屏蔽用户”的分页结果，默认返回第1页的结果。
     *
     * @param page 分页
     * @return 指定页数的结果列表，当指定页数<=0时返回第1页的结果；当指定页数大于实际结果的最大页数时返回最后一页的结果
     */
    @RequestMapping(value = "/i/blocks", method = RequestMethod.GET)
    public MyBlockPaginationList getMyBlocks(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return blockService.findByPage(page);
    }

    /**
     * “屏蔽/取消屏蔽”。
     *
     * @param blockRequest “屏蔽/取消屏蔽”的请求对象
     */
    @RequestMapping(value = "/i/blocks", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void block(@RequestBody BlockRequest blockRequest) {
        blockService.block(blockRequest.getBlockUserId());
    }
}
