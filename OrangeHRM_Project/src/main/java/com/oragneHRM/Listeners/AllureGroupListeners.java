package com.oragneHRM.Listeners;

import io.qameta.allure.Allure;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

public class AllureGroupListeners implements IInvokedMethodListener {
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        String[] groups = testResult.getMethod().getGroups();
        for (String group : groups) {
            Allure.label("tag", group);
        }
    }
}
