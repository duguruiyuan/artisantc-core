package cn.artisantc.core.service;

import cn.artisantc.core.persistence.entity.Bank;
import cn.artisantc.core.persistence.entity.MerchantBankCard;
import cn.artisantc.core.persistence.entity.RealName;
import cn.artisantc.core.persistence.entity.User;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.persistence.repository.BankRepository;
import cn.artisantc.core.persistence.repository.MerchantBankCardRepository;
import cn.artisantc.core.persistence.repository.OAuth2Repository;
import cn.artisantc.core.persistence.repository.RealNameRepository;
import cn.artisantc.core.util.ConverterUtil;
import cn.artisantc.core.util.SMSUtil;
import cn.artisantc.core.web.rest.v1_0.vo.APIErrorResponse;
import cn.artisantc.core.web.rest.v1_0.vo.MerchantBankCardView;
import cn.artisantc.core.web.rest.v1_0.vo.ValidateBankCardView;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * “MerchantBankCardService”接口的实现类。
 * Created by xinjie.li on 2016/10/9.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Service
@Transactional
public class MerchantBankCardServiceImpl implements MerchantBankCardService {

    private static final Logger LOG = LoggerFactory.getLogger(MerchantBankCardServiceImpl.class);

    private OAuth2Repository oAuth2Repository;

    private MessageSource messageSource;

    private MerchantBankCardRepository bankCardRepository;

    private BankRepository bankRepository;

    private RealNameRepository realNameRepository;

    @Autowired
    public MerchantBankCardServiceImpl(OAuth2Repository oAuth2Repository, MessageSource messageSource, MerchantBankCardRepository bankCardRepository,
                                       BankRepository bankRepository, RealNameRepository realNameRepository) {
        this.oAuth2Repository = oAuth2Repository;
        this.messageSource = messageSource;
        this.bankCardRepository = bankCardRepository;
        this.bankRepository = bankRepository;
        this.realNameRepository = realNameRepository;
    }

    @Override
    public List<MerchantBankCardView> findMyBankCards() {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 构建返回数据
        List<MerchantBankCardView> bankCardViews = new ArrayList<>();
        List<MerchantBankCard> bankCards = bankCardRepository.findAllByUser_id(user.getId());
        if (null != bankCards && !bankCards.isEmpty()) {
            for (MerchantBankCard bankCard : bankCards) {
                bankCardViews.add(this.getMerchantBankCardView(bankCard));
            }
        }

        return bankCardViews;
    }

    @Override
    public void bindMerchantBankCard(String bankAccount, String mobile, String realName, String smsCaptcha, String userAgent) {
        // 校验“银行卡号”
        if (StringUtils.isBlank(bankAccount)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010070.getErrorCode());
        }

