package service;

import static org.junit.Assert.*;
import java.util.Date;
import model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServiceTestLastbilsAnkomst {

	private ServiceDAO serviceDAO;
	private Service service;
	private Lastbil lastbil9577;
	
	@Before
	public void setUp() throws Exception {
		service = Service.getInstance();
		service.setHidePopups(true);
		serviceDAO = ServiceDAO.getInstance();	
		serviceDAO.setHidePopups(true);
		serviceDAO.createAndStoreSomeObjects();
		lastbil9577 = serviceDAO.createLastbil(9577, "Karsten", "1111111", 20100, 100);
		Ordre ordre1 = serviceDAO.createOrdre(9577, TransportMateriale.JULETRAEER, 10000, 0.01, DateUtil.dateToString(new Date()));
		Delordre delordre1 = ordre1.createDelordre(9577, 40, 10000, DateUtil.dateToString(new Date()), lastbil9577);
	}

	@After
	public void tearDown() throws Exception {
		Delordre dl = lastbil9577.getDelordre();
		Ordre o = dl.getOrdre();
		o.deleteDelordre(dl);
		lastbil9577.setDelordre(null);
		
		serviceDAO.deleteOrdre(o);
		serviceDAO.deleteLastbil(lastbil9577);
		
		service.setHidePopups(false);
		serviceDAO.setHidePopups(false);
	}

	private void forventSucces(Lastbil lastbil, int hviletid) {
		assertEquals(1, lastbil.getBesoeg().size());
		Besoeg besoeg = lastbil.getBesoeg().last();
		Laesning laesning = besoeg.getLaesning();
		assertNotNull(laesning);
		assertNotNull(laesning.getDelordre());
		assertEquals(hviletid, besoeg.getHviletid());
		assertNotNull(laesning.getRampe());
		assertNotNull(laesning.getForventetStart());
		assertEquals(TrailerStatus.ANKOMMET, lastbil.getTrailerStatus());
	}
	
	@Test
	public void testUT2_1() {
		service.registrerLastbilsAnkomst(9577, 45);
		forventSucces(lastbil9577, 45);
	}
	
	@Test
	public void testUT2_2() {
		service.registrerLastbilsAnkomst(9577, 1);
		forventSucces(lastbil9577, 1);
	}
	
	@Test
	public void testUT2_3() {
		service.registrerLastbilsAnkomst(9577, 0);
		forventSucces(lastbil9577, 0);
	}
	
	@Test (expected = RuntimeException.class)
	public void testUT2_4() {
		service.registrerLastbilsAnkomst(0, 45);
	}
	
	@Test (expected = RuntimeException.class)
	public void testUT2_5() {
		service.registrerLastbilsAnkomst(9577, -1);
	}
	
	@Test (expected = RuntimeException.class)
	public void testUT2_6() {
		service.registrerLastbilsAnkomst(321, 45);
	}
	
	@Test
	public void testUT2_7() {
		for(Rampe rampe : ServiceDAO.getInstance().getAllRamper()) {
			rampe.setRampeStatus(RampeStatus.STOPPET);
		}
		service.registrerLastbilsAnkomst(9577, 45);
		
		assertEquals(1, lastbil9577.getBesoeg().size());
		Besoeg besoeg = lastbil9577.getBesoeg().last();
		Laesning laesning = besoeg.getLaesning();
		assertNotNull(laesning);
		assertNotNull(laesning.getDelordre());
		assertEquals(45, besoeg.getHviletid());
		assertNull(laesning.getRampe());
		assertTrue(laesning.getRampeIkkeFundet());
		assertEquals(1, besoeg.getFejl().size());
		assertEquals(Fejl.RAMPE_IKKE_FUNDET, besoeg.getFejl().get(0));
		assertEquals(TrailerStatus.ANKOMMET, lastbil9577.getTrailerStatus());
	}
}