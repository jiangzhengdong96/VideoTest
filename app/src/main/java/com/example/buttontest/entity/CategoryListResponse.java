package com.example.buttontest.entity;

import java.io.Serializable;
import java.util.List;


public class CategoryListResponse implements Serializable {

    /**
     * msg : success
     * code : 0
     * page : {"totalCount":8,"pageSize":10,"totalPage":1,"currPage":1,"list":[{"categoryId":1,"categoryName":"游戏"},{"categoryId":2,"categoryName":"音乐"},{"categoryId":3,"categoryName":"美食"},{"categoryId":4,"categoryName":"农人"},{"categoryId":5,"categoryName":"vlog"},{"categoryId":6,"categoryName":"搞笑"},{"categoryId":7,"categoryName":"宠物"},{"categoryId":8,"categoryName":"军事"}]}
     */

    private String msg;
    private Integer code;
    private PageDTO page;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public PageDTO getPage() {
        return page;
    }

    public void setPage(PageDTO page) {
        this.page = page;
    }

    public static class PageDTO {
        /**
         * totalCount : 8
         * pageSize : 10
         * totalPage : 1
         * currPage : 1
         * list : [{"categoryId":1,"categoryName":"游戏"},{"categoryId":2,"categoryName":"音乐"},{"categoryId":3,"categoryName":"美食"},{"categoryId":4,"categoryName":"农人"},{"categoryId":5,"categoryName":"vlog"},{"categoryId":6,"categoryName":"搞笑"},{"categoryId":7,"categoryName":"宠物"},{"categoryId":8,"categoryName":"军事"}]
         */

        private Integer totalCount;
        private Integer pageSize;
        private Integer totalPage;
        private Integer currPage;
        private List<CategoryEntity> list;

        public Integer getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(Integer totalCount) {
            this.totalCount = totalCount;
        }

        public Integer getPageSize() {
            return pageSize;
        }

        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }

        public Integer getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(Integer totalPage) {
            this.totalPage = totalPage;
        }

        public Integer getCurrPage() {
            return currPage;
        }

        public void setCurrPage(Integer currPage) {
            this.currPage = currPage;
        }

        public List<CategoryEntity> getList() {
            return list;
        }

        public void setList(List<CategoryEntity> list) {
            this.list = list;
        }
    }
}
