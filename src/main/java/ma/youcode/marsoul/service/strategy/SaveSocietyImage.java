package ma.youcode.marsoul.service.strategy;

import com.flickr4java.flickr.FlickrException;
import ma.youcode.marsoul.entity.Society;
import ma.youcode.marsoul.exception.UpdateImageException;
import ma.youcode.marsoul.service.FlickrService;
import ma.youcode.marsoul.service.SocietyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("societyStrategy")
public class SaveSocietyImage implements Strategy<Society> {

    @Autowired
    private SocietyService societyService;

    @Autowired
    private FlickrService flickrService;


    @Override
    public Society saveImage(Long id, InputStream image, String title) throws FlickrException {
        Society society = societyService.getSocietyById(id);
        String urlImage = flickrService.saveImage(image, title);
        if (!StringUtils.hasLength(urlImage)) {
            throw new UpdateImageException("Image does not save correctly");
        }
        society.setImage(urlImage);
        return societyService.updateSociety(id, society);
    }
}
