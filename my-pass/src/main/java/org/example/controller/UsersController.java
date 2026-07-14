package org.example.controller;

import org.example.entity.Users;
import org.example.result.R;
import org.example.service.UsersService;
import org.example.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * 用户接口
 *
 * <p></p>
 *
 * @author WenLong
 * @version 1.0.0
 * @date 2026/7/14 09:39
 * @see UsersController
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService; // 假设存在

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public R<String> register(@RequestBody Map<String, String> params) {
        String username = params.get("userName");
        String password = params.get("passWord");
        if (username == null || password == null) {
            return R.error("用户名和密码不能为空");
        }
        // 检查用户名是否已存在
        if (usersService.findByUserName(username) != null) {
            return R.error("用户名已存在");
        }
        // 加密密码
        String hashed = passwordEncoder.encode(password);
        // 创建用户
        usersService.createUser(username, hashed);
        return R.success("注册成功");
    }

    @PostMapping("/login")
    public R<Map<String, String>> login(@RequestBody Map<String, String> params) {
        String username = params.get("userName");
        String password = params.get("passWord");
        if (username == null || password == null) {
            return R.error("字段错误:未抓取到用户名与密码(userName,passWord)");
        }
        Users user = usersService.findByUserName(username);
        if (user == null || !passwordEncoder.matches(password, user.getPasswordHash())) {
            return R.error("用户名或密码错误");
        }
        // 生成token
        String token = jwtUtil.generateToken(user.getId(), user.getUserName());
        return R.success(Map.of("token", token));
    }

    @GetMapping("/info")
    public R<Map<String, Object>> info(@RequestHeader("X-User-Id") Long userId) {
        // 从请求头获取userId，由AuthFilter注入
        Users user = usersService.findById(userId);
        if (user == null) {
            return R.error("用户不存在");
        }
        return R.success(Map.of(
                "id", user.getId(),
                "userName", user.getUserName(),
                "createdAt", user.getCreatedAt()
        ));
    }
}