package ma.youcode.marsoul.service;

import com.flickr4java.flickr.FlickrException;

import java.io.InputStream;

public interface FlickrService {

    String saveImage(InputStream image, String title) throws FlickrException;

}
