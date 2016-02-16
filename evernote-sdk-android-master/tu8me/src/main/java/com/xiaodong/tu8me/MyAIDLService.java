package com.xiaodong.tu8me;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class MyAIDLService extends Service {
    public MyAIDLService() {
    }
    class MIBinder extends IMyAidlInterface.Stub{
        @Override
        public void basicTypes() throws RemoteException {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return  new MIBinder();
    }
}
