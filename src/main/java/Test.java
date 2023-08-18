import java.io.File;
import java.io.IOException;
import ws.schild.jave.AudioAttributes;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.EncodingAttributes;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.VideoAttributes;
import ws.schild.jave.DefaultFFMPEGLocator;
import ws.schild.jave.FFMPEGExecutor;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test{
    public static void main(String[] args) {

        try {
            File source = new File("C:\\Users\\hxian\\Videos\\Captures\\test1.mp4");
            File target = new File("C:\\Users\\hxian\\Videos\\Captures\\test33.mp4");

            //VideoConvert.convertMP4(source, target);
//            zipVideo(source, target);

            zipVideo2(source, target);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * 等待命令执行成功，退出
     *
     * @param br
     * @throws IOException
     */
    private static void blockFfmpeg(BufferedReader br) throws IOException {
        String line;
        // 该方法阻塞线程，直至合成成功
        while ((line = br.readLine()) != null) {
           System.out.println(line);
        }
    }

    public static void  zipVideo2(File source, File target) throws IOException{
        try{
            FFMPEGExecutor ffmpeg = new DefaultFFMPEGLocator().createExecutor();
            ffmpeg.addArgument("-i");
            ffmpeg.addArgument(source.getAbsolutePath());
            ffmpeg.addArgument("-b:v");
            ffmpeg.addArgument("1000k");
            ffmpeg.addArgument("-bufsize");
            ffmpeg.addArgument("1000k");
            ffmpeg.addArgument("-r");
            ffmpeg.addArgument("24");
            ffmpeg.addArgument(target.getAbsolutePath());
            ffmpeg.execute();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(ffmpeg.getErrorStream()))) {
                blockFfmpeg(br);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void zipVideo(File source, File target) throws IOException {
        try {

            // 音频编码设置
            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("libmp3lame");
            audio.setBitRate(new Integer(64000));
            audio.setChannels(new Integer(1));
            audio.setSamplingRate(new Integer(16000));

            // 视频编码设置
            VideoAttributes video = new VideoAttributes();

//            video.setCodec("mpeg4");

            video.setBitRate(new Integer(320000)); //160000

            video.setFrameRate(new Integer(15));
            video.setQuality(7);
            //            video.setSize(new VideoSize(100, 150));

            // 视频转码编码设置
            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setFormat("mp4");
            attrs.setAudioAttributes(audio);
            attrs.setVideoAttributes(video);

            // 编码器
            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject(source), target, attrs);
        } catch (EncoderException e) {
            e.printStackTrace();
        }
    }

    public static void cmd() {
        // FIXME: 2023/1/31  还有很多类似命令 不再一一列举 ，附上命令,具体写法参考 getTargetThumbnail或addSubtitle方法
        // FIXME: 2023/1/31 ffmpeg命令网上搜索即可

        // 剪切视频
        // ffmpeg -ss 00:00:00 -t 00:00:30 -i test.mp4 -vcodec copy -acodec copy output.mp4
        // * -ss 指定从什么时间开始
        // * -t 指定需要截取多长时间
        // * -i 指定输入文件

        // ffmpeg -ss 10 -t 15 -accurate_seek -i test.mp4 -codec copy cut.mp4
        // ffmpeg -ss 10 -t 15 -accurate_seek -i test.mp4 -codec copy -avoid_negative_ts 1 cut.mp4

        // 拼接MP4
        // 第一种方法：
        // ffmpeg -i "concat:1.mp4|2.mp4|3.mp4" -codec copy out_mp4.mp4
        // 1.mp4 第一个视频文件的全路径
        // 2.mp4 第二个视频文件的全路径

        // 提取视频中的音频
        // ffmpeg -i input.mp4 -acodec copy -vn output.mp3
        // -vn: 去掉视频；-acodec: 音频选项， 一般后面加copy表示拷贝

        // 音视频合成
        // ffmpeg -y –i input.mp4 –i input.mp3 –vcodec copy –acodec copy output.mp4
        // -y 覆盖输出文件

        // 剪切视频
        //  ffmpeg -ss 0:1:30 -t 0:0:20 -i input.mp4 -vcodec copy -acodec copy output.mp4
        // -ss 开始时间; -t 持续时间

        // 视频截图
        //  ffmpeg –i test.mp4 –f image2 -t 0.001 -s 320x240 image-%3d.jpg
        // -s 设置分辨率; -f 强迫采用格式fmt;

        // 视频分解为图片
        //   ffmpeg –i test.mp4 –r 1 –f image2 image-%3d.jpg
        // -r 指定截屏频率

        // 将图片合成视频
        //  ffmpeg -f image2 -i image%d.jpg output.mp4

        // 视频拼接
        //  ffmpeg -f concat -i filelist.txt -c copy output.mp4

        // 将视频转为gif
        //    ffmpeg -i input.mp4 -ss 0:0:30 -t 10 -s 320x240 -pix_fmt rgb24 output.gif
        // -pix_fmt 指定编码

        // 视频添加水印
        //  ffmpeg -i input.mp4 -i logo.jpg
        // -filter_complex[0:v][1:v]overlay=main_w-overlay_w-10:main_h-overlay_h-10[out] -map [out] -map
        // 0:a -codec:a copy output.mp4
        // main_w-overlay_w-10 视频的宽度-水印的宽度-水印边距；

    }


}
