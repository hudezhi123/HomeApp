package com.hudezhi.freedom.homeapp.something;

import android.content.Context;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by boy on 2017/6/14.
 */

public class WebServiceMananger {
    private final int CONNECT_TIMEOUT = 30 * 1000;
    private final int READ_TIMEOUT = 30 * 1000;
    private Context context;
    private String methodName;
    private byte[] outBytes;

    public WebServiceMananger(Context context, String methodName, HashMap<String, String> paramValues) {
        this.context = context;
        this.methodName = methodName;
        this.outBytes = GetXmlParams(methodName, paramValues);
    }

    /**
     * the attribute i is useless just for request params of object
     *
     * @param context
     * @param methodName
     * @param paramsValues
     * @param i
     */
    public WebServiceMananger(Context context, String methodName, HashMap<String, Object> paramsValues, int i) {
        this.context = context;
        this.methodName = methodName;
        this.outBytes = GetXmlparamsForBasicDataType(methodName, paramsValues);
    }

    public String OpenConnect(String methodPath) {
        String result = "";
        if (CommonFunc.getLocalIdAddress().endsWith("0.0.0.0")) {
            return "";
        }
        try {
            String serverName = CommonFunc.GetServer(context);
            URL url = new URL(serverName + methodPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(true);
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setRequestProperty("Content-Type",
                    "text/xml; charset=utf-8");
            connection.setRequestProperty("SOAPAction", "http://tempuri.org/"
                    + methodName);
            connection.setRequestMethod("POST");
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(outBytes);
            outputStream.flush();
            outputStream.close();
            int resCode = connection.getResponseCode();
            if (resCode != 200) {

            }
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String lines;
            while ((lines = reader.readLine()) != null) {
                result += lines;
            }
            if (result.equals("")) {
                return "";
            }
            result = GetXmlJson(result);
            connection.disconnect();
            connection=null;
        } catch (Exception e) {
            return "";
        }
        return result;
    }

    private String GetXmlJson(String result) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource inputSource = new InputSource(new StringReader(result));
            Document dom = builder.parse(inputSource);
            Element root = dom.getDocumentElement();
            String json = dom.getTextContent();
            return json;
        } catch (Exception e) {
            return "";
        }
    }

    private byte[] GetXmlparamsForBasicDataType(String methodName, HashMap<String, Object> paramsValues) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        sb.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
        sb.append("<soap:Body>");
        sb.append("<" + methodName + "  xmlns=\"http://tempuri.org/\">");
        for (Map.Entry<String, Object> item : paramsValues.entrySet()) {
            String key = item.getKey();
            Object value = item.getValue();
            sb.append("<" + key + ">");
            String FormName = value.getClass().getSimpleName();
            if (FormName.equals("String")) {
                String temp = (String) value;
                sb.append(temp);
            } else if (FormName.equals("Byte")) {
                byte temp = (Byte) value;
                sb.append(temp);
            } else if (FormName.equals("Character")) {
                char temp = (Character) value;
                sb.append(temp);
            } else if (FormName.equals("Integer")) {
                int temp = (Integer) value;
                sb.append(temp);
            } else if (FormName.equals("Long")) {
                long temp = (Long) value;
                sb.append(temp);
            } else if (FormName.equals("Float")) {
                float temp = (Float) value;
                sb.append(temp);
            } else if (FormName.equals("Double")) {
                double temp = (Double) value;
                sb.append(temp);
            }
            sb.append("</" + key + ">");
            sb.append("<" + key + ">");
        }
        sb.append("</" + methodName + ">");
        sb.append("</soap:Body>");
        sb.append("</soap:Envelope>");
        try {
            Log.e("请求数据", sb.toString());
            return sb.toString().getBytes("utf-8");
        } catch (Exception ex) {
            return null;
        }
    }

    private byte[] GetXmlParams(String methodName, HashMap<String, String> paramValues) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        sb.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
        sb.append("<soap:BOdy>");
        sb.append("<" + methodName + "  xmlns=\"http://tempuri.org/\">");
        for (Map.Entry<String, String> item : paramValues.entrySet()) {
            String key = item.getKey();
            String value = item.getValue();
            if (value == null) {
                value = "";
            } else {
                value = value.trim();
            }
            sb.append("<" + key + ">");
            sb.append(value);
            sb.append("</" + key + ">");
        }
        sb.append("</" + methodName + ">");
        sb.append("</soap:Body>");
        sb.append("</soap:Envelope>");
        try {
            Log.e("请求数据", sb.toString());
            return sb.toString().getBytes("utf-8");
        } catch (Exception ex) {
            return null;
        }
    }

}
