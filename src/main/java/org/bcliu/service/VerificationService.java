package org.bcliu.service;

public interface VerificationService {
    String genAndStoreCode(String phoneNumber);
    boolean verifyCode(String phoneNumber, String submittedCode);
}
