package me.batizhao.thumbnail;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;

/**
 * @author: batizhao
 * @since: 11-11-28 下午5:02
 */
public class ThumbnailServices {

    public void doIt(String fileName) {

        try {
            Thumbnails.of(new File(fileName))
                    .size(160, 160)
                    .toFile(new File("data/thumbnail.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        ThumbnailServices services = new ThumbnailServices();
        services.doIt("src/test/resources/01.jpg");

    }

}
