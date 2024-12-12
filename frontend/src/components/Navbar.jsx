import { NavLink } from 'react-router-dom'

function Navbar() {
    return (
        <nav className="relative mx-auto flex h-24 max-w-[1240px] px-4 justify-between font-semibold items-center">
            <p className='text-lg'>Ticket System</p>
            <div className='flex gap-10'>
                <NavLink to={'/'}
                    className={({ isActive }) => (isActive ? 'text-blue-500' : '')}
                >
                    Dashboard
                </NavLink>
                <NavLink to={'/configuration'}
                    className={({ isActive }) => (isActive ? 'text-blue-500' : '')}
                >
                    Configuration
                </NavLink>
            </div>
        </nav>
    )

}

export default Navbar