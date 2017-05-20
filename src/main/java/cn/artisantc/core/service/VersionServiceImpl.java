package cn.artisantc.core.service;

import cn.artisantc.core.persistence.entity.Version;
import cn.artisantc.core.persistence.repository.VersionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * “VersionService”接口的实现类。
 * Created by xinjie.li on 2016/12/26.
 *
 * @author xinjie.li
 * @since 2.0
 */
@Service
@Transactional
public class VersionServiceImpl implements VersionService {

    private static final Logger LOG = LoggerFactory.getLogger(VersionServiceImpl.class);

    private VersionRepository versionRepository;

    public VersionServiceImpl(VersionRepository versionRepository) {
        this.versionRepository = versionRepository;
    }

    @Override
    public List<Version> findAll() {
        return versionRepository.findAll();
    }
}
