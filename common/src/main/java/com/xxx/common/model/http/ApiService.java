package com.xxx.common.model.http;


import com.xxx.common.model.http.bean.AppVersionBean;
import com.xxx.common.model.http.bean.LoginBean;
import com.xxx.common.model.http.bean.MyUserInfoBean;
import com.xxx.common.model.http.bean.MyUserStarBean;
import com.xxx.common.model.http.bean.base.BaseBean;
import com.xxx.common.model.http.bean.base.CountyBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    //登录
    @FormUrlEncoded
    @POST("/uc/login")
    Observable<BaseBean<LoginBean>> login(
            @Field("username") String account,
            @Field("password") String password
    );

    //发送短信验证码
    @FormUrlEncoded
    @POST("/uc/mobile/code")
    Observable<BaseBean<Object>> sendSMSCode(
            @Field("phone") String phone,
            @Field("country") String country
    );

    //注册
    @FormUrlEncoded
    @POST("/uc/register/phone_ext")
    Observable<BaseBean<Object>> register(
            @Field("username") String account,
            @Field("phone") String phone,
            @Field("code") String smsCode,
            @Field("password") String password,
            @Field("country") String country,
            @Field("promotion") String promotion
    );

    //忘记密码
    @FormUrlEncoded
    @POST("/uc/reset/login/password")
    Observable<BaseBean<Object>> forgetPsw(
            @Field("account") String account,
            @Field("code") String smsCode,
            @Field("password") String password,
            @Field("mode") String mode
    );

    //发送忘记密码短信验证码
    @FormUrlEncoded
    @POST("/uc/mobile/reset/code")
    Observable<BaseBean<Object>> sendForgetSMSCode(
            @Field("account") String phone,
            @Field("geetest_challenge") String a,
            @Field("geetest_validate") String b,
            @Field("geetest_seccode") String c
    );

    //发送修改登录密码短信验证码
    @POST("/uc/mobile/update/password/code")
    Observable<BaseBean<Object>> sendModifyLoginSMSCode();

    //发送修改资金密码短信验证码
    @POST("/uc/mobile/transaction/code")
    Observable<BaseBean<Object>> sendModifyPaySMSCode();

    //修改登录密码
    @FormUrlEncoded
    @POST("/uc/approve/update/password")
    Observable<BaseBean<Object>> modifyLoginPsw(
            @Field("code") String smsCode,
            @Field("oldPassword") String password,
            @Field("newPassword") String mode
    );

    //修改资金密码
    @FormUrlEncoded
    @POST("/uc/approve/reset/transaction/password")
    Observable<BaseBean<Object>> modifyPayPsw(
            @Field("code") String smsCode,
            @Field("newPassword") String password
    );

    //设置资金密码
    @FormUrlEncoded
    @POST("/uc/approve/transaction/password")
    Observable<BaseBean<Object>> settingPayPsw(
            @Field("jyPassword") String smsCode
    );

    //获取城市编码
    @POST("/uc/support/country")
    Observable<BaseBean<List<CountyBean>>> getCounty();

    //意见反馈
    @FormUrlEncoded
    @POST("/uc/feedback")
    Observable<BaseBean<Object>> submitFeedback(
            @Field("remark") String remark
    );

    //获取用户信息
    @GET("/gene/getMemberById")
    Observable<BaseBean<MyUserInfoBean>> getUserInfo(
            @Query("id") int memberId
    );

    //获取用户信息
    @FormUrlEncoded
    @POST("/gene/gene_backend/getUserStar")
    Observable<BaseBean<MyUserStarBean>> getUserStar(
            @Field("memberId") int memberId
    );

    //上传头像
    @FormUrlEncoded
    @POST("/uc/upload/oss/base64")
    Observable<BaseBean<String>> upLoadIcon(
            @Field("base64Data") String base64Data
    );

    //修改昵称
    @FormUrlEncoded
    @POST("/gene/members/edit")
    Observable<BaseBean<Object>> setUserName(
            @Field("id") int memberId,
            @Field("username") String nickName
    );

    //修改头像
    @FormUrlEncoded
    @POST("/gene/members/edit")
    Observable<BaseBean<Object>> setIcon(
            @Field("id") int memberId,
            @Field("avatar") String icon
    );

    //修改性别
    @FormUrlEncoded
    @POST("/gene/members/edit")
    Observable<BaseBean<Object>> setUserSex(
            @Field("id") int memberId,
            @Field("googleState") Integer googleState
    );

    //检查app版本
    @POST("/uc/ancillary/system/app/version/0")
    Observable<BaseBean<AppVersionBean>> checkVersion();

}