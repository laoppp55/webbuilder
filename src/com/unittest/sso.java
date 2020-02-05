package com.unittest;

import com.bjtoon.uia.sdk.UiaConstants;
import com.bjtoon.uia.sdk.client.DefaultUiaClient;
import com.bjtoon.uia.sdk.client.UiaClient;
import com.bjtoon.uia.sdk.domain.AccessTokenVo;
import com.bjtoon.uia.sdk.domain.UserInfoVo;
import com.bjtoon.uia.sdk.exception.UiaException;
import com.bjtoon.uia.sdk.request.UiaApiRequest;
import com.bjtoon.uia.sdk.request.UiaOauthAccessTokenRequest;
import com.bjtoon.uia.sdk.request.UiaOauthUserInfoRequest;
import com.bjtoon.uia.sdk.response.UiaApiResponse;
import com.bjtoon.uia.sdk.response.UiaOauthAccessTokenResponse;
import com.bjtoon.uia.sdk.response.UiaOauthUserInfoResponse;

//北京市统一用户认证平台接口
public class sso {
    public AccessTokenVo getAccessToken(String grantCode) {
        UiaClient uiaClient = new DefaultUiaClient("https://t200renzheng.zhengtoon.com");
        AccessTokenVo accessTokenVo = null;
        try {                                                                                    //获取 access_token
            UiaOauthAccessTokenRequest request = new UiaOauthAccessTokenRequest("/api/oauth/getAccessToken",         //接口路径
                            "100100000131",                                            //应用client_id
                            "27a2cdc6f8a6304087aa8a6d00de8cd6",                  //应用秘钥client_secret
                            grantCode,                                                            // 回调code (刷新refresh_code)
                            UiaConstants.GRANT_TYPE_CODE,                                      //code方式获取access_token
                            // UiaConstants.GRANT_TYPE_REFRESH,                                  //刷新refresh_code方式获取access_token
                            UiaConstants.SCOPE_USER_INFO);                                      //获取用户信息返回scope
            UiaOauthAccessTokenResponse tokenResponse = uiaClient.execute(request);
            if (tokenResponse.isSuccess()) { //调用成功
                accessTokenVo=tokenResponse.getAccessTokenVo();
                System.out.println(accessTokenVo);
            }else { //调用失败
                System.out.println(tokenResponse.getMeta().getMessage());
            }
        } catch (UiaException e) {
            //TODO 处理异常
        }

        return accessTokenVo;
    }

    public UserInfoVo getUserInfo(AccessTokenVo accessTokenVo) {
        UiaClient uiaClient = new DefaultUiaClient("https://t200renzheng.zhengtoon.com");
        UserInfoVo userInfoVo = null;
        try {
            UiaOauthUserInfoRequest uiaOauthUserInfoRequest =
                    new UiaOauthUserInfoRequest("/api/info/getUserInfo",              //接口路径
                            accessTokenVo.getAccess_token(),                                  //令牌 access_token
                            "27a2cdc6f8a6304087aa8a6d00de8cd6");             //应用秘钥client_secret
            //解密返回的身份证号
            UiaOauthUserInfoResponse userInfoResponse = uiaClient.execute(uiaOauthUserInfoRequest, true);
            //不解密返回的身份证号
            //UiaOauthUserInfoResponse userInfoResponse = uiaClient.execute(uiaOauthUserInfoRequest, false);
            if (null != userInfoResponse && userInfoResponse.isSuccess()) {
                userInfoVo = userInfoResponse.getUserInfoVo();
                System.out.println(userInfoVo.toString());
            } else {
                System.out.println(userInfoResponse.getMeta().getMessage());
            }
        } catch (UiaException e) {
            //TODO 处理异常
        }

        return userInfoVo;
    }

    public void getLogout(AccessTokenVo accessTokenVo) {
        UiaClient uiaClient = new DefaultUiaClient("https://t200renzheng.zhengtoon.com");
        //获取用户注销登录接口
        try {UiaApiRequest uiaApiRequest = new UiaApiRequest("/api/login/doNationalSSOLogout", //接口路径
                accessTokenVo.getAccess_token());//令牌
            UiaApiResponse uiaApiResponse = uiaClient.execute(uiaApiRequest);
        } catch (UiaException e) {
            //TODO 处理异常
        }
    }

    public UiaApiResponse getLoginStatus(AccessTokenVo accessTokenVo) {
        UiaApiResponse uiaApiResponse = null;
        UiaClient uiaClient = new DefaultUiaClient("https://t200renzheng.zhengtoon.com");
        //获取用户SSO登录会话状态
        try {
            UiaApiRequest uiaApiRequest = new UiaApiRequest("/api/login/getSSOStatus",      //接口路径
                    accessTokenVo.getAccess_token());                                                //令牌 access_token
            uiaApiResponse = uiaClient.execute(uiaApiRequest);
            if (null != uiaApiResponse) {
                if (uiaApiResponse.isSuccess()) {
                    System.out.println(uiaApiResponse.getData());
                } else {
                    System.out.println(uiaApiResponse.getMeta().getMessage());
                }
            }
        } catch (UiaException e) {

        }

        return uiaApiResponse;
    }
}
