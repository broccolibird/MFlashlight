package com.uncorkedstudios.flashlight;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.util.Log;

/**
 * Created by Kat on 7/28/15.
 */
public class FlashlightUtil extends CameraManager.TorchCallback {

    private static final String TAG = "FlashlightUtil";

    private static FlashlightUtil instance;

    private CameraManager mCameraManager;

    private boolean mTorchMode = false;


    public static FlashlightUtil getInstance(Context context) {
        if (instance == null) {
            instance = new FlashlightUtil(context);
        }
        return instance;
    }

    public FlashlightUtil(Context context) {
        Log.d(TAG, "new instance");
        mCameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        mCameraManager.registerTorchCallback(this, null);
    }

    public void toggle() {
        Log.d(TAG, "toggle");
        try {
            String[] cameraIds = mCameraManager.getCameraIdList();
            mCameraManager.setTorchMode(cameraIds[0], !mTorchMode);
        } catch (CameraAccessException e) {
            Log.e(TAG, "Camera error", e);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Camera error", e);
        }
    }

    @Override
    public void onTorchModeUnavailable(String cameraId) {
        super.onTorchModeUnavailable(cameraId);
        Log.d(TAG, "onTorchModeUnavailable");

        mTorchMode = false;
    }

    @Override
    public void onTorchModeChanged(String cameraId, boolean enabled) {
        super.onTorchModeChanged(cameraId, enabled);

        Log.d(TAG, "onTorchModeChanged: " + enabled);
        mTorchMode = enabled;
//        mCameraManager.
    }

    public void release() {
        mCameraManager.unregisterTorchCallback(this);
        instance = null;
    }
}
