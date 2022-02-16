package com.ruoyi.system.utils;

import okhttp3.Authenticator;
import okhttp3.ConnectionPool;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @author jiangxianwei
 * @version 1.0
 * @description: SSL的okhttp的client
 * @date 2022/2/15 16:43
 */
public class OkHttpsClientUtil {

    private static OkHttpClient clientInstance;

    private static final Integer connectTimeout_time = 20;
    private static final Integer writeTimeout_time = 20;
    private static final Integer readTimeout_time = 30;

    public OkHttpsClientUtil() {
    }

    static {
        clientInstance = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout_time, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout_time, TimeUnit.SECONDS)
                .readTimeout(readTimeout_time, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }

    /**
     * 正常客户端
     *
     * @return
     */
    public OkHttpClient getNomalClientInstance() {
        return clientInstance;
    }

    /**
     * 正常代理的客户端
     *
     * @param userName : 代理账号名称
     * @param passWord : 代理账号密码
     * @param hostName : 代理主机
     * @param port     : 代理主机的端口号
     * @return
     */
    public OkHttpClient getNomalProxyClientInstance(String userName, String passWord, String hostName, int port) {

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(hostName, port));

        Authenticator proxyAuthenticator = (route, response) -> {
            String credential = Credentials.basic(userName, passWord);
            return response.request().newBuilder()
                    .header("Proxy-Authorization", credential)
                    .build();
        };
        return new OkHttpClient().newBuilder().
                connectTimeout(connectTimeout_time, TimeUnit.SECONDS).readTimeout(readTimeout_time, TimeUnit.SECONDS)
                .proxy(proxy)
                .proxyAuthenticator(proxyAuthenticator)
                // 解决内存溢出问题
                .connectionPool(new ConnectionPool(5, 1, TimeUnit.SECONDS)).build();
    }

    /**
     * SSL的客户端
     *
     * @param cerPath : 证书的位置
     * @return
     */
    public OkHttpClient getSslClientInstance(String cerPath) {

        OkHttpClient.Builder builder = getSSlBuilder(cerPath);

        return builder.connectTimeout(connectTimeout_time, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout_time, TimeUnit.SECONDS)
                .readTimeout(readTimeout_time, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }

    /**
     * SSL的代理客户端
     *
     * @param cerPath  : 证书的位置
     * @param userName : 代理账号名称
     * @param passWord : 代理账号密码
     * @param hostName : 代理主机
     * @param port     : 代理主机的端口号
     * @return
     */
    public OkHttpClient getSslProxyClientInstance(String cerPath, String userName, String passWord, String hostName, int port) {
        OkHttpClient.Builder builder = getSSlBuilder(cerPath);

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(hostName, port));

        Authenticator proxyAuthenticator = (route, response) -> {
            String credential = Credentials.basic(userName, passWord);
            return response.request().newBuilder()
                    .header("Proxy-Authorization", credential)
                    .build();
        };

        return builder.connectTimeout(connectTimeout_time, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout_time, TimeUnit.SECONDS)
                .readTimeout(readTimeout_time, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .proxy(proxy)
                .proxyAuthenticator(proxyAuthenticator)
                // 解决内存溢出问题
                .connectionPool(new ConnectionPool(5, 1, TimeUnit.SECONDS)).build();

    }


    private OkHttpClient.Builder getSSlBuilder(String cerPath) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        X509TrustManager trustManager;
        SSLSocketFactory sslSocketFactory;
        try {
            trustManager = trustManagerForCertificates(trustedCertificatesInputStream(cerPath));
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        builder.sslSocketFactory(sslSocketFactory, trustManager);
        builder.hostnameVerifier((hostname, session) -> {
            Certificate[] localCertificates = new Certificate[0];
            try {
                //获取证书链中的所有证书
                localCertificates = session.getPeerCertificates();
            } catch (SSLPeerUnverifiedException e) {
                e.printStackTrace();
            }
            //打印所有证书内容
            for (Certificate c : localCertificates) {
                System.err.println("verify: " + c.toString());
            }
            return true;
        });
        return builder;
    }

    private InputStream trustedCertificatesInputStream(String filePath) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    private X509TrustManager trustManagerForCertificates(InputStream in)
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        char[] password = "password".toCharArray();
        // Put the certificates a key store.
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (Certificate certificate : certificates) {
            System.err.println("trustManagerForCertificates: " + certificate.toString());
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }
        // Use it to build an X509 trust manager.
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream in = null;
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }


}
