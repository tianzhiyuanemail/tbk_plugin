package com.tbk.tbk_plugin;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * TbkPlugin
 */
public class TbkPlugin implements MethodCallHandler {
    //
    private static LoginServiceHandler loginServiceHandler;
    private static TradeHandler tradeHandler;

    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "com.tbk.tbk_plugin");
        channel.setMethodCallHandler(new TbkPlugin());

        loginServiceHandler = new LoginServiceHandler(registrar);
        tradeHandler = new TradeHandler(registrar);
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {


        if (call.method.equals("login")) {
            this.loginServiceHandler.handleLogin(call, result);
        } else if (call.method.equals("initLogin")) {
            this.loginServiceHandler.initLogin(result);
        } else if (call.method.equals("isLogin")) {
            this.loginServiceHandler.handleIsLogin(result);
        } else if (call.method.equals("getUser")) {
            this.loginServiceHandler.handleGetUser(result);
        } else if (call.method.equals("logout")) {
            this.loginServiceHandler.handleLogout(result);
        } else if (call.method.equals("initTradeAsync")) {
            this.tradeHandler.initTradeAsync(call, result);
        } else if (call.method.equals("openItemDetail")) {
            this.tradeHandler.openItemDetail(call, result);
        } else if (call.method.equals("openUrl")) {
            this.tradeHandler.openUrl(call, result);
        } else if (call.method.equals("destroy")) {
            this.tradeHandler.destroy();
        } else {
            result.notImplemented();
        }


    }
}
