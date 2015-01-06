package org._2585robophiles.frc2015.tests;

import org._2585robophiles.frc2015.RobotMap;
import org._2585robophiles.frc2015.input.InputMethod;
import org._2585robophiles.frc2015.systems.WheelSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for the WheelSystem class
 */
public class WheelSystemTest {

	private TestWheelSystem wheelSystem;
	private double currentNormalMovement, currentSidewaysMovement, currentRotation;
	
	/**
	 * Set up the unit test
	 */
	@Before
	public void setUp() {
		wheelSystem = new TestWheelSystem();
	}

	/**
	 * Test the run() method of the WheelSystem class
	 */
	@Test
	public void testRun() {
		// first make sure we aren't moving without input
		Assert.assertEquals(0, currentSidewaysMovement, 0);
		Assert.assertEquals(0, currentNormalMovement, 0);
		Assert.assertEquals(0, currentRotation, 0);
		
		// test ramping
		wheelSystem.setInput(new InputMethod() {
			
			@Override
			public double sidewaysMovement() {
				return 0;
			}
			
			@Override
			public double rotation() {
				return 0;
			}
			
			@Override
			public double forwardMovement() {
				return 1;
			}
		});
		wheelSystem.run();
		Assert.assertEquals(0, currentRotation, 0);
		Assert.assertEquals(0, currentSidewaysMovement, 0);
		Assert.assertEquals(RobotMap.FORWARD_RAMPING, currentNormalMovement, 0);
		wheelSystem.setInput(new InputMethod() {
			
			@Override
			public double sidewaysMovement() {
				return 0;
			}
			
			@Override
			public double rotation() {
				return 0;
			}
			
			@Override
			public double forwardMovement() {
				return -1;
			}
		});
		wheelSystem.run();
		Assert.assertEquals(0, currentRotation, 0);
		Assert.assertEquals(0, currentSidewaysMovement, 0);
		Assert.assertEquals(RobotMap.FORWARD_RAMPING + (-1 - RobotMap.FORWARD_RAMPING) * RobotMap.FORWARD_RAMPING, currentNormalMovement , 0);
	}

	/**
	 * WheelSystem subclass for the purpose of unit testing
	 */
	private class TestWheelSystem extends WheelSystem{

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.WheelSystem#drive(double, double, double)
		 */
		@Override
		public void drive(double normalMovement, double sidewaysMovement, double rotation) {
			currentNormalMovement = normalMovement;
			currentSidewaysMovement = sidewaysMovement;
			currentRotation = rotation;
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.WheelSystem#setInput(org._2585robophiles.frc2015.input.InputMethod)
		 */
		@Override
		protected synchronized void setInput(InputMethod input) {
			super.setInput(input);
		}
		
	}
}