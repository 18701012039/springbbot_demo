package com.zxb.service.controller;

import com.zxb.api.IDataResourceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 *
 * @author zxb
 * @create 2020/7/22
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "/dataResource")
public class DataResourceController {

    @Resource
    private IDataResourceService dataResourceService;

    @RequestMapping(value = "/saveZxbOne",method = {RequestMethod.POST,RequestMethod.GET})
    public void saveZxbOne(){
        dataResourceService.saveUserOne();
    }

    @RequestMapping(value = "/saveZxbTwo",method = {RequestMethod.POST,RequestMethod.GET})
    public void saveZxbTwo(){
        dataResourceService.saveUserTwo();
    }
}
