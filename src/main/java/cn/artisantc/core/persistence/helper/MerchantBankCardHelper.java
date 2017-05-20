package cn.artisantc.core.persistence.helper;

import cn.artisantc.core.exception.MerchantBankCardNotFoundException;
import cn.artisantc.core.persistence.entity.MerchantBankCard;
import cn.artisantc.core.web.rest.v1_0.vo.APIErrorResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * “MerchantBankCard”的帮助类。
 * Created by xinjie.li on 2016/11/21.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MerchantBankCardHelper {

    private MerchantBankCardHelper() {
    }

    /**
     * todo:javadoc
     *
     * @param bankCard
     * @return
     */
    public static MerchantBankCard validateBankCardId(MerchantBankCard bankCard) {
        if (null == bankCard) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010213.getErrorCode());
        }
        if (MerchantBankCard.Category.DEBIT_CARD != bankCard.getCategory()) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010215.getErrorCode());
        }

        return bankCard;
    }

    /**
     * todo:javadoc
     *
     * @param bankCardId
     */
    public static void validateBankCardId(String bankCardId) {
        if (StringUtils.isBlank(bankCardId) || !NumberUtils.isParsable(bankCardId)) {
            throw new MerchantBankCardNotFoundException();
        }
    }
}
