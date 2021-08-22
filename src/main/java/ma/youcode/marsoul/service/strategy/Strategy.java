package ma.youcode.marsoul.service.strategy;

import com.flickr4java.flickr.FlickrException;

import java.io.InputStream;

public interface Strategy<T> {
    
    T saveImage(Long id, InputStream image, String title) throws FlickrException;
    
}
