import asyncio
import websockets

# Puerto en el que escuchará el servidor
PORT = 12345

async def handle_connection(websocket, path):
    print(f"New connection from {websocket.remote_address}")  # Cuando un cliente se conecta

    try:
        # Recibe los 32 bytes del cliente
        data = await websocket.recv()
        
        # Verifica que se recibieron exactamente 32 bytes
        if len(data) == 32:
            print(f"Received data: {data}")  # Imprime los 32 bytes recibidos
            await websocket.send("ACK")  # Responde con un mensaje de éxito
            print("ACK sent back to client.")
        else:
            print(f"Error: Expected 32 bytes, but received {len(data)} bytes.")
            await websocket.send("Error: Invalid data size")  # Responde con un error si el tamaño no es 32 bytes

    except Exception as e:
        print(f"Error: {e}")
        await websocket.send("Error: Communication failure")

    finally:
        print(f"Connection with {websocket.remote_address} closed.")  # Cuando la conexión se cierra

# Configura y ejecuta el servidor
async def main():
    server = await websockets.serve(handle_connection, "localhost", PORT)
    print(f"Server listening on ws://localhost:{PORT}")  # Indica que el servidor está escuchando
    await server.wait_closed()

# Inicia el servidor
if __name__ == "__main__":
    asyncio.run(main())
