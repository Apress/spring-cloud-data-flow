package com.apress.cloud.task;


import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Log4j2
public class ImageToDropboxUtils {

    private ImageToDropboxProperties imageToDropboxProperties;
    private DbxClientV2 client;

    public ImageToDropboxUtils(ImageToDropboxProperties imageToDropboxProperties){
        this.imageToDropboxProperties = imageToDropboxProperties;
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/scdf-imdb").build();
        this. client = new DbxClientV2(config, this.imageToDropboxProperties.getApiKey());
    }

    public void fromUrlToDropBox(String fromUrl, String filename) throws DbxException, IOException {
        log.debug("Attempting to download: " + fromUrl);
        FileUtils.copyURLToFile(new URL(fromUrl), new File(this.imageToDropboxProperties.getLocalTmpFolder() + filename), 10000, 10000);

        InputStream in = new FileInputStream(this.imageToDropboxProperties.getLocalTmpFolder() + filename);
        log.debug("Attempting to Save to Dropbox in: {}", this.imageToDropboxProperties.getPath() + filename);
        client.files()
              .uploadBuilder(this.imageToDropboxProperties.getPath() + filename)
              .uploadAndFinish(in);
        log.debug("Uploaded to Dropbox");

        log.debug("Removing temporal file: {}", this.imageToDropboxProperties.getLocalTmpFolder() + filename);
        FileUtils.deleteQuietly(new File(this.imageToDropboxProperties.getLocalTmpFolder() + filename));

    }
}
