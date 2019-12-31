
package com.wanderbon.keyevent;

import android.text.Editable;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class RNReactNativeKeyEventModule extends ReactContextBaseJavaModule implements KeyListener {

  private final ReactApplicationContext reactContext;

  public RNReactNativeKeyEventModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNReactNativeKeyEvent";
  }

  @Override
  public int getInputType() {
    return 0;
  }

  @Override
  public boolean onKeyDown(View view, Editable text, int keyCode, KeyEvent event) {
    sendeKeyEvent("onKeyDown", keyCode, event);
    return false;
  }

  @Override
  public boolean onKeyUp(View view, Editable text, int keyCode, KeyEvent event) {
    sendeKeyEvent("onKeyUp", keyCode, event);
    return false;
  }

  @Override
  public boolean onKeyOther(View view, Editable text, KeyEvent event) {
    return false;
  }

  @Override
  public void clearMetaKeyState(View view, Editable content, int states) {

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
}