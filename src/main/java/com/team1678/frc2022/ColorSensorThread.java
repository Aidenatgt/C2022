package com.team1678.frc2022;

import java.util.concurrent.BlockingQueue;

import com.team1678.lib.drivers.REVColorSensorV3Wrapper;
import com.team1678.lib.drivers.REVColorSensorV3Wrapper.ColorSensorData;

public class ColorSensorThread extends Thread{

    private REVColorSensorV3Wrapper colorSensor;
    private BlockingQueue<Integer> commandQueue;
    private BlockingQueue<ColorSensorData> outputQueue;
    private ColorSensorData mRawColorData;

    public ColorSensorThread(REVColorSensorV3Wrapper colorSensor, BlockingQueue<Integer> commandQueue,
    BlockingQueue<ColorSensorData> outputQueue) {
        colorSensor = this.colorSensor;
        commandQueue = this.commandQueue;
        outputQueue = this.outputQueue;
    }

    public void run() {
        try {
            while (true) {
                System.out.println("Running Color Sensor Thread");
                Integer command = commandQueue.take();
                mRawColorData = colorSensor.getLatestReading();
                outputQueue.put(mRawColorData);
            }
            

        } catch (Exception e) {
            System.out.println("Exception is caught");
        }
    }

}
