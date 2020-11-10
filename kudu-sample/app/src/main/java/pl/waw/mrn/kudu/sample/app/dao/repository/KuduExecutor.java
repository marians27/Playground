package pl.waw.mrn.kudu.sample.app.dao.repository;

import org.apache.kudu.client.KuduException;

public class KuduExecutor {

    public static void execute(KuduAction action) {
        try {
            action.run();
        } catch (KuduException e) {
            throw new RuntimeException("Error while executing Kudu action", e);
        }
    }

    public static <T> T execute(KuduOperation<T> action) {
        try {
            return action.run();
        } catch (KuduException e) {
            throw new RuntimeException("Error while performing Kudu operation", e);
        }
    }

    @FunctionalInterface
    public interface KuduAction {
        void run() throws KuduException;
    }

    @FunctionalInterface
    public interface KuduOperation<T> {
        T run() throws KuduException;
    }
}
