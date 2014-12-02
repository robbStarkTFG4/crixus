/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatizacion;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import pistonccontrol.PistonControl;

/**
 *
 * @author NORE
 */
public class WritableCommands {

    public static final int START = 0;
    private static final int STOP = 1;
    private SimpleBooleanProperty start;
    public SimpleBooleanProperty stop;
    public SimpleBooleanProperty anotherStask;
    public SimpleDoubleProperty contador;

    public WritableCommands() {
        start = new SimpleBooleanProperty(false);
        start.addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // get modbus instance from crixus and write
                System.out.println("ME LLAMASTE?");
                Crixus.getInstance().getModbus().writeCommandThread(WritableCommands.START, newValue);

            }

        });

        stop = new SimpleBooleanProperty(false);
        stop.addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                //.getInstance().getModbus().writeCommand(WritableCommands.STOP, newValue);
                Crixus.getInstance().getModbus().writeCommandThread(4, newValue);
            }

        });
        anotherStask = new SimpleBooleanProperty(false);
        anotherStask.addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                //   Crixus.getInstance().getModbus().writeCommand(2, newValue);
            }

        });

        contador = new SimpleDoubleProperty();
        contador.addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//                Crixus.getInstance().getModbus().writeContador(newValue.intValue());
            }

        });
    }

    public void setStart(boolean value) {
        start.set(value);
    }

    public boolean getStart() {
        return start.get();
    }

    public void AssignColor(String current) {

        switch (current) {
            case "negro":
                System.out.println("ENTRE EN NEGRO");
                Crixus.getInstance().getModbus().writeCommandThread(2, true);

                Crixus.getInstance().getGemma().cajaColor = GemmaController.CAJA_NEGRA;
                Crixus.getInstance().getGemma().vastagoRetraction.set(PistonControl.POS_2);
                break;
            case "azul":
                System.out.println("ENTRE EN AZUL");
                Crixus.getInstance().getModbus().writeCommandThread(3, true);
                Crixus.getInstance().getGemma().cajaColor = GemmaController.CAJA_AZUL;
                Crixus.getInstance().getGemma().vastagoRetraction.set(PistonControl.POS_1);
                break;
            default:
                //Crixus.getInstance().getModbus().writeCommand(3, true);
                // Crixus.getInstance().getModbus().writeCommand(2, false);
                break;
        }

        /*  if (current.equals("negro")) {
         Crixus.getInstance().getModbus().writeCommandThread(3, false);
         } else {
         Crixus.getInstance().getModbus().writeCommandThread(2, false);
         }*/
    }
}
