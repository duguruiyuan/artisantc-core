package cn.artisantc.core.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.annotation.PostConstruct;

/**
 * Spring MVC 的基于注解方式的配置。
 * Created by xinjie.li on 2016/8/30.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Configuration(value = "MvcConfig")
@EnableWebMvc
@ComponentScan(basePackages = {
        "cn.artisantc.core.web"
})
public class MvcConfig extends WebMvcConfigurerAdapter {

//    private static final int CACHE_PERIOD = 31556926; // one year

    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @PostConstruct
    public void init() {
        requestMappingHandlerAdapter.setIgnoreDefaultModelOnRedirect(true);
    }

    /**
     * 静态资源的映射。
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // “网站图标”资源
        registry.addResourceHandler("/favicon.ico")
                .addResourceLocations("/img/favicon.ico");

        // “CSS”资源
        registry
                .addResourceHandler("/css/**")
                .addResourceLocations("/css/")
//                .setCachePeriod(CACHE_PERIOD)
        ;

        // “JS”资源
        registry
                .addResourceHandler("/js/**")
                .addResourceLocations("/js/")
//                .setCachePeriod(CACHE_PERIOD)
        ;

        // “图片”资源
        registry.addResourceHandler("/img/**")
                .addResourceLocations("/img/");

        // “字体”资源
        registry.addResourceHandler("/fonts/**")
                .addResourceLocations("/fonts/");
    }

    /**
     * Resolves view names to protected .jsp resources within the /WEB-INF/views directory.
     */
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }

    /**
     * 国际化资源配置。
     */
    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:i18n/message");
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }

    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(5242880);// 5M: 5 * 1024 *1024,，允许一次性上传的文件的总大小不超过5M，无论有多少个文件
        resolver.setDefaultEncoding("UTF-8");

        return resolver;
    }

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        // 这里放进去的URI通常是不需要有任何逻辑处理的情况，直接指向到具体的JSP页面，比如404错误的提示页面
//        registry.addViewController("/404"); // the page did not required any controller TODO：没起作用，配合web.xml里的配置
//        registry.addViewController("/500"); // the page did not required any controller TODO：没起作用，配合web.xml里的配置
//    }

//    @Override
//    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//        // Serving static files using the Servlet container's default Servlet.
//        configurer.enable();
//    }

//    @Override
//    public void addFormatters(FormatterRegistry formatterRegistry) {
//        // add your custom formatter
//    }
}
