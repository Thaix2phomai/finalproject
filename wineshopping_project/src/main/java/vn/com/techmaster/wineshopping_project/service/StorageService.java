package vn.com.techmaster.wineshopping_project.service;


import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

@Service
public class StorageService {
    @Value("${upload.path}")
    private String path;

    /*
    Bóc tách file extension từ file name. Ví dụ
    input: pic1.png
    output: png
    */
    public String getFileExtension(String fileName){
        int postOfDot = fileName.lastIndexOf(".");
        if (postOfDot >= 0) {
            return fileName.substring(postOfDot + 1);
        } else {
            return null;
        }
    }
}

