import { useEffect, useState } from "react";
import toast from "react-hot-toast";
import axios from "axios";

function ConfigForm() {
  const [configData, setConfigData] = useState({
    totalTickets: "",
    ticketReleaseRate: "",
    customerRetrievalRate: "",
    maxTicketCapacity: "",
  });

  const [placeholderData, setPlaceholderData] = useState({
    totalTickets: "Enter the total tickets",
    ticketReleaseRate: "Enter the ticket release rate",
    customerRetrievalRate: "Enter the customer retrieval rate",
    maxTicketCapacity: "Enter the maximum ticket capacity",
  });

  useEffect(() => {
    fetchConfiguration();
  }, []);

  const fetchConfiguration = async () => {
    try {
      const response = await axios.get("http://localhost:8070/api/config");
      setPlaceholderData(response.data.data);
      localStorage.setItem('config',JSON.stringify(response.data.data ))
    } catch (error) {
      console.log(error)
    }
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      const response = await axios.post("http://localhost:8070/api/config", { ...configData });
      console.log(response);

      setPlaceholderData({ ...configData });

      setConfigData({
        totalTickets: "",
        ticketReleaseRate: "",
        customerRetrievalRate: "",
        maxTicketCapacity: "",
      });

      localStorage.setItem('config',JSON.stringify(response.data.data ))
      toast.success(response.data.message);
    } catch (error) {
      console.log(error);
      toast.error(error.response.data.message);
    }
  };

  return (
    <form
      onSubmit={handleSubmit}
      className="flex w-96 flex-col rounded-md bg-black p-5 text-white"
    >
      <label>Enter the total tickets</label>
      <input
        className="mb-5 rounded-sm bg-white p-1 text-black"
        type="number"
        value={configData.totalTickets}
        onChange={(event) =>
          setConfigData({ ...configData, totalTickets: event.target.value })
        }
        placeholder={placeholderData.totalTickets}
        required
      />

      <label>Enter the ticket release rate</label>
      <input
        className="mb-5 rounded-sm bg-white p-1 text-black"
        type="number"
        value={configData.ticketReleaseRate}
        onChange={(event) =>
          setConfigData({
            ...configData,
            ticketReleaseRate: event.target.value,
          })
        }
        placeholder={placeholderData.ticketReleaseRate}
        required
      />

      <label>Enter the customer retrieval rate</label>
      <input
        className="mb-5 rounded-sm bg-white p-1 text-black"
        type="number"
        value={configData.customerRetrievalRate}
        onChange={(event) =>
          setConfigData({
            ...configData,
            customerRetrievalRate: event.target.value,
          })
        }
        placeholder={placeholderData.customerRetrievalRate}
        required
      />

      <label>Enter the maximum ticket capacity</label>
      <input
        className="mb-5 rounded-sm bg-white p-1 text-black"
        type="number"
        value={configData.maxTicketCapacity}
        onChange={(event) =>
          setConfigData({
            ...configData,
            maxTicketCapacity: event.target.value,
          })
        }
        placeholder={placeholderData.maxTicketCapacity}
        required
      />

      <button
        type="submit"
        className="mt-4 rounded-md bg-green-500 p-2 text-white hover:bg-green-700"
      >
        Update
      </button>
    </form>
  );
}

export default ConfigForm;

