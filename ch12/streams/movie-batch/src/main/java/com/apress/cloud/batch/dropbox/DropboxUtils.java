package com.apress.cloud.batch.dropbox;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Log4j2
@Component
public class DropboxUtils {

    private DbxClientV2 client = null;
    private DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/scdf-imdb").build();

    public void fromUrlToDropBox(String fromUrl, String filename, String dropboxToken, String dropboxPath, String tmpFolder) throws DbxException, IOException {
        log.debug("Attempting to download: {}" , fromUrl);
        this. client = new DbxClientV2(config, dropboxToken);
        FileUtils.copyURLToFile(new URL(fromUrl), new File(tmpFolder + filename), 10000, 10000);

        InputStream in = new FileInputStream(tmpFolder + filename);
        log.debug("Attempting to Save to Dropbox in: {}", dropboxPath + filename);
        client.files()
                .uploadBuilder(dropboxPath + filename)
                .uploadAndFinish(in);
        log.debug("Uploaded to Dropbox");

        log.debug("Removing temporal file: {}", tmpFolder + filename);
        FileUtils.deleteQuietly(new File(tmpFolder + filename));

    }
}
