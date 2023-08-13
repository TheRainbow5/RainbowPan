package com.example.rainbowend.Service.Index;

import com.example.rainbowend.Entity.User;

/**
 * Rainbow
 *
 * @DATE:2023/8/7 0007
 */
public interface IndexService {

    User getUserInfo(String email);

    int saveImgUrl(String email,String imgUrl);

    User getUserImageUrl(String email);
}
