package ru.netology.utils;

import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Генерация хэш кода
 */
public class HashUtil {

    public static String generateHash(MultipartFile file) throws IOException {
        return DigestUtils.md5DigestAsHex(file.getInputStream());
    }
}
