package com.zxb;

import com.zxb.api.IDataResourceService;
import com.zxb.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author zxb
 * @create 2020/7/22
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataResourceTest {

    @Autowired
    private IDataResourceService dataResourceService;

    @Test
    public void dataResourceZxbOneTest(){
        dataResourceService.saveUserOne();
    }

    @Test
    public void dataResourceZxbTwoTest(){
        dataResourceService.saveUserTwo();
    }


}
