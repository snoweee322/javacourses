package ru.sibsutis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Thr implements Runnable {

    Socket client;
    public String dir = "C:\\Users\\User\\Desktop\\trashcan";

    Thr (Socket client) {
        this.client = client;
    }


    public String formJSONString() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Shot shot = new Shot();
        shot.setStatus("OK");
        ArrayList<String> temp = new ArrayList<>();
        Files.list(new File(dir).toPath())
                .forEach(path -> {
                    temp.add(path.toString());
                });
        shot.setFiles(temp);
        return mapper.writeValueAsString(shot);
    }

    @Override
    public void run() {
        Scanner in = null;
        try {
            in = new Scanner(client.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter out = null;
        try {
            out = new PrintWriter(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str = in.nextLine();
        System.out.println("CLIENT REQUEST: " + str);
        String JSONString = "";
        int sw = 0;
        if (str.contains("info"))
            sw = 1;
        if (str.contains("delete"))
            sw = 2;
        if (str.contains("copy"))
            sw = 3;
        if (str.contains("move"))
            sw = 4;
        String tmp;
        Scanner scan;
        Shot shot = null;
        ObjectMapper mapper = new ObjectMapper();
        Pattern p;
        Matcher m;
        Integer num;
        switch (sw) {
            case (1): // info
                try {
                    JSONString = formJSONString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                tmp = "HTTP/1.1 200 OK\r\n" +
                "Content-Length: " + JSONString.length() +"\r\n" +
                "Content-Type: application/json\r\n" +
                "\r\n" +
                JSONString;
                System.out.println(tmp);
                out.print(tmp);
                out.flush();
                break;

            case (2): // deletion
                try {
                    JSONString = formJSONString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                p = Pattern.compile("\\d+"); // удаление
                m = p.matcher(str);
                m.find();
                num = Integer.parseInt(m.group());
                System.out.println("TO DELETE: " + num + " STRING: " + str); // отладка
                try {
                    shot = mapper.readValue(JSONString, Shot.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                String fileToDel = shot.getFiles().get(num);
                try {
                    Files.deleteIfExists(Path.of(fileToDel));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    JSONString = formJSONString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                tmp = "HTTP/1.1 200 OK\r\n" +
                        "Content-Length: " + JSONString.length() +"\r\n" +
                        "Content-Type: application/json\r\n" +
                        "\r\n" +
                        JSONString;
                System.out.println(tmp);
                out.print(tmp);
                out.flush();
                break;

            case (3): // copy
                try {
                    JSONString = formJSONString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                p = Pattern.compile("\\d+");
                m = p.matcher(str);
                m.find();
                Integer toCopy = Integer.parseInt(m.group());
                m.find();
                Integer destination = Integer.parseInt(m.group());
                try {
                    shot = mapper.readValue(JSONString, Shot.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                Path toCopyS = Path.of(shot.getFiles().get(toCopy));
                Path destinationS = Path.of(shot.getFiles().get(destination)+"\\"+toCopyS.getFileName());
                System.out.println(toCopyS);
                System.out.println(destinationS);
                try {
                    Files.copy(toCopyS, destinationS, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    JSONString = formJSONString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                tmp = "HTTP/1.1 200 OK\r\n" +
                        "Content-Length: " + JSONString.length() +"\r\n" +
                        "Content-Type: application/json\r\n" +
                        "\r\n" +
                        JSONString;
                System.out.println(tmp);
                out.print(tmp);
                out.flush();
                break;

            case (4): // move
                try {
                    JSONString = formJSONString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                p = Pattern.compile("\\d+");
                m = p.matcher(str);
                m.find();
                Integer toMove = Integer.parseInt(m.group());
                m.find();
                destination = Integer.parseInt(m.group());
                try {
                    shot = mapper.readValue(JSONString, Shot.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                Path toMoveS = Path.of(shot.getFiles().get(toMove));
                destinationS = Path.of(shot.getFiles().get(destination)+"\\"+toMoveS.getFileName());
                System.out.println(toMoveS);
                System.out.println(destinationS);
                try {
                    Files.move(toMoveS, destinationS);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    JSONString = formJSONString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                tmp = "HTTP/1.1 200 OK\r\n" +
                        "Content-Length: " + JSONString.length() +"\r\n" +
                        "Content-Type: application/json\r\n" +
                        "\r\n" +
                        JSONString;
                System.out.println(tmp);
                out.print(tmp);
                out.flush();
                break;

            case (0):
            default:
                tmp = "HTTP/1.1 200 OK\r\n" +
                        "Accept-Ranges: bytes\r\n" +
                        "Content-Length: 52\r\n" +
                        "Connection: close\r\n" +
                        "Content-Type: text/html\r\n" +
                        "\r\n" +
                        "<html><body><h1>This is a server!</h1></body></html>";
                System.out.println(tmp);
                out.print(tmp);
                out.flush();
                break;
        }
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
