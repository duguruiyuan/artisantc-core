package cn.artisantc.core.web.rest.v1_0.vo.request;

/**
 * “屏蔽/取消屏蔽”的请求对象。
 * Created by xinjie.li on 2016/9/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class BlockRequest {

    private String blockUserId;

    public String getBlockUserId() {
        return blockUserId;
    }

    public void setBlockUserId(String blockUserId) {
        this.blockUserId = blockUserId;
    }
}
