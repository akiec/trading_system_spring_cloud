package com.onlineshop.Service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.RandomUtil;
import com.onlineshop.DTO.LoginDTO;
import com.onlineshop.DTO.Result;
import com.onlineshop.DTO.UserDTO;
import com.onlineshop.Mapper.AddressMapper;
import com.onlineshop.Mapper.UserMapper;
import com.onlineshop.Service.UserService;
import com.onlineshop.Utils.FormatUtils;
import com.onlineshop.Utils.IdCreater;
import com.onlineshop.Utils.NameContains;
import com.onlineshop.Utils.UserHolder;
import com.onlineshop.entity.User;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.onlineshop.Utils.NameContains.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserHolder userHolder;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private IdCreater idCreater;
    @Resource
    private AddressMapper addressMapper;
    //账号密码登录
    @Override
    public Result login(LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        String temToken = (String) stringRedisTemplate.opsForHash().get(LOGIN_IN, username);
        if (temToken == null) {
            User user = userMapper.queryByName(username);
            if(user == null) {
                return Result.error("用户名不存在请注册");
            }else if(!user.getPassWord().equals(password)) {
                return Result.error("用户名或密码错误");
            }
            //校验完成返回token
            String token = user.getUserId().toString();
            //写入redis
            Map<String, Object> usermap = BeanUtil.beanToMap(user,new HashMap<>(), CopyOptions.create().setIgnoreNullValue(true).
                    setFieldValueEditor((fieldName,fieldValue)->{
                        if (fieldValue == null){
                            fieldValue = "0";
                        }else {
                            fieldValue = fieldValue.toString();
                        }
                        return fieldValue;
                    }));
            stringRedisTemplate.opsForHash().putAll(LOGIN_IN+token,usermap);
            stringRedisTemplate.expire("login:token:"+token,30, TimeUnit.MINUTES);
            return Result.success(token);
        }
        return Result.success(temToken);
    }
    //验证码登录
    @Override
    public Result loginByPhone(LoginDTO loginDTO) {
        String phone = loginDTO.getPhone();
        //查询Redis中的验证码
        String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE + phone);
        String code = loginDTO.getCode();
        if (cacheCode == null || !code.equals(cacheCode)) {
            return Result.error("验证码错误");
        }
        //查询用户
        User user = userMapper.queryByPhone(phone);
        if (user==null) {
            user = createUserByPhone(phone);
        }
        //返回token
        String token = user.getUserId().toString();
        Map<String, Object> usermap = BeanUtil.beanToMap(user,new HashMap<>(), CopyOptions.create().setIgnoreNullValue(true).
                setFieldValueEditor((fieldName,fieldValue)->{
                    if (fieldValue == null){
                        fieldValue = "0";
                    }else {
                        fieldValue = fieldValue.toString();
                    }
                    return fieldValue;
                }));
        stringRedisTemplate.opsForHash().putAll(LOGIN_IN+token,usermap);
        stringRedisTemplate.expire("login:token:"+token,30, TimeUnit.MINUTES);
        return Result.success(token);
    }
    //发送验证码
    @Override
    public Result sendCode(String phone) {
        //核验手机号是否符合规则

        if (FormatUtils.isPhoneInvalid(phone)) {
            return Result.error("手机号不符合格式");
        }

//        if (!FormatUtils.isPhoneInvalid(phone)) {
//            return Result.error("手机号不符合格式");
//        }

        //生成验证码
        String code = RandomUtil.randomNumbers(6);
        //写入redis
        stringRedisTemplate.opsForValue().set(LOGIN_CODE+phone, code, 30, TimeUnit.MINUTES);
        //发送验证码
        //没钱啊，直接输出log吧
        log.info("发送验证码成果，验证码为："+code);
        return Result.success();
    }
    //查询用户信息
    @Override
    public Result userDetails(String userId) {
        User user = userMapper.queryByID(userId);
        UserDTO userDTO = UserDTOCopy(user);;
        return Result.success(userDTO);
    }
    //用户信息维护
    @Override
    public Result updateUser(UserDTO userDTO) {
        User user = new User();
        BeanUtil.copyProperties(userDTO, user,CopyOptions.create().setIgnoreNullValue(true));
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateUser(user);
        return Result.success();
    }

    @Override
    public Result changeAddress(UserDTO userDTO) {
        addressMapper.changeAddress(userDTO);
        return Result.success();
    }
    //注册用户
    @Override
    public Result registerUser(LoginDTO loginDTO) {
        User userdata = userMapper.queryByName(loginDTO.getUsername());
        if (userdata != null) {
            return Result.error("用户已经存在");
        }
        User user = new User();
        String id = idCreater.createId(USER_ID).toString();
        user.setUserId(id);
        user.setIsAdmin(false);
        //生成随机的账号密码
        user.setUserName(loginDTO.getUsername());
        user.setPassWord(loginDTO.getPassword());
        //创建时间和修改时间
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.createUser(user);
        return Result.success();
    }

    //实体复制DTO
    private UserDTO UserDTOCopy(User user) {
        UserDTO userDTO = new UserDTO();
        BeanUtil.copyProperties(user,userDTO,CopyOptions.create().setIgnoreNullValue(true));
        return userDTO;
    }
    //根据手机号创建用户
    private User createUserByPhone(String phone) {
        User user = new User();
        user.setPhone(phone);
        String id = idCreater.createId(USER_ID).toString();
        user.setUserId(id);
        user.setIsAdmin(false);
        //生成随机的账号密码
        user.setUserName(RandomUtil.randomNumbers(9));
        user.setPassWord(RandomUtil.randomNumbers(6));
        //创建时间和修改时间
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.createUser(user);
        return user;
    }


}
