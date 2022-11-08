package com.demo.config.poc;

public class TenantContext {

    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();
	
	//private static ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static void setTenantId(String tenantId) {
        CONTEXT.set(tenantId);
        System.out.println("thread initialized" + CONTEXT.hashCode());
    }

    public static String getTenantId() {
        return CONTEXT.get();
    }

    public static void clear() throws InterruptedException {
    	System.out.println(" thread context removed " + CONTEXT.hashCode());
        CONTEXT.remove();
        //CONTEXT = null;
    }
}