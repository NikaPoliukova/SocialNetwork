package org.example.controller;

import org.example.service.amazonService.AwsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/images")
public class AwsController {

  private final AwsService awsService;

  @Autowired
  public AwsController(AwsService awsService) {
    this.awsService = awsService;
  }

  @PostMapping("/upload")
  public void uploadImage(@RequestParam("file") MultipartFile file) {
    try {
      awsService.uploadFile(file.getInputStream(), file.getOriginalFilename());
    } catch (IOException e) {
      // Обработка ошибки загрузки файла
      e.printStackTrace();
    }
  }

  @GetMapping("/{imageName}")
  public URI getImage(@PathVariable String imageName) throws URISyntaxException {
    return awsService.getImagePath(imageName);
  }

  @DeleteMapping("/{imageName}")
  public void deleteImage(@PathVariable String imageName) {
    awsService.deleteImage(imageName);
  }
}
