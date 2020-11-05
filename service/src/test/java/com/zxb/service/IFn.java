package com.zxb.service;

import java.io.Serializable;

/**
 * @author zxb
 * @date 2018/10/19
 */
@FunctionalInterface
public interface IFn<F,T> extends Serializable {
    T apply(F source);
}
