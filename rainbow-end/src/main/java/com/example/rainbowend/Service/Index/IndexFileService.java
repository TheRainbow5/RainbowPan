package com.example.rainbowend.Service.Index;

import com.example.rainbowend.Entity.UserFile;

/**
 * Rainbow
 *
 * @DATE:2023/8/10 0010
 */
public interface IndexFileService {
    int saveDir(UserFile userFile);

    UserFile existDir(UserFile userFile);
}
