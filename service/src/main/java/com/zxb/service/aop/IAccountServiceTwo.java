package com.zxb.service.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理
 * @author zxb
 * @date 2018/10/19
 */
public interface IAccountServiceTwo {
    //主业务逻辑转账
    void transfer();
}

class AccountServiceTwoImpl implements IAccountServiceTwo{

    @Override
    public void transfer() {
        System.out.println("调用dao层 完成转账主业务");
    }
}

class AccountAdvice implements InvocationHandler {

    private IAccountServiceTwo accountServiceTwo;

    public AccountAdvice(IAccountServiceTwo accountServiceTwo){
        this.accountServiceTwo=accountServiceTwo;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        return method.invoke(accountServiceTwo,args);
    }

    private void before(){
        System.out.println("前置输出");
    }

    public static void main(String[] args) {
        //创建目标对象
        IAccountServiceTwo accountServiceTwo=new AccountServiceTwoImpl();
        //创建代理对象
        IAccountServiceTwo proxy = (IAccountServiceTwo)Proxy.newProxyInstance(accountServiceTwo.getClass().getClassLoader(),accountServiceTwo.getClass().getInterfaces(), new AccountAdvice(accountServiceTwo));
        proxy.transfer();
    }
}