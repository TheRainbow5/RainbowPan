package com.example.rainbowend.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Rainbow
 *
 * @DATE:2023/7/31 0031
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserJwt {
    private String data;
    private String password;
}
