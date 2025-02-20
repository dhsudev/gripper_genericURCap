import socket
import time

HOST = "0.0.0.0" # The remote host
PORT = 20 # The same port as used by the server
print ("Starting Program")
count = 0

keepGoing = True


s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
s.bind((HOST, PORT)) # Bind to the port
s.listen(5) # Now wait for client connection.
c, addr = s.accept() # Establish connection with client. https://docs.python.org/3/library/socket.html#socket-objects

try:
     actual_state = [0, 42]
     output = bytes(actual_state)  # Example data
     c.send(output)  # Send raw bytes
     actual_state =  list(output)
     print("Send =",actual_state)
     time.sleep(1)  # Wait 1 second

     # Receive
     actual_state = list(c.recv(3))
     print("Received Raw =", actual_state)

     print("Hola bon dia")
     while keepGoing:

         # Ensure we have at least two elements before modifying
         if len(actual_state) > 1:
             match actual_state[0]:  # Switch on first byte
                 case 0:
                     print("STOP!")
                 case 1:
                     actual_state[1] += 1  # Increment the width of the graph
                     print("Incremented the width of the graph:", actual_state[1])
                 case 2:
                     actual_state[1] -= 1  # Decrement the width of the graph
                     print("Decremented the width of the graph:", actual_state[1])
                 case _:
                     print("para")
                     keepGoing = False

        # Convert decimal array back to bytes
         output = bytes(actual_state)
         c.send(output)  # Send modified data back
         print("Sent Updated =", list(output))

         # Receive
         actual_state = list(c.recv(3))
         print("Received Raw =", actual_state)
         time.sleep(1)
except socket.error as socketerror:
    print("Algun error hi ha")
    print(count)
    c.close()
    s.close()
c.close()
s.close()
print ("Program finish")