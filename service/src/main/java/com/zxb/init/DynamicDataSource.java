package com.zxb.init;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.stat.DruidDataSourceStatManager;
import com.zxb.domain.DataSource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;
import java.util.Set;

/**
 * 新增数据源删除数据源切换数据源
 * @author admin
 * @create 2020/7/16
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
@Service
public class DynamicDataSource extends AbstractRoutingDataSource {
    private boolean debug = true;
    private Map<Object, Object> dynamicTargetDataSources;
    private Object dynamicDefaultTargetDataSource;


    /**
     * 看源码可以看出在创建连接时进行了判断是否有数据源
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        String datasource = DataSourceContextHolder.getDataSource();
        if (!StringUtils.isEmpty(datasource)) {
            Map<Object, Object> dynamicTargetDataSources2 = this.dynamicTargetDataSources;
            if (dynamicTargetDataSources2.containsKey(datasource)) {
                log.info("---当前数据源：" + datasource + "---");
            } else {
                log.info("不存在的数据源：");
                return null;
            }
        } else {
            log.info("---当前数据源：默认数据源---");
        }
        return datasource;
    }

    /**
     * 设置目标数据源
     * @param targetDataSources
     */
    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        this.dynamicTargetDataSources = targetDataSources;
    }

    /**
     * 设置默认数据源
     * @param defaultTargetDataSource
     */
    @Override
    public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        this.dynamicDefaultTargetDataSource = defaultTargetDataSource;
    }
    //创建数据源
    public boolean createDataSource(String key, String driveClass, String url, String username, String password, String dataBaseType) {
        try {
            try { // 排除连接不上的错误
                Class.forName(driveClass);
                DriverManager.getConnection(url, username, password);// 相当于连接数据库
            } catch (Exception e) {
                return false;
            }
            @SuppressWarnings("resource")
            DruidDataSource druidDataSource = new DruidDataSource();
            druidDataSource.setName(key);
            druidDataSource.setDriverClassName(driveClass);
            druidDataSource.setUrl(url);
            druidDataSource.setUsername(username);
            druidDataSource.setPassword(password);
            druidDataSource.setInitialSize(1); //初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
            druidDataSource.setMaxActive(20); //最大连接池数量
            druidDataSource.setMaxWait(60000); //获取连接时最大等待时间，单位毫秒。当链接数已经达到了最大链接数的时候，应用如果还要获取链接就会出现等待的现象，等待链接释放并回到链接池，如果等待的时间过长就应该踢掉这个等待，不然应用很可能出现雪崩现象
            druidDataSource.setMinIdle(5); //最小连接池数量
            String validationQuery = "select 1 from dual";
            druidDataSource.setTestOnBorrow(true); //申请连接时执行validationQuery检测连接是否有效，这里建议配置为TRUE，防止取到的连接不可用
            druidDataSource.setTestWhileIdle(true);//建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
            druidDataSource.setValidationQuery(validationQuery); //用来检测连接是否有效的sql，要求是一个查询语句。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
            druidDataSource.setFilters("stat");//属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：监控统计用的filter:stat日志用的filter:log4j防御sql注入的filter:wall
            druidDataSource.setTimeBetweenEvictionRunsMillis(60000); //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
            druidDataSource.setMinEvictableIdleTimeMillis(180000); //配置一个连接在池中最小生存的时间，单位是毫秒，这里配置为3分钟180000
            druidDataSource.setKeepAlive(true); //打开druid.keepAlive之后，当连接池空闲时，池中的minIdle数量以内的连接，空闲时间超过minEvictableIdleTimeMillis，则会执行keepAlive操作，即执行druid.validationQuery指定的查询SQL，一般为select * from dual，只要minEvictableIdleTimeMillis设置的小于防火墙切断连接时间，就可以保证当连接空闲时自动做保活检测，不会被防火墙切断
            druidDataSource.setRemoveAbandoned(true); //是否移除泄露的连接/超过时间限制是否回收。
            druidDataSource.setRemoveAbandonedTimeout(3600); //泄露连接的定义时间(要超过最大事务的处理时间)；单位为秒。这里配置为1小时
            druidDataSource.setLogAbandoned(true); ////移除泄露连接发生是是否记录日志
            druidDataSource.init();
            this.dynamicTargetDataSources.put(key, druidDataSource);
            setTargetDataSources(this.dynamicTargetDataSources);// 将map赋值给父类的TargetDataSources
            super.afterPropertiesSet();// 将TargetDataSources中的连接信息放入resolvedDataSources管理
            log.info(key+"数据源初始化成功");
            return true;
        } catch (Exception e) {
            log.error(e + "");
            return false;
        }
    }
    // 删除数据源
    public boolean delDataSources(String dataSourceId) {
        Map<Object, Object> dynamicTargetDataSources2 = this.dynamicTargetDataSources;
        if (dynamicTargetDataSources2.containsKey(dataSourceId)) {
            Set<DruidDataSource> druidDataSourceInstances = DruidDataSourceStatManager.getDruidDataSourceInstances();
            for (DruidDataSource l : druidDataSourceInstances) {
                if (dataSourceId.equals(l.getName())) {
                    dynamicTargetDataSources2.remove(dataSourceId);
                    DruidDataSourceStatManager.removeDataSource(l);
                    setTargetDataSources(dynamicTargetDataSources2);// 将map赋值给父类的TargetDataSources
                    super.afterPropertiesSet();// 将TargetDataSources中的连接信息放入resolvedDataSources管理
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    // 测试数据源连接是否有效
    public boolean testDatasource(String key, String driveClass, String url, String username, String password) {
        try {
            Class.forName(driveClass);
            DriverManager.getConnection(url, username, password);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void createDataSourceWithCheck(DataSource dataSource) throws Exception {
        String datasourceId = dataSource.getDataSourceId();
        log.info("正在检查数据源："+datasourceId);
        Map<Object, Object> dynamicTargetDataSources2 = this.dynamicTargetDataSources;
        if (dynamicTargetDataSources2.containsKey(datasourceId)) {
            log.info("数据源"+datasourceId+"之前已经创建，准备测试数据源是否正常...");
            DruidDataSource druidDataSource = (DruidDataSource) dynamicTargetDataSources2.get(datasourceId);
            boolean rightFlag = true;
            Connection connection = null;
            try {
                log.info(datasourceId+"数据源的概况->当前闲置连接数："+druidDataSource.getPoolingCount());
                long activeCount = druidDataSource.getActiveCount();
                log.info(datasourceId+"数据源的概况->当前活动连接数："+activeCount);
                if(activeCount > 0) {
                    log.info(datasourceId+"数据源的概况->活跃连接堆栈信息："+druidDataSource.getActiveConnectionStackTrace());
                }
                log.info("准备获取数据库连接...");
                connection = druidDataSource.getConnection();
                log.info("数据源"+datasourceId+"正常");
            } catch (Exception e) {
                log.error(e.getMessage(),e); //把异常信息打印到日志文件
                rightFlag = false;
                log.info("缓存数据源"+datasourceId+"已失效，准备删除...");
                if(delDataSources(datasourceId)) {
                    log.info("缓存数据源删除成功");
                } else {
                    log.info("缓存数据源删除失败");
                }
            } finally {
                if(null != connection) {
                    connection.close();
                }
            }
            if(rightFlag) {
                log.info("不需要重新创建数据源");
            } else {
                log.info("准备重新创建数据源...");
                createDataSource(dataSource);
                log.info("重新创建数据源完成");
            }
        } else {
            createDataSource(dataSource);
        }

    }

    private  void createDataSource(DataSource dataSource){
        String datasourceId = dataSource.getDataSourceId();
        log.info("准备创建数据源"+datasourceId);
        String dataBaseType = dataSource.getDataBaseType();
        String username = dataSource.getUserName();
        String password = dataSource.getPassWord();
        String url = dataSource.getUrl();
        String driveClass = "com.mysql.cj.jdbc.Driver";

        if(testDatasource(datasourceId,driveClass,url,username,password)) {
            boolean result = this.createDataSource(datasourceId, driveClass, url, username, password, dataBaseType);
            if(!result) {
                log.error("数据源"+datasourceId+"配置正确，但是创建失败");
            }
        } else {
            log.error("数据源配置有错误");
        }
    }
}
