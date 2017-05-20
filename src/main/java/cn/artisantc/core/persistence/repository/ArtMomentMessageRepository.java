package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.ArtMomentMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “艺文相关的消息”的数据持久化操作。
 * Created by xinjie.li on 2017/1/17.
 *
 * @author xinjie.li
 * @since 2.3
 */
@Repository
public interface ArtMomentMessageRepository extends JpaRepository<ArtMomentMessage, Long>, JpaSpecificationExecutor<ArtMomentMessage> {
}
