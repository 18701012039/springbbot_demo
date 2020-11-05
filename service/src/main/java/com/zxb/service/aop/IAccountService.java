package com.zxb.service.aop;

/**
 * 静态代理
 * @author zxb
 * @date 2018/10/19
 */
public interface IAccountService {

    public void transfer();
}

/**
 * 转账业务
 */
class AccountServiceImpl implements IAccountService{

    @Override
    public void transfer() {
        System.out.println("调用dao层 实现业务");
    }
}

class AccountProxy implements IAccountService{

    //目标类
    private IAccountService accountService;

    public AccountProxy(IAccountService accountService){
        this.accountService=accountService;
    }

    @Override
    public void transfer() {
        before();
        accountService.transfer();
    }

    private void before(){
        System.out.println("前置输出");
    }
    public static void main(String[] args) {
        IAccountService accountService=new AccountServiceImpl();
        AccountProxy ac=new AccountProxy(accountService);
        ac.transfer();

    }

}

