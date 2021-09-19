package com.rootdevs.storyapp.Utils;

public class Constants {

    public static final String domain = "http://192.168.43.22:";
    private static final String portNo = "2798";
    private static final String domainSuffix = "/Story";
    private static final String finalDomain = domain + portNo + domainSuffix;

    public static final String loginPostApi = finalDomain + "/user/login";
    public static final int loginPostApiRequestId = 1001;

    public static final String registerPostApi = finalDomain + "/user/register";
    public static final int registerPostRequestId = 1002;

    public static final String updatePassPostApi = finalDomain + "/user/updatePass";
    public static final int updatePassRequestId = 1003;

    public static final String getOtpApi = finalDomain + "/user/getOtp?email=";
    public static final int getOtpRequestId = 1004;

    public static final String getCategoriesApi = finalDomain + "/story/categories";
    public static final int getCategoriesRequestId = 1005;

    public static final String addCategoriesApi = finalDomain + "/admin/createCategory";
    public static final int addCategoriesRequestId = 1006;

    public static final String deleteCategoriesApi = finalDomain + "/admin/deleteCategory?categoryId=";
    public static final int deleteCategoriesRequestId = 1007;

    public static final String uploadImageApi = finalDomain + "/story/uploadImage";
    public static final int uploadImageRequestId = 1008;

    public static final String getStoriesApi = finalDomain + "/story/getStories?categoryId=";
    public static final int getStoriesRequestId = 1009;

    public static final String addStoriesApi = finalDomain + "/admin/addStory";
    public static final int addStoriesRequestId = 1010;

    public static final String deleteStoriesApi = finalDomain + "/admin/deleteStory?storyId=";
    public static final int deleteStoriesRequestId = 1011;

}
