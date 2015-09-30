package model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class LaesningTest {
	Lastbil lastbil1;
	Ordre ordre1;
	Delordre delordre1;
	Rampe rampe1;
	Besoeg besoeg1;
	Laesning laesning1;
	
	@Before
	public void setUp() throws Exception {
		lastbil1 = new Lastbil(1, "Karsten", "1111111", 20100, 100);
		ordre1 = new Ordre(1, TransportMateriale.JULETRAEER, 10000, 0.01, "20131120000000");
		delordre1 = ordre1.createDelordre(10, 40, 10000, "20131120000000", lastbil1);
		rampe1 = new Rampe(1,TransportMateriale.JULETRAEER);
		besoeg1 = lastbil1.createBesoeg(45);
		
		laesning1 = new Laesning(delordre1);
		laesning1.setBesoeg(besoeg1);
		laesning1.setRampe(rampe1);
		laesning1.setForventetStart("20131129135000");
		laesning1.setAktuelStartVaegt(120);
		laesning1.setAktuelSlutVaegt(15320);
		laesning1.setAktuelStart("20131129135200");
		laesning1.setAktuelSlut("20131129143100");
		laesning1.setRampeIkkeFundet(false);
	}

	@Test
	public void testUT1_1() {//denne test tester også om constructoren virker da den går ind i det eneste object som sendes med når constructoren kaldes
		assertEquals(TransportMateriale.JULETRAEER, laesning1.getTransportMateriale());
	}

	@Test
	public void testUT1_2() {
		laesning1.setRampe(null);
		assertEquals(null, laesning1.getRampe());
	}

	@Test
	public void testUT1_3() {
		laesning1.setRampe(null);
		laesning1.setRampe(rampe1);
		assertEquals(rampe1, laesning1.getRampe());
	}

	@Test
	public void testUT1_4() {
		laesning1.setBesoeg(null);
		assertEquals(null, laesning1.getBesoeg());
	}

	@Test
	public void testUT1_5() {
		laesning1.setBesoeg(null);
		laesning1.setBesoeg(besoeg1);
		assertEquals(besoeg1, laesning1.getBesoeg());
	}

	@Test
	public void testUT1_6() {
		laesning1.setAktuelStartVaegt(0);
		assertEquals(0.00, laesning1.getAktuelStartVaegt(),0.01);
	}

	@Test
	public void testUT1_7() {
		laesning1.setAktuelStartVaegt(324);
		assertEquals(324.00, laesning1.getAktuelStartVaegt(),0.01);
	}

	@Test
	public void testUT1_8() {
		laesning1.setForventetStart(null);
		assertEquals(null, laesning1.getForventetStart());
	}

	@Test
	public void testUT1_9() {
		assertEquals("20131129135000", laesning1.getForventetStart());
	}

	@Test
	public void testUT1_10() {
		laesning1.setAktuelStart(null);
		assertEquals("20131129143000", laesning1.beregnForventetSlut());
	}

	@Test
	public void testUT1_11() {
		laesning1.setAktuelStart(null);
		assertEquals(null, laesning1.getAktuelStart());
	}

	@Test
	public void testUT1_12() {
		laesning1.setAktuelStart("20131129175200");
		assertEquals("20131129175200", laesning1.getAktuelStart());
	}

	@Test
	public void testUT1_13() {
		assertEquals("20131129143200", laesning1.beregnForventetSlut());
	}

	@Test
	public void testUT1_14() {
		laesning1.setAktuelSlutVaegt(0);
		assertEquals(0.00, laesning1.getAktuelSlutVaegt(),0.01);
	}

	@Test
	public void testUT1_15() {
		laesning1.setAktuelSlutVaegt(14235);
		assertEquals(14235.00, laesning1.getAktuelSlutVaegt(),0.01);
	}

	@Test
	public void testUT1_16() {
		laesning1.setAktuelSlut(null);
		assertEquals(null, laesning1.getAktuelSlut());
	}

	@Test
	public void testUT1_17() {
		laesning1.setAktuelSlut("20131129143500");
		assertEquals("20131129143500", laesning1.getAktuelSlut());
	}

	@Test
	public void testUT1_18() {
		laesning1.setRampeIkkeFundet(false);
		assertEquals(false, laesning1.getRampeIkkeFundet());
		assertEquals(0, besoeg1.getFejl().size());
	}

	@Test
	public void testUT1_19() {
		laesning1.setRampeIkkeFundet(true);
		assertEquals(true, laesning1.getRampeIkkeFundet());
		assertEquals(1, besoeg1.getFejl().size());
	}

	@Test
	public void testUT1_20() {
		laesning1.setDelordre(null);
		assertEquals(null, laesning1.getDelordre());
	}

	@Test
	public void testUT1_21() {
		laesning1.setDelordre(null);
		laesning1.setDelordre(delordre1);
		assertEquals(delordre1, laesning1.getDelordre());
	}
}