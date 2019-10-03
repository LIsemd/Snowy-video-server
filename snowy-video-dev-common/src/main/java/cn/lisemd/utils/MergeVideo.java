package cn.lisemd.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MergeVideo {

    private String ffmpegEXE;

    public MergeVideo(String ffmpegEXE) {
        super();
        this.ffmpegEXE = ffmpegEXE;
    }

    public void convertor(String mp3InputPath, String videoInputPath, double seconds, String videoOutputPath) throws Exception {
        // ffmpeg.exe -i bgm.mp3 -i 天气之子混剪.mp4  -t 10 -y 新的视频.mp4
        // 原视频有音轨，两种方法：
        // 1.需要先删除 ffmpeg.exe -i 有声音.mp4 -c:v copy -an 无声音.mp4
        // 2.若要保留原声合并音视频 ffmpeg -i bgm.mp3 -i input.mp4 -t 6 -filter_complex amix=inputs=2 output.mp4
        List<String> command = new ArrayList<>();
        command.add(ffmpegEXE);

        command.add("-i");
        command.add(mp3InputPath);

        command.add("-i");
        command.add(videoInputPath);

        command.add("-t");
        command.add(String.valueOf(seconds));

        command.add("-filter_complex");
        command.add("amix=inputs=2");

        command.add(videoOutputPath);

//        for (String c : command) {
//            System.out.println(c + " ");
//        }

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

        MergeVideo ffmpeg = new MergeVideo("D:\\FFMpeg\\ffmpeg-4.2.1-win64-static\\bin\\ffmpeg.exe");
        try {
            ffmpeg.convertor("D:\\CloudMusic\\水月陵 - 死者の嘆き.mp3",
                    "C:\\Users\\ZERO\\Videos\\无声版.mp4",
                    10.1,
                    "C:\\Users\\ZERO\\Videos\\天气之子.mp4");
        } catch (Exception e) {
            e.printStackTrace();
        }

//        System.out.println("转换成功");

    }

}
