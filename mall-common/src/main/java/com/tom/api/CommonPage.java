package com.tom.api;

import com.github.pagehelper.PageInfo;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
@Data
public class CommonPage<T> {
    private Integer pageNum;
    private Integer pageSize;
    private Integer totalPage;
    private Long total;
    private List<T> list;

    public CommonPage() {
    }
    public static <T>CommonPage<T> restPage(List<T> list) {
        CommonPage<T> commonPage = new CommonPage<>();
        PageInfo<T> pageInfo = PageInfo.of(list);
        commonPage.setPageNum(pageInfo.getPageNum());
        commonPage.setPageSize(pageInfo.getPageSize());
        commonPage.setTotal(pageInfo.getTotal());
        commonPage.setTotalPage(pageInfo.getPages());
        commonPage.setList(pageInfo.getList());
        return commonPage;
    }

    public static <T> CommonPage<T> restPage(Page<T> page) {
        CommonPage<T> commonPage = new CommonPage<>();
        commonPage.setTotalPage(page.getTotalPages());
        commonPage.setTotal(page.getTotalElements());
        commonPage.setPageSize(page.getSize());
        commonPage.setPageNum(page.getNumber());
        commonPage.setList(page.getContent());
        return commonPage;
    }
}
