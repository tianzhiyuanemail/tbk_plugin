package com.tbk.tbk_plugin;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcDetailPage;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.alibaba.baichuan.trade.biz.applink.adapter.AlibcFailModeType;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;
import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;


public final class TradeHandler {
    private final Registrar registry;

    public final void initTradeAsync(MethodCall call, final Result result) {
//        String version = call.argument("version");

//        if (version != null) {
//            AlibcTradeSDK.setISVVersion(version);
//        }

//        Boolean debuggable = call.argument("debuggable");
//        if (debuggable) {
//            AlibcTradeCommon.turnOnDebug();
//            AlibcTradeBiz.turnOnDebug();
//            MemberSDK.turnOnDebug();
//        } else {
//            AlibcTradeCommon.turnOffDebug();
//            AlibcTradeBiz.turnOffDebug();
//            MemberSDK.turnOnDebug();
//        }

        AlibcTradeSDK.asyncInit(this.registry.activity().getApplication(), (new AlibcTradeInitCallback() {
            public void onSuccess() {
                Map<String, Object> map = new HashMap<>();
                map.put("platform", Constants.keyAndroid);
                map.put("result", true);

                result.success(map);
            }

            public void onFailure(int code, String msg) {
                Map<String, Object> map = new HashMap<>();
                map.put("platform", Constants.keyAndroid);
                map.put("result", false);
                map.put("errorCode", code);
                map.put("errorMessage", msg);

                result.success(map);
            }
        }));
    }

    public void destroy() {
        AlibcTradeSDK.destory();
    }

    public void openItemDetail(MethodCall call, Result result) {
        String itemID = call.argument("itemID");
        this.openByPage(new AlibcDetailPage(itemID), call, result);
    }

    public void openUrl(MethodCall call, Result result) {
        String pageUrl = call.argument("pageUrl");
        this.openByPage(new AlibcPage(pageUrl), call, result);
    }

    private void openByPage(AlibcBasePage alibcBasePage, MethodCall call, final Result result) {
        Integer openResultCode = -1;
        AlibcShowParams alibcShowParams = this.buildShowParams(call);
        AlibcTaokeParams alibcTaokeParams = this.buildTaoKeParams(call);
        Map extParams = call.argument("extParams");

        openResultCode = AlibcTrade.show(
                this.registry.activity(),
                alibcBasePage,
                alibcShowParams,
                alibcTaokeParams,
                extParams,
                new TradeCallback(result, openResultCode)
        );

    }

    private AlibcTaokeParams buildTaoKeParams(MethodCall call) {
        AlibcTaokeParams taoKe = new AlibcTaokeParams();
        Map taoKeParams = call.argument("taoKeParams");

        if (taoKeParams != null) {
            taoKe.pid = taoKeParams.get("taoKeParamsPid").toString();
            taoKe.subPid = taoKeParams.get("taoKeParamsSubPid").toString();
            taoKe.unionId = taoKeParams.get("taoKeParamsUnionId").toString();
            taoKe.adzoneid = taoKeParams.get("taoKeParamsAdzoneId").toString();
            if (taoKeParams.get("taoKeParamsExtParams") != null) {
                taoKe.extraParams = ((HashMap) taoKeParams.get("taoKeParamsExtParams"));
            } else {
                taoKe.extraParams = new HashMap();
            }
        }
        return taoKe;
    }

    private AlibcShowParams buildShowParams(MethodCall call) {
        Integer openType = call.argument("openType");
        String backUrl = call.argument("backUrl");
        Integer openFailedMode = call.argument("openNativeFailedMode");
        String schemeType = call.argument("schemeType");

        if (schemeType == null) {
            schemeType = "tmall_scheme";
        }


        AlibcShowParams alibcShowParams = new AlibcShowParams(this.intToOpenType(openType), false);
        alibcShowParams.setBackUrl(backUrl);
        alibcShowParams.setClientType(schemeType);
        alibcShowParams.setNativeOpenFailedMode(this.intToFailedMode(openFailedMode));
        return alibcShowParams;
    }

    private OpenType intToOpenType(int type) {
        // AUTO, NATIVE, H5
        OpenType openType;
        switch (type) {
            case 0:
                openType = OpenType.Auto;
                break;
            case 1:
                openType = OpenType.Native;
                break;
            default:
                openType = OpenType.H5;
        }

        return openType;
    }

    private AlibcFailModeType intToFailedMode(int type) {
        //JUMP_H5, JUMP_DOWNLOAD, JUMP_BROWSER, NONE
        AlibcFailModeType modeType;
        switch (type) {
            case 0:
                modeType = AlibcFailModeType.AlibcNativeFailModeJumpH5;
                break;
            case 1:
                modeType = AlibcFailModeType.AlibcNativeFailModeJumpDOWNLOAD;
                break;
            case 2:
                modeType = AlibcFailModeType.AlibcNativeFailModeJumpBROWER;
                break;
            case 3:
                modeType = AlibcFailModeType.AlibcNativeFailModeNONE;
                break;
            default:
                modeType = AlibcFailModeType.AlibcNativeFailModeJumpH5;
        }

        return modeType;
    }

    public TradeHandler(Registrar registry) {
        super();
        this.registry = registry;
    }
}
