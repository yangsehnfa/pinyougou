package com.pinyougou.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.pinyougou.http.Result;
import com.pinyougou.pojo.Brand;
import com.pinyougou.sellergoods.service.BrandService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/brand")
public class BrandController {
    @Reference
    private BrandService brandService;
    /***
     * 分页查询数据
     * 获取JSON数据
     * @return
     */
    @RequestMapping(value = "/list")
    public PageInfo<Brand> list(@RequestBody Brand brand,@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return brandService.getAll(page, size,brand);
    }
    /***
     * 增加品牌数据
     * @param brand
     * 响应数据：success
     *                  true:成功  false：失败
     *           message
     *                  响应的消息
     *
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result add(@RequestBody Brand brand){

        try {
            //执行增加
            int acount = brandService.add(brand);

            if(acount>0){
                //增加成功
                return  new Result(true,"增加品牌成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false,"增加品牌失败");
    }
    /***
     * 修改品牌信息
     * @param brand
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result modify(@RequestBody Brand brand){
        try {
            //根据ID修改品牌信息
            int mcount = brandService.updateBrandById(brand);
            if(mcount>0){
                return new Result(true,"品牌修改成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false,"品牌修改失败");
    }

    /***
     * 根据ID查询品牌信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Brand getById(@PathVariable(value = "id")long id){
        //根据ID查询品牌信息
        Brand brand = brandService.getOneById(id);
        return brand;
    }
    /***
     * 根据ID批量删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete")
    public Result delete(@RequestBody List<Long> ids){
        try {
            //根据ID删除数据
            int dcount = brandService.deleteByIds(ids);

            if(dcount>0){
                return new Result(true,"品牌删除成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false,"品牌删除失败");
    }
}
