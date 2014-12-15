package image;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class ImageLocation { //Denna klass används som en pekare för vart filerna ligger
	
	static public Image load(String img){
		
		return new Image(Display.getDefault(), ImageLocation.class.getResourceAsStream(img));
	}

}
