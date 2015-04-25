package com.tarikzunic.framework.imp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by Tarik on 18.4.2015.
 */
public class CompasHandler implements SensorEventListener {
    float yaw;
    float pitch;
    float roll;
    private float gravity[] = new float[3];
    private float geomagnetic[] = new float[3];
    private float[] R = new float[9];
    private float[] I = new float[9];
    private float[] orientation = new float[3];

    public CompasHandler(Context context) {
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor sensorGravity = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor sensorGeomagnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(this, sensorGravity, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, sensorGeomagnetic, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            gravity = event.values;
        }

        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            geomagnetic = event.values;
        }

        if (SensorManager.getRotationMatrix(R, I, gravity, geomagnetic)) {
            SensorManager.getOrientation(R, orientation);
        }

        yaw = orientation[0];
        pitch = orientation[1];
        roll = orientation[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public float getRoll() {
        return roll;
    }
}
