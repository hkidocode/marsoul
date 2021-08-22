package ma.youcode.marsoul.service.strategy;

import com.flickr4java.flickr.FlickrException;
import ma.youcode.marsoul.exception.ContextException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class StrategyImageContext {

    @Autowired
    private BeanFactory beanFactory; // to define which context/implementation that I want to use

    private Strategy strategy;
    
    public Object saveImage(String context, Long id, InputStream image, String title) throws FlickrException {
        determineContext(context);
        return strategy.saveImage(id, image, title);
    }

    private void determineContext(String context) {
        final String beanName = context + "Strategy";
        if (context.equals("user")) {
            strategy = beanFactory.getBean(beanName, SaveUserImage.class);
        } else if (context.equals("society")) {
            strategy = beanFactory.getBean(beanName, SaveSocietyImage.class);
        } else {
              throw new ContextException("Unknown context to save image!");
        }
    }
}
