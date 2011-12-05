package me.batizhao.converter;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

import java.io.File;
import java.net.ConnectException;

/**
 * @author: batizhao
 * @since: 11-12-5 下午2:36
 */
public class DocToPdfServices {

    public void doIt(String filename) throws ConnectException {

        File inputFile = new File(filename);
        File outputFile = new File("data/02.pdf");

        // connect to an OpenOffice.org instance running on port 8100
        OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
        connection.connect();

        // convert
        DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
        converter.convert(inputFile, outputFile);

        // close the connection
        connection.disconnect();
    }

    public static void main(String[] args) throws ConnectException {

        DocToPdfServices dtp = new DocToPdfServices();
        dtp.doIt("src/test/resources/02.doc");

        System.out.println("Document has converted.");
    }
}
