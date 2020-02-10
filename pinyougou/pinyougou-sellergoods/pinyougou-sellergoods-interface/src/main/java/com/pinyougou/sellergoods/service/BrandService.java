package com.pinyougou.sellergoods.service;

import com.github.pagehelper.PageInfo;
import com.pinyougou.pojo.Brand;

import java.util.List;
import java.util.Map;

public interface BrandService {
    /***
     * 分页返回列表
     * @param pageNum
     * @param pageSize
     * @param brand
     * @return
     */
    public PageInfo<Brand> getAll(int pageNum, int pageSize, Brand brand);

    int add(Brand brand);

    int updateBrandById(Brand brand);

    Brand getOneById(long id);

    int deleteByIds(List<Long> ids);

    List<Map<String, Object>> selectOptionList();
}
