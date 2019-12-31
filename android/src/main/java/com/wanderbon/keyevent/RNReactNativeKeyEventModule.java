
package com.wanderbon.keyevent;

import android.text.Editable;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import javax.net.ssl.KeyManager;

public class RNReactNativeKeyEventModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNReactNativeKeyEventModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;

    View mView = new View(reactContext);

    mView.setOnKeyListener(new View.OnKeyListener() {

      @Override
      public boolean onKey(View v, int keyCode, KeyEvent event) {
        Log.i("onKey", keyCode + " | " + event.getDisplayLabel());
        sendeKeyEvent("onKey", keyCode, event);
        return false;
      }
    });

    mView.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        Log.i("onKey_onTouch", event.getAction() + "");
        return false;
      }
    });
  }

  @Override
  public String getName() {
    return "RNReactNativeKeyEvent";
  }

  private void sendeKeyEvent(String eventName, int keyCode, KeyEvent event) {
    WritableMap params = Arguments.createMap();

    params.putInt("keyCode", keyCode);
    params.putInt("scanCode", event.getScanCode());
    params.putInt("unicodeChar", event.getUnicodeChar());
    params.putString("Number", String.valueOf(event.getNumber()));
    params.putBoolean("isShiftPressed", event.isShiftPressed());
    params.putBoolean("isLongPress", event.isLongPress());
    params.putBoolean("isCapsLockOn", event.isCapsLockOn());
    params.putString("displayLabel", String.valueOf(event.getDisplayLabel()));

    reactContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit(eventName, params);
  }

  @Override
  public void addView(View view, ViewGroup.LayoutParams params) {

  }

  @Override
  public void updateViewLayout(View view, ViewGroup.LayoutParams params) {

  }

  @Override
  public void removeView(View view) {

  }
}