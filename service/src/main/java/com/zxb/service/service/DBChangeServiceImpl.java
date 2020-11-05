package com.zxb.service.service;

import com.zxb.api.DBChangeService;
import com.zxb.domain.DataSource;
import com.zxb.init.DataSourceContextHolder;
import com.zxb.init.DynamicDataSource;
import com.zxb.service.mapper.DataSourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 切换数据源和获取数据源
 * @author admin
 * @create 2020/7/16
 * @since 1.0.0
 */
@Service
public class DBChangeServiceImpl implements DBChangeService {
    @Autowired
    DataSourceMapper dataSourceMapper;
    @Autowired
    private DynamicDataSource dynamicDataSource;
    @Override
    public List<DataSource> get() {
        return dataSourceMapper.get();
    }

    @Override
    public boolean changeDb(String datasourceId) throws Exception {
        //默认切换到主数据源,进行整体资源的查找
        DataSourceContextHolder.clearDataSource();
        //查询数据库的所有数据源
        List<DataSource> dataSourcesList = dataSourceMapper.get();
        for (DataSource dataSource : dataSourcesList) {
            if (dataSource.getCode().equals(datasourceId)) {
                System.out.println("需要使用的的数据源已经找到,datasourceId是：" + dataSource.getDataSourceId());
                //创建数据源连接&检查 若存在则不需重新创建
                dynamicDataSource.createDataSourceWithCheck(dataSource);
                //切换到该数据源
                DataSourceContextHolder.setDataSource(dataSource.getDataSourceId());
                return true;
            }
        }
        return false;

    }
}
