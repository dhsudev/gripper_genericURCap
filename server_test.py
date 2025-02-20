import socket
import time

HOST = "0.0.0.0" # The remote host
PORT = 30002 # The same port as used by the server
print ("Starting Program")
count = 0

while (count < 1000):
 s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
 s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
 s.bind((HOST, PORT)) # Bind to the port
 s.listen(5) # Now wait for client connection.
 c, addr = s.accept() # Establish connection with client. https://docs.python.org/3/library/socket.html#socket-objects

 try:
     # Send a byte array
     byte_array_send = bytes([10, 20, 30])  # Example byte array
     c.sendall(byte_array_send)  # Send raw bytes
     print("Sent Bytes:", list(byte_array_send))

     # Receive a byte array
     received_data = c.recv(1024)  # Receive up to 1024 bytes
     received_bytes = list(received_data)  # Convert to list for display
     print("Received Bytes:", received_bytes)

     # Modify received bytes (if at least 2 bytes exist)
     if len(received_bytes) > 1:
         received_bytes[1] += 1  # Example modification
         print("Modified Bytes:", received_bytes)

     # Send back modified bytes
     c.sendall(bytes(received_bytes))
     print("Sent Back Modified Bytes:", received_bytes)
 except socket.error as socketerror:
   print("Algun error hi ha")
   print(count)
   c.close()
   s.close()
 c.close()
 s.close()
print ("Program finish")