import { create } from "zustand"

const useVendorCustomerStore = create((set) => {
    const storedData = localStorage.getItem("data");
    const parsedData = storedData ? JSON.parse(storedData) : { vendors: [], customers: [] };

    return {
        vendorCustomerData: parsedData,
        
        updateVendorCustomerData: (newData) => {
            set({ vendorCustomerData: newData })
            localStorage.setItem("data", JSON.stringify(newData))
        },

        clearVendorCustomerData: () => {            
            set({ vendorCustomerData: { vendors: [], customers: [] } });
            localStorage.removeItem("data");
        },
    }
})

export default useVendorCustomerStore;