package com.hudezhi.freedom.homeapp.live_video.bean;

/**
 * Created by Administrator on 2016/10/18.
 */
public class RankListAPI {
    //周星榜 日
    public static String getStarDayList(){
        return "http://qf.56.com/rank/v1/rankStar.android?cVer=55&imei=357092061156226&pageSize=30&ts=1476553783993&type=1&signature=56e11ad399fc5e0885e4e49b0320266f";
    }
    //周星榜 周
    public static String getStarWeekList(){
        return "http://qf.56.com/rank/v1/rankStar.android?cVer=55&imei=357092061156226&pageSize=30&ts=1476553784003&type=2&signature=cd47f5ad6a6937fc222ee9ff7731cb84";
    }
    //周星榜 月
    public static String getStarMonthList(){
        return "http://qf.56.com/rank/v1/rankStar.android?cVer=55&imei=357092061156226&pageSize=30&ts=1476555304736&type=3&signature=9035f405213aad36d3e471420ccfe51a";
    }
    //周星榜 总
    public static String getStarYearList(){
        return "http://qf.56.com/rank/v1/rankStar.android?cVer=55&imei=357092061156226&pageSize=30&ts=1476555304748&type=4&signature=ae88247aec04caa99ecd7cbb0349770c";
    }
    //富豪榜 日
    public static String getRichDayList(){
        return "http://qf.56.com/rank/v1/rankWealth.android?cVer=55&imei=357092061156226&pageSize=30&ts=1476553784017&type=1&signature=2a7b8c1ce9937172ba0f3e20d5bae73f";
    }
    //富豪榜 周
    public static String getRichWeekList(){
        return "http://qf.56.com/rank/v1/rankWealth.android?cVer=55&imei=357092061156226&pageSize=30&ts=1476553784027&type=2&signature=ea21a58335f25ee96b65726f6fa24bb2";
    }
    //富豪榜 月
    public static String getRichMonthList(){
        return "http://qf.56.com/rank/v1/rankWealth.android?cVer=55&imei=357092061156226&pageSize=30&ts=1476555563781&type=3&signature=cd507851c169dadcc1899920eec1b9c3";
    }
    //富豪榜 总
    public static String getRichYearList(){
        return "http://qf.56.com/rank/v1/rankWealth.android?cVer=55&imei=357092061156226&pageSize=30&ts=1476555420589&type=4&signature=2d1918942677f6682c12589f7933a5e1";
    }
    //人气榜 日
    public static String getPopularityDayList(){
        return "http://qf.56.com/rank/v1/rankPopularity.android?cVer=55&imei=357092061156226&pageSize=30&ts=1476553844771&type=1&signature=8d0754a6ee9cc67e437f614dc793e30a";
    }
    //人气榜 周

    public static String getPopularityWeekList(){
        return "http://qf.56.com/rank/v1/rankPopularity.android?cVer=55&imei=357092061156226&pageSize=30&ts=1476553844784&type=2&signature=3e79c175440dde3962a6b1b31914ccb0";
    }
    //人气榜 月
    public static String getPopularityMonthList(){
        return "http://qf.56.com/rank/v1/rankPopularity.android?cVer=55&imei=357092061156226&pageSize=30&ts=1476554732188&type=3&signature=9841319fb0259c098459127e9a8855d3";
    }
    //人气榜 总
    public static String getPopularityYearList(){
        return "http://qf.56.com/rank/v1/rankPopularity.android?cVer=55&imei=357092061156226&pageSize=30&ts=1476554733905&type=4&signature=52966fc7697c3bb93e4a5a8a934ee00e";
    }
    //周星榜 上周
    public static String getWeekStarLastList(){
        return "http://qf.56.com/activity/star/v1/rankAll.android?cVer=55&imei=357092061156226&ts=1476553845170&weekly=1&signature=7bbf3521e80c7ebe05058322f6f9ece6";
    }
    //周星榜 本周
    public static String getWeekStarThisList(){
        return "http://qf.56.com/activity/star/v1/rankAll.android?cVer=55&imei=357092061156226&ts=1476553845152&weekly=0&signature=0d952ab7b6746cb173a8614e05d0d1b5";
    }
}
