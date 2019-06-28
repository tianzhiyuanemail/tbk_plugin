package com.tbk.tbk_plugin;

import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.trade.biz.context.AlibcResultType;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.MethodChannel;

public class TradeCallback implements AlibcTradeCallback {
    MethodChannel.Result result;
    Integer openResultCode;
    @Override
    public void onTradeSuccess(AlibcTradeResult tradeResult) {
        if (tradeResult == null) {
            Map<String,Object> map = new HashMap<>();
            map.put("openResultCode",openResultCode);
            map.put("platform",Constants.keyAndroid);
            map.put("result",false);
            map.put("errorCode",-999);
            map.put("errorMessage","tradeResult is null");

            result.success(map);
        }
        if (tradeResult.resultType == AlibcResultType.TYPEPAY){

            Map<String,Object> map = new HashMap<>();
            map.put("openResultCode",openResultCode);
            map.put("platform",Constants.keyAndroid);
            map.put("result",true);
            map.put("tradeResultType",0);
            map.put("paySuccessOrders",tradeResult.payResult.paySuccessOrders);
            map.put("payFailedOrders",tradeResult.payResult.payFailedOrders);
            result.success(map);
        }else if (tradeResult.resultType == AlibcResultType.TYPECART){

            Map<String,Object> map = new HashMap<>();
            map.put("openResultCode",openResultCode);
            map.put("platform",Constants.keyAndroid);
            map.put("result",true);
            map.put("tradeResultType",1);
            result.success(map);
        }else {
            Map<String,Object> map = new HashMap<>();
            map.put("openResultCode",openResultCode);
            map.put("platform",Constants.keyAndroid);
            map.put("result",true);
            map.put("tradeResultType",-1);
            result.success(map);

        }

    }

    @Override
    public void onFailure(int i, String s) {
        Map<String,Object> map = new HashMap<>();
        map.put("openResultCode",openResultCode);
        map.put("platform",Constants.keyAndroid);
        map.put("result",false);
        map.put("errorCode",i);
        map.put("errorMessage",s);

        result.success(map);
    }

    public TradeCallback(MethodChannel.Result result) {
        this.result = result;
    }

    public TradeCallback(MethodChannel.Result result, Integer openResultCode) {
        this.result = result;
        this.openResultCode = openResultCode;
    }
}
