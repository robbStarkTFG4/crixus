/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modbustest;

/**
 *
 * @author NORE
 */
import com.serotonin.io.serial.*;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusLocator;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.base.SlaveAndRange;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.code.RegisterRange;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.msg.ReadCoilsRequest;
import com.serotonin.modbus4j.msg.ReadCoilsResponse;
import com.serotonin.modbus4j.msg.ReadDiscreteInputsRequest;
import com.serotonin.modbus4j.msg.ReadDiscreteInputsResponse;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersRequest;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersResponse;
import com.serotonin.modbus4j.msg.WriteCoilRequest;
import com.serotonin.modbus4j.msg.WriteCoilResponse;
import com.serotonin.modbus4j.msg.WriteRegistersRequest;
import com.serotonin.modbus4j.msg.WriteRegistersResponse;

import java.util.*;

public class HardneedlTest {

// MODBUS network slave address  paquete  windows EmbeddedInteliggence
    private final static int SLAVE_ADDRESS = 1;

    // Serial port baud rate
    private final static int BAUD_RATE = 9600;

    public static void main(String[] args) throws Exception {

        SerialParameters serialParameters = new SerialParameters();
        // Set the serial port of the MODBUS communication serialParameters
        serialParameters.
                setCommPortId("COM1");
        // Set no parity

        serialParameters.setParity(0);
        // Set the data bits is 8 bits serialParameters
        serialParameters.
                setDataBits(8);
        // Set to 1 stop bit

        serialParameters.setStopBits(1);

        serialParameters.setPortOwnerName("Numb nuts");
        // Serial port baud rate serialParameters
        serialParameters.
                setBaudRate(BAUD_RATE);

        ModbusFactory modbusFactory = new ModbusFactory();

        ModbusMaster master = modbusFactory.createRtuMaster(serialParameters);

        try {

            master.init();

            // 
            readDiscreteInputTest(master, SLAVE_ADDRESS, 0, 8);
            //writeRegistersTest(master, SLAVE_ADDRESS, 0, new short[]{0x31, 0xb, 0xc, 0xd, 0xe, 0x9, 0x8, 0x7, 0x6});
            //readHoldingRegistersTest(master, SLAVE_ADDRESS, 0, 8);
            //readCoils(master, SLAVE_ADDRESS, 16, 32);
            //writeCoils(master, SLAVE_ADDRESS, 19, true);
            //readCoils(master, SLAVE_ADDRESS, 16, 32);
            //master.setValue(1, RegisterRange.HOLDING_REGISTER, 0, DataType.TWO_BYTE_INT_UNSIGNED, 100); // change Holding register value
            //setValue(slaveID,range,registerNumber,dataType,value); FORMA DEL METODO
            //  master.setValue(1, RegisterRange.COIL_STATUS, 0, DataType.BINARY, true); // change coils state

            // System.out.println("VALOR REGISTRO: " + master.getValue(1,RegisterRange.HOLDING_REGISTER,  0, DataType.TWO_BYTE_INT_UNSIGNED)); //Read register

             for (int i = 1; i < 32; i++) {
             //i is the register number
             System.out.println("VALOR REGISTRO: " + master.getValue(1, RegisterRange.COIL_STATUS, i, DataType.BINARY)); // read coil
             }
        } finally {

            master.destroy();

        }

    }

    /**
     *
     * read digital inputs
     */
    private static void readDiscreteInputTest(ModbusMaster master, int slaveId, int start, int len) {

        try {

            ReadDiscreteInputsRequest request = new ReadDiscreteInputsRequest(slaveId, start, len);

            ReadDiscreteInputsResponse response = (ReadDiscreteInputsResponse) master.send(request);

            if (response.isException()) {
                System.out.println("Exception response: message =" + response.getExceptionMessage());
            } else {
                System.out.println(Arrays.toString(response.getBooleanData()));
            }

        } catch (ModbusTransportException e) {

            e.printStackTrace();

        }

    }

    /**
     *
     * Read Holding register
     *
     */
    private static void readHoldingRegistersTest(ModbusMaster master, int slaveId, int start, int len) {

        try {

            ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(slaveId, start, len);

            ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) master.send(request);
            if (response.isException()) {
                System.out.println("Exception response: message =" + response.getExceptionMessage());
            } else {
                System.out.println(Arrays.toString(response.getShortData()));
            }

        } catch (ModbusTransportException e) {

            e.printStackTrace();

        }

    }

    /**
     *
     * write data to the holding register
     *
     */
    public static void writeRegistersTest(ModbusMaster master, int slaveId, int start, short[] values) {

        try {

            WriteRegistersRequest request = new WriteRegistersRequest(slaveId, start, values);

            WriteRegistersResponse response = (WriteRegistersResponse) master.send(request);
            if (response.isException()) {
                System.out.println("Exception response: message =" + response.getExceptionMessage());
            } else {
                System.out.println("success");
            }

        } catch (ModbusTransportException e) {

            e.printStackTrace();

        }

    }

    /*
     read coils
     */
    public static void readCoils(ModbusMaster master, int slaveId, int start, int len) {
        try {
            ReadCoilsRequest request = new ReadCoilsRequest(slaveId, start, start);

            ReadCoilsResponse response = (ReadCoilsResponse) master.send(request);

            if (response.isException()) {
                System.out.println("Exception response: message =" + response.getExceptionMessage());
            } else {
                System.out.println("SIZE: " + response.getShortData().length);
                System.out.println(Arrays.toString(response.getBooleanData()));
            }

        } catch (ModbusTransportException e) {

        }
    }

    /**
     *
     * write coils state
     *
     */
    public static void writeCoils(ModbusMaster master, int slaveId, int position, boolean value) {

        try {

            WriteCoilRequest request = new WriteCoilRequest(slaveId, position, value);

            WriteCoilResponse response = (WriteCoilResponse) master.send(request);
            if (response.isException()) {
                System.out.println("Exception response: message =" + response.getExceptionMessage());
            } else {
                System.out.println("success");
            }

        } catch (ModbusTransportException e) {

            e.printStackTrace();

        }

    }

}
