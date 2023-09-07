package nl.softcause.onestoplogshop.connector;

import java.net.URI;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SSLLogbackContext {
    private static final Logger logger = LoggerFactory.getLogger(SSLLogbackContext.class);


    private String location;

    private String passphrase;

    private SSLContext ctx;
    private KeyManagerFactory kmf;
    private TrustManagerFactory tmf;
    private KeyStore ks;

    public SSLLogbackContext(@Value("${listener.socket.ssl-keystore.location}")String location, @Value("${listener.socket.ssl-keystore.passphrase}") String passphrase) {
        this.location = location;
        this.passphrase = passphrase;

        logger.info("Initializing keystore for SSL");
        try {
            char[] passphraseChars = passphrase.toCharArray();

            ctx = SSLContext.getInstance("TLS");
            kmf = KeyManagerFactory.getInstance("SunX509");
            tmf = TrustManagerFactory.getInstance("SunX509");
            ks = KeyStore.getInstance("JKS");
            logger.info("Loading keystore from {}", location);

            ks.load(URI.create(location).toURL().openStream(), passphraseChars);
            logger.info("Keystore from {} loaded", location);

            logger.info("Opening keystore with passphrase", location);
            kmf.init(ks, passphraseChars);
            tmf.init(ks);
            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            logger.info("Keystore initialized for SSL");
        }catch (Exception e){
            logger.error("Keystore init failed: {}",e.getMessage(),e);
            throw new RuntimeException(e);
        }
    }

    public SSLServerSocketFactory getSocketFactory() {
            return ctx.getServerSocketFactory();
    }
}
