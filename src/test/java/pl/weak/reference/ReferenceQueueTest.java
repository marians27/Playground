package pl.weak.reference;

import org.junit.Assert;
import org.junit.Test;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * Simple tests, which shows
 */
public class ReferenceQueueTest {

    @Test
    public void weakReferenceQueue() throws InterruptedException {

        Element testElement = new Element("Weak Reference queue");
        ReferenceQueue<Element> referenceQueue = new ReferenceQueue<Element>();
        WeakReference<Element> weakReference = new WeakReference<Element>(testElement, referenceQueue);

        //test element is still strongly referenced
        Assert.assertNull(referenceQueue.remove(100));

        testElement = null;
        //test element is not strongly referenced, but most probably GS did not run so far
        Assert.assertNull(referenceQueue.remove(100));

        System.gc();
        //Gc most probably run
        Assert.assertNull(weakReference.get());

        // TODO: Why poll some times returns null? But after wait not
        //Thread.sleep(100);
        //Assert.assertNull(referenceQueue.poll());

        Assert.assertEquals(weakReference, referenceQueue.remove(100));
    }

    @Test
    public void softReferenceQueue() throws InterruptedException {

        Element testElement = new Element("Weak Reference queue");
        ReferenceQueue<Element> referenceQueue = new ReferenceQueue<Element>();
        SoftReference<Element> softReference = new SoftReference<Element>(testElement, referenceQueue);

        //test element is still strongly referenced
        Assert.assertNull(referenceQueue.remove(100));

        testElement = null;
        //test element is not strongly referenced, but most probably GS did not run so far
        Assert.assertNull(referenceQueue.remove(100));

        System.gc();
        //Gc most probably run
        // Here is difference between soft and weak reference
        Assert.assertNotNull(softReference.get());

        // TODO: Why poll sometimes returns null? But after wait not
        //Thread.sleep(100);
        Assert.assertNull(referenceQueue.poll());

        Assert.assertNull(referenceQueue.remove(100));
    }
}
