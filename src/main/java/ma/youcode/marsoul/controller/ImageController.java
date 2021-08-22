package ma.youcode.marsoul.controller;

import com.flickr4java.flickr.FlickrException;
import ma.youcode.marsoul.service.strategy.StrategyImageContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/marsoul/api/v1/images")
public class ImageController {

    @Autowired
    private StrategyImageContext strategyImageContext;

    @PostMapping("/{id}/{title}/{context}")
    public ResponseEntity<?> saveImage(@PathVariable("id") Long id,
                                    @PathVariable("context") String context,
                                    @PathVariable("title") String title,
                                    @RequestPart("file") MultipartFile image) throws IOException, FlickrException {
        Object targetedImage = strategyImageContext.saveImage(context, id, image.getInputStream(), title);
        return ResponseEntity.ok().body(targetedImage);
    }

}
