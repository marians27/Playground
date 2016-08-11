package pl.marian.playground.init.jetty;

import pl.marian.playground.init.jetty.server.EmbeddedJettyServer;

public class EmbeddedJettyRunner {

    public static void main(String[] args) throws Exception {
        EmbeddedJettyServer embeddedJettyServer = new EmbeddedJettyServer(9090, "springRestOnEmbeddedJetty");
        try {
            embeddedJettyServer.runEmbeddedJettyAndJoin();
        } catch (InterruptedException e) {
            embeddedJettyServer.stopEmbeddedJetty();
        }
    }
}
