package com.example.leandrocastilho.helowoordturbo.util;

public class WSConstants {
    WSConstants() {
    }

    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_URL_ENCODED = "application/x-www-form-urlencoded";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_DELETE = "DELETE";

    public static final int READ_TIMEOUT = 8000;
    public static final int CONNECTION_TIMEOUT = 12000;

    public static final String PREF_WS_MESSAGE_ = "574033931406";

    public static final String PREF_WS_SALES_ACCESS_TOKEN = "pref_ws_sales_access_token";
    public static final String PREF_WS_SALES_ACCESS_TOKEN_EXPIRATION = "pref_ws_sales_access_token_expiration";

    public static final String PREF_WS_MESSAGE_ACCESS_TOKEN = "pref_ws_message_access_token";
    public static final String PREF_WS_MESSAGE_ACCESS_TOKEN_EXPIRATION = "pref_ws_message_access_token_expiration";
}
