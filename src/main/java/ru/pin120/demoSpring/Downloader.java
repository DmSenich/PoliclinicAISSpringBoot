package ru.pin120.demoSpring;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

final public class Downloader {
    private static String REPORTS_DIR = System.getProperty("user.dir") + "\\reports";

    static public ResponseEntity<Resource> downloadFile(String fileName) throws FileNotFoundException {
        String filePath = REPORTS_DIR +File.separator+ fileName;

        File file = new File(filePath);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);

    }
//    @PostMapping("file/{fileName}")
//    public String load(Model model){
//        String url = model.getAttribute("url").toString();
//        return url;
//    }
}
