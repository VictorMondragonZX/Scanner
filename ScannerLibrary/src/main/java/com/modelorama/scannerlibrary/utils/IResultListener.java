package com.modelorama.scannerlibrary.utils;


public interface IResultListener<A, B, C> {
    void onDataResult(A isSuccessful, B result, C message);
}
