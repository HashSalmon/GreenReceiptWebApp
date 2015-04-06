package com.springapp.mvc.ReceiptObjects;

/**
 * Created by jordanwanlass on 4/6/15.
 */
public class ReceiptImageObject {
    private Integer ReceiptId;
    private String FileName;
    private String Base64Image;
    private Integer ImageSize;
    private String FileExtension;

    public Integer getReceiptId() {
        return ReceiptId;
    }

    public void setReceiptId(Integer receiptId) {
        ReceiptId = receiptId;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getBase64Image() {
        return Base64Image;
    }

    public void setBase64Image(String base64Image) {
        Base64Image = base64Image;
    }

    public Integer getImageSize() {
        return ImageSize;
    }

    public void setImageSize(Integer imageSize) {
        ImageSize = imageSize;
    }

    public String getFileExtension() {
        return FileExtension;
    }

    public void setFileExtension(String fileExtension) {
        FileExtension = fileExtension;
    }
}
