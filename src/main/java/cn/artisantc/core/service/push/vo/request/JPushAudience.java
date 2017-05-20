package cn.artisantc.core.service.push.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * “推送目标”的封装类，根据JPush的接口文档实现。
 * 详情参阅：https://docs.jiguang.cn/jpush/server/push/rest_api_v3_push/#audience
 * Created by xinjie.li on 2017/2/17.
 *
 * @author xinjie.li
 * @since 2.4
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JPushAudience {

    /*
    数组。多个标签之间是 OR 的关系，即取并集。用标签来进行大规模的设备属性、用户属性分群。 一次推送最多 20 个。
    有效的 tag 组成：字母（区分大小写）、数字、下划线、汉字、特殊字符@!#$&*+=.|￥。
    限制：每一个 tag 的长度限制为 40 字节。（判断长度需采用UTF-8编码）。
    例如："tag" : [ "深圳", "广州", "北京" ]
     */
    private String[] tag;// 标签

    /*
    数组。多个标签之间是 AND 关系，即取交集。
    注册与 tag 区分。一次推送最多 20 个。
    例如："tag_and" : [ "深圳", "女" ]
     */
    @JsonProperty(value = "tag_and")
    private String[] tagAnd;// 标签AND

    /*
    数组。多个别名之间是 OR 关系，即取并集。
    用别名来标识一个用户。一个设备只能绑定一个别名，但多个设备可以绑定同一个别名。一次推送最多 1000 个。
    有效的 alias 组成：字母（区分大小写）、数字、下划线、汉字、特殊字符@!#$&*+=.|￥。
    限制：每一个 alias 的长度限制为 40 字节。（判断长度需采用UTF-8编码）。
    例如："alias" : [ "4314", "892", "4531" ]
     */
    private String[] alias;// 别名

    /*
    数组。多个注册ID之间是 OR 关系，即取并集。
    设备标识。一次推送最多 1000 个。
     */
    @JsonProperty(value = "registration_id")
    private String[] registrationId;// 注册ID

    public String[] getTag() {
        return tag;
    }

    public void setTag(String[] tag) {
        this.tag = tag;
    }

    public String[] getTagAnd() {
        return tagAnd;
    }

    public void setTagAnd(String[] tagAnd) {
        this.tagAnd = tagAnd;
    }

    public String[] getAlias() {
        return alias;
    }

    public void setAlias(String[] alias) {
        this.alias = alias;
    }

    public String[] getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String[] registrationId) {
        this.registrationId = registrationId;
    }
}
