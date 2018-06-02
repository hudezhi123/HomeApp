package com.hudezhi.freedom.homeapp.rxjava_retrofit;

import android.content.Context;
import android.text.TextUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by boy on 2017/11/29.
 */

public class WebServiceManager {

    public static String BASE_URL = "";

    private final int CONNECT_TIMEOUT = 30 * 1000;   //连接超时
    private final int READ_TIMEOUT = 30 * 1000;   //读取超时
    private Context mContext;
    private String methodName;
    private byte[] outBytes;

    public static void init(String baseUrl) {
        BASE_URL = baseUrl;
    }

    public WebServiceManager(String methodName, HashMap<String, Object> paramValue) {
        this.methodName = methodName;
        this.outBytes = SoapRequestXML(methodName, paramValue);
    }

    private String SoapRequest(String methodPath) {
        String result = "";
        try {
            URL url = new URL(BASE_URL + methodPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);    //是否允许输出
            conn.setDoInput(true);     //是否允许输入
            conn.setUseCaches(true);
            conn.setDefaultUseCaches(true);
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            conn.setRequestProperty("SOAPAction", "http://tempuri.org/" + methodName);
            conn.setRequestMethod("POST");
            OutputStream output = conn.getOutputStream();
            output.write(outBytes);
            output.flush();
            output.close();
            int resCode = conn.getResponseCode();
            if (resCode != 200) {
                return methodName + "请求失败！";
            } else {
                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String lines;
                while ((lines = reader.readLine()) != null) {
                    result += lines;
                }
                if (TextUtils.isEmpty(result)) {
                    return "";
                }

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String getXMLJson(String xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource inputSource = new InputSource(new StringReader(xml));
            Document dom = builder.parse(inputSource);
            Element root = dom.getDocumentElement();
            String json = root.getTextContent();
            return json;
        } catch (Exception e) {
            return "";
        }
    }

    private byte[] SoapRequestXML(String methodName, HashMap<String, Object> paramValue) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sb.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n");
        sb.append("<soap:Body>");
        sb.append("<" + methodName + " xmlns=\"http://tempuri.org/\">\n");
        for (HashMap.Entry<String, Object> item : paramValue.entrySet()) {
            String key = item.getKey();
            Object value = item.getValue();
            String type = value.getClass().getSimpleName();
            sb.append("<" + key + ">");
            switch (type) {
                case "String":
                    String temp_str = (String) value;
                    sb.append(temp_str);
                    break;
                case "Byte":
                    byte temp_byte = (byte) value;
                    sb.append(temp_byte);
                    break;
                case "Character":
                    char temp_char = (char) value;
                    sb.append(temp_char);
                    break;
                case "Integer":
                    int temp_int = (int) value;
                    sb.append(temp_int);
                    break;
                case "Long":
                    long temp_long = (long) value;
                    sb.append(temp_long);
                    break;
                case "Float":
                    float temp_float = (float) value;
                    sb.append(temp_float);
                    break;
                case "Double":
                    double temp_double = (double) value;
                    sb.append(temp_double);
                    break;
            }
            sb.append("<" + key + ">");
            sb.append("</" + methodName + ">\n");
            sb.append("</soap:Body>\n");
            sb.append("</soap:Envelope>\n");
        }
        try {
            return sb.toString().getBytes("utf-8");
        } catch (Exception e) {
            return null;
        }
    }
}

