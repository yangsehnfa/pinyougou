package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.BrandMapper;
import com.pinyougou.pojo.Brand;
import com.pinyougou.sellergoods.service.BrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass=BrandService.class,timeout = 5000)
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageInfo<Brand> getAll(int pageNum, int pageSize, Brand brand) {
        PageHelper.startPage(pageNum,pageSize);
       // List<Brand> brands = brandMapper.selectAll();
        //条件查询
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        if(brand!=null){
            //名字模糊搜索
            if(StringUtils.isNotBlank(brand.getName())){
                criteria.andLike("name","%"+brand.getName()+"%");
            }

            //首字母搜索
            if(StringUtils.isNotBlank(brand.getFirstChar())){
                criteria.andEqualTo("firstChar",brand.getFirstChar());
            }
        }
        //执行查询
        List<Brand> all = brandMapper.selectByExample(example);
        PageInfo<Brand> pageInfo = new PageInfo<Brand>(all);

        return pageInfo;
    }

    @Override
    public int add(Brand brand) {
        return brandMapper.insertSelective(brand);
    }

    @Override
    public int updateBrandById(Brand brand) {
        return brandMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public Brand getOneById(long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteByIds(List<Long> ids) {
        //创建Example，来构建根据ID删除数据
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        //所需的SQL语句类似 delete from tb_brand where id in(1,2,5,6)
        criteria.andIn("id",ids);
        return brandMapper.deleteByExample(example);
    }

    @Override
    public List<Map<String, Object>> selectOptionList() {
        List<Brand> brands = brandMapper.selectAll();
        List<Map<String, Object>> list=new ArrayList<>();
        for (Brand brand : brands) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id",brand.getId());
            map.put("text",brand.getName());
            list.add(map);
        }
        return list;
    }
}
