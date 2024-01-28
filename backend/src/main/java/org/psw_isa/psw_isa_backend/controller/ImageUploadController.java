package org.psw_isa.psw_isa_backend.controller;

import org.psw_isa.psw_isa_backend.FormyConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;

@RestController
@RequestMapping("images")
public class ImageUploadController {

    @Autowired
    private FormyConfiguration formyConfiguration;

    @PostMapping("upload")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) {
        String imageDirectory = formyConfiguration.getMediaDir();

        if (!file.getContentType().equals("image/jpeg")) {
            return new ResponseEntity<>("Only JPG images are accepted", HttpStatus.BAD_REQUEST);
        }

        // create path for media files if it does not exist
        File directory = new File(imageDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file!", HttpStatus.BAD_REQUEST);
        }
        try {
            String filename = file.getOriginalFilename();
            String filepath = Paths.get(imageDirectory, filename).toString();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filepath)));
            stream.write(file.getBytes());
            stream.close();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Image uploaded successfully: " + file.getOriginalFilename(), HttpStatus.OK);
    }
}
