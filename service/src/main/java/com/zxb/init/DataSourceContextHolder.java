package com.zxb.init;
 /**
 * @Author : zxb
 * @CreateTime : 2019/12/10
 * @Description :
 **/
public class DataSourceContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    // 设置数据源名
    public static void setDataSource(String dbName) {
        contextHolder.set(dbName);
    }

    // 获取数据源名
    public static String getDataSource() {
        return (contextHolder.get());
    }

    // 清除数据源名
    public static void clearDataSource() {
        contextHolder.remove();
    }
}
