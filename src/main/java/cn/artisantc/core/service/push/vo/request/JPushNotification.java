package cn.artisantc.core.service.push.vo.request;

/**
 * “推送通知”的封装类，根据JPush的接口文档实现。
 * 详情参阅：https://docs.jiguang.cn/jpush/server/push/rest_api_v3_push/#notification
 * Created by xinjie.li on 2017/2/17.
 *
 * @author xinjie.li
 * @since 2.4
 */
public class JPushNotification {

    private String alert;// 通知内容

//    private AndroidNotification notification;
//
//    public class AndroidNotification {
//
//        private String title = "艺匠说";// 通知的标题
//
//        public String getTitle() {
//            return title;
//        }
//
//        public void setTitle(String title) {
//            this.title = title;
//        }
//    }
//
//    public class IOSNotification {
//
//        private String sound = "default";// 通知提示声音
//
//        private String badge = "+1";// 应用角标
//
//        public String getSound() {
//            return sound;
//        }
//
//        public void setSound(String sound) {
//            this.sound = sound;
//        }
//
//        public String getBadge() {
//            return badge;
//        }
//
//        public void setBadge(String badge) {
//            this.badge = badge;
//        }
//    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }
}
