package top.myfss.lawedu.util;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * 图像工具类
 * Created by Allen on 2017/4/9.
 */
public class ImageUtil {

    /** 水印透明度 */
    private static float alpha = 0.5f;
    /** 水印图片旋转角度 */
    private static double degree = 0f;
    private static int interval = 0;
    private String waterImgPath =  getClass().getResource("/").getFile().toString()+"intermediaryshopWaterMark.png";


    /**
     * 给图片添加水印图片
     *
     * @param waterImgPath
     *            水印图片路径
     * @param srcImgPath
     *            源图片路径
     * @param targerPath
     *            目标图片路径
     */
    public void waterMarkByImg(String waterImgPath, String srcImgPath,
                                      String targerPath) throws Exception {
        waterMarkByImg(waterImgPath, srcImgPath, targerPath, 0);
    }

    /**
     * 给图片添加水印图片
     *
     * @param waterImgPath
     *            水印图片路径
     * @param srcImgPath
     *            源图片路径
     * @param srcImgPath
     *            目标图片路径
     */
    public  void waterMarkByImg(String waterImgPath, String srcImgPath) {
        try {
            waterMarkByImg(waterImgPath, srcImgPath, srcImgPath, 0);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 给图片添加水印图片
     *
     * @param srcImgPath
     *            源图片路径
     */
    public  void waterMarkByImg(String srcImgPath) {
        try {
            waterMarkByImg(waterImgPath, srcImgPath, srcImgPath, 0);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 给图片添加水印图片、可设置水印图片旋转角度
     *
     * @param waterImgPath
     *            水印图片路径
     * @param srcImgPath
     *            源图片路径
     * @param targerPath
     *            目标图片路径
     * @param degree
     *            水印图片旋转角度
     */
    public  void waterMarkByImg(String waterImgPath, String srcImgPath,
                                      String targerPath, double degree) throws Exception {
        OutputStream os = null;
        try {
            Image srcImg = ImageIO.read(new File(srcImgPath));
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
            // 1、得到画笔对象
            Graphics2D g = buffImg.createGraphics();
            // 2、设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg
                    .getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
            // 3、设置水印旋转
            if (0 != degree) {
                g.rotate(Math.toRadians(degree),
                        (double) buffImg.getWidth() / 2, (double) buffImg
                                .getHeight() / 2);
            }
            // 4、水印图片的路径 水印图片一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(waterImgPath);
            // 5、得到Image对象。
            Image img = imgIcon.getImage();
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));
            // 6、水印图片的位置
            for (int height = interval + imgIcon.getIconHeight(); height < buffImg
                    .getHeight(); height = height +interval+ imgIcon.getIconHeight()) {
                for (int weight = interval + imgIcon.getIconWidth(); weight < buffImg
                        .getWidth(); weight = weight +interval+ imgIcon.getIconWidth()) {
                    g.drawImage(img, weight - imgIcon.getIconWidth(), height
                            - imgIcon.getIconHeight(), null);
                }
            }
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            // 7、释放资源
            g.dispose();
            // 8、生成图片
            os = new FileOutputStream(targerPath);
            ImageIO.write(buffImg, "JPG", os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 打印文字水印图片
     * @param pressText 文字
     * @param targetImg 目标图片
     * @param outImg 输出图片
     * @param fontName 字体名
     * @param fontStyle 字体样式
     *                  Font.java
     * @param color 字体颜色
     *              Color.java
     * @param fontSize 字体大小
     * @param x  偏移量
     * @param y
     */
    public void waterMarkByText(String pressText, String targetImg,String outImg,String fontName, int fontStyle, Color color, int fontSize, int x,int y) {
        try {
            File _file = new File(targetImg);
            Image src = ImageIO.read(_file);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);
            g.setColor(color);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.drawString(pressText, x, y);
            g.dispose();
            FileOutputStream out = new FileOutputStream(outImg);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(image);
            out.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        ImageUtil imageUtil = new ImageUtil();
        /*imageUtil.waterMarkByText("中介超市","C:\\Users\\Allen\\Desktop\\新营业执照.jpg",
                "C:\\Users\\Allen\\Desktop\\新营业执照111.jpg","宋体",Font.BOLD,Color.RED,30,30,30);*/
        try {
            imageUtil.waterMarkByImg("C:\\Users\\Allen\\Desktop\\新营业执照.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
