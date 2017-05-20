package cn.artisantc.core.configuration;

import cn.artisantc.core.web.rest.v1_0.convert.ArtMomentCommentViewConverter;
import cn.artisantc.core.web.rest.v1_0.convert.ArtMomentImageViewConverter;
import cn.artisantc.core.web.rest.v1_0.convert.ArtMomentLikeViewConverter;
import cn.artisantc.core.web.rest.v1_0.convert.ArtMomentViewConverter;
import cn.artisantc.core.web.rest.v1_0.convert.ItemDetailViewConverter;
import cn.artisantc.core.web.rest.v1_0.convert.ItemOrderViewConverter;
import cn.artisantc.core.web.rest.v1_0.convert.ItemViewConverter;
import cn.artisantc.core.web.rest.v1_0.convert.MerchantMarginOrderViewConverter;
import cn.artisantc.core.web.rest.v1_0.convert.MyBlockViewConverter;
import cn.artisantc.core.web.rest.v1_0.convert.MyFansViewConverter;
import cn.artisantc.core.web.rest.v1_0.convert.MyFavoriteArtMomentViewConverter;
import cn.artisantc.core.web.rest.v1_0.convert.MyFollowViewConverter;
import cn.artisantc.core.web.rest.v1_0.convert.UserRewardOrderViewConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.GenericConversionService;

/**
 * Spring Framework 的基于注解方式的配置。
 * Created by xinjie.li on 2016/8/30.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Configuration(value = "AppConfig")
//@ComponentScan(basePackages = {"cn.artisantc"})
@Import(value = {
        DataSourceConfig.class,
        InfrastructureConfig.class,
        MvcConfig.class,
        RepositoryConfig.class,
        ServiceConfig.class,
        SecurityConfig.class,
        AopConfig.class
//        QuartzConfig.class // 在2.4版本中关闭“任务Quartz”的运行，因为目前的逻辑是不需要该任务的。
})
public class AppConfig {

//    public static final String PRODUCTION_PROFILE_VALUE = "production";

    //    private static final Logger LOG = LoggerFactory.getLogger(AppConfig.class);
//
//    @Autowired
//    private Environment env;
//
//    @PostConstruct
//    public void initApp() {
//        LOG.debug("Looking for Spring profiles...");
//        if (env.getActiveProfiles().length == 0) {
//            LOG.info("No Spring profile configured, running with default configuration.");
//        } else {
//            for (String profile : env.getActiveProfiles()) {
//                LOG.info("Detected Spring profile: {}", profile);
//            }
//        }
//    }

    private MessageSource messageSource;

    @Autowired
    public AppConfig(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Bean
    public ConversionService conversionService() {
        GenericConversionService genericConversionService = new GenericConversionService();
        genericConversionService.addConverter(new ArtMomentViewConverter());
        genericConversionService.addConverter(new ArtMomentImageViewConverter());
        genericConversionService.addConverter(new ArtMomentCommentViewConverter());
        genericConversionService.addConverter(new ArtMomentLikeViewConverter());
        genericConversionService.addConverter(new MyFollowViewConverter());
        genericConversionService.addConverter(new MyFansViewConverter());
        genericConversionService.addConverter(new MyBlockViewConverter());
        genericConversionService.addConverter(new MyFavoriteArtMomentViewConverter());
        genericConversionService.addConverter(new ItemViewConverter(messageSource));
        genericConversionService.addConverter(new ItemDetailViewConverter(messageSource));
        genericConversionService.addConverter(new ItemOrderViewConverter(messageSource));
        genericConversionService.addConverter(new MerchantMarginOrderViewConverter(messageSource));
        genericConversionService.addConverter(new UserRewardOrderViewConverter(messageSource));

        return genericConversionService;
    }
}
