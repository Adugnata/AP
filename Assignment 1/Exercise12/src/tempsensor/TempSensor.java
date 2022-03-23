package tempsensor;

import java.beans.*;
import java.io.Serializable;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class TempSensor implements Serializable {
    
    public static final String PROP_SAMPLE_PROPERTY = "currentTemp";
    
    protected float currentTemp;
    
    protected PropertyChangeSupport propertySupport;
    
    public TempSensor() {
        propertySupport = new PropertyChangeSupport(this);
    }
    
    public class Sensor extends TimerTask {
        @Override
        public void run() {
            Random r = new Random();
            float temperature = -20 + 70*r.nextFloat();
            setCurrentTemp(temperature);
        }
    }
    
    public void go(){
        Timer timer = new Timer();
        Sensor senseTemp = new Sensor();
        timer.scheduleAtFixedRate(senseTemp, 0, 1000);
    }
    
    public float getCurrentTemp() {
        return currentTemp;
    }
    
    public void setCurrentTemp(float value) {
        float oldValue = currentTemp;
        currentTemp = value;
        propertySupport.firePropertyChange(PROP_SAMPLE_PROPERTY, oldValue, currentTemp);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }

}
