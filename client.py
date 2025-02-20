import socket
import time

HOST = "127.0.0.1"  # Change this to the server's IP if running remotely
PORT = 20# Same port as the server
#for _ in range(5):  # Try 5 times before giving up
try:
    # Create a socket and connect to the server
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.connect((HOST, PORT))
    print("Connected to server.")

    # Receive a byte array from the server
    received_data = s.recv(3)  # Receive up to 3 bytes
    received_bytes = list(received_data)  # Convert to list for easier manipulation
    print("Received Bytes from Server:", received_bytes)

    print("hello " , bytes([3, 211]))


    decrement = 3
    while decrement > 0:
        decrement -= 1
        # Ensure there's data to modify
        if len(received_bytes) > 1:
            # Modify the first byte to simulate different cases
            print("Hello")
            received_bytes[0] = 2 # 0 = STOP, 1 = Increment, 2 = Decrement

            # Send modified byte array back to the server
            s.sendall(bytes(received_bytes))
            print("Sent Modified Bytes to Server:", received_bytes)

            # Receive the updated response from the server
            final_data = s.recv(3)  # Receive the modified respo
            final_bytes = list(final_data)
            print("Final Bytes from Server:", final_bytes)

    decrement = 3
    while decrement > 0:
        decrement -= 1
        # Ensure there's data to modify
        if len(received_bytes) > 1:
            # Modify the first byte to simulate different cases
            print("Hello")
            received_bytes[0] = 1  # 0 = STOP, 1 = Increment, 2 = Decrement

            # Send modified byte array back to the server
            s.sendall(bytes(received_bytes))
            print("Sent Modified Bytes to Server:", received_bytes)

            # Receive the updated response from the server
            final_data = s.recv(3)  # Receive the modified respo
            final_bytes = list(final_data)
            print("Final Bytes from Server:", final_bytes)

    s.sendall(bytes([3, 211]))

except socket.error as socketerror:
    print("Socket error:", socketerror)

finally:
    s.close()
    print("Connection closed.")



