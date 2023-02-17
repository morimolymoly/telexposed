package com.mmmly.telexposed;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import android.telephony.TelephonyManager;
import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class TelexMain implements IXposedHookLoadPackage {

    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        try
        {
            if ("".equals(lpparam.packageName))// System
            {
                return;
            }
            Log.d("TELEX","handleLoadPackage " + lpparam.packageName);
            XSharedPreferences pre = new XSharedPreferences("com.mmmly.telexposed", TelexActivity.PREF_NAME);
            HookMethod(TelephonyManager.class.getName(),lpparam.classLoader,"getLine1Number",pre.getString(TelexActivity.PHONE_NUM, ""));
            Log.d("TELEX", pre.getString(TelexActivity.PHONE_NUM, ""));

        } catch (Throwable e)
        {
            Log.d("TELEX","failed to change getDeviceId" + e.getMessage());
        }
    }

    public void HookMethod(String className,ClassLoader classLoader,String method,
                           final Object result)
    {
        findAndHookMethod(className, classLoader, method, new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Log.d("TELEX", "HOOKED " + method + result.toString());
                param.setResult(result);
            }

        });
    }
}