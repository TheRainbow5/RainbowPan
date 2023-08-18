package com.example.rainbowend.Service.Index;

import com.example.rainbowend.Entity.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * Rainbow
 *
 * @DATE:2023/8/7 0007
 */
public interface IndexUserService {

    ResponseResult getUserInfo(String email);

    ResponseResult saveImgUrl(String email, MultipartFile multipartFile);

    ResponseResult getUserImageUrl(String email);

    ResponseResult logoffUser(String email);
}
