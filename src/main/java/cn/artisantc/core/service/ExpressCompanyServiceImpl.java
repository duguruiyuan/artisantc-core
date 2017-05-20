package cn.artisantc.core.service;

import cn.artisantc.core.persistence.entity.ExpressCompany;
import cn.artisantc.core.persistence.repository.ExpressCompanyRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * “ExpressCompanyService”接口的实现类。
 * Created by xinjie.li on 2016/10/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Service
@Transactional
public class ExpressCompanyServiceImpl implements ExpressCompanyService {

    private static final Logger LOG = LoggerFactory.getLogger(ExpressCompanyServiceImpl.class);

    private ExpressCompanyRepository expressCompanyRepository;

    @Autowired
    public ExpressCompanyServiceImpl(ExpressCompanyRepository expressCompanyRepository) {
        this.expressCompanyRepository = expressCompanyRepository;
    }

    @Override
    public void create() {
        // 重新抓取
        String url = "http://www.kuaidi100.com/js/share/company.js?version=201603311022";

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));// 设置编码，Spring默认使用的是“ISO-8859-1”，返回结果中的中文会是乱码
        RestTemplate template = new RestTemplate(messageConverters);
        ResponseEntity<String> responseEntity = template.getForEntity(url, String.class);
        String response = responseEntity.getBody();
        String companyJSON = response.substring(response.indexOf("var jsoncom=") + 23, response.length() - 18);// 去掉最开始的“var jsoncom=”，去掉末尾的“,'error_size':-1};”
        companyJSON = companyJSON.replaceAll("'", "\"");// 将单引号全部替换成双引号

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);// 反序列化时，忽略对象中没有与JSON对应的属性
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, ExpressCompanyResponse.class);// 设置反序列化时的对象为复杂对象

        List<ExpressCompanyResponse> expressCompanyResponses = null;
        try {
            expressCompanyResponses = objectMapper.readValue(companyJSON, javaType);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        if (null != expressCompanyResponses && !expressCompanyResponses.isEmpty()) {
            List<ExpressCompany> expressCompanies = new ArrayList<>();
            for (ExpressCompanyResponse expressCompanyResponse : expressCompanyResponses) {
                ExpressCompany expressCompany = new ExpressCompany();
                expressCompany.setCode(expressCompanyResponse.getCode());
                expressCompany.setName(expressCompanyResponse.getCompanyname());
                expressCompany.setShortName(expressCompanyResponse.getShortname());

                expressCompanies.add(expressCompany);
            }
            expressCompanyRepository.save(expressCompanies);
        }
    }

    @Override
    public List<ExpressCompany> findAll() {
        return expressCompanyRepository.findAll();
    }

    /**
     * “银行卡验证结果”，该结果是调用第三方接口获得的。
     */
    public static class ExpressCompanyResponse {

        private String companyname;// 快递公司名称，例如：申通快递

        private String shortname;// 简称，例如：申通

        private String code;// 公司代码，例如：shentong

        public String getCompanyname() {
            return companyname;
        }

        public void setCompanyname(String companyname) {
            this.companyname = companyname;
        }

        public String getShortname() {
            return shortname;
        }

        public void setShortname(String shortname) {
            this.shortname = shortname;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
