package cn.artisantc.core.service.security;

import cn.artisantc.core.persistence.entity.AdministratorPermission;
import cn.artisantc.core.persistence.entity.Permission;
import cn.artisantc.core.persistence.repository.AdministratorPermissionRepository;
import cn.artisantc.core.persistence.repository.PermissionRepository;
import cn.artisantc.core.util.WordEncoderUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * “FilterInvocationSecurityMetadataSource”接口的实现类。
 * Created by xinjie.li on 2016/9/1.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Service(value = "filterInvocationSecurityMetadataSourceImpl")
@Transactional
public final class FilterInvocationSecurityMetadataSourceImpl implements FilterInvocationSecurityMetadataSource {

    private static final Logger LOG = LoggerFactory.getLogger(FilterInvocationSecurityMetadataSourceImpl.class);

    private static Map<RequestMatcher, Collection<ConfigAttribute>> requestMap = new HashMap<>();

    private PermissionRepository permissionRepository;

    private AdministratorPermissionRepository administratorPermissionRepository;

    @Autowired
    public FilterInvocationSecurityMetadataSourceImpl(PermissionRepository permissionRepository, AdministratorPermissionRepository administratorPermissionRepository) {
        this.permissionRepository = permissionRepository;
        this.administratorPermissionRepository = administratorPermissionRepository;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if ((null == object) || !this.supports(object.getClass())) {
            throw new IllegalArgumentException("object.must.be.FilterInvocation");
        }

        final HttpServletRequest request = ((FilterInvocation) object).getRequest();
        if (null == requestMap || requestMap.isEmpty()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Not found Request Map, load the Request Map by all Permission Definition!");
            }
            this.loadPermissionDefinition();
        }

        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {
            if (entry.getKey().matches(request)) {
                return entry.getValue();
            }
        }
        return SecurityConfig.createList("拒绝访问(" + WordEncoderUtil.encodeWord(RandomStringUtils.random(20, true, true), RandomStringUtils.random(6)) + ")");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return new ArrayList<>();
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    private void loadPermissionDefinition() {
        // get all Resources
        List<Permission> permissions = permissionRepository.findAll();
        if (null == permissions) {
            if (LOG.isWarnEnabled()) {
                LOG.warn("Not define Permission!");
            }
            permissions = new ArrayList<>();
        }

        for (Permission permission : permissions) {
            Collection<ConfigAttribute> configAttributes = new ArrayList<>();
            ConfigAttribute configAttribute = new SecurityConfig(permission.getName());
            configAttributes.add(configAttribute);

            RequestMatcher requestMatcher = new AntPathRequestMatcher(permission.getUri(), permission.getHttpMethod());

            requestMap.put(requestMatcher, configAttributes);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Permission Definition: {}, {}, {}", permission.getUri(), permission.getName(), permission.getHttpMethod());
            }
        }

        // get all Administrator's Resources
        List<AdministratorPermission> administratorPermissions = administratorPermissionRepository.findAll();
        if (null == administratorPermissions) {
            if (LOG.isWarnEnabled()) {
                LOG.warn("Not define Administrator Permission!");
            }
            administratorPermissions = new ArrayList<>();
        }

        for (AdministratorPermission administratorPermission : administratorPermissions) {
            Collection<ConfigAttribute> configAttributes = new ArrayList<>();
            ConfigAttribute configAttribute = new SecurityConfig(administratorPermission.getName());
            configAttributes.add(configAttribute);

            RequestMatcher requestMatcher = new AntPathRequestMatcher(administratorPermission.getUri(), administratorPermission.getHttpMethod());

            requestMap.put(requestMatcher, configAttributes);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Administrator Permission Definition: {}, {}, {}", administratorPermission.getUri(), administratorPermission.getName(), administratorPermission.getHttpMethod());
            }
        }
    }
}
