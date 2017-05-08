package pl.marian.sample.log;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleLoggingApp {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleLoggingApp.class);
    private static final long DEFAULT_WAIT = 2000;

    public static void main(String args[]) throws Exception {
        while (true) {
            LOG.info("Example of info message");
            LOG.warn("Warning");
            LOG.error("Exception occured", new RuntimeException(new RuntimeException("Cause of error")));
            Thread.sleep(args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_WAIT);
        }
    }
}
