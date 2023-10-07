package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.service.amazonService.AwsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/images")
public class AwsController {

  private final AwsService awsService;

  @PostMapping("/upload")
  public void uploadImage(@RequestParam("file") MultipartFile file) {
    try {
      awsService.uploadFile(file.getInputStream(), file.getOriginalFilename());
    } catch (IOException e) {
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
