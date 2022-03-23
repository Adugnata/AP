package CheckNPE;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CheckNPE {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IllegalMonitorStateException {

        Class c = Class.forName(args[0]);
        Object t = c.newInstance();
        Constructor[] ctr = c.getConstructors();
        for (Constructor con : ctr) {
            Class[] params = con.getParameterTypes();
            System.out.println();
            Object[] obj = new Object[params.length];
            if (params.length > 0) {
                System.out.print("Constructor has" + " parameters ");
                assignParameters(params, obj);
            } else {
                System.out.print("Constructor has no parameters ");
            }
            System.out.println();
            String np = "not ";
            try {
                con.newInstance(obj); //instantiating an object
            } catch (InvocationTargetException ex) {
                if (ex.getCause().toString().equals("java.lang.NullPointerException"))
                    np = "";
            } finally {
                System.out.println("This method is " + np + "NPE-sensible.");
            }
        }
        Method[] ms = c.getMethods();
        for (Method md : ms) {
            String mname = md.getName();
            Class[] params = md.getParameterTypes();
            System.out.println();
            Object[] ps = new Object[params.length];
            if (params.length > 0) {
                System.out.print("Method " + mname + " has" + " parameters ");
                assignParameters(params, ps);
            } else {
                System.out.print("Method " + mname + " has no parameters ");
            }
            System.out.println();
            String np = "not ";
           try {
               md.invoke(t, ps);
            } catch (InvocationTargetException ex) {
                if (ex.getCause().toString().equals("java.lang.NullPointerException"))
                    np = "";
            } finally {
                System.out.println("This method is " + np + "NPE-sensible.");
            }
        }
    }

    private static void assignParameters(Class[] pars, Object[] ps) { 
        for (int k = 0; k < pars.length; ++k) {
            String paramType = pars[k].getName();
            switch (paramType) {
                case "byte":
                case "short":
                case "int":
                case "long":
                case "float":
                case "double":ps[k] = 0;
                    break;
                case "char":ps[k] = '\u0000';
                    break;
                case "boolean":ps[k] = false;
                    break;
                default:ps[k] = null;
                    break;
            }
            System.out.print(paramType + " ");
        }
    }
}
