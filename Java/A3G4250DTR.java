// Distributed with a free-will license.
// Use it any way you want, profit or free, provided it fits in the licenses of its associated works.
// A3G4250DTR
// This code is designed to work with the A3G4250DTR_I2CS I2C Mini Module available from ControlEverything.com.
// https://www.controleverything.com/products

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;

public class A3G4250DTR
{
	public static void main(String args[]) throws Exception
	{
		// Create I2C bus
		I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
		// Get I2C device, A3G4250DTR I2C address is 0x68(104)
		I2CDevice device = bus.getDevice(0x68);

		// Select control register1
		// X, Y and Z Axis enable, power on mode, data rate o/p 100 Hz
		device.write(0x20, (byte)0x0F);
		// Select control register4
		// Data LSB @lower address, Self test disabled
		device.write(0x23, (byte)0x00);
		Thread.sleep(500);

		// Read 6 bytes of data
		// xGyro lsb, xGyro msb, yGyro lsb, yGyro msb, zGyro lsb, zGyro msb
		byte[] data = new byte[6];
		data[0] = (byte)device.read(0x28);
		data[1] = (byte)device.read(0x29);
		data[2] = (byte)device.read(0x2A);
		data[3] = (byte)device.read(0x2B);
		data[4] = (byte)device.read(0x2C);
		data[5] = (byte)device.read(0x2D);

		// Convert the data
		int xGyro = ((data[1] & 0xFF) * 256 + (data[0] & 0xFF));
		if(xGyro > 32767)
		{
			xGyro -= 65536;
		}	

		int yGyro = ((data[3] & 0xFF) * 256 + (data[2] & 0xFF));
		if(yGyro > 32767)
		{
			yGyro -= 65536;
		}

		int zGyro = ((data[5] & 0xFF) * 256 + (data[4] & 0xFF));
		if(zGyro > 32767)
		{
			zGyro -= 65536;
		}

		// Output data to screen
		System.out.printf("X-Axis of Rotation : %d %n", xGyro);
		System.out.printf("Y-Axis of Rotation : %d %n", yGyro);
		System.out.printf("Z-Axis of Rotation : %d %n", zGyro);
	}
}
