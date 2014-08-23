package pl.weak.reference;

import org.junit.Assert;
import org.junit.Test;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * Simple test cases which shows the meaning of weak and soft reference in Java
 */
public class WeakReferenceTest {

    @Test
    public void weakReferenceTest() {
        Element testElement = new Element("Weak reference test");
        WeakReference<Element> weakReference = new WeakReference<Element>(testElement);
        //Should be always true
        Assert.assertEquals(testElement, weakReference.get());

        testElement = null;
        //Can fail here, but there is a little chance that GC run. So in most cases it is true.
        Assert.assertEquals("Weak reference test", weakReference.get().getName());

        // Run gc to clean weak reference. Note that System.gc does not mean that GC will run for sure.
        // So assertion may fail.
        System.gc();
        Assert.assertNull(weakReference.get());
    }

    @Test
    public void softReferenceTest() {
        Element testElement = new Element("Soft reference test");
        SoftReference<Element> softReference = new SoftReference<Element>(testElement);
        //Should be always true
        Assert.assertEquals(testElement, softReference.get());

        testElement = null;
        //Can fail here, but there is a little chance that GC run. So in most cases it is true.
        Assert.assertEquals("Soft reference test", softReference.get().getName());

        // Run gc to clean weak reference. Note that System.gc does not mean that GC will run for sure.
        // Here is a difference between soft and weak reference
        System.gc();
        Assert.assertEquals("Soft reference test", softReference.get().getName());
    }

}
