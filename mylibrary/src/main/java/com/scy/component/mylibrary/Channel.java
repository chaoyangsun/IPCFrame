package com.scy.component.mylibrary;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.TextureView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scy.component.mylibrary.annotation.ServiceId;
import com.scy.component.mylibrary.model.Parameters;
import com.scy.component.mylibrary.model.Request;
import com.scy.component.mylibrary.model.Response;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Channel {

    /**
     * 单例
     */
    private static volatile Channel instance;

    //已经绑定过的
    private ConcurrentHashMap<Class<? extends IPCService>, Boolean> mBinds =
            new ConcurrentHashMap<>();
    //正在绑定的
    private ConcurrentHashMap<Class<? extends IPCService>, Boolean> mBinding =
            new ConcurrentHashMap<>();
    //已经绑定的服务对应的ServiceConnect
    private final ConcurrentHashMap<Class<? extends IPCService>, IPCServiceConection> mServiceConnections =
            new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Class<? extends IPCService>, IIPCService> mBinders =
            new ConcurrentHashMap<>();

    private Channel() {
    }

    public static Channel getInstance() {
        if (null == instance) {
            synchronized (Channel.class) {
                if (null == instance) {
                    instance = new Channel();
                }
            }
        }
        return instance;
    }

    private Gson gson = new Gson();
    public void bind(Context context, String packageName, Class<? extends  IPCService> service){
        IPCServiceConection ipcServiceConection;
        //是否已经绑定
        Boolean isBound = mBinds.get(service);
        if (isBound != null && isBound){
            return;
        }
        //是否正在绑定
        Boolean isBinding = mBinding.get(service);
        if (isBinding != null && isBinding) {
            return;
        }
        //要绑定了
        mBinding.put(service, true);
        ipcServiceConection = new IPCServiceConection(service);
        mServiceConnections.put(service, ipcServiceConection);
        Intent intent;
        if (TextUtils.isEmpty(packageName)){
            intent = new Intent(context, service);
        }else {
            intent = new Intent();
            intent.setClassName(packageName, service.getName());
        }
        context.bindService(intent, ipcServiceConection, Context.BIND_AUTO_CREATE);
        Log.e("--==", "bind");
    }

    public void unbind(Context context, Class<? extends IPCService> service){
        Boolean bound = mBinds.get(service);
        if (bound != null && bound){
            IPCServiceConection conection = mServiceConnections.get(service);
            if (conection != null){
                context.unbindService(conection);
            }
            mBinds.put(service, false);
        }
    }

    private class  IPCServiceConection implements ServiceConnection{
        private final Class<? extends IPCService> mService;

        public IPCServiceConection(Class<? extends IPCService> mService) {
            this.mService = mService;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IIPCService iipcService = IIPCService.Stub.asInterface(service);
            mBinders.put(mService, iipcService);
            mBinds.put(mService, true);
            mBinding.remove(mService);
            Log.e("--==", "connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBinders.remove(mService);
            mBinds.remove(mService);
            Log.e("--==", "Disconnected");
        }
    }

    public Response send(int type,Class<? extends IPCService> service, Class<?> classType,
                         String methodName, Object[] parameters) {
        // ipcService: 绑定的服务中onbind返回的binder对象
        IIPCService iipcService = mBinders.get(service);
        if (iipcService == null){
            //没有绑定服务
            return new Response(null, false);
        }
        //发送请求给服务器
        ServiceId annotation = classType.getAnnotation(ServiceId.class);
        String serviceId = annotation.value();
        Request request = new Request(type, serviceId, methodName, makeParameters(parameters));
        try {
            return iipcService.send(request);
        } catch (RemoteException e) {
            return new Response(null, false);
        }
    }

    //将参数制作为Parameters
//    private Parameters[] makeParameters(Object[] parameters) {
//        Parameters[] collect = (Parameters[]) Arrays.stream(parameters)
//                .map(p -> new Parameters(p.getClass().getName(), gson.toJson(p)))
//                .toArray();
//        return collect;
//    }

//    //将参数制作为Parameters
    private Parameters[] makeParameters(Object[] parameters) {
        Parameters[] p;
        if (null != parameters) {
            p = new Parameters[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                Object object = parameters[i];
                p[i] = new Parameters(object.getClass().getName(), gson.toJson(object));
            }
        } else {
            p = new Parameters[0];
        }
        return p;
    }
}
