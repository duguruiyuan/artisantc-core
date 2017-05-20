package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.UserShowImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * “用户的个人展示”的数据持久化操作。
 * Created by xinjie.li on 2016/11/18.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface UserShowImageRepository extends JpaRepository<UserShowImage, Long>, JpaSpecificationExecutor<UserShowImage> {

    /**
     * 查找指定“用户匠号”的个人展示图片，按照“创建时间”倒序、“ID”倒序排列。
     *
     * @param serialNumber 用户匠号
     * @return 指定“用户匠号”的个人展示图片
     */
    List<UserShowImage> findByUser_serialNumberOrderByCreateDateTimeDescIdDesc(String serialNumber);

    /**
     * 查找指定“用户匠号”的个人展示图片，按照“创建时间”升序、“ID”倒序升列。
     *
     * @param serialNumber 用户匠号
     * @return 指定“用户匠号”的个人展示图片
     */
    List<UserShowImage> findByUser_serialNumberOrderByCreateDateTimeAscIdAsc(String serialNumber);
}
