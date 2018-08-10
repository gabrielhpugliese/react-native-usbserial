package com.bmateam.reactnativeusbserial;

import com.facebook.react.bridge.Promise;
import com.hoho.android.usbserial.driver.UsbSerialPort;

import java.io.IOException;

public class UsbSerialDevice {
    public UsbSerialPort port;
    private static final int SERIAL_TIMEOUT = 30 * 1000;

    public UsbSerialDevice(UsbSerialPort port) {
        this.port = port;
    }

    public byte[] hexStringToByteArray(String s) {
        byte[] b = new byte[s.length() / 2];
        for (int i = 0; i < b.length; i++) {
            int index = i * 2;
            int v = Integer.parseInt(s.substring(index, index + 2), 16);
            b[i] = (byte) v;
        }
        return b;
    }

    public void writeAsync(String value, Promise promise) {

        if (port != null) {

            try {
                byte b[] = hexStringToByteArray(value);
                port.write(b, SERIAL_TIMEOUT);

                promise.resolve(null);
            } catch (IOException e) {
                promise.reject(e);
            }

        } else {
            promise.reject(getNoPortErrorMessage());
        }
    }

    public void readAsync(Promise promise) {

        if (port != null) {
            // TODO
            promise.resolve(null);
        } else {
            promise.resolve(getNoPortErrorMessage());
        }
    }

    private Exception getNoPortErrorMessage() {
        return new Exception("No port present for the UsbSerialDevice instance");
    }
}
