package com.hudezhi.freedom.homeapp.rxjava_retrofit.config;

/**
 * Created by boy on 2017/11/29.
 */

public class Configuration {
    public static final String BASE_URL = "http://58.211.125.61:8005/WebService/";

    public static class MethodPath {
        public static final String DevFault = "DevFault.asmx";
    }

    public static class MethodName {
        public static final String Delete_Log = "DeleteTicketWorkLog";
        public static final String Insert_Log = "InsertTicketWorkLog";
        public static final String Update_Log = "UpdateTicketWorkLog";
        public static final String Search_Log = "SearchTicketWorkLog";
    }

}
