package com.zxb.service.mapper;

import com.zxb.domain.DataSource;
import com.zxb.domain.User;
import tk.mybatis.mapper.common.MySqlMapper;
import java.util.List;

/**
 * 获取所有的数据源
 * @author admin
 * @create 2020/7/16
 * @since 1.0.0
 */
public interface DataSourceMapper extends tk.mybatis.mapper.common.Mapper<DataSource>, MySqlMapper<DataSource> {


    List<DataSource> get();
}
