package cn.artisantc.core.persistence.entity;

import cn.artisantc.core.persistence.EntityConstant;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * “拍品类别”父类。
 * Created by xinjie.li on 2016/9/21.
 *
 * @author xinjie.li
 * @since 1.0
 */
@MappedSuperclass
public class BaseItemCategory extends BaseEntity {

    @Column(name = "category_name", length = 30)
    private String categoryName;// 类别名称

    @Column(name = "category_code", length = EntityConstant.ITEM_CATEGORY_LENGTH)
    private String categoryCode;// 类别代码

    @Column
    private String icon;// 类别图标

    /**
     * “拍品的一级分类”。
     */
    public enum Category {
        /**
         * 书画
         */
        CALLIGRAPHY_AND_PAINTING,
        /**
         * 章料
         */
        SIGNET_MATERIAL,
        /**
         * 玉器
         */
        JADEWARE,
        /**
         * 木艺
         */
        WOODEN,
        /**
         * 篆刻
         */
        SEAL_CUTTING,
        /**
         * 陶瓷
         */
        CERAMIC,
        /**
         * 文献
         */
        LITERATURE,
        /**
         * 其他
         */
        OTHERS;
    }

    /**
     * “拍品的二级分类，隶属于一级分类”。
     */
    public enum SubCategory {
        /**
         * 书画：全部
         */
        CALLIGRAPHY_AND_PAINTING_ALL,
        /**
         * 书画：国画
         */
        CHINESE_PAINTING,
        /**
         * 书画：书法
         */
        CALLIGRAPHY,
        /**
         * 书画：版画
         */
        WOODCUT,
        /**
         * 书画：油画
         */
        OIL_PAINTING,
        /**
         * 书画：唐卡
         */
        THANGKA,
        /**
         * 书画：素描
         */
        SKETCH,
        /**
         * 书画：水粉
         */
        GOUACHE,
        /**
         * 书画：其他
         */
        CALLIGRAPHY_AND_PAINTING_OTHERS,
        /**
         * 章料：全部
         */
        SIGNET_MATERIAL_ALL,
        /**
         * 章料：青田石
         */
        QINGTIAN_STONE,
        /**
         * 章料：寿山石
         */
        SHOUSHAN_STONE,
        /**
         * 章料：昌化石
         */
        CHANGHUA_STONE,
        /**
         * 章料：巴林石
         */
        BARLIN_STONE,
        /**
         * 章料：老挝石
         */
        LAOS_STONE,
        /**
         * 章料：其他
         */
        SIGNET_MATERIAL_OTHERS,
        /**
         * 玉器：全部
         */
        JADEWARE_ALL,
        /**
         * 玉器：高古
         */
        ANCIENT_JADE,
        /**
         * 玉器：新玉
         */
        NEW_JADE,
        /**
         * 玉器：和田
         */
        NEPHRITE,
        /**
         * 玉器：翡翠
         */
        JADEITE,
        /**
         * 玉器：明清
         */
        MING_AND_QING_DYNASTIES,
        /**
         * 玉器：其他
         */
        JADEWARE_OTHERS,
        /**
         * 木艺：全部
         */
        WOODEN_ALL,
        /**
         * 木艺：紫檀
         */
        PADAUK,
        /**
         * 木艺：花梨
         */
        PALISANDER,
        /**
         * 木艺：沉香
         */
        EAGLEWOOD,
        /**
         * 木艺：酸枝
         */
        SANTOS_ROSE_WOOD,
        /**
         * 木艺：楠木
         */
        KUSUNOKI,
        /**
         * 木艺：崖柏
         */
        THUJA,
        /**
         * 木艺：黄花梨
         */
        ROSE_WOOD,
        /**
         * 木艺：鸡翅木
         */
        WENGE,
        /**
         * 木艺：铁刀木
         */
        SENNA_SIAMEA,
        /**
         * 木艺：乌木
         */
        EBONY,
        /**
         * 木艺：榉木
         */
        BEECH,
        /**
         * 木艺：榆木
         */
        ELM,
        /**
         * 木艺：黄杨
         */
        BOXWOOD,
        /**
         * 木艺：樟木
         */
        CAMPHORWOOD,
        /**
         * 木艺：其他
         */
        WOODEN_OTHERS,
        /**
         * 篆刻：全部
         */
        SEAL_CUTTING_ALL,
        /**
         * 篆刻：古玺印
         */
        ANCIENT_ROYAL_SEAL,
        /**
         * 篆刻：汉玉印
         */
        HAN_JADE_SEAL,
        /**
         * 篆刻：肖形印
         */
        PICTORIAL_SEALS,
        /**
         * 篆刻：闲章印
         */
        FREE_SEAL,
        /**
         * 篆刻：流派印
         */
        SEALS_OF_SCHOOLS_OF_ARTISTS,
        /**
         * 篆刻：六面印
         */
        SIX_SIDES_SEAL,
        /**
         * 篆刻：单字印
         */
        SINGLE_SEAL,
        /**
         * 篆刻：其他
         */
        SEAL_CUTTING_OTHERS,
        /**
         * 陶瓷：全部
         */
        CERAMIC_ALL,
        /**
         * 陶瓷：紫砂
         */
        PURPLE_SAND,
        /**
         * 陶瓷：青瓷
         */
        CELADON,
        /**
         * 陶瓷：白瓷
         */
        WHITE_PORCELAIN,
        /**
         * 陶瓷：青白瓷
         */
        GREENISH_WHITE_PORCELAIN,
        /**
         * 陶瓷：黑瓷
         */
        BLACK_PORCELAIN,
        /**
         * 陶瓷：彩绘瓷
         */
        PAINTED_CHINA,
        /**
         * 陶瓷：颜色釉
         */
        COLOUR_GLAZE,
        /**
         * 陶瓷：传统陶艺
         */
        TRADITIONAL_CERAMIC,
        /**
         * 陶瓷：现代陶艺
         */
        MODERN_CERAMIC,
        /**
         * 陶瓷：生活陶艺
         */
        LIVING_CERAMIC,
        /**
         * 陶瓷：其他
         */
        CERAMIC_OTHERS,
        /**
         * 文献：全部
         */
        LITERATURE_ALL,
        /**
         * 文献：古籍
         */
        ANCIENT_BOOKS,
        /**
         * 文献：票证
         */
        TICKET,
        /**
         * 文献：碑帖拓片
         */
        INSCRIPTION_RUBBING,
        /**
         * 文献：名人信札
         */
        CELEBRITY_LETTERS,
        /**
         * 文献：其他
         */
        LITERATURE_OTHERS,
        /**
         * 其他：全部
         */
        OTHERS_ALL,
        /**
         * 其他：文房
         */
        FOUR_TREASURES_OF_STUDY,
        /**
         * 其他：匏器
         */
        MOULDED_GOURD_SHAPED_ARTICLES,
        /**
         * 其他：奇石
         */
        RARE_STONE,
        /**
         * 其他：徽章
         */
        BADGE
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
