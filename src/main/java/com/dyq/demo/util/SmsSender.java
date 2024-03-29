package com.dyq.demo.util;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;

import java.io.IOException;

/**
 * @author dengyouquan
 * @createTime 2019-05-10
 **/
public class SmsSender {
    // 短信应用SDK AppID
    private static int appid = 1400208398; // 1400开头

    // 短信应用SDK AppKey
    private static String appkey = "aff75ac6e1d8dc26f171b4e2491ca5da";

    // 短信模板ID，需要在短信应用中申请
    private static int templateId = 328790; // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请

    // 签名
    private static String smsSign = "实践小站"; // NOTE: 这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台中申请，另外签名参数使用的是`签名内容`，而不是`签名ID`

    public static boolean send(String phoneNumber, String phoneCode, long validTime) {
        String[] params = {phoneCode, validTime + ""};
        try {
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumber,
                    templateId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
            System.out.println(result);
            return true;
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        } finally {
            return false;
        }
    }
}
