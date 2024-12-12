import ControlPanel from "../components/ControlPanel";
import TicketChart from "../components/TicketChart.jsx";
import SessionDetials from "../components/SessionDetails";

function Home() {
    return (
        <div className="bg-gradient-to-r min-h-screen flex flex-col justify-center">
            <div className="mx-auto max-w-6xl p-10">
                <div className="flex gap-10">
                    <ControlPanel />
                    <SessionDetials />
                </div>
                <TicketChart />
            </div>
        </div>
    );
}

export default Home;
