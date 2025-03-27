public class Test{
    public static void main(String[] args) {
        Communicator comm = new Communicator("localhost", 12345, true);
        DataProcessor dataProcessor = new DataProcessor();
        comm.start();
        System.out.println("Is connected: " + comm.getIsConnected());
        System.out.println("Input buffer: " + comm.getInputBuffer());
        comm.setOutputBuffer(new byte[32]);

        delay(500);
        System.out.println("trying to write in digital outpus");
        dataProcessor.setDigitalOutputs(new boolean[]{false, false, false, false, false, false, false, false});
        dataProcessor.setDigitalOutputByIndex(0, true);
        dataProcessor.setDigitalOutputByIndex(1, true);
        comm.setOutputBuffer(dataProcessor.getOutputBuffer());
        delay(1000);
        System.out.println("trying to write in analog outputs");
        dataProcessor.setAnalogOutputs(new int[]{0, 0});
        dataProcessor.setAnalogOutputByIndex(100, 0);
        comm.setOutputBuffer(dataProcessor.getOutputBuffer());
        delay(1000);
        System.out.println("trying to read inputs");
        dataProcessor.setInputState(comm.getInputBuffer());
        System.out.println("Digital inputs: " + dataProcessor.getDigitalInputs());
        System.out.println("Analog inputs: " + dataProcessor.getAnalogInputs());
        comm.stop();
    }
}