package ma.youcode.marsoul.service.impl;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.uploader.UploadMetaData;
import ma.youcode.marsoul.service.FlickrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class FlickrServiceImpl implements FlickrService {

    @Autowired
    private Flickr flickr;

    @Override
    public String saveImage(InputStream image, String title) throws FlickrException {
        UploadMetaData uploadMetaData = new UploadMetaData();
        uploadMetaData.setTitle(title);
        String imageId = flickr.getUploader().upload(image, uploadMetaData);
        return flickr.getPhotosInterface().getPhoto(imageId).getMedium640Url();
    }

}
