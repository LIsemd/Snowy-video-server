package cn.lisemd.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class FetchVideoCover {

    private String ffmpegEXE;


    public FetchVideoCover(String ffmpegEXE) {
        super();
        this.ffmpegEXE = ffmpegEXE;
    }

    public void convertor(String videoInputPath, String coverOutputPath) throws Exception {
        // ffmpeg.exe -ss 00:00:01 -i XXX.mp3 -vframes 1 xx.jpg
        List<String> command = new ArrayList<>();
        command.add(ffmpegEXE);

        command.add("-ss");
        command.add("00:00:01");

        command.add("-i");
        command.add(videoInputPath);

        command.add("-vframes");
        command.add("1");

        command.add(coverOutputPath);


        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();
        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);


        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
        }
        if (errorStream != null) {
            errorStream.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (bufferedReader != null) {
            bufferedReader.close();
        }
    }

    public static void main(String[] args) {

        FetchVideoCover ffmpeg = new FetchVideoCover("D:\\FFMpeg\\ffmpeg-4.2.1-win64-static\\bin\\ffmpeg.exe");
        try {
            ffmpeg.convertor("C:\\Users\\ZERO\\Videos\\无声版.mp4",
                    "C:\\Users\\ZERO\\Videos\\new.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
