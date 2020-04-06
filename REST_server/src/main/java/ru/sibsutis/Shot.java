package ru.sibsutis;

import java.util.ArrayList;

class Shot {            // {"status": "ok", "files": ["tmp", "tmp1.txt",]}
    private String status;
    private ArrayList<String> files = new ArrayList<>();

    public Shot() { }

    public Shot(String status, ArrayList<String> files) {
        this.status = status;
        this.files = files;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<String> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<String> files) {
        this.files = files;
    }
}
