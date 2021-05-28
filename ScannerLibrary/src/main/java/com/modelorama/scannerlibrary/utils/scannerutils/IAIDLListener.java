package com.modelorama.scannerlibrary.utils.scannerutils;

import android.content.ServiceConnection;

public interface IAIDLListener {
    
    public static final int STATE_UNKNOW = -1;
	public  void  serviceConnected(Object objService, ServiceConnection connection);

}
