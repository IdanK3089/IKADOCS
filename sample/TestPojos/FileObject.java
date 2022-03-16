package sample.TestPojos;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.io.File;

public class FileObject extends RecursiveTreeObject<FileObject> {

    String name;

    java.io.File File;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.io.File getFile() {
        return File;
    }

    public void setFile(java.io.File file) {
        File = file;
    }
}
