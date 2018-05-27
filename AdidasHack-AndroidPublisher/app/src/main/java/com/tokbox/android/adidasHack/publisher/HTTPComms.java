package com.tokbox.android.adidasHack.publisher;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateException;
import java.util.Map;

import javax.net.ssl.SSLHandshakeException;

public class HTTPComms {
    private static final String TAG = "HTTPComms";

    public static JSONObject callServer(String urlString, String postDataString,
                                         Map<String, String> extraHeaders) throws IllegalArgumentException {
        HttpURLConnection conn = null;
        try {
            Log.d(TAG, "callServer URL: " + urlString);
            Log.d(TAG, "POST data: " + postDataString);

            URL url = new URL(urlString);

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            conn.setDoInput(true);
            conn.addRequestProperty("Accept", "application/json");

            if (extraHeaders != null) {
                for (Map.Entry<String, String> entry : extraHeaders.entrySet()) {
                    String key = entry.getKey();
                    String val = entry.getValue();
                    Log.d(TAG, "Adding extra header: " + key + ": " + val);
                    conn.addRequestProperty(key, val);
                }
            }

            if (postDataString != null) {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.addRequestProperty("Content-Type", "application/json");

                OutputStream outputStream = conn.getOutputStream();

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(postDataString);

                writer.flush();
                writer.close();
                outputStream.close();
            }

            String response = "";
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = br.readLine()) != null) {
                response += line;
            }
            response = response.trim();
            br.close();

            Log.d(TAG, "Server response: '" + response + "'");

            int responseCode = conn.getResponseCode();
            if (responseCode >= 200 && responseCode < 300) {
                //Check if it contains JSON and success = true
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    return jsonObject;
                } catch (JSONException e) {
                    Log.w(TAG, "Failed to process the response as JSON. Server call failed");
                    return null;
                }
            } else {
                Log.w(TAG, "Failed to upload the data");
                Log.d(TAG, "Response: " + responseCode + " - " + response);
                if (responseCode == 401 || responseCode == 403) {
                    IllegalArgumentException ex = new IllegalArgumentException("Unauthorized");
                    throw ex;
                }
            }
        } catch (IOException e) {
            if (conn != null) {
                try {
                    int responseCode = conn.getResponseCode();
                    String response = conn.getResponseMessage();
                    Log.d(TAG, "Server error response: " + responseCode + " - " + response);
                    if (responseCode == 401 || responseCode == 403) {
                        throw new IllegalArgumentException("Unauthorized");
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            Log.e(TAG, "IOException in postData()", e);
            try {
                if (e instanceof SSLHandshakeException) {
                    Throwable cause = e.getCause();
                    if (cause instanceof CertificateException) {
                        CertPathValidatorException ex = (CertPathValidatorException) cause.getCause();
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            CertPathValidatorException.Reason reason = ex.getReason();
                            Log.v(TAG, "CertPathValidatorException reason: " + reason);
                        }
                        CertPath certPath = ex.getCertPath();
                        Log.v(TAG, "CertPathValidatorException Certificates: " + certPath.getCertificates());
                    }
                }
            } catch (Exception exc) {
            }
        } finally {
            if (conn != null) conn.disconnect();
        }

        return null;
    }
}
