package me.batizhao.thumbnail;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;

/**
 * @author: batizhao
 * @since: 11-11-28 下午5:02
 */
public class Thumbnailator {

    public static void main(String[] args) throws IOException {

        Thumbnails.of(new File("src/test/resources/01.jpg"))
                .size(160, 160)
                .toFile(new File("data/thumbnail.jpg"));

    }

}
