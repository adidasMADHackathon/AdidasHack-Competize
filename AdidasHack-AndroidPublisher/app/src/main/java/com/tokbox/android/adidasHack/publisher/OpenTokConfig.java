package com.tokbox.android.adidasHack.publisher;

import android.webkit.URLUtil;

public class OpenTokConfig {

    private static final String CHAT_SERVER_URL = "https://adidashack.herokuapp.com";
    public static final String SESSION_INFO_ENDPOINT = CHAT_SERVER_URL + "/room/adidas";
    public static final String ARCHIVE_START_ENDPOINT = CHAT_SERVER_URL + "/archive/start";
    public static final String ARCHIVE_STOP_ENDPOINT = CHAT_SERVER_URL + "/archive/:archiveId/stop";
    public static final String ARCHIVE_PLAY_ENDPOINT = CHAT_SERVER_URL + "/archive/:archiveId/view";

    public static String configErrorMessage;

    public static boolean isConfigUrlValid(){
        if (OpenTokConfig.CHAT_SERVER_URL == null) {
            configErrorMessage = "CHAT_SERVER_URL in OpenTokConfig.java must not be null";
            return false;
        } else if ( !( URLUtil.isHttpsUrl(OpenTokConfig.CHAT_SERVER_URL) || URLUtil.isHttpUrl(OpenTokConfig.CHAT_SERVER_URL)) ) {
            configErrorMessage = "CHAT_SERVER_URL in OpenTokConfig.java must be specified as either  http or https";
            return false;
        } else if ( !URLUtil.isValidUrl(OpenTokConfig.CHAT_SERVER_URL) ) {
            configErrorMessage = "CHAT_SERVER_URL in OpenTokConfig.java is not a valid URL";
            return false;
        } else {
            return true;
        }
    }
}
