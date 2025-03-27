package com.itb.tweezer.utils;

public class DataProcessor {
    // Data buffers for exchange
    private byte[] outputBuffer = new byte[32];
    private byte[] inputBuffer = new byte[32];

    // Information extracted & sent with the buffers in communicator
    private boolean[] digitalInputs = new boolean[8];
    private boolean[] digitalOutputs = new boolean[8];
    private int[] analogInputs = new int[2];
    private int[] analogOutputs = new int[2];

    private int model = 0;
    private int[] version = new int[2];

    /* **********************************
    * READ STATE                        *
    * Process the input buffer          *
    *************************************/
    public void setInputState(byte[] data) {
        if (data == null || data.length != 32) {
            throw new IllegalArgumentException("The data buffer must be an array of 32 bytes");
        }
        this.inputBuffer = data;
        updateDigitalInputs();
        updateAnalogInputs();
        updateModel();
        updateVersion();
    }

    /* **********************************
    * READ OPERATIONS                   *
    * Read methods to update the state  *
    *************************************/
    private void updateDigitalInputs() {
        for (int i = 0; i < 8; i++) {
            digitalInputs[i] = (inputBuffer[0] & (1 << i)) != 0;
        }
    }

    private void updateAnalogInputs() {
        analogInputs[0] = inputBuffer[2];
        analogInputs[1] = inputBuffer[3];
    }

    private void updateModel() {
        model = inputBuffer[29];
    }

    private void updateVersion() {
        version[0] = inputBuffer[30];
        version[1] = inputBuffer[31];
    }


    /* **********************************
    * WRITE OPERATIONS                  *
    * Write methods to update the buffer*
    *************************************/

    public void setDigitalOutputs(boolean[] digitalOutputs) {
        if (digitalOutputs == null || digitalOutputs.length != 8) {
            throw new IllegalArgumentException("The digitalOutputs buffer must be an array of 8 booleans");
        }
        for (int i = 0; i < 8; i++) {
            this.digitalOutputs[i] = digitalOutputs[i];
        }
        updateDigitalOutputs();
    }

    public void setDigitalOutputByIndex(boolean value, int index) {
        digitalOutputs[index] = value;
        updateDigitalOutputs();
    }

    public void setAnalogOutputs(int[] analogOutputs) {
        if (analogOutputs == null || analogOutputs.length != 2) {
            throw new IllegalArgumentException("The analogOutputs buffer must be an array of 2 integers");
        }
        for (int i = 0; i < 2; i++) {
            this.analogOutputs[i] = analogOutputs[i];
        }
        updateAnalogOutputs();
    }

    public void setAnalogOutputByIndex(int value, int index) {
        analogInputs[index] = value;
        updateAnalogOutputs();
    }

    public void updateAnalogOutputs() {
        outputBuffer[2] = (byte) analogOutputs[0];
        outputBuffer[3] = (byte) analogOutputs[1];
    }

    public void updateDigitalOutputs() {
        for (int i = 0; i < 8; i++) {
            if (digitalOutputs[i]) {
                outputBuffer[0] |= (1 << i);
            } else {
                outputBuffer[0] &= ~(1 << i);
            }
        }
    }


    /* **********************************
    *               GETTERS             *
    *************************************/
    public boolean[] getDigitalInputs() {
        return digitalInputs;
    }

    public boolean[] getDigitalOutputs() {
        return digitalOutputs;
    }

    public int[] getAnalogInputs() {
        return analogInputs;
    }

    public int[] getAnalogOutputs() {
        return analogOutputs;
    }

    public int getModel() {
        return model;
    }

    public int[] getVersion() {
        return version;
    }

    public byte[] getOutputBuffer() {
        return outputBuffer;
    }
}
