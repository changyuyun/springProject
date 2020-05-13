package com.ityun.web.controller.site;

import com.ityun.base.lang.Consts;
import com.ityun.base.lang.Result;
import com.ityun.base.lang.ResultConst;
import com.ityun.modules.entity.User;
import com.ityun.modules.group.UserEdit;
import com.ityun.modules.group.UserPasswordReset;
import com.ityun.web.annotation.DisableAuth;
import com.ityun.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    HttpServletRequest request;
    @GetMapping("/info")
    public Result info() {
        String token = String.valueOf(request.getAttribute("token"));
        Object tokenInfo = getTokenInfo(token);
        if (tokenInfo == null) {
            return Result.failure(ResultConst.commonCode.COMMON_AUTH_FAILURE, ResultConst.commonMessage.COMMON_AUTH_FAILURE);
        }
        return Result.success(ResultConst.commonMessage.COMMON_SUCCESS, tokenInfo);
    }

    @PostMapping("edit")
    public Result edit(@Validated({UserEdit.class}) @RequestBody(required = true) User user) {
        String token = String.valueOf(request.getAttribute("token"));
        return executeEdit(user, token);
    }

    @PostMapping("avatar")
    @DisableAuth
    public Result avatar(@RequestParam(value = "file") MultipartFile file) {
        ArrayList<String> type = new ArrayList<>();
        type.add("image/png");
        type.add("image/gif");
        type.add("image/jpg");
        if( file.isEmpty() ){
            return Result.failure(ResultConst.fileCode.IMAGE_FILE_EMPTY_ERROR, ResultConst.fileMessage.IMAGE_FILE_EMPTY_ERROR);
        }
        String contentType = file.getContentType();
        long size = file.getSize();
        if(!type.contains(contentType)) {
            return Result.failure(ResultConst.fileCode.IMAGE_FILE_TYPE_ERROR, ResultConst.fileMessage.IMAGE_FILE_TYPE_ERROR);
        }
        if (size/(1024*1024) > 1) {
            return Result.failure(ResultConst.fileCode.IMAGE_FILE_SIZE_ERROR, ResultConst.fileMessage.IMAGE_FILE_SIZE_ERROR);
        }
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        String rootPath = siteConfig.getLocation();
        String filePath = Consts.avatarPath;
        fileName = UUID.randomUUID() + suffixName;
        File dest = new File(rootPath+filePath + fileName);
        if (!dest.exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            return Result.failure(e.getMessage());
        }
        Map<String, String> res = new HashMap<>();
        res.put("filename", siteConfig.getUrl()+filePath+fileName);
        return Result.success(ResultConst.commonMessage.COMMON_SUCCESS, res);
    }

    /**
     * 重置密码
     * @param user
     * @return
     */
    @PostMapping("reset/password")
    public Result resetPassword(@Validated({UserPasswordReset.class}) @RequestBody(required = true) User user) {
        //用户名 旧密码 新密码
        return executePasswordReset(user);
    }
}
