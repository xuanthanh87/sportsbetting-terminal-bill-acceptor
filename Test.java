import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

class SimpleRead implements Runnable, SerialPortEventListener {
    static CommPortIdentifier portId;
   static Enumeration portList;
    static InputStream inputStream;
    static SerialPort serialPort;
    Thread            readThread;

    /**
     * Method declaration
     *
     *
     * @param args
     *
     * @see
     */
    public static void main(String[] args) throws IOException {
        boolean           portFound = false;
        String            defaultPort = "COM3";

        if (args.length > 0) {
            defaultPort = args[0];
        }

        portList = CommPortIdentifier.getPortIdentifiers();




        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                System.out.printf("\n=== " + portId.getName() + "/" + portId.getPortType() + "/" + portId.getCurrentOwner());
                if (portId.getName().equals(defaultPort)) {
                    System.out.println("Found port: " + defaultPort);
                   // jTextArea1.setText("Found port: "+defaultPort);
                    portFound = true;
                    SimpleRead reader = new SimpleRead();

                }
            }
        }
        if (!portFound) {
          //  jTextArea1.setText("port " + defaultPort + " not found.");
        }

    }

    /**
     * Constructor declaration
     *
     *
     * @see
     */
    public SimpleRead() throws IOException {
        try {
            serialPort = (SerialPort) portId.open("COM3", 2000);
        } catch (Exception e) {}

        try {
            inputStream = serialPort.getInputStream();

            byte[] readBuffer = new byte[20];


                while (inputStream.available() > 0) {
                    int numBytes = inputStream.read(readBuffer);
                }

                //   jTextArea1.setText(new String(readBuffer));
                System.out.printf("ok!! " + new String(readBuffer));

        } catch (IOException e) {}

        try {
            serialPort.addEventListener(this);
        } catch (Exception e) {}

        serialPort.notifyOnDataAvailable(true);

        try {
            serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
        } catch (Exception e) {}

        readThread = new Thread(this);

        readThread.start();
    }

    /**
     * Method declaration
     *
     *
     * @see
     */
    public void run() {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {}
    }

    /**
     * Method declaration
     *
     *
     * @param event
     *
     * @see
     */
    public void serialEvent(SerialPortEvent event) {
        switch (event.getEventType()) {

            case SerialPortEvent.BI:

            case SerialPortEvent.OE:

            case SerialPortEvent.FE:

            case SerialPortEvent.PE:

            case SerialPortEvent.CD:

            case SerialPortEvent.CTS:

            case SerialPortEvent.DSR:

            case SerialPortEvent.RI:

            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;

            case SerialPortEvent.DATA_AVAILABLE:
                byte[] readBuffer = new byte[20];

                try {
                    while (inputStream.available() > 0) {
                        int numBytes = inputStream.read(readBuffer);
                    }

                 //   jTextArea1.setText(new String(readBuffer));
                    System.out.printf(new String(readBuffer));
                } catch (IOException e) {}

                break;
        }
    }

}