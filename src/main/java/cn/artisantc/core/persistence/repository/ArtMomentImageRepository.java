package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.ArtMomentImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “艺术圈的艺文”的图片的数据持久化操作。
 * Created by xinjie.li on 2016/9/6.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface ArtMomentImageRepository extends JpaRepository<ArtMomentImage, Long>, JpaSpecificationExecutor<ArtMomentImage> {
}
