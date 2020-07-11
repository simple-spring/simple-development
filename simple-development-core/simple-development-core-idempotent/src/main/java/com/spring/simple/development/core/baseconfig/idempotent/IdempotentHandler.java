package com.spring.simple.development.core.baseconfig.idempotent;

/**
 * desc:  保留用户请求的标识
 */
public class IdempotentHandler {
    /**
     * 注意：保留用户请求的标识
     */
    private static final ThreadLocal<IdempotentModel> IDEMPOTENT_HANDLER = new ThreadLocal<IdempotentModel>();

    public static IdempotentModel getIdempotentModel() {
        return IDEMPOTENT_HANDLER.get() == null ? new IdempotentModel() : IDEMPOTENT_HANDLER.get();
    }

    public static void setIdempotentModel(IdempotentModel idempotentModel) {
        IDEMPOTENT_HANDLER.set(idempotentModel);
    }

    public static void clearIdempotentModel() {
        IDEMPOTENT_HANDLER.remove();
    }

    public static void fastSetIdempotentModel(String ip, String url,String sessionId) {
        IdempotentModel idempotentModel = new IdempotentModel();
        idempotentModel.setIp(ip);
        idempotentModel.setUrl(url);
        idempotentModel.setSessionId(sessionId);
        IDEMPOTENT_HANDLER.set(idempotentModel);
    }
}
