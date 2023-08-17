import java.io.File;
import java.io.IOException;
import ws.schild.jave.AudioAttributes;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.EncodingAttributes;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.VideoAttributes;
public class Test{
    public static void main(String[] args) {

        try {
            File source = new File("/Users/xianhui/programe/work/videopress/test.mp4");
            File target = new File("/Users/xianhui/programe/work/videopress/testout.mp4");

            //VideoConvert.convertMP4(source, target);
            zipVideo(source, target);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void zipVideo(File source, File target) throws IOException {
        try {
            // 音频编码设置
            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("libmp3lame");
            audio.setBitRate(new Integer(64000));
            audio.setChannels(new Integer(1));
            audio.setSamplingRate(new Integer(22050));

            // 视频编码设置
            VideoAttributes video = new VideoAttributes();
            video.setCodec("mpeg4");
            video.setBitRate(new Integer(160000));
            video.setFrameRate(new Integer(15));
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

}
