import ConfigForm from "../components/ConfigForm.jsx"
import VenCustForm from "../components/VenCustForm.jsx"
import VenCustTable from "../components/VenCustTable.jsx"

function Configuration() {
    return (
        <div className="mx-auto max-w-6xl flex">
            <div className="flex flex-col gap-10 justify-evenly">
                <ConfigForm/>
                <VenCustForm/>
            </div>
            <div className="ml-20">
                <VenCustTable/>
            </div>
        </div>
    )

}

export default Configuration