package com.example.buttontest.util;

public class AppConfig {
    public static final int PAGE_SIZE = 5;
    public static final String BASE_URl = "http://47.112.180.188:8080/renren-fast";
    public static final String LOGIN = "/app/login";
    public static final String REGISTER = "/app/register";
    public static final String VIDEO_LIST = "/app/videolist/list";
    public static final String VIDEO_LIST_BY_CATEGORY = "/app/videolist/getListByCategoryId";
    public static final String VIDEO_CATEGORY_LIST = "/app/videocategory/list";
    public static final String NEWS_LIST = "/app/news/api/list";//资讯列表
    public static final String VIDEO_UPDATE_COUNT = "/app/videolist/updateCount";//更新点赞,收藏,评论
    public static final String VIDEO_MYCOLLECT = "/app/videolist/mycollect";//我的收藏
}
