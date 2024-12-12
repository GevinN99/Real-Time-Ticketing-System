import useVendorCustomerStore from "../hooks/useVendorCustomerStore";
import useWebSocketStore from "../hooks/useWebSocketStore";

function SessionDetails() {
    const { vendorCustomerData } = useVendorCustomerStore();
    const { socketData } = useWebSocketStore();

    const storedData = localStorage.getItem('config');
    const configdata = storedData ? JSON.parse(storedData) : null;

    return (
        <div className="ml-20 flex flex-col md:flex-row gap-10 p-6 bg-gray-900 rounded-lg shadow-lg text-white">
            <div>
                <p className="text-xl font-bold mb-4">Session Details</p>
                <ul className="ml-4 space-y-2">
                    <li>Total tickets: <span className="text-green-400">{configdata?.totalTickets || 0}</span></li>
                    <li>Vendor ticket release rate: <span className="text-green-400">{configdata?.ticketReleaseRate || 0}</span></li>
                    <li>Customer ticket retrieval rate: <span className="text-green-400">{configdata?.customerRetrievalRate || 0}</span></li>
                    <li>Maximum ticket capacity: <span className="text-green-400">{configdata?.maxTicketCapacity || 0}</span></li>
                    <br />
                    <li>Total vendors: <span className="text-green-400">{vendorCustomerData.vendors.length || 0}</span></li>
                    <li>Total customers: <span className="text-green-400">{vendorCustomerData.customers.length || 0}</span></li>
                </ul>
            </div>

            {socketData && (
                <div className="flex flex-col items-center">
                    <p className="text-xl font-bold mb-2">Tickets Remaining</p>
                    <p className="text-6xl text-green-500">{socketData.totalTicketsRemaining}</p>
                    {socketData.totalTicketsRemaining === 0 && (
                        <p className="text-sm text-red-500 mt-2">*All tickets have been purchased</p>
                    )}
                </div>
            )}
        </div>
    );
}

export default SessionDetails;