package com.zxb.service.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.zxb.api.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 简单redis测试
 * @author zxb
 * @create 2020/7/8
 * @since 1.0.0
 */
@Service
@Slf4j
public class RedisServiceImpl implements IRedisService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 新增一条数据
     * @param name 存到redis中的值
     * @return 是否新增成功
     */
    @Override
    public boolean addRedisString(String name) {
        redisTemplate.opsForValue().set("zxb",name);
        //存储5秒
        redisTemplate.opsForValue().set("zqq","27",5, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set("test-1","hello world");
        redisTemplate.opsForValue().set("test-1","redis",6); //6是偏移量相当于后面覆盖
        String s = redisTemplate.opsForValue().get("test-1");//hello redis
        log.info(s);
        return true;
    }

    /**
     * 根据可以删除一条数据
     * @param key 需要删除数据的key
     * @return 删除的数据
     */
    @Override
    public String deleteRedisString(String key) {
        String o = redisTemplate.opsForValue().get(key);
        log.info("需要删除的数据{}",o);
        redisTemplate.delete(key);
        return "删除成功";
    }

    /**
     * 根据redis key 获取数据
     * @param key 查询redis的key
     * @return 返回存储的数据
     */
    @Override
    public String getString(String key) {
        redisTemplate.opsForValue().set(key,"zqq");
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 如果redis是单节点的可以用setnx做简单的分布式锁
     * @param key 查询数据的key
     * @return 判断当前key是否存在
     */
    @Override
    public boolean getKeyFlag(String key) throws Exception {
        //对应的命令是setnx 判断是否已经有值如果有值就返回false
        boolean flag = redisTemplate.opsForValue().setIfAbsent("zxb", "27");
        if (!flag){
            log.info("当前key以及存在");
            throw new Exception("当前key以及存在");
        }
        return flag;
    }

    /**
     * 根据key集合获取批量的值
     * @return 根据key集合返回数据集合
     */
    @Override
    public List<String> getMapKey() {
        List<String> keyList=new ArrayList<>();
        keyList.add("key1");
        keyList.add("key2");
        keyList.add("key3");
        return redisTemplate.opsForValue().multiGet(keyList);
    }

    /**
     * 设置新值并返回旧值
     * @return 返回旧值
     */
    @Override
    public String getAndSet() {
        return redisTemplate.opsForValue().getAndSet("key1", "key1");
    }

    /**
     * 获取序列 相当于原有值和新增值相加
     * @return 返回当前序列值
     */
    @Override
    public Long getIncrement(){
        //可以获取序列号
        Long increment = redisTemplate.opsForValue().increment("increment", 1);
        log.info("比如点赞就+1   {}",increment);
        Long increment1 = redisTemplate.opsForValue().increment("increment", -1);
        log.info("比如取消点赞就-1  {}",increment1);
        return increment;
    }

    /**
     * 后面追加以及截取
     * @return
     */
    @Override
    public String redisStringAppend() {
        redisTemplate.opsForValue().set("append","Hello");
        log.info(redisTemplate.opsForValue().get("append"));
        redisTemplate.opsForValue().append("append"," World");
        log.info(redisTemplate.opsForValue().get("append"));
        log.info("截取值",redisTemplate.opsForValue().get("append"),0,5);
        return "测试成功";
    }


    /**
     * 批量新增数据
     * @return 批量新增是否成功
     */
    @Override
    public boolean addMapKey() {
        Map<String,String> map=new HashMap<>();
        map.put("key1","1");
        map.put("key2","2");
        map.put("key3","3");
        redisTemplate.opsForValue().multiSet(map);
        return true;
    }



    /**
     * 用set新增数据
     * @param valueOne
     * @param valueTwo
     * @return
     */
    @Override
    public boolean addSet(String valueOne, String valueTwo) {
        return false;
    }


    /**
     * 用list插入数据
     * @return 返回插入的数据
     */
    @Override
    public List<String> addListByRedis() {
        List<String> list= ImmutableList.of("c#", "c++", "python", "java", "c#", "c#");
        redisTemplate.opsForList().leftPushAll("list",list);
        List<String> list1 = redisTemplate.opsForList().range("list", 0, -1);
        return list1;
    }


    /**
     * redis中list的基本操作的一些基本操作
     * @return
     */
    @Override
    public boolean listBasicMethod() {
        ListOperations<String, String> stringStringListOperations = redisTemplate.opsForList();
        //截取方法
        List<String> list = redisTemplate.opsForList().range("list", 0, -1);
        log.info("redis的存储数据"+list);
        redisTemplate.opsForList().trim("list",1,-1);
        log.info("上一个方法对数据进行了处理{}",redisTemplate.opsForList().range("list",0,-1));
        //左边添加一个值
        stringStringListOperations.leftPush("list","zxb");
        log.info("左边添加一个值{}",stringStringListOperations.range("list",0,-1));
        //右边添加一个值
        stringStringListOperations.rightPush("list","zqq");
        log.info("右边添加一个值之后{}",stringStringListOperations.range("list",0,-1));
        //左边获取一个值同时删除
        stringStringListOperations.leftPop("list");
        log.info("左边删除一个值之后的集合{}",stringStringListOperations.range("list",0,-1));
        //右边获取一个值同时删除
        log.info("右边获取一个值同时删除之后的集合{}",stringStringListOperations.range("list",0,-1));
        //获取集合的长度
        log.info("集合长度{}",stringStringListOperations.size("list"));
        //添加一个集合
        stringStringListOperations.leftPushAll("list",ImmutableList.of("abc","def"));
        log.info("添加一个集合的集合数据{}",stringStringListOperations.range("list",0,-1));
        //删除一个元素 删除第一次出现的元素 1是从哪个下标开始
        stringStringListOperations.remove("list", 1, "zqq");
        log.info("删除之后的数据{}",stringStringListOperations.range("list",0,-1));
        //根据下标获取值
        log.info("获取下标2的元素{}",stringStringListOperations.index("list",2));
        //可以用list做成队列比如如果没有获取值就一直等待
        //秒数是0的意思是如果该列表中最后一个没有数据就一直阻塞等待有值才通过
        stringStringListOperations.rightPop("list",0,TimeUnit.SECONDS);
        return false;
    }


    /**
     * set的一些基本操作
     * @return
     */
    @Override
    public boolean setBasicMethod() {
        SetOperations<String, String> stringStringSetOperations = redisTemplate.opsForSet();
        //应用常用比如抽奖小程序的应用场景
        //第一步把所有的人录入
        Set<String> set= ImmutableSet.of("a","b","c","d");
        stringStringSetOperations.add("setTest","a","b","c","c");
        //将中奖的人移到中奖的set中
        stringStringSetOperations.move("setTest","a","setTest2");
        //输出中奖人数
        log.info("获取中奖人数{}",stringStringSetOperations.size("setTest2"));
        log.info("展示所有人名{}",stringStringSetOperations.members("setTest2"));
        //输出未中奖人数
        log.info("获取未中奖人数{}",stringStringSetOperations.size("setTest"));
        log.info("展示人名{}",stringStringSetOperations.members("setTest2"));
        Cursor<String> setTest = stringStringSetOperations.scan("setTest", ScanOptions.NONE);
        while(setTest.hasNext()){
            String next = setTest.next();
            log.info("{}未中奖",next);
        }
        Cursor<String> setTest2 = stringStringSetOperations.scan("setTest2", ScanOptions.NONE);
        while (setTest2.hasNext()){
            String next = setTest2.next();
            log.info("{}中奖",next);
        }
        //比如问a是否中奖
        boolean member = stringStringSetOperations.isMember("setTest", "a");
        if (member){
            //存在说明未中奖
            log.info("a{}","未中奖");
        }
        //两个参数的交集
        Set<String> intersect = stringStringSetOperations.intersect("setTest", "setTest1");

        return false;
    }

    /**
     * 应用场景可以用在淘宝购物车
     * hash的一些操作方法
     * @return
     */
    @Override
    public boolean mapBasicMethod() {
        Map<String,Object> mapOne=new HashMap<>();
        mapOne.put("name","zxb");
        mapOne.put("age",27);
        mapOne.put("class","user");

        Map<String, String> mapTwo = new HashMap<>();
        mapTwo.put("name","zqq");
        mapTwo.put("age","28");
        mapTwo.put("class","userTwo");
        HashOperations<String, Object, Object> redisHash = redisTemplate.opsForHash();

        redisHash.putAll("mapOne",mapOne);
        redisHash.putAll("mapTwo",mapTwo);
        //展示hash值mapOne
        log.info("{}",redisHash.entries("mapOne"));
        //展示hash值mapTwo
        log.info("{}",redisHash.entries("mapTwo"));
        //删除map中某一个值
        redisHash.delete("mapOne","class");
        log.info("删除完之后的数据{}",redisHash.entries("mapOne"));
        //判断key是否存在
        log.info("mapOne的class key是否存在{}",redisHash.hasKey("mapOne","class"));
        log.info("{}",redisHash.hasKey("mapOne","name"));
        //获取map中的某一个值
        log.info("获取mapOne中的name值{}",redisHash.get("mapOne","name"));
        //根据key集合获取值集合
        log.info("获取mapOne中的name和age值{}",redisHash.multiGet("mapOne",ImmutableList.of("name","age")));
        //map也可以获取自增
        log.info("age+1 {}",redisHash.increment("mapOne","age",1));
        //获取key集合
        log.info("keys {}",redisHash.keys("mapOne"));
        //获取字的个数
        log.info("size {}",redisHash.size("mapOne"));
        //如果值不存在就添加如果值存在就不添加 就相当于不覆盖
        redisHash.putIfAbsent("mapOne","name","zqq");
        log.info("展示{}",redisHash.entries("mapOne"));
        redisHash.putIfAbsent("mapOne","address","address");
        log.info("新增了一个元素重新展示{}",redisHash.entries("mapOne"));
        //获取字的集合
        log.info("values{}",redisHash.values("mapOne"));
        //使用Cursor在key的hash中迭代，相当于迭代器。
        Cursor<Map.Entry<Object, Object>> mapOne1 = redisHash.scan("mapOne", ScanOptions.NONE);
        while (mapOne1.hasNext()){
            Map.Entry<Object, Object> next = mapOne1.next();
            log.info("key==="+next.getKey());
            log.info("value===="+next.getValue());
        }
        return false;
    }




}
