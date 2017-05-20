package cn.artisantc.core.service;

import cn.artisantc.core.exception.BankException;
import cn.artisantc.core.persistence.entity.Bank;
import cn.artisantc.core.persistence.repository.BankRepository;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * “BankService”接口的实现类。
 * Created by xinjie.li on 2016/10/9.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Service
@Transactional
public class BankServiceImpl implements BankService {

    private BankRepository bankRepository;

    @Autowired
    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public void create() {
        // 重新抓取
        String url = "https://ab.alipay.com/i/yinhang.htm";

        Connection connection = Jsoup.connect(url);
        Document document;
        try {
            document = connection.get();
        } catch (IOException e) {
            throw new BankException();
        }
        Elements elements = document.select("span[class~=icon *]");// 使用正则表达式
        if (null != elements && !elements.isEmpty()) {
            // 删除原有的“银行”信息
            bankRepository.deleteAll();

            // 存储新抓取的数据
            List<Bank> banks = new ArrayList<>();
            for (Element element : elements) {
                // 排除以下格式，即class为icon-box的元素是不需要的，目前是2个：
                /*
                <span class="icon-box">
                    <span class="icon HKBEA">东亚银行</span>
                </span>
                 */
                if (element.children().size() == 0) {
                    Bank bank = new Bank();
                    bank.setCode(element.attr("class").substring(5));
                    bank.setName(element.text());

                    banks.add(bank);
                }
            }
            bankRepository.save(banks);
        }
    }

    @Override
    public List<Bank> findAll() {
        return bankRepository.findAll();
    }
}
