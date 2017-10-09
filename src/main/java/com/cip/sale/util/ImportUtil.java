package com.cip.sale.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class ImportUtil {
	
	public   void importPdf(MultipartFile file,String realPath,long articleId) throws IOException {
	File file2 = new File(realPath+"/"+ articleId+".pdf");
	//FileInputStream f1 = new FileInputStream(file);
	FileOutputStream f2 = new FileOutputStream(file2);
	 // 循环取出流中的数据
    byte[] b = new byte[100];
    int len;
    try {
    //    while ((len = f1.read(b)) > 0)
           // f2.write(b, 0, len);
     //  f1.close();
       f2.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
	}
	
}
