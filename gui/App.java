package gui;

import service.ServiceDAO;

public class App {

	public static final boolean useJPA = !true;
	public static final boolean createAndStoreSomeObjects = true;
	
	public static void main(String[] args) {
		if(createAndStoreSomeObjects)
			ServiceDAO.getInstance().createAndStoreSomeObjects();
		new FrameChauffoer().setVisible(true);
		new FrameDCMedarbejder().setVisible(true);		
	}
}