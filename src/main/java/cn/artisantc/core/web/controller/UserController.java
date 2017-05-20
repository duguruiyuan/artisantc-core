package cn.artisantc.core.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * “管理端”的“用户管理”的界面。
 * Created by xinjie.li on 2017/1/3.
 *
 * @author xinjie.li
 * @since 2.1
 */
@Controller
@RequestMapping(value = "/console")
public class UserController {

    /**
     * “用户列表”页面。
     *
     * @return “用户列表”页面
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getUsers() {
        return "/users";
    }

    /**
     * “用户详情”页面。
     *
     * @param id 用户ID
     * @return “用户详情”页面
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public String getUserById(@PathVariable(value = "id") String id) {
        return "/user_detail";
    }

    /**
     * “大咖列表”页面。
     *
     * @return “大咖列表”页面
     */
    @RequestMapping(value = "/art-big-shots", method = RequestMethod.GET)
    public String getArtBigShots() {
        return "/art_big_shots";
    }

    /**
     * “大咖详情”页面。
     *
     * @return “大咖详情”页面
     */
    @RequestMapping(value = "/art-big-shots/{id}", method = RequestMethod.GET)
    public String getArtBigShotById(@PathVariable(value = "id") String id) {
        return "/art_big_shot_detail";
    }

    /**
     * 获得“成为大咖”页面的内容。
     *
     * @return “成为大咖”页面的内容
     */
    @RequestMapping(value = "/art-big-shot-modal", method = RequestMethod.GET)
    public String toUpgradeToArtBigShot() {
        return "modal/art_big_shot_create_modal_content";
    }

    /**
     * 获得“评论”页面的内容。
     *
     * @return “评论”页面的内容
     */
    @RequestMapping(value = "/comment-on-modal", method = RequestMethod.GET)
    public String toCommentOn() {
        return "modal/comment_on_modal_content";
    }
}
