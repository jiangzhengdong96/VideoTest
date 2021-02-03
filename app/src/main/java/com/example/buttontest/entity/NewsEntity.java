package com.example.buttontest.entity;

import java.io.Serializable;
import java.util.List;

public class NewsEntity implements Serializable{
    /**
     * newsId : 1
     * newsTitle : 《忍者蛙》发售日公布 已上架Steam、支持简中
     * authorName : 3DMGAME
     * headerUrl : https://p9.pstatp.com/thumb/6eed00026c4eac713a44
     * commentCount : 3
     * releaseDate : 2020-07-31 22:23:00
     * type : 1
     * thumbEntities : [{"thumbId":1,"thumbUrl":"http://p1-tt.byteimg.com/large/pgc-image/S6KR5958Y5X2Qt?from=pc","newsId":1}]
     * imgList : null
     */

    private Integer newsId;
    private String newsTitle;
    private String authorName;
    private String headerUrl;
    private Integer commentCount;
    private String releaseDate;
    private Integer type;
    private Object imgList;
    private List<ThumbEntitiesDTO> thumbEntities;

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getHeaderUrl() {
        return headerUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Object getImgList() {
        return imgList;
    }

    public void setImgList(Object imgList) {
        this.imgList = imgList;
    }

    public List<ThumbEntitiesDTO> getThumbEntities() {
        return thumbEntities;
    }

    public void setThumbEntities(List<ThumbEntitiesDTO> thumbEntities) {
        this.thumbEntities = thumbEntities;
    }

    public static class ThumbEntitiesDTO {
        /**
         * thumbId : 1
         * thumbUrl : http://p1-tt.byteimg.com/large/pgc-image/S6KR5958Y5X2Qt?from=pc
         * newsId : 1
         */

        private Integer thumbId;
        private String thumbUrl;
        private Integer newsId;

        public Integer getThumbId() {
            return thumbId;
        }

        public void setThumbId(Integer thumbId) {
            this.thumbId = thumbId;
        }

        public String getThumbUrl() {
            return thumbUrl;
        }

        public void setThumbUrl(String thumbUrl) {
            this.thumbUrl = thumbUrl;
        }

        public Integer getNewsId() {
            return newsId;
        }

        public void setNewsId(Integer newsId) {
            this.newsId = newsId;
        }
    }
}
