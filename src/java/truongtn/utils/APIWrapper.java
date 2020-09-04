/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truongtn.utils;

import com.google.gson.Gson;
import truongtn.entity.FbUserInfo;

/**
 *
 * @author truongtn
 */
public class APIWrapper {
    private static final String appID = "548837722387883";
    private static final String appSecret = "0c64733d3b1014c096c984522b274522";
    private static final String redirectUrl = "http://localhost:8080/Blog_Lab1.2/MainController?action=loginByFb";
    private String accessToken;
    private Gson gson;

    public APIWrapper() {
        this.gson = new Gson();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public static String getDialogLink(){
        String dialogLink = "https://www.facebook.com/dialog/oauth?client_id=%s&redirect_uri=%s";
        return String.format(dialogLink, appID, redirectUrl);
    }
    
    public String getAccessToken(String code) throws Exception{
        String accessTokenLink = "https://graph.facebook.com/oauth/access_token?"
                + "client_id=%s"
                + "&client_secret=%s"
                + "&redirect_uri=%s"
                + "&code=%s";
        accessTokenLink = String.format(accessTokenLink, appID, appSecret, redirectUrl, code);
        System.out.println("Accesstoken link: " + accessTokenLink);
        String result = NetUtils.GetReusult(accessTokenLink);
        String token = result.substring(result.indexOf(":") + 2, result.indexOf(",") - 1);
        System.out.println("Result: " + result);
        System.out.println("Token: " + token);
        return token;
    }
    
    public FbUserInfo getFbUserInfo() throws Exception{
        String infoUrl = "https://graph.facebook.com/me?fields=email,name,id&access_token=%s";
        infoUrl = String.format(infoUrl, this.accessToken);
        
        String result = NetUtils.GetReusult(infoUrl);
        
        FbUserInfo fbUserInfo = gson.fromJson(result, FbUserInfo.class);
        
        return fbUserInfo;
    }
    
}