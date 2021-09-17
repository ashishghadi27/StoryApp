package com.rootdevs.storyapp.Utils;

public class Constants {

    private static final String domain = "http://localhost:";
    private static final String portNo = "2798";
    private static final String domainSuffix = "/Story/story";
    private static final String finalDomain = domain + portNo + domainSuffix;

    public static final String loginPostApi = finalDomain + "/user/login";
    public static final int loginPostApiRequestId = 1001;

    public static final String registerPostApi = finalDomain + "/user/register";
    public static final int registerPostRequestId = 1002;

    public static final String updatePassPostApi = finalDomain + "updatePass";
    public static final int updatePassRequestId = 1003;

    public static final String getOtpApi = finalDomain + "getOtp?email=";
    public static final int getOtpRequestId = 1004;

}
