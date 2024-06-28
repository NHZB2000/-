package com.gaolu.springbootinit.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/gan")
public class FileUploadController {

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        String directory = "XXXXX";
        File destinationFile = new File(directory + "/" + file.getOriginalFilename());

        try {
            file.transferTo(destinationFile);
            // 上传成功，现在执行Python脚本
            executePythonScript();
            return ResponseEntity.ok("文件上传成功: " + destinationFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("无法上传文件");
        }
    }

    private void executePythonScript() {
        try {
            String command = "XXXXXXXXX";
            Process process = Runtime.getRuntime().exec(command);

            // 设置超时时间为30秒
            boolean finished = process.waitFor(30, TimeUnit.SECONDS);
            if (finished && process.exitValue() == 0) {
                System.out.println("Python script executed successfully");
            } else if (finished) {
                System.out.println("Python script execution failed with exit code: " + process.exitValue());
            } else {
                System.out.println("Python script did not finish within 30 seconds and will be terminated");
                process.destroy(); // 尝试终止进程
            }
        } catch (IOException e) {
            System.out.println("Failed to execute the script due to an IO exception");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Execution was interrupted");
            Thread.currentThread().interrupt(); // 重新设置中断状态
            e.printStackTrace();
        }
    }
}

