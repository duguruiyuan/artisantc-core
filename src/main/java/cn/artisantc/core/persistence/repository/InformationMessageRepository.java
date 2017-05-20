package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.InformationMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “资讯相关的消息”的数据持久化操作。
 * Created by xinjie.li on 2017/1/18.
 *
 * @author xinjie.li
 * @since 2.3
 */
@Repository
public interface InformationMessageRepository extends JpaRepository<InformationMessage, Long>, JpaSpecificationExecutor<InformationMessage> {
}
