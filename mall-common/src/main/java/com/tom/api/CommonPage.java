package com.tom.api;

import lombok.Data;

import java.util.List;
@Data
public class CommonPage<T> {
    private Integer pageNum;
    private Integer pageSize;
    private Integer totalPage;
    private Integer total;
    private List<T> list;

    public CommonPage() {
    }
    public static <T>CommonPage<T> restPage(List<T> list) {
        CommonPage<T> commonPage = new CommonPage<>();

    }
}
