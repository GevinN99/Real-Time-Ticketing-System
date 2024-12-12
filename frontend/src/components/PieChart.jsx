import {useEffect, useState} from "react";
import {Cell, Legend, Pie, PieChart, ResponsiveContainer, Tooltip} from "recharts";
import useWebSocketStore from "../hooks/useWebSocketStore";

function PieChart() {
    const socketData = useWebSocketStore((state) => state.socketData);
    const [customerData, setCustomerData] = useState([]);
    const [vendorData, setVendorData] = useState([]);

    useEffect(() => {
        if (socketData) {
            const data = socketData;

            // Customer Data
            const customerGraphData = Object.entries(data.customerTicketsBought).map(
                ([key, value]) => ({
                    name: key,
                    value: value,
                })
            );

            // Vendor Data
            const vendorGraphData = Object.entries(data.vendorTicketsReleased).map(
                ([key, value]) => ({
                    name: key,
                    value: value,
                })
            );

            setCustomerData(customerGraphData);
            setVendorData(vendorGraphData);
        } else {
            setCustomerData([]);
            setVendorData([])
        }
    }, [socketData]);

    // Define colors for the pie chart segments
    const vendorColors = ["#8884d8", "#83a6ed", "#8dd1e1", "#82ca9d"];
    const customerColors = ["#82ca9d", "#8884d8", "#8dd1e1", "#83a6ed"];

    return (
        <div className="flex items-center mt-20 justify-evenly gap-10">
            <div className="text-center w-full">
                <h2 className="text-2xl font-bold text-purple-600 mb-10">
                    Vendor Tickets Released
                </h2>
                {vendorData.length > 0 ? (
                    <div className="shadow-lg p-4 rounded-lg bg-white">
                        <ResponsiveContainer width="100%" height={300}>
                            <PieChart>
                                <Tooltip/>
                                <Legend/>
                                <Pie
                                    data={vendorData}
                                    dataKey="value"
                                    nameKey="name"
                                    cx="50%"
                                    cy="50%"
                                    outerRadius={100}
                                    fill="#8884d8"
                                    label
                                >
                                    {vendorData.map((entry, index) => (
                                        <Cell key={`cell-${index}`} fill={vendorColors[index % vendorColors.length]}/>
                                    ))}
                                </Pie>
                            </PieChart>
                        </ResponsiveContainer>
                    </div>
                ) : (
                    <p>Start the system to display data</p>
                )}
            </div>

            <div className="text-center w-full">
                <h2 className="text-2xl font-bold text-sky-600 mb-10">
                    Customer Tickets Bought
                </h2>
                {customerData.length > 0 ? (
                    <div className="shadow-lg p-4 rounded-lg bg-white">
                        <ResponsiveContainer width="100%" height={300}>
                            <PieChart>
                                <Tooltip/>
                                <Legend/>
                                <Pie
                                    data={customerData}
                                    dataKey="value"
                                    nameKey="name"
                                    cx="50%"
                                    cy="50%"
                                    outerRadius={100}
                                    fill="#82ca9d"
                                    label
                                >
                                    {customerData.map((entry, index) => (
                                        <Cell key={`cell-${index}`}
                                              fill={customerColors[index % customerColors.length]}/>
                                    ))}
                                </Pie>
                            </PieChart>
                        </ResponsiveContainer>
                    </div>
                ) : (
                    <p>Start the system to display data</p>
                )}
            </div>
        </div>
    );
}

export default PieChart;