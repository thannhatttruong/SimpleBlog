/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truongtn.utils;
import org.apache.http.client.fluent.Request;
/**
 *
 * @author truongtn
 */
public class NetUtils {
    public static String GetReusult(String url) throws Exception{
        try {
            return Request.Get(url).setHeader("Accept-Charset", "utf-8")
                    .execute().returnContent().asString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}