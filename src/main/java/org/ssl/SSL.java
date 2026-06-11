package org.ssl;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class SSL {
    public SSL() {

    }

    public void handleSSL() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS"); // You can choose other protocols like "TLSv1.3"
            char[] password = "customPassword".toCharArray();
            KeyStore ks = KeyStore.getInstance("JKS"); // You can use other keystore types (e.g., PCKS12)
            ks.load(new FileInputStream("your_keystore_file"), password);

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, password);

            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ks); // If using a CA certificate, use a trust store containing the CA cert

            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        } catch (NoSuchAlgorithmException nsae) {
            System.out.println("No such algo present for ssl context. Exception is: " + nsae);
        } catch (KeyStoreException kse) {
            System.out.println("Key store type not found for ssl context. Exception is: " + kse);
        } catch (FileNotFoundException fnfe) {
            System.out.println("File not found. Exception is: " + fnfe);
        } catch (IOException | CertificateException ce) {
            System.out.println("File password wrong or cert not found. Exception is: " + ce);
        } catch (UnrecoverableKeyException uke) {
            System.out.println("Key is not recoverable." + uke);
        } catch (KeyManagementException kme) {
            System.out.println("Key is expired." + kme);
        }
    }
}
