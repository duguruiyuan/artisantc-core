package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “意见反馈”的数据持久化操作。
 * Created by xinjie.li on 2016/11/3.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Long>, JpaSpecificationExecutor<Suggestion> {
}
