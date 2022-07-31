package com.example.demo.validation;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/validation")
public class TestContoller {

    @PostMapping("/test_validation")
    public String test_validation(@Validated @RequestBody LoginForm loginForm, BindingResult result){
        StringBuilder sb = new StringBuilder();
        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                String field = error.getField();
                String message = error.getDefaultMessage();
                sb.append(field + " : " +message);
            }
        } else {
            sb.append(loginForm.toString());
        }

        return sb.toString();
    }
}
