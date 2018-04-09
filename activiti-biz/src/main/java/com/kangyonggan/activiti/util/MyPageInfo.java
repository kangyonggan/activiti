package com.kangyonggan.activiti.util;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author kangyonggan
 * @date 4/9/18
 */
public class MyPageInfo<T> extends PageInfo<T> {

    public MyPageInfo(List<T> list, int pageNum, int pageSize, int total) {
        this.setPageNum(pageNum);
        this.setPageSize(pageSize);
        this.setPages((total - 1) / pageSize + 1);
        this.setList(list);
        this.setTotal(total);
        this.setSize(list.size());
        this.setStartRow((pageNum - 1) * pageSize);
        this.setEndRow(pageNum * pageSize);

        this.setNavigatePages(8);
        this.calcNavigatepageNums();
        this.calcPage();
        this.judgePageBoudary();
    }

    private void calcNavigatepageNums() {
        int i;
        if (this.getPages() <= this.getNavigatePages()) {
            this.setNavigatepageNums(new int[this.getPages()]);

            for (i = 0; i < this.getPages(); ++i) {
                this.getNavigatepageNums()[i] = i + 1;
            }
        } else {
            this.setNavigatepageNums(new int[this.getNavigatePages()]);
            i = this.getPageNum() - this.getNavigatePages() / 2;
            int endNum = this.getPageNum() + this.getNavigatePages() / 2;
            if (i < 1) {
                i = 1;

                for (i = 0; i < this.getNavigatePages(); ++i) {
                    this.getNavigatepageNums()[i] = i++;
                }
            } else if (endNum > this.getPages()) {
                endNum = this.getPages();

                for (i = this.getNavigatePages() - 1; i >= 0; --i) {
                    this.getNavigatepageNums()[i] = endNum--;
                }
            } else {
                for (i = 0; i < this.getNavigatePages(); ++i) {
                    this.getNavigatepageNums()[i] = i++;
                }
            }
        }

    }

    private void calcPage() {
        if (this.getNavigatepageNums() != null && this.getNavigatepageNums().length > 0) {
            this.setFirstPage(this.getNavigatepageNums()[0]);
            this.setLastPage(this.getNavigatepageNums()[this.getNavigatepageNums().length - 1]);
            if (this.getPageNum() > 1) {
                this.setPrePage(this.getPageNum() - 1);
            }

            if (this.getPageNum() < this.getPages()) {
                this.setNextPage(this.getPageNum() + 1);
            }
        }

    }

    private void judgePageBoudary() {
        this.setIsFirstPage(this.getPageNum() == 1);
        this.setIsLastPage(this.getPageNum() == this.getPages());
        this.setHasPreviousPage(this.getPageNum() > 1);
        this.setHasNextPage(this.getPageNum() < this.getPages());
    }

}
