package com.java017.tripblog.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtils {
        public byte[] getContent(String filePath) throws IOException {
            File file = new File(filePath);
            long fileSize = file.length();
            if (fileSize > Integer.MAX_VALUE) {
                System.out.println("file too big...");
                return null;
            }
            FileInputStream fi = new FileInputStream(file);
            byte[] buffer = new byte[(int) fileSize];
            int offset = 0;
            int numRead = 0;
            while (offset < buffer.length
                    && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
                offset += numRead;
            }
            // 確保所有資料均被讀取
            if (offset != buffer.length) {
                throw new IOException("Could not completely read file "
                        + file.getName());
            }
            fi.close();
            return buffer;
        }
}
