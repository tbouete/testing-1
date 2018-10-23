package testing_base;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;



@RunWith (MockitoJUnitRunner.class)
class TestMyPoint {

	private double valX = Math.random();
	private double valY = Math.random();
	
	private MyPoint pointConstructor1 = new MyPoint();
	private MyPoint pointConstructor2 = new MyPoint(valX, valY);
	private MyPoint pointConstructor3 = new MyPoint(pointConstructor2);
	private MyPoint pointConstructor3Null = new MyPoint(null);
	
	private Random mockRandom1;
	private Random mockRandom2;
	
	private ITranslation mockTranslation;
	
	@Before
	void setUp() {
		System.out.println("setUp");
	}
	
	@Test
	void testConstructor1() {	
		
		assertEquals(0, pointConstructor1.getX());
		assertEquals(0, pointConstructor1.getY());		
	}
	
	
	@Test
	void testConstructor2() {
		assertEquals(valX, pointConstructor2.getX());
		assertEquals(valY, pointConstructor2.getY());		
	}
	
	@Test
	void testConstructor3NullParam() {
		/*
		 * testing that the X and Y parameters are correctly set to 0
		 * if the MyPoint used by the constructor is null
		 */
		assertEquals(0d, pointConstructor3Null.getX());
		assertEquals(0d, pointConstructor3Null.getY());
		
	}
	
	@Test
	void testConstructor3RegularPoint(){
		assertEquals(valX, pointConstructor3.getX());
		assertEquals(valY, pointConstructor3.getY());
		
	}
	
	
	
	@Test
	void testAcesseursX() {
		double newValX =  Math.random();
		
		assertEquals(valX, pointConstructor2.getX());	//Test getter
		pointConstructor2.setX(newValX);
		assertEquals(newValX, pointConstructor2.getX());	//Test setter
	}
	
	@Test
	void testAcesseursY() {
		double newValY = Math.random();
		
		assertEquals(valY, pointConstructor2.getY());	//Test getter
		pointConstructor2.setY(newValY);
		assertEquals(newValY, pointConstructor2.getY());	//Test setter
	}
	
	@Test
	void testSettersWithDoubleNaN() {		
		pointConstructor1.setX(Double.NaN);
		pointConstructor1.setY(Double.NaN);
		
		assertFalse(Double.isNaN(pointConstructor1.getX()));
		assertFalse(Double.isNaN(pointConstructor1.getX()));
	}
	
	@Test
	void testScale() {
		double scalingFactor = Math.random();
		MyPoint testedPoint = pointConstructor2.scale(scalingFactor);
		
		assertEquals(valX * scalingFactor, testedPoint.getX());
		assertEquals(valY * scalingFactor, testedPoint.getY());
		
	}
	
	@Test
	void testHorizontalSymmetryException() {
		try {
			pointConstructor2.horizontalSymmetry(null);
			fail("Une IllegalArgumentException aurait dûe être lancée !");
		} catch(IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	@Test
	void testHorizontalSymmetry() {
		//On effectue la symmetrie horizontale par rapport au point 0/0
		MyPoint symmetrcialPoint = pointConstructor2.horizontalSymmetry(pointConstructor1);
		
		//On vérfie que l'ordonnée du point symmetrique est bien l'opposée de l'ordonnée initiale
		//(soit sa valeur négtive car on effectue la symétrie sur le point 0/0)
		assertEquals(-pointConstructor2.getX(), symmetrcialPoint.getX());
	}
	
	@Test
	void testSetPoint() {
		int randomInt1 = (int) Math.random();
		int randomInt2 = (int) Math.random();
		
		this.mockRandom1 = Mockito.mock(Random.class);
		Mockito.when(mockRandom1.nextInt()).thenReturn(randomInt1);
		
		this.mockRandom2 = Mockito.mock(Random.class);
		Mockito.when(mockRandom2.nextInt()).thenReturn(randomInt2);
		
		pointConstructor1.setPoint(mockRandom1, mockRandom2);
		assertEquals(randomInt1, pointConstructor1.getX());
		assertEquals(randomInt2, pointConstructor1.getY());
		
	}
	
	@Test
	void testTranslate() {
		this.mockTranslation = Mockito.mock(ITranslation.class);
		Mockito.when(mockTranslation.getTx()).thenReturn(0);
		Mockito.when(mockTranslation.getTy()).thenReturn(1);
		
		pointConstructor1.translate(mockTranslation);
		assertEquals(0, pointConstructor1.getX());
		assertEquals(1, pointConstructor1.getY());
		
	}
	
	/*
	@Test
	public void testCentralSymmetryNULL ( ) {
	    new MyPoint(10, 20).centralSymmetry(null);
	}
	*/
	@After
	void tearDown() {
		System.out.println("tearDown");
	}

}
