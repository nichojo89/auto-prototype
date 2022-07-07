package com.example.carapihelloworld;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.car.Car;
import android.car.VehiclePropertyIds;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.property.CarPropertyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TelemetryFragment extends Fragment {
    private CarPropertyManager mCarPropertyManager;

    private static final String TAG = "CarApiHelloWorld";
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 1;

    TextView mGearTextView;
    TextView mSpeedTextView;
    TextView mBatteryTextView;
    TextView mFuelDoorTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initCarPropertyManager();
        registerCarPropertyManagerCBs();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_telemetry, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        mGearTextView = getView().findViewById(R.id.gear_textview);
        mSpeedTextView = getView().findViewById(R.id.speed_textview);
        mBatteryTextView = getView().findViewById(R.id.battery_textview);
        mFuelDoorTextView = getView().findViewById(R.id.fuel_door_textview);
    }

    private void initCarPropertyManager() {
        mCarPropertyManager = (CarPropertyManager) Car.createCar(getContext()).getCarManager(Car.PROPERTY_SERVICE);
    }

    private void registerCarPropertyManagerCBs() {
        Log.d(TAG, "Test CarPropertyManager callbacks:");
        mCarPropertyManager.registerCallback(new CarPropertyManager.CarPropertyEventCallback() {
            @Override
            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                Log.d(TAG, "GEAR_SELECTION: onChangeEvent(" + carPropertyValue.getValue() + ")");

                String gear = carPropertyValue.getValue().toString();
                mGearTextView.setText("The vehicle is in "+gear+"th gear");
            }

            @Override
            public void onErrorEvent(int propId, int zone) {
                Log.d(TAG, "GEAR_SELECTION: onErrorEvent(" + propId + ", " + zone + ")");
            }
        }, VehiclePropertyIds.GEAR_SELECTION, CarPropertyManager.SENSOR_RATE_NORMAL);

        mCarPropertyManager.registerCallback(new CarPropertyManager.CarPropertyEventCallback() {
            @Override
            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                Log.d(TAG, "PERF_VEHICLE_SPEED: onChangeEvent(" + carPropertyValue.getValue() + ")");
                mSpeedTextView.setText("The vehicles speed is " + carPropertyValue.getValue().toString() + "mph");
            }

            @Override
            public void onErrorEvent(int propId, int zone) {
                Log.d(TAG, "PERF_VEHICLE_SPEED: onErrorEvent(" + propId + ", " + zone + ")");
            }
        }, VehiclePropertyIds.PERF_VEHICLE_SPEED, CarPropertyManager.SENSOR_RATE_NORMAL);

        mCarPropertyManager.registerCallback(new CarPropertyManager.CarPropertyEventCallback() {
            @Override
            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                Log.d(TAG, "EV_BATTERY_LEVEL: onChangeEvent(" + carPropertyValue.getValue() + ")");
                mBatteryTextView.setText("The cars battery charge is " + carPropertyValue.getValue().toString() + " volts");
            }

            @Override
            public void onErrorEvent(int propId, int zone) {
                Log.d(TAG, "EV_BATTERY_LEVEL: onErrorEvent(" + propId + ", " + zone + ")");
            }
        }, VehiclePropertyIds.EV_BATTERY_LEVEL, CarPropertyManager.SENSOR_RATE_ONCHANGE);

        mCarPropertyManager.registerCallback(new CarPropertyManager.CarPropertyEventCallback() {
            @Override
            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                Log.d(TAG, "FUEL_DOOR_OPEN: onChangeEvent(" + carPropertyValue.getValue() + ")");
                Boolean open = Boolean.valueOf(carPropertyValue.getValue().toString());
                if(open){
                    mFuelDoorTextView.setText("Your fuel door is open");
                } else {
                    mFuelDoorTextView.setText("Your fuel door is closed");
                }
            }

            @Override
            public void onErrorEvent(int propId, int zone) {
                Log.d(TAG, "FUEL_DOOR_OPEN: onErrorEvent(" + propId + ", " + zone + ")");
            }
        }, VehiclePropertyIds.FUEL_DOOR_OPEN, CarPropertyManager.SENSOR_RATE_ONCHANGE);
    }
}