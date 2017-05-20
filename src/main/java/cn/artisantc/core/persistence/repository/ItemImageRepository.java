package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “拍品图片”的数据持久化操作。
 * Created by xinjie.li on 2016/9/29.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface ItemImageRepository extends JpaRepository<ItemImage, Long>, JpaSpecificationExecutor<ItemImage> {
}
