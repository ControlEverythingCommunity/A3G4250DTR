// Distributed with a free-will license.
// Use it any way you want, profit or free, provided it fits in the licenses of its associated works.
// A3G4250DTR
// This code is designed to work with the A3G4250DTR_I2CS I2C Mini Module available from ControlEverything.com.
// https://www.controleverything.com/products

#include<Wire.h>

// A3G4250DTR I2C address is 0x68(104)
#define Addr 0x68

void setup()
{
	// Initialise I2C communication
	Wire.begin();
	// Initialise Serial Communication
	Serial.begin(9600);

	// Start I2C Transmission
	Wire.beginTransmission(Addr);
  	// Select control register 1
	Wire.write(0x20 );
	// Send data to enable X, Y, Z-Axis
	Wire.write(0x0F);
	// Stop I2C Transmission
	Wire.endTransmission();

	// Start I2C Transmission
	Wire.beginTransmission(Addr);
	// Select control register 4
	Wire.write(0x23);
	// Send data LSB @ lower address
	Wire.write(0x00);
	// Stop I2C Transmission
	Wire.endTransmission();
	delay(300);
}

void loop()
{
	unsigned int data[6];
	for(int i = 0; i < 6; i++)
	{ 
		// Start I2C Transmission
		Wire.beginTransmission(Addr);
		// Select data register
		Wire.write((40+i));
		// Stop I2C Transmission
		Wire.endTransmission();

		// Request 6 byte of data
		// X-Axis lsb, X-Axis msb, Y-Axis lsb, Y-Axis msb, Z-Axis lsb, Z-Axis msb
		Wire.requestFrom(Addr, 1);

    		// Read 1 byte of data
		if(Wire.available() == 1)
		{
			data[i] = Wire.read();
		}
	}

	// Convert the data
	int xGyro = (data[1] * 256 + data[0]);
	int yGyro = (data[3] * 256 + data[2]);
	int zGyro = (data[5] * 256 + data[4]);
  
	// Output data to serial monitor
	Serial.print("X-Axis of Rotation :");
	Serial.println(xGyro);
	Serial.print("Y-Axis of Rotation :");
	Serial.println(yGyro);	
	Serial.print("Z-Axis of Rotation :");
	Serial.println(zGyro);
	delay(1000);
}

