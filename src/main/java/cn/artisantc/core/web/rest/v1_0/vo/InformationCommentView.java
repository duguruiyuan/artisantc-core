package cn.artisantc.core.web.rest.v1_0.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * “资讯的评论”的详细内容的视图对象。
 * Created by xinjie.li on 2017/1/6.
 *
 * @author xinjie.li
 * @since 2.2
 */
public class InformationCommentView extends BaseCommentView {

    private List<InformationCommentView> childrenComments = new ArrayList<>();// 该评论的子评论

    public List<InformationCommentView> getChildrenComments() {
        return childrenComments;
    }

    public void setChildrenComments(List<InformationCommentView> childrenComments) {
        this.childrenComments = childrenComments;
    }
}
