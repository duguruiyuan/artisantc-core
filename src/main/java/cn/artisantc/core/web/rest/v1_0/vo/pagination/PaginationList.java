package cn.artisantc.core.web.rest.v1_0.vo.pagination;

/**
 * 提供了分页列表的公共属性。
 * Created by xinjie.li on 2016/9/8.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class PaginationList {

    private String totalPages = "0";// 总页数

    private String totalRecords = "0";// 总记录数

    private String nextPage = "1";// 下一页的页数

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public String getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(String totalRecords) {
        this.totalRecords = totalRecords;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }
}
