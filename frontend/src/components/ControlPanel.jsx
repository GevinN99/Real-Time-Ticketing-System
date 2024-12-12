import axios from "axios";
import toast from "react-hot-toast";

import useWebSocketStore from "../hooks/useWebSocketStore";
import useVendorCustomerStore from "../hooks/useVendorCustomerStore";

function ControlPanel() {
    const { clearVendorCustomerData } = useVendorCustomerStore();
    const { startWebSocket, stopWebSocket, clearSocketData } = useWebSocketStore();

    const handleStart = async () => {
        let storedData = localStorage.getItem("data");
        if (storedData) {
            storedData = JSON.parse(storedData);
        }

        try {
            if (
                storedData &&
                storedData.vendors.length > 0 &&
                storedData.customers.length > 0
            ) {
                const response = await axios.post("/start", storedData, {
                    headers: { "Content-Type": "application/json" },
                });
                startWebSocket();
                toast.success(response.data.message);
            } else {
                toast.error("Add customers and vendors to get started");
            }
        } catch (error) {
            console.error(error);
            toast.error(error.response?.data?.message || "Error occurred");
        }
    };

    const handleStop = async () => {
        try {
            const response = await axios.post("/stop");
            stopWebSocket();
            toast.success(response.data.message);
        } catch (error) {
            console.error(error);
            toast.error(error.response?.data?.message || "Error occurred");
        }
    };

    const handleReset = async () => {
        clearSocketData();
    };

    return (
        <div className="flex flex-col my-5 bg-gray-800 w-96 rounded-lg items-center gap-4 p-6 shadow-lg">
            <button
                onClick={handleStart}
                className="rounded-md bg-blue-600 p-3 text-white hover:bg-blue-700 w-44 font-bold transition duration-300"
            >
                Start
            </button>
            <button
                onClick={handleStop}
                className="rounded-md bg-red-600 p-3 text-white hover:bg-red-700 w-44 font-bold transition duration-300"
            >
                Stop
            </button>
            <button
                onClick={handleReset}
                className="rounded-md bg-yellow-600 p-3 text-white hover:bg-yellow-700 w-44 font-bold transition duration-300"
            >
                Reset
            </button>
        </div>
    );
}

export default ControlPanel;