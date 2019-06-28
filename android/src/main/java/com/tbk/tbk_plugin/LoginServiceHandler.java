package com.tbk.tbk_plugin;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.ActivityResultListener;
import io.flutter.plugin.common.PluginRegistry.Registrar;


public  class LoginServiceHandler {
    private  Registrar registrar;

    public  void initLogin(final Result result) {
        AlibcLogin.getInstance().init((new AlibcLoginCallback() {
            @Override
            public void onSuccess(int i) {
                Toast.makeText(registrar.activity().getApplication(), "登录成功 ",
                        Toast.LENGTH_LONG).show();
                //获取淘宝用户信息
                Log.i("", "获取淘宝用户信息: " + AlibcLogin.getInstance().getSession());

                Map<String, String> userMap = buildUser();
                result.success(new ResultUtil());
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(registrar.activity().getApplication(), "登录失败 ", Toast.LENGTH_LONG).show();
                result.success(new ResultUtil(false, i, s));
            }
        }));
    }

    public  void handleLogin(MethodCall call, final Result result) {
        this.registrar.addActivityResultListener(new ActivityResultListener() {
            @Override
            public boolean onActivityResult(int i, int i1, Intent intent) {
                return false;
            }
        });
        AlibcLogin alibcLogin = AlibcLogin.getInstance();
        alibcLogin.showLogin((new AlibcLoginCallback() {
            @Override
            public void onSuccess(int i) {
                Map<String, String> userMap = buildUser();
                result.success(new ResultUtil(userMap));
            }

            @Override
            public void onFailure(int i, String s) {
                result.success(new ResultUtil(false, i, s));
            }
        }));
    }

    public void handleIsLogin(Result result) {
        AlibcLogin alibcLogin = AlibcLogin.getInstance();
        result.success(alibcLogin.isLogin());
    }

    public void handleGetUser(Result result) {
        Map<String, String> userMap = new HashMap<>();
        AlibcLogin alibcLogin = AlibcLogin.getInstance();
        if (alibcLogin.isLogin()) {
            userMap = buildUser();
        }

        result.success(new ResultUtil(userMap));
    }

    public void handleLogout(Result result) {
        this.registrar.addActivityResultListener(new ActivityResultListener() {
            @Override
            public boolean onActivityResult(int i, int i1, Intent intent) {
                return false;
            }
        });
        AlibcLogin alibcLogin = AlibcLogin.getInstance();
        alibcLogin.logout((new AlibcLoginCallback() {
            @Override
            public void onSuccess(int i) {
                Toast.makeText(registrar.activity().getApplication(), "退出成功 ",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(registrar.activity().getApplication(), "退出失败 ",
                        Toast.LENGTH_LONG).show();
            }
        }));
    }


    private Map<String, String> buildUser() {

        //获取淘宝用户信息
        Map<String, String> userMap = new HashMap<>();
        AlibcLogin alibcLogin = AlibcLogin.getInstance();

        userMap.put("avatarUrl", alibcLogin.getSession().avatarUrl);
        userMap.put("nick", alibcLogin.getSession().nick);
        userMap.put("openId", alibcLogin.getSession().openId);
        userMap.put("openSid", alibcLogin.getSession().openSid);
        userMap.put("userId", alibcLogin.getSession().userid);
        userMap.put("topAccessToken", alibcLogin.getSession().topAccessToken);
        userMap.put("topAuthCode", alibcLogin.getSession().topAuthCode);
        userMap.put("topExpireTime", alibcLogin.getSession().topExpireTime);

        return userMap;
    }

    public LoginServiceHandler(Registrar registrar) {
        this.registrar = registrar;
    }
}
