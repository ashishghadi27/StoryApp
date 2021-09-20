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

    public static final String getMCQSetsApi = finalDomain + "/story/getMcqSets?storyId=";
    public static final int getMCQSetsRequestId = 1012;

    public static final String getAssignSetsApi = finalDomain + "/story/getAssignmentSets?storyId=";
    public static final int getAssignSetsRequestId = 1013;

    public static final String addMCQSetsApi = finalDomain + "/admin/createMcqSet";
    public static final int addMCQSetsRequestId = 1014;

    public static final String addAssignSetsApi = finalDomain + "/admin/createAssignmentSet";
    public static final int addAssignSetsRequestId = 1015;

    public static final String deleteMCQSetsApi = finalDomain + "/admin/deleteMCQSet?setId=";
    public static final int deleteMCQSetsRequestId = 1016;

    public static final String deleteAssignSetsApi = finalDomain + "/admin/deleteAssignmentSet?setId=";
    public static final int deleteAssignSetsRequestId = 1017;

    //**********************************************************************

    public static final String getMCQsApi = finalDomain + "/story/getMcqs?setId=";
    public static final int getMCQsRequestId = 1018;

    public static final String getAssignsApi = finalDomain + "/story/getAssignments?setId=";
    public static final int getAssignsRequestId = 1019;

    public static final String addMCQsApi = finalDomain + "/admin/addMcq";
    public static final int addMCQsRequestId = 1020;

    public static final String addAssignsApi = finalDomain + "/admin/addAssignmentQts";
    public static final int addAssignsRequestId = 1021;

    public static final String deleteMCQsApi = finalDomain + "/admin/deleteMCQ?mcqId=";
    public static final int deleteMCQsRequestId = 1022;

    public static final String deleteAssignsApi = finalDomain + "/admin/deleteAssignmentQts?quesId=";
    public static final int deleteAssignsRequestId = 1023;

    public static final String setMCQAnswerApi = finalDomain + "/users/saveMcqAnswer";
    public static final int setMCQAnswerRequestId = 1025;

    public static final String setAssignAnswerApi = finalDomain + "/users/saveAssignmentAnswer";
    public static final int setAssignAnswerRequestId = 1026;

    public static final String getAllUsers = finalDomain + "/admin/allUsers";
    public static final int getAllUsersRequestId = 1027;

    public static final String getUsersScore = finalDomain + "//admin/getMCQSetsByUser?userId=";
    public static final int getAllUsersScoreRequestId = 1028;


}
