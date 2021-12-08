import java.util.*;
public class Test {
    public static void main(String args[]) {
        ArrayList l = new ArrayList();
        l.add(12);
        l.add("Java");
        l.add(15.6);
        System.out.println("The size is "+l.size());
        System.out.println("The contents are "+l);
        System.out.println("The first one is "+((int)l.get(0)+2));
    }
}