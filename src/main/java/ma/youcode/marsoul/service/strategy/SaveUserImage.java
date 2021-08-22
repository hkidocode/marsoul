package ma.youcode.marsoul.service.strategy;

import com.flickr4java.flickr.FlickrException;
import ma.youcode.marsoul.entity.User;
import ma.youcode.marsoul.exception.UpdateImageException;
import ma.youcode.marsoul.service.FlickrService;
import ma.youcode.marsoul.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("userStrategy")
public class SaveUserImage implements Strategy<User> {

    @Autowired
    private UserService userService;

    @Autowired
    private FlickrService flickrService;


    @Override
    public User saveImage(Long id, InputStream image, String title) throws FlickrException {
        User user = userService.getUserById(id);
        String urlImage = flickrService.saveImage(image, title);
        if (!StringUtils.hasLength(urlImage)) {
            throw new UpdateImageException("Image does not save correctly");
        }
        user.setImage(urlImage);
        return userService.updateUser(id, user);
    }
}
