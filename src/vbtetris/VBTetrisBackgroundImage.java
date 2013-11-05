package vbtetris;

import java.awt.image.BufferedImage;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Mode;

public class VBTetrisBackgroundImage extends BufferedImage
{	
	VBTetrisBackgroundImage(BufferedImage img)
	{
        super(img.getWidth(), img.getHeight(), img.getType());
        setData(createBackground(img).getData());
	}
	
	private BufferedImage createBackground(BufferedImage img)
	{		
		return cropBackground(scaleBackground(img));
	}
	
	private BufferedImage scaleBackground(BufferedImage img)
	{
		double widthRatio = (double)getWidth() / VBTetris.BOARD_WIDTH_PX;
		double heightRatio = (double)getHeight() / VBTetris.BOARD_HEIGHT_PX;

		if (widthRatio > heightRatio)
			return Scalr.resize(img, Mode.FIT_TO_HEIGHT, VBTetris.BOARD_HEIGHT_PX);
		else
			return Scalr.resize(img, Mode.FIT_TO_WIDTH, VBTetris.BOARD_WIDTH_PX);
	}
	
	private BufferedImage cropBackground(BufferedImage img)
	{	
		int imgOffset;
		
		if (img.getWidth() > VBTetris.BOARD_WIDTH_PX) {
			imgOffset = (img.getWidth() - VBTetris.BOARD_WIDTH_PX) / 2;
			return img.getSubimage(imgOffset, 0, VBTetris.BOARD_WIDTH_PX, VBTetris.BOARD_HEIGHT_PX);
		} else if (img.getHeight() > VBTetris.BOARD_HEIGHT_PX) {
			imgOffset = (img.getHeight() - VBTetris.BOARD_HEIGHT_PX) / 2;
			return img.getSubimage(0, imgOffset, VBTetris.BOARD_WIDTH_PX, VBTetris.BOARD_HEIGHT_PX);
		} else {
			return img;
		}
	}
		
}
