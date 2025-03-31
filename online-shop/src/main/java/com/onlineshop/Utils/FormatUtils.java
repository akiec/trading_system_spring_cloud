package com.onlineshop.Utils;


import io.netty.util.internal.StringUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

public class FormatUtils {
    //手机号是否符合规则
    public static Boolean isPhoneInvalid(String phone) {
        return mismatch(phone, RegexPatterns.PHONE_REGEX);
    }
    //邮箱是否符合规则
    public static Boolean isEmailInvalid(String email) {
        return mismatch(email, RegexPatterns.EMAIL_REGEX);
    }
    //密码是否符合规则
    public static Boolean isPasswordInvalid(String password) {
        return mismatch(password, RegexPatterns.PASSWORD_REGEX);
    }
    //验证码是否符合规则
    public static Boolean isVerifyCodeInvalid(String code) {
        return mismatch(code,RegexPatterns.VERIFY_CODE_REGEX);
    }
    //匹配
    public static Boolean mismatch(String str,String regex){
        if (StrUtil.isBlank(str)) {
            return true;
        }
        return !str.matches(regex);
    }
}
