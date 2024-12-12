import useVendorCustomerStore from "../hooks/useVendorCustomerStore";

function VenCustForm() {
  const {vendorCustomerData, updateVendorCustomerData} = useVendorCustomerStore()

  const handleAdd = async (type, value) => {
    if (!value.trim()) return;

    const updatedData = {
      ...vendorCustomerData,
      [type]: [...(vendorCustomerData[type] || []), value.trim()],
    }

    updateVendorCustomerData(updatedData)
  };

  return (
    <div className="flex flex-col gap-5">
      <form
        className="flex flex-col w-96 bg-black p-5 rounded-lg"
        onSubmit={(e) => {
          e.preventDefault();
          handleAdd("vendors", e.target.vendor.value);
          e.target.reset();
        }}
      >
        <label className="text-white mb-2">Add Vendor</label>
        <input
          name="vendor"
          className="mb-5 rounded-sm bg-white p-1 text-black w-full"
          type="text"
          placeholder="Enter vendor name"
        />
        <button
          type="submit"
          className="bg-green-500 text-white p-2 rounded-sm w-full hover:bg-green-600 transition duration-300"
        >
          Add Vendor
        </button>
      </form>

      <form
        className="flex flex-col w-96 bg-black p-5 rounded-lg mt-2"
        onSubmit={(e) => {
          e.preventDefault();
          handleAdd("customers", e.target.customer.value);
          e.target.reset(); // Clear input field
        }}
      >
        <label className="text-white mb-2">Add Customer</label>
        <input
          name="customer"
          className="mb-5 rounded-sm bg-white p-1 text-black w-full"
          type="text"
          placeholder="Enter customer name"
        />
        <button
          type="submit"
          className="bg-green-500 text-white p-2 rounded-sm w-full hover:bg-green-600 transition duration-300"
        >
          Add Customer
        </button>
      </form>
    </div>
  );
}

export default VenCustForm;
