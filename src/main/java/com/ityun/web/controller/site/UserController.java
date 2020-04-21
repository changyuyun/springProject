package com.ityun.web.controller.site;

import com.ityun.base.lang.Result;
import com.ityun.modules.entity.User;
import com.ityun.modules.group.UserEdit;
import com.ityun.web.controller.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 用户相关接口
 */
@RestController
@RequestMapping("/api/user")
@Validated
public class UserController extends BaseController {

    @GetMapping("/info")
    public Result info(@NotBlank(message = "token is must") String token) {
        Object tokenInfo = getTokenInfo(token);
        if (tokenInfo == null) {
            return Result.failure("not login status");
        }
        return Result.success("ok", tokenInfo);
    }

    @PostMapping("edit")
    public Result edit(@Validated({UserEdit.class}) @RequestBody(required = true) User user) {
        return executeEdit(user);
    }

    @PostMapping("avatar")
    public Result avatar(@RequestParam(value = "file") MultipartFile file) {
        ArrayList<String> type = new ArrayList<>();
        type.add("image/png");
        type.add("image/gif");
        type.add("image/jpg");
        if( file.isEmpty() ){
            return Result.failure("file can not been empty");
        }
        String contentType = file.getContentType();
        long size = file.getSize();
        if(!type.contains(contentType)) {
            return Result.failure("file type is error");
        }
        if (size/(1024*1024) > 1) {
            return Result.failure("file size is must low at 1M");
        }
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        String filePath = "D:\\temp-data\\avatar\\";
        fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);
        if (!dest.exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            return Result.failure(e.getMessage());
        }
        Map<String, String> res = new HashMap<>();
        res.put("filename", filePath+fileName);
        return Result.success("ok", res);
    }
}
