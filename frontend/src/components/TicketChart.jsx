import {useEffect, useState} from "react";
import {Bar, BarChart, CartesianGrid, Label, ResponsiveContainer, Tooltip, XAxis, YAxis,} from "recharts";
import useWebSocketStore from "../hooks/useWebSocketStore";

function TicketChart() {
    const socketData = useWebSocketStore((state) => state.socketData);
    const [customerData, setCustomerData] = useState([]);
    const [vendorData, setVendorData] = useState([]);

    useEffect(() => {
        if (socketData) {
            const customerGraphData = Object.entries(socketData.customerTicketsBought).map(
                ([key, value]) => ({
                    name: key,
                    ticketsBought: value,
                })
            );

            const vendorGraphData = Object.entries(socketData.vendorTicketsReleased).map(
                ([key, value]) => ({
                    name: key,
                    ticketsReleased: value,
                })
            );

            setCustomerData(customerGraphData);
            setVendorData(vendorGraphData);
        } else {
            setCustomerData([]);
            setVendorData([])
        }
    }, [socketData]);

    return (
        <div className="flex flex-col items-center mt-20 justify-evenly gap-10">
            <div className="text-center w-full">
                <h2 className="text-2xl font-bold text-purple-600 mb-4">
                    Vendor Tickets Released
                </h2>
                {vendorData.length > 0 ? (
                    <div className="shadow-lg p-4 rounded-lg bg-white">
                        <ResponsiveContainer width="100%" height={300}>
                            <BarChart data={vendorData}>
                                <CartesianGrid strokeDasharray="3 3"/>
                                <XAxis dataKey="name" tick={{fontSize: 14}}/>
                                <YAxis>
                                    <Label
                                        value="Number of Tickets"
                                        angle={-90}
                                        position="center"
                                        fontSize={16}
                                    />
                                </YAxis>
                                <Tooltip/>
                                <Bar dataKey="ticketsReleased" fill="#8884d8" barSize={50}/>
                            </BarChart>
                        </ResponsiveContainer>
                    </div>
                ) : (
                    <p>Start the system to display data</p>
                )}
            </div>
            <div className="text-center w-full">
                <h2 className="text-2xl font-bold text-sky-600 mb-4">
                    Customer Tickets Bought
                </h2>
                {customerData.length > 0 ? (
                    <div className="shadow-lg p-4 rounded-lg bg-white">
                        <ResponsiveContainer width="100%" height={300}>
                            <BarChart data={customerData}>
                                <CartesianGrid strokeDasharray="3 3"/>
                                <XAxis dataKey="name" tick={{fontSize: 14}}/>
                                <YAxis>
                                    <Label
                                        value="Number of Tickets"
                                        angle={-90}
                                        position="center"
                                        fontSize={16}
                                    />
                                </YAxis>
                                <Tooltip/>
                                <Bar dataKey="ticketsBought" fill="#82ca9d" barSize={50}/>
                            </BarChart>
                        </ResponsiveContainer>
                    </div>
                ) : (
                    <p>Start the system to display data</p>
                )}
            </div>
        </div>
    );
}

export default TicketChart;