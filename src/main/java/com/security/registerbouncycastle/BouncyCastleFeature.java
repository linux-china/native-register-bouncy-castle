package com.security.registerbouncycastle;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.graalvm.nativeimage.hosted.Feature;

import java.security.Security;

/**
 * A GraalVM Feature that registers the Bouncy Castle provider.
 * This is required so that native image builds verify and include the provider.
 */
public class BouncyCastleFeature implements Feature {
    
    @Override
    public void afterRegistration(AfterRegistrationAccess access) {
        // Register the Bouncy Castle provider
        Security.addProvider(new BouncyCastleProvider());
    }
}
