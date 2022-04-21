package com.github.liyue2008.rpc.client;

import com.github.liyue2008.rpc.transport.Transport;

import java.lang.reflect.Proxy;

/**
 * @author: zhangxuelei
 * @date: 2020/5/7 16:32
 */
public class JdkDynamicStubFactory implements StubFactory {

    @Override
    public <T> T createStub(Transport transport, Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(serviceClass.getClassLoader(), new Class<?>[] {serviceClass} ,
                new JdkDynamicProxy(transport, serviceClass));
    }
}