        // 校验“银行卡预留手机号”
        if (StringUtils.isBlank(mobile)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010071.getErrorCode());
        }

        // 校验“银行卡的主人的真实姓名”
        if (StringUtils.isBlank(realName)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010072.getErrorCode());
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 校验是否进行过“实名认证”
        RealName approvedRealName = realNameRepository.findByUser_idAndStatus(user.getId(), RealName.Status.APPROVED);// 已经通过的“实名认证”
        if (null == approvedRealName) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010129.getErrorCode());
        }

        // 校验“实名认证”的姓名和“待绑定银行卡的姓名”是否一致
        if (!approvedRealName.getRealName().equals(realName)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010078.getErrorCode());
        }


        // 校验“短信验证码”：确保最后一步才进行，这样可以保证“短信验证码”的有效性，避免让用户重复的获取。
        if (!SMSUtil.verifySMSCaptcha(mobile, smsCaptcha, userAgent)) {
            // 验证失败，则抛出异常
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990010.getErrorCode());
        }

        // 绑定银行卡
        List<MerchantBankCard> bankCards = bankCardRepository.findAllByUser_id(user.getId());
        MerchantBankCard newBankCard = new MerchantBankCard();
        if (null != bankCards && !bankCards.isEmpty()) {
            newBankCard.setProceeds(Boolean.FALSE);
            for (MerchantBankCard bankCard : bankCards) {
                if (bankCard.getBankAccount().equals(bankAccount)) {
                    // 找到了已经存在的“银行卡号”
                    throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010073.getErrorCode());
                }
            }
        } else {
            newBankCard.setProceeds(Boolean.TRUE);// 第一张绑定的银行卡，默认设置为“收款账户”
        }
        Date date = new Date();
        newBankCard.setCreateDateTime(date);
        newBankCard.setUpdateDateTime(date);
        newBankCard.setRealName(realName);
        newBankCard.setUser(user);
        newBankCard.setBankAccount(bankAccount);
        newBankCard.setMobile(mobile);

        ValidateBankCardResult validateBankCardResult = this.validateBankAccount(bankAccount);
        if (validateBankCardResult.isValidated()) {
            newBankCard.setBankCode(validateBankCardResult.getBank());

            Bank bank = bankRepository.findOne(newBankCard.getBankCode());
            newBankCard.setBankName(bank.getName());

            if ("DC".equals(validateBankCardResult.getCardType())) {
                newBankCard.setCategory(MerchantBankCard.Category.DEBIT_CARD);
            } else if ("CC".equals(validateBankCardResult.getCardType())) {
                newBankCard.setCategory(MerchantBankCard.Category.CREDIT_CARD);
            } else if ("SCC".equals(validateBankCardResult.getCardType())) {
                newBankCard.setCategory(MerchantBankCard.Category.SEMI_CREDIT_CARD);
            }
            bankCardRepository.save(newBankCard);
        } else {
            // “银行卡号”验证失败
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010074.getErrorCode());
        }
    }

    @Override
    public void setDefaultBankCard(String bankCardId) {
        // 校验“银行卡ID”
        if (StringUtils.isBlank(bankCardId) || !NumberUtils.isParsable(bankCardId)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010070.getErrorCode());
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        List<MerchantBankCard> bankCards = bankCardRepository.findAllByUser_id(user.getId());
        MerchantBankCard defaultBankCard = null;
        if (null != bankCards && !bankCards.isEmpty()) {
            // 先遍历查找当前设置的默认银行卡
            for (MerchantBankCard bankCard : bankCards) {
                if (bankCard.isProceeds()) {
                    defaultBankCard = bankCard;
                    break;
                }
            }

            // 再遍历查找要设置的银行卡
            for (MerchantBankCard bankCard : bankCards) {
                if (bankCard.getId() == NumberUtils.toLong(bankCardId)) {
                    // 找到了要设置的银行卡
                    assert defaultBankCard != null;
                    if (bankCard.getId() == defaultBankCard.getId()) {
                        break;// 如果新设置的银行卡和原来的“默认银行卡”是同一张，则不做更新操作
                    }
                    // 原默认卡设为非默认，设置新的默认卡
                    Date date = new Date();
                    if (defaultBankCard.isProceeds()) {
                        defaultBankCard.setUpdateDateTime(date);
                        defaultBankCard.setProceeds(Boolean.FALSE);
                        bankCardRepository.save(defaultBankCard);
                    }
                    if (!bankCard.isProceeds()) {
                        bankCard.setUpdateDateTime(date);
                        bankCard.setProceeds(Boolean.TRUE);
                        bankCardRepository.save(bankCard);
                    }
                    break;
                }
            }
        }
    }

    @Override
    public ValidateBankCardView validateBankCard(String bankAccount, String realName) {
        // 校验“银行卡号”
        if (StringUtils.isBlank(bankAccount)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010070.getErrorCode());
        }

        // 校验“银行卡的主人的真实姓名”
        if (StringUtils.isBlank(realName)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010072.getErrorCode());
        }

        ValidateBankCardResult validateBankCardResult = this.validateBankAccount(bankAccount);
        if (validateBankCardResult.isValidated()) {
            ValidateBankCardView view = new ValidateBankCardView();
            view.setBankAccount(bankAccount);
            view.setRealName(realName);

            // 设置“银行名称”
            Bank bank = bankRepository.findOne(validateBankCardResult.getBank());
            view.setBankName(bank.getName());

            // 设置“银行卡类型”
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            MerchantBankCard.Category category = MerchantBankCard.Category.DEBIT_CARD;
            if ("DC".equals(validateBankCardResult.getCardType())) {
                category = MerchantBankCard.Category.DEBIT_CARD;
            } else if ("CC".equals(validateBankCardResult.getCardType())) {
                category = MerchantBankCard.Category.CREDIT_CARD;
            } else if ("SCC".equals(validateBankCardResult.getCardType())) {
                category = MerchantBankCard.Category.SEMI_CREDIT_CARD;
            }
            view.setCategory(messageSource.getMessage(category.getMessageKey(), null, request.getLocale()));

            return view;
        } else {
            // “银行卡号”验证失败
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010074.getErrorCode());
        }
    }

    @Override
    public MerchantBankCardView getBankCardById(String bankCardId) {
        // 校验“银行卡ID”
        if (StringUtils.isBlank(bankCardId) || !NumberUtils.isParsable(bankCardId)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010070.getErrorCode());
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        MerchantBankCard bankCard = bankCardRepository.findOne(NumberUtils.toLong(bankCardId));
        return this.getMerchantBankCardView(bankCard);
    }

    /**
     * 通过银行卡卡号解析出“发卡行”和“银行卡类别（储蓄卡/信用卡）”。
     *
     * @param bankAccount 银行卡卡号
     */
    private ValidateBankCardResult validateBankAccount(String bankAccount) {
        assert StringUtils.isNotBlank(bankAccount);
        String url = "https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?_input_charset=utf-8&cardNo={bankAccount}&cardBinCheck=true";

        RestTemplate template = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        ResponseEntity<ValidateBankCardResult> responseEntity = template.getForEntity(url, ValidateBankCardResult.class, bankAccount);
        return responseEntity.getBody();
    }

    /**
     * “银行卡验证结果”，该结果是调用第三方接口获得的。
     */
    public static class ValidateBankCardResult {

        private String bank;// 银行代码，例如：ABC

        private boolean validated;// 验证结果

        private String cardType;// 银行卡类型，例如：信用卡

        private String key;// 银行卡号

        private String stat;// 银行卡状态，一般都为ok，暂时没有对这个做其他逻辑处理

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public boolean isValidated() {
            return validated;
        }

        public void setValidated(boolean validated) {
            this.validated = validated;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getStat() {
            return stat;
        }

        public void setStat(String stat) {
            this.stat = stat;
        }
    }

    private MerchantBankCardView getMerchantBankCardView(MerchantBankCard bankCard) {
        MerchantBankCardView view = new MerchantBankCardView();
        view.setBankCardId(String.valueOf(bankCard.getId()));
        view.setBankAccount(ConverterUtil.getWrapperBankCardAccount(bankCard.getBankAccount()));
        view.setBankCode(bankCard.getBankCode());
        view.setBankName(bankCard.getBankName());

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String category = messageSource.getMessage(bankCard.getCategory().getMessageKey(), null, request.getLocale());
        view.setCategory(category);
        view.setIsProceeds(String.valueOf(bankCard.isProceeds()));

        view.setWrapperBankCard(bankCard.getBankName() + "(" + category + ")");
//                view.setIconUrl();// TODO: 2016/10/10 银行图标的URL

        return view;
    }
}
