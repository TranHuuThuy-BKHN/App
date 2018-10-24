package sample;

import java.io.File;

public class Audio {
    private File file;

    public Audio(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        if(file.getName().length() > 15){
            return file.getName().substring(0, 8) + "...mp3";
        } else  return file.getName();
    }
}
