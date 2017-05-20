package cn.artisantc.core.exception;

/**
 * “找不到指定的拍品分类”的异常。
 * Created by xinjie.li on 2016/9/22.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ItemCategoryNotFoundException extends BaseNotFoundException {
    public ItemCategoryNotFoundException(String message) {
        super(message);
    }
}
