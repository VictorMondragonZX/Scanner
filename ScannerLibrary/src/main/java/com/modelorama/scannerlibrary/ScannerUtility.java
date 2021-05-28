package com.modelorama.scannerlibrary;

import android.content.*;
import android.os.*;

import com.cloudpos.scanserver.aidl.*;
import com.modelorama.scannerlibrary.utils.*;
import com.modelorama.scannerlibrary.utils.scannerutils.*;


public class ScannerUtility implements IAIDLListener {


    private IScanService scanService = null;
    private ServiceConnection scanConn = null;
    private IAIDLListener listener;
    private Context context;


    public ScannerUtility(Context context) {
        this.context = context;
        listener = ScannerUtility.this;
        AidlController.getInstance().startScanService(context, listener);
    }


    @Override
    public void serviceConnected(Object objService, ServiceConnection connection) {
        if (objService instanceof IScanService) {
            scanService = (IScanService) objService;
            scanConn = connection;
            android.util.Log.d("SCANNER", "Ya inicio el scanner");
        }
    }

    public void startScan(IResultListener<Boolean, String, String> scanResultListener) {

        ScanParameter parameter = new ScanParameter();
        try {
            scanService.startScan(parameter, new IScanCallBack.Stub() {
                @Override
                public void foundBarcode(ScanResult result) {
                    if (result.getResultCode() == ScanResult.SCAN_SUCCESS) {
                        scanResultListener.onDataResult(true, result.getText(), null);
                    } else {
                        scanResultListener.onDataResult(false, null, "Error al utilizar el scanner");
                    }

                    stopScan();

                }
            });
        } catch (RemoteException e) {
            android.util.Log.d("ModeloramaApp", "synchronizationScan " + e.toString());
            scanResultListener.onDataResult(false, null, "Error general en el scanner");
        }
    }

    public void stopScan() {
        if (scanService != null) {
            try {
                android.util.Log.d("ModeloramaApp", "Intentar detener el scanner");
                this.scanService.stopScan();
            } catch (RemoteException e) {
                android.util.Log.d("ModeloramaApp", "stopScan: " + e.toString());
                e.printStackTrace();
            }
        }
    }

    public void destroyScan() {
        if (scanService != null) {
            try {
                this.scanService.stopScan();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            this.scanService = null;
            context.unbindService(scanConn);
        }
    }
}
