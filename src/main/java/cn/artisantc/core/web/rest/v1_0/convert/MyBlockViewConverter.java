package cn.artisantc.core.web.rest.v1_0.convert;

import cn.artisantc.core.persistence.entity.MyBlock;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.web.rest.v1_0.vo.MyBlockView;
import org.springframework.core.convert.converter.Converter;

/**
 * “MyBlock”转“MyBlockView”的转换器。
 * Created by xinjie.li on 2016/9/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MyBlockViewConverter implements Converter<MyBlock, MyBlockView> {

    @Override
    public MyBlockView convert(MyBlock source) {
        MyBlockView vo = new MyBlockView();
        vo.setBlockUserId(String.valueOf(source.getBlock().getId()));
        vo.setBlockUserNickname(source.getBlock().getProfile().getNickname());
        vo.setBlockUserAvatarUrl(UserHelper.getAvatar3xUrl(source.getBlock()));

        return vo;
    }
}
