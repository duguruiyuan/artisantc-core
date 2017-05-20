package cn.artisantc.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * “艺术大咖”。
 * Created by xinjie.li on 2016/12/30.
 *
 * @author xinjie.li
 * @since 2.1
 */
@Entity
@Table(name = "t_art_big_shot")
public class ArtBigShot extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ART_BIG_SHOT_USER_ID"))
    private User user;// 对应的用户

    @Column(length = 400)
    private String introduce;// 大咖的详细介绍

    @Column(length = 60)
    private String overview;// 大咖的一句话简介

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
