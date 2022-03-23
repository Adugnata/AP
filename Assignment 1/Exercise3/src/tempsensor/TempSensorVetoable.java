package tempsensor;

import java.beans.*;
import java.io.Serializable;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class TempSensorVetoable extends TempSensor implements Serializable {
    
    private VetoableChangeSupport vetoableSupport;
    
    public TempSensorVetoable() {
        vetoableSupport = new VetoableChangeSupport(this);
    }
    
    public class VetoSensor extends TimerTask {
        @Override
        public void run() {
            Random r = new Random();
            float temperature =(70*r.nextFloat())-20;
            try{
                setCurrentTempVeto(temperature);
            }
            catch(PropertyVetoException e){
                
            }
        }
    }
    
    @Override
    public void go(){
        Timer timer = new Timer();
        VetoSensor senseTemp = new VetoSensor();
        timer.scheduleAtFixedRate(senseTemp, 0, 1000);
    }
    
    public void setCurrentTempVeto(float value) throws PropertyVetoException{
        float oldValue = currentTemp;
        currentTemp = value;
        try{
            vetoableSupport.fireVetoableChange(PROP_SAMPLE_PROPERTY, oldValue, currentTemp);
        }
        catch(PropertyVetoException e){
            
        }
    }
    
    public void addVetoableChangeListener(VetoableChangeListener listener) {
        if(vetoableSupport != null){
            vetoableSupport.addVetoableChangeListener(listener);
        }
    }

}