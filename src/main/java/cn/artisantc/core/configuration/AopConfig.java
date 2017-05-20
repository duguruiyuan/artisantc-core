package cn.artisantc.core.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 开启“基于Spring AOP注解”的基配置。
 * Created by xinjie.li on 2016/9/18.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Configuration(value = "AopConfig")
@EnableAspectJAutoProxy
public class AopConfig {
}
