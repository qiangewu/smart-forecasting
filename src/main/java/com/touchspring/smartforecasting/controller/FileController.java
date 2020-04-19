package com.touchspring.smartforecasting.controller;

import com.touchspring.core.mvc.ui.ResultData;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;


@RestController
public class FileController {

    @RequestMapping("/upload")
    public ResultData imageUpload(@RequestParam(value = "file", required = false) MultipartFile file) {
        String realPath = new File("").getAbsolutePath();
        String suffix = (Objects.requireNonNull(file.getOriginalFilename()).substring
                (file.getOriginalFilename().lastIndexOf("."))).toLowerCase();
        String dateFilePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String canonicalPath = File.separator + "data" + File.separator + dateFilePath;
        String dirPath = realPath + canonicalPath;
        File imagesFile = new File(dirPath);
        if (!imagesFile.exists()) {
            imagesFile.mkdirs();
        }
        String name = System.currentTimeMillis() + suffix;
        String fullImagePath = dirPath + File.separator + name;
        File targetFile = new File(fullImagePath);
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String webPath = canonicalPath.replaceAll("\\\\", "/");
        return ResultData.ok().putDataValue("path", webPath + "/" + name);
    }

    @RequestMapping("/file/{path}/{date}/{fileName}")
    public ResponseEntity<FileSystemResource> imageUpload(@PathVariable("path") String path, @PathVariable("date") String date, @PathVariable("fileName") String fileName) {
        String realPath = new File("").getAbsolutePath();
        File file = new File(realPath + File.separator + path + File.separator + date + File.separator + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + file.getName());
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Last-Modified", new Date().toString());
        headers.add("ETag", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/octet-stream")).body(new FileSystemResource(file));
    }
}
