package service;

import exceptions.DBException;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class CommonService {
    public String getJsonWithIntResult(int value) {
        return "{\"result\":"+value+"}";
    }

    public String getJsonWithBooleanResult(boolean value) {
        return "{\"result\":"+value+"}";
    }

    public int createReport(String reportType, String path) throws DBException {
        try {
            XWPFDocument docxModel = new XWPFDocument();

            XWPFParagraph bodyParagraph = docxModel.createParagraph();
            bodyParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun paragraphConfig = bodyParagraph.createRun();
            paragraphConfig.setBold(true);
            paragraphConfig.setFontSize(25);

            paragraphConfig.setText("X-Report");

            File dir = new File(path+"\\reports");
            int free = 1;
            for (String filename : dir.list()) {
                try {
                    int curr = Integer.parseInt(filename.substring(filename.length()-5));
                    if (free <= curr) free = curr + 1;
                } catch (IndexOutOfBoundsException | NumberFormatException ignored) { }
            }

            FileOutputStream outputStream = new FileOutputStream(path+"\\reports\\"+free+".docx");
            docxModel.write(outputStream);
            outputStream.close();

            return free;
        } catch (Exception e) {
            throw new DBException(e);
        }
    }


}
