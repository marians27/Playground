package pl.weak.reference;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Illustrates when soft and weak references are cleaned up
 */
public class WeakReferenceExample {

    public static void main(String[] args) {
        WeakReference<Element> weakReference = new WeakReference<Element>(new Element("Weak referenced element"));
        System.out.format("Weak reference contains element: %s\n", weakReference.get());

        SoftReference<Element> softReference = new SoftReference<Element>(new Element("Soft referenced element"));
        System.out.format("Soft reference contains element: %s\n", softReference.get());

        System.gc();
        System.out.format("Weak reference contains element: %s\n", weakReference.get());
        System.out.format("Soft reference contains element: %s\n", softReference.get());

        try {
            List<Element> elements = new ArrayList<Element>();
            for (int i = 0; i < 100000; i++) {
                //List<Element> elements = new ArrayList<Element>();
                for (int j=0; j< 1000; j++) {
                    elements.add(new Element("s"));
                }
            }
        } catch (OutOfMemoryError err) {
            // At this point soft reference must have been set to null
            System.out.println("OutOfMemoryError thrown");
        }

        System.out.format("Weak reference contains element: %s\n", weakReference.get());
        System.out.format("Soft reference contains element: %s\n", softReference.get());

    }
}
