package pl.weak.reference;

import org.junit.Assert;
import org.junit.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

/**
 * Demonstrates how phantom reference works
 */
public class PhantomReferenceTest {

    @Test
    public void phantomReferenceUsage() throws InterruptedException {
        Element testElement = new Element("Phantom reference test");
        ReferenceQueue<Element> referenceQueue = new ReferenceQueue<Element>();

        // The only valid constructor is with reference queue
        // as there is no point to use phantom reference without reference queue
        PhantomReference<Element> phantomReference = new PhantomReference<Element>(testElement, referenceQueue);

        // Phantom reference always return null
        Assert.assertNull(phantomReference.get());

        // Element is still strongly referenced. So it was not yet enqueued
        Assert.assertNull(referenceQueue.remove(100));

        testElement = null;
        // There are no strong reference to the element, but most probably GC did not run so far
        Assert.assertNull(referenceQueue.remove(100));

        System.gc();
        // Most probably GC run
        Reference<? extends Element> enqueuedReference = referenceQueue.remove(100);
        Assert.assertNotNull(enqueuedReference);
        Assert.assertEquals(phantomReference, enqueuedReference);

    }
}
