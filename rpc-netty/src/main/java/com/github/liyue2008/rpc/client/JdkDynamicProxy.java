package com.github.liyue2008.rpc.client;

import com.github.liyue2008.rpc.client.stubs.AbstractStub;
import com.github.liyue2008.rpc.client.stubs.RpcRequest;
import com.github.liyue2008.rpc.serialize.SerializeSupport;
import com.github.liyue2008.rpc.transport.Transport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author: zhangxuelei
 * @date: 2020/5/7 16:30
 */
public class JdkDynamicProxy extends AbstractStub implements InvocationHandler {

    private Class clz;

    public JdkDynamicProxy(Transport transport, Class serviceClass) {
        this.transport = transport;
        this.clz = serviceClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return SerializeSupport.parse(invokeRemote(new RpcRequest(clz.getCanonicalName(), method.getName(), SerializeSupport.serialize(args))));
    }
}
