package com.security.registerbouncycastle;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

/**
 * This class is used to register BouncyCastle as a security provider with GraalVM builder
 */
public class BouncyCastleInitializer  {

    static {
        // Force provider registration during image build
        Security.addProvider(new BouncyCastleProvider());
    }
}
