import {create} from 'zustand'
const useWebSocketStore = create((set) => ({
  socketData: null,
  ws: null,
  startWebSocket: () => {
    const ws = new WebSocket("ws://localhost:8070/ticket-status");

    ws.onopen = () => {
      console.log("Connected to WebSocket");
    };

    ws.onmessage = (event) => {
      try {
        const data = JSON.parse(event.data); 
        set({ socketData: data });
        console.log(data);
      } catch (error) {
        console.error("Failed to parse WebSocket message:", event.data, error);
      }
    };


    ws.onerror = (error) => {
      console.error("WebSocket error:", error);
    };

    ws.onclose = (event) => {
      console.log("WebSocket closed:", event);
    };

    set({ ws });
  },
  stopWebSocket: () => {
    const { ws } = useWebSocketStore.getState();
    if (ws) {
      ws.close();      
      console.log("WebSocket closed");
    }
  },

  clearSocketData: () => {    
    set({ socketData: null });
  },
}));

export default useWebSocketStore;
