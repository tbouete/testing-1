package testing_base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;



@RunWith (MockitoJUnitRunner.class)
public class TestMyPoint {
	
	private static double DELTA = 0.0005d;

	private double valX = Math.random();
	private double valY = Math.random();
	
	private MyPoint pointConstructor1;
	private MyPoint pointConstructor2;
	private MyPoint pointConstructor3;
	private MyPoint pointConstructor3Null;
	
	private Random mockRandom1;
	private Random mockRandom2;
	
	private ITranslation mockTranslation;
	
	@Before
	public void setUp() {
		pointConstructor1 = new MyPoint();
		pointConstructor2 = new MyPoint(valX, valY);
		pointConstructor3 = new MyPoint(pointConstructor2);
		pointConstructor3Null = new MyPoint(null);
	}
	
	@After
	public void tearDown() {
		pointConstructor1 = null;
		pointConstructor2 = null;
		pointConstructor3 = null;
		pointConstructor3Null = null;
	}
	
	@Test
	public void testConstructor1() {		
		//Testing that x and y are both set to 0
		assertEquals(0, pointConstructor1.getX(), DELTA);
		assertEquals(0, pointConstructor1.getY(), DELTA);		
	}
	
	
	@Test
	public void testConstructor2() {
		//Testing that x and y are both set to the parameter values
		assertEquals(valX, pointConstructor2.getX(), DELTA);
		assertEquals(valY, pointConstructor2.getY(), DELTA);		
	}
	
	@Test
	public void testConstructor3NullParam() {
		/*
		 * testing that the X and Y parameters are correctly set to 0
		 * if the MyPoint used by the constructor is null
		 */
		assertEquals(0d, pointConstructor3Null.getX(), DELTA);
		assertEquals(0d, pointConstructor3Null.getY(), DELTA);
		
	}
	
	@Test
	public void testConstructor3RegularPoint(){
		//X and y of the new MyPoint should be the same as the one from the parameter MyPoint
		assertEquals(valX, pointConstructor3.getX(), DELTA);
		assertEquals(valY, pointConstructor3.getY(), DELTA);
		
	}
	
	
	@Test 
	public void testGetterX(){
		assertEquals(valX, pointConstructor2.getX(), DELTA);
	}
	
	@Test
	public void testSetterX() {
		double newValX =  Math.random();
		pointConstructor2.setX(newValX);
		assertEquals(newValX, pointConstructor2.getX(), DELTA);
	}
	
	@Test
	public void testGetterY() {
		assertEquals(valY, pointConstructor2.getY(), DELTA);
	}
	
	@Test
	public void testSetterY() {
		double newValY = Math.random();		
		pointConstructor2.setY(newValY);
		assertEquals(newValY, pointConstructor2.getY(), DELTA);
	}
	
	@Test
	public void testSettersWithDoubleNaN() {
		/* Trying to set both x and y to Double.Nan
		 * The modifications should not take place
		 */
		
		
		pointConstructor1.setX(Double.NaN);
		pointConstructor1.setY(Double.NaN);
		
		assertFalse(Double.isNaN(pointConstructor1.getX()));
		assertFalse(Double.isNaN(pointConstructor1.getX()));
	}
	
	@Test
	public void testScale() {
		//La méthode scale doit multiplir x et y par son paramètre
		double scalingFactor = Math.random();
		MyPoint testedPoint = pointConstructor2.scale(scalingFactor);
		
		assertEquals(valX * scalingFactor, testedPoint.getX(), DELTA);
		assertEquals(valY * scalingFactor, testedPoint.getY(), DELTA);
		
	}
	
	@Test
	public void testHorizontalSymmetryException() {
		//Test du lancement de l'exception dans le cas d'un argument null
		try {
			pointConstructor2.horizontalSymmetry(null);
			fail("Une IllegalArgumentException aurait dûe être lancée !");
		} catch(IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testHorizontalSymmetry() {
		//On effectue la symmetrie horizontale par rapport au point 0/0
		MyPoint symmetrcialPoint = pointConstructor2.horizontalSymmetry(pointConstructor1);
		
		//On vérfie que l'ordonnée du point symmetrique est bien l'opposée de l'ordonnée initiale
		//(soit sa valeur négtive car on effectue la symétrie sur le point 0/0)
		assertEquals(-pointConstructor2.getX(), symmetrcialPoint.getX(), DELTA);
	}
	
	@Test
	public void testSetPoint() {
		int randomInt1 = (int) Math.random();
		int randomInt2 = (int) Math.random();
		
		this.mockRandom1 = Mockito.mock(Random.class);
		Mockito.when(mockRandom1.nextInt()).thenReturn(randomInt1);
		
		this.mockRandom2 = Mockito.mock(Random.class);
		Mockito.when(mockRandom2.nextInt()).thenReturn(randomInt2);
		
		pointConstructor1.setPoint(mockRandom1, mockRandom2);
		assertEquals(randomInt1, pointConstructor1.getX(), DELTA);
		assertEquals(randomInt2, pointConstructor1.getY(), DELTA);
		
	}
	
	@Test
	public void testTranslate() {
		this.mockTranslation = Mockito.mock(ITranslation.class);
		Mockito.when(mockTranslation.getTx()).thenReturn(0);
		Mockito.when(mockTranslation.getTy()).thenReturn(1);
		
		pointConstructor1.translate(mockTranslation);
		assertEquals(0, pointConstructor1.getX(), DELTA);
		assertEquals(1, pointConstructor1.getY(), DELTA);
		
	}
	
	
	@Test
	public void testCentralSymmetryNULL() {
		//Test du lancement de l'exception dans le cas d'un argument null
		try {
		    new MyPoint(10, 20).centralSymmetry(null);
			fail("Une IllegalArgumentException aurait dûe être lancée !");
		}
	    catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
}
