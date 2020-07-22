package com.zxb.api;

import com.zxb.domain.DataSource;
import java.util.List;

/**
 * 获取数据源和切换数据源
 * @author zxb
 * @create 2020/7/16
 * @since 1.0.0
 */
public interface DBChangeService {
    List<DataSource> get();

    boolean changeDb(String datasourceId) throws Exception;
}
