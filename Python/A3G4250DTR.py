# Distributed with a free-will license.
# Use it any way you want, profit or free, provided it fits in the licenses of its associated works.
# A3G4250DTR
# This code is designed to work with the A3G4250DTR_I2CS I2C Mini Module available from ControlEverything.com.
# https://www.controleverything.com/products

import smbus
import time

# Get I2C bus
bus = smbus.SMBus(1)

# A3G4250DTR address, 0x68(104)
# Select control register1, 0x20(32)
#		0x0F(15)	Output Data rate = 100Hz, Power ON, X , Y, Z-Axis enabled
bus.write_byte_data(0x68, 0x20, 0x0F)
# A3G4250DTR address, 0x68(104)
# Select control register4, 0x23(35)
#		0x00(00)	Data LSB @ lower address, Self test disabled
bus.write_byte_data(0x68, 0x23, 0x00)

time.sleep(0.5)

# A3G4250DTR address, 0x68(104)
# Read data back from 0x28(40), 2 bytes
# X-Axis LSB, X-Axis MSB
data0 = bus.read_byte_data(0x68, 0x28)
data1 = bus.read_byte_data(0x68, 0x29)

# Convert the data
xGyro = data1 * 256 + data0
if xGyro > 32767 :
	xGyro -= 65536

# A3G4250DTR address, 0x68(104)
# Read data back from 0x2A(42), 2 bytes
# Y-Axis LSB, Y-Axis MSB
data0 = bus.read_byte_data(0x68, 0x2A)
data1 = bus.read_byte_data(0x68, 0x2B)

# Convert the data
yGyro = data1 * 256 + data0
if yGyro > 32767 :
	yGyro -= 65536

# A3G4250DTR address, 0x68(104)
# Read data back from 0x2C(44), 2 bytes
# Z-Axis LSB, Z-Axis MSB
data0 = bus.read_byte_data(0x68, 0x2C)
data1 = bus.read_byte_data(0x68, 0x2D)

# Convert the data
zGyro = data1 * 256 + data0
if zGyro > 32767 :
	zGyro -= 65536

# Output data to screen
print "X-Axis of Rotation : %d" %xGyro
print "Y-Axis of Rotation : %d" %yGyro
print "Z-Axis of Rotation : %d" %zGyro
