/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatizacion;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author NORE
 */
public class Crixus {

    private static Crixus mira = new Crixus();
    private GemmaController gemma = null;
    // HILOS
    private ColorSensor colorSensor = null;
    private BlueServer blueServer = null;
    private ModBus modbus = null;

    // pistones
    private WritableCommands command = null;
    private ReadOnlyCommands read = null;

    public Thread arya = null;

    private Crixus() {
        init();
    }

    private void init() {

        command = new WritableCommands();
        read = new ReadOnlyCommands();

        System.out.println("Arrancando hilos......");

        modbus = new ModBus();
        arya = new Thread(modbus);
       // arya.start();

        blueServer = new BlueServer();
        Thread blue = new Thread(blueServer);
        blue.start();
        
        colorSensor = new ColorSensor();
        Thread sensor = new Thread(colorSensor);
        sensor.start();

    }

    public static Crixus getInstance() {
        return mira;
    }

    public void setGemma(GemmaController gemma) {
        this.gemma = gemma;
    }

    public GemmaController getGemma() {
        return gemma;
    }

    public WritableCommands getCommand() {
        return command;
    }

    public ReadOnlyCommands getRead() {
        return read;
    }

    public ColorSensor getColorSensor() {
        return colorSensor;
    }

    public ModBus getModbus() {
        return modbus;
    }

    public void release() {

        //  blueServer.active = false;
        colorSensor.readPort = false;
        colorSensor.close();
        modbus.close();
        ModBus.alive = false;
        gemma.turnOnScada = false;
        System.out.println("Recursos Liberados");
    }

}
