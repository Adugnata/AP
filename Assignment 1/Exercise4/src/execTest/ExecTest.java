package execTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ExecTest {
    public static void main(String args[]) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class c = Class.forName(args[0]);// Get class name as string & Create class of type Base.
        Object t = c.newInstance(); // create object of type Base
        Method[] allMethods = c.getMethods();//reading the methods of base type
        for (Method m : allMethods)
            if (m.getName().startsWith("test") && m.getParameterTypes().length == 0)
                m.invoke(t);
    }
}