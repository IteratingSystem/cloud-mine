package org.example.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * <p></p>
 *
 * @author WenLong
 * @version 1.0.0
 * @date 2026/7/13 17:00
 * @see TestController
 */
@RestController()
@RequestMapping("/test")
public class TestController {
    @GetMapping("/get")
    public String get() {
        return "这是 GET 请求";
    }
}
