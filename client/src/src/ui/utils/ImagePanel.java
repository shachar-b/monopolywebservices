package ui.utils;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * public class ImagePanel extends JPanel
 * a JPanel which holds an resizeble image
 * @author someone online edited by Omer Shenhar and Shachar Butnaro
 *
 */
public class ImagePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image image;
	private Image scaledImage;
	private int imageWidth = 0;
	private int imageHeight = 0;
	private String imagePath;
	//private long paintCount = 0;
	private boolean retainAspectRatio=false;

	/**
	 * public ImagePanel(String string)
	 * a constructor for an image panel 
	 * @param string - a path for a valid picture
	 */
	public ImagePanel(String string) {
		this(string,false);
	}
	
	/**
	 * public ImagePanel(String string, boolean keepAspectRatio)
	 * a constructor for an image panel 
	 * @param string - a path for a valid picture
	 * @param keepAspectRatio - if set true the image keeps it ratio if set false it doesn't
	 */
	public ImagePanel(String string, boolean keepAspectRatio) {
		super();
		imagePath = string;
		retainAspectRatio = keepAspectRatio;
		try {
			loadImage(string);
		} catch (IOException e) {
			throw new RuntimeException("missing component:  A compunent failed to load an Image from "+string);
		}
		setOpaque(true);
	}

	/**
	 * public ImagePanel getCopy()
	 * @return a new ImagePanel which has the same Image
	 */
	public ImagePanel getCopy()
	{
		return new ImagePanel(imagePath);
	}

	/**
	 * 	public void loadImage(String file) throws IOException 
	 * loads an image into the panel
	 * @param file - a valid path for an image
	 * @throws IOException - if for some reason the image can not be loaded
	 */
	public void loadImage(String file) throws IOException {
		image = ImageIO.read(new File(file));
		imageWidth = image.getWidth(this);
		imageHeight = image.getHeight(this);
		setScaledImage();
	}

	/**
	 * public void scaleImage()
	 * containing frame might call this from formComponentResized
	 * 
	 */
	public void scaleImage() {
		setScaledImage();
	}

	/** public void paintComponent(Graphics g)
	 * 	scales the image and paints it
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if ( scaledImage != null ) {
			scaleImage();
			g.drawImage(scaledImage, 0, 0, this);
		}
	}

	/**
	 * private void setScaledImage()
	 * scales the image according to containing window size
	 */
	private void setScaledImage() {
		if ( image != null ) {

			//use floats so division below won't round
			float iw = imageWidth;
			float ih = imageHeight;
			float pw = this.getWidth();   //panel width
			float ph = this.getHeight();  //panel height

			/* compare some ratios and then decide which side of image to anchor to panel
                   and scale the other side
                   (this is all based on empirical observations and not at all grounded in theory)*/

			//System.out.println("pw/ph=" + pw/ph + ", iw/ih=" + iw/ih);
			if(retainAspectRatio)
			{
				if ( (pw / ph) > (iw / ih) ) { 
					iw = -1; 
					ih = ph; 
				} else { 
					iw = pw; 
					ih = -1; 
				} 
			}
			else
			{
				iw =pw;
				ih = ph;
			}

			//prevent errors if panel is 0 wide or high
			if (iw == 0) {
				iw = -1;
			}
			if (ih == 0) {
				ih = -1;
			}

			scaledImage = image.getScaledInstance(
					new Float(iw).intValue(), new Float(ih).intValue(), Image.SCALE_REPLICATE);

		} else {
			scaledImage = image;
		}

	}
	
	/**
	 * public void setRetainAspectRatio(boolean retain) 
	 * sets the image to keep spectRatio if and only if retain is true
	 * @param retain  - true if the image should keep its original AspectRatio false otherwise
	 */
	public void setRetainAspectRatio(boolean retain) {
		retainAspectRatio=retain;
	}


}