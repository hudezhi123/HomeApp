package com.hudezhi.freedom.homeapp.live_video.bean;

/**
 * Created by Administrator on 2016/10/17.
 */
public class LiveInterface {

    public static String getUrl(int index) {
        String uriStr = null;
        switch (index) {
            case 0:
                uriStr = getAll();
                break;
            case 1:
                uriStr = getbeauty();
                break;
            case 2:
                uriStr = getCute();
                break;
            case 3:
                uriStr = getVoice();
                break;
            case 4:
                uriStr = getTalent();
                break;
            case 5:
                uriStr = getFresh();
                break;
            case 6:
                uriStr = getMC();
                break;
            case 7:
                uriStr = getJIngBao();
                break;
            case 8:
                uriStr = getMore();
                break;
        }
        return uriStr;
    }

    //标题
    private static String getTypes() {
        return "";
    }

    //全部
    private static String getAll() {
        return "http://qf.56.com/home/v4/homeData.android";
    }

    //高颜值
    private static String getbeauty() {
        return "http://qf.56.com/home/v4/moreAnchor" +
                ".android?cVer=55&imei=357092061156226&index=0&ts=1476550057633&type=0&signature" +
                "=aa59b9f9d6b3f2d75738800ed5e98fd7";
    }

    //萌妹子
    private static String getCute() {
        return "http://qf.56.com/home/v4/moreAnchor" +
                ".android?cVer=55&imei=357092061156226&index=0&ts=1476551119614&type=1&signature" +
                "=bbf788b39572eea7dd4877f46e7c417a";
    }

    //好声音
    private static String getVoice() {
        return "http://qf.56.com/home/v4/moreAnchor" +
                ".android?cVer=55&imei=357092061156226&index=0&ts=1476551126571&type=2&signature" +
                "=c09222be42447622d4e6f718ff2562d0";
    }

    //有才艺
    private static String getTalent() {
        return "http://qf.56.com/home/v4/moreAnchor" +
                ".android?cVer=55&imei=357092061156226&index=0&ts=1476551890121&type=4&signature" +
                "=b91f8f92b781a77739b4d78fa1751e5a";
    }

    //小鲜肉
    private static String getFresh() {
        return "http://qf.56.com/home/v4/moreAnchor" +
                ".android?cVer=55&imei=357092061156226&index=0&ts=1476551891593&type=5&signature" +
                "=c8a4708a4bdbebe6a1a6fae16aaadf2c";
    }

    //逗B
    private static String getMC() {
        return "http://59.56.26.49/home/v4/moreAnchor" +
                ".android?cVer=55&imei=357092061156226&index=0&ts=1476551899255&type=8&signature" +
                "=d8eb919b0e7d1ebad484288e92b74e49";
    }

    //劲爆
    private static String getJIngBao() {
        return "http://qf.56.com/home/v4/moreAnchor" +
                ".android?cVer=55&imei=357092061156226&index=0&ts=1476551902369&type=7&signature" +
                "=999f3ee9ebf48f27d13a8cf23900b4b6";
    }

    //还有更多
    private static String getMore() {
        return "http://qf.56.com/home/v4/moreAnchor" +
                ".android?cVer=55&imei=357092061156226&index=0&ts=1476551904692&type=3&signature" +
                "=865dcc42ad9685cbbdb5d01db9ef42eb";
    }


    public static String enterAnchorRoom(String roomId) {
        return "http://qf.56.com/play/v1/preLoading" +
                ".android?roomId=" + roomId + "&ts=1471699894242&signature" +
                "=43feb311908f543a4b1870a710d4d871";
    }


}
