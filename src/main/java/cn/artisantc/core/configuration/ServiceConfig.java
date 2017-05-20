/**
 * Copyright 2014 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package cn.artisantc.core.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xinjie.li on 2016/8/30.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Configuration(value = "ServiceConfig")
//@EnableAsync
//@EnableScheduling
//@EnableAspectJAutoProxy
//@EnableCaching
@ComponentScan(basePackages = {"cn.artisantc.core.service"})
public class ServiceConfig/* implements AsyncConfigurer*/ {
    
    private final Logger log = LoggerFactory.getLogger(ServiceConfig.class);

//    @Bean
//    public CacheManager cacheManager() {
//        SimpleCacheManager cacheManager = new SimpleCacheManager();
//        List<Cache> caches = new ArrayList<Cache>();
//        // to customize
//        caches.add(defaultCache());
//        cacheManager.setCaches(caches);
//        return cacheManager;
//    }

//    @Bean
//    public Cache defaultCache() {
//        ConcurrentMapCacheFactoryBean cacheFactoryBean = new ConcurrentMapCacheFactoryBean();
//        cacheFactoryBean.setName("default");
//        cacheFactoryBean.afterPropertiesSet();
//        return cacheFactoryBean.getObject();
//
//    }

//    @Override
//    public Executor getAsyncExecutor() {
//        log.debug("Creating Async Task Executor");
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        // to customize with your requirements
//        executor.setCorePoolSize(5);
//        executor.setMaxPoolSize(40);
//        executor.setQueueCapacity(100);
//        executor.setThreadNamePrefix("MyExecutor-");
//        executor.initialize();
//        return executor;
//    }

}
