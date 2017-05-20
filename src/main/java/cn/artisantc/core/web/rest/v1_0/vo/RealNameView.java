package cn.artisantc.core.web.rest.v1_0.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “实名认证”的视图对象；
 * Created by xinjie.li on 2016/10/7.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class RealNameView {

    @JsonProperty(value = "realNameId")
    private String id;// 实名认证的ID

    private String realName;// 真实姓名

    private String identityNumber;// 身份证号

    private String status;// 认证状态

    private String statusCode;// 认证状态代码

    private String userId;// 实名认证的用户ID

    @JsonProperty(value = "images")
    private List<RealNameImageView> imageViews = new ArrayList<>();// 实名认证时提交的三张图片

    private String createDateTime;// 申请时间

    private boolean showAuditButtons = false;// 是否显示审核操作的按钮

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<RealNameImageView> getImageViews() {
        return imageViews;
    }

    public void setImageViews(List<RealNameImageView> imageViews) {
        this.imageViews = imageViews;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isShowAuditButtons() {
        return showAuditButtons;
    }

    public void setShowAuditButtons(boolean showAuditButtons) {
        this.showAuditButtons = showAuditButtons;
    }
}
