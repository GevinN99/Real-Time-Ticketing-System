import useVendorCustomerStore from "../hooks/useVendorCustomerStore";

function VenCustTable() {
    const { vendorCustomerData, updateVendorCustomerData } = useVendorCustomerStore();

    const handleRemove = (type, index) => {
        const confirmDelete = window.confirm("Are you sure you want to remove this item?");
        if (confirmDelete) {
            const updatedData = {
                ...vendorCustomerData,
                [type]: vendorCustomerData[type].filter((_, i) => i !== index),
            };
            updateVendorCustomerData(updatedData);
        }
    };

    return (
        <div className="flex flex-col md:flex-row gap-10 p-6 bg-gray-900 rounded-lg shadow-lg">
            <div className="w-full md:w-1/2">
                <h2 className="text-2xl font-bold mb-4 text-blue-400">Vendors</h2>
                {vendorCustomerData.vendors.length > 0 ? (
                    <table className="table-auto w-full border-collapse border border-gray-700 shadow-md">
                        <thead>
                        <tr>
                            <th className="border border-gray-700 px-4 py-2 bg-gray-800 text-left text-white">Name</th>
                            <th className="border border-gray-700 px-4 py-2 bg-gray-800 text-left text-white">Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        {vendorCustomerData.vendors.map((vendor, index) => (
                            <tr key={index} className="hover:bg-gray-700 transition duration-300">
                                <td className="border border-gray-700 px-4 py-2 text-white">{vendor}</td>
                                <td className="border border-gray-700 px-4 py-2">
                                    <button
                                        onClick={() => handleRemove("vendors", index)}
                                        className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600 transition duration-300"
                                    >
                                        Remove
                                    </button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                ) : (
                    <p className="text-gray-500 italic">No vendors available. Add some to get started!</p>
                )}
            </div>

            <div className="w-full md:w-1/2">
                <h2 className="text-2xl font-bold mb-4 text-green-400">Customers</h2>
                {vendorCustomerData.customers.length > 0 ? (
                    <table className="table-auto w-full border-collapse border border-gray-700 shadow-md">
                        <thead>
                        <tr>
                            <th className="border border-gray-700 px-4 py-2 bg-gray-800 text-left text-white">Name</th>
                            <th className="border border-gray-700 px-4 py-2 bg-gray-800 text-left text-white">Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        {vendorCustomerData.customers.map((customer, index) => (
                            <tr key={index} className="hover:bg-gray-700 transition duration-300">
                                <td className="border border-gray-700 px-4 py-2 text-white">{customer}</td>
                                <td className="border border-gray-700 px-4 py-2">
                                    <button
                                        onClick={() => handleRemove("customers", index)}
                                        className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600 transition duration-300"
                                    >
                                        Remove
                                    </button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                ) : (
                    <p className="text-gray-500 italic">No customers available. Add some to get started!</p>
                )}
            </div>
        </div>
    );
}

export default VenCustTable;
