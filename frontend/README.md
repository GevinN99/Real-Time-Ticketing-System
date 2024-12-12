# Ticket Jet - Frontend

## Description

The Ticket Jet frontend application is built using React and Vite, designed to manage and visualize the ticketing system for vendors and customers. It provides interactive features like session details, vendor/customer management, ticket release and purchase statistics via charts, and live updates through WebSocket.

## Features

- **Pie and Bar Charts** for ticketing data (Customer Tickets Bought and Vendor Tickets Released).
- **Session Details** to track total tickets, ticket release rates, and remaining tickets.
- **Vendor and Customer Management**: Add and remove vendors and customers, and view the list in a table format.
- **Real-time Updates** via WebSocket, displaying live ticketing data.
- **Responsive Design** for mobile and desktop devices.

## Prerequisites

Before you begin, ensure you have the following installed:

- **Node.js** (v14 or higher)
- **npm** (v6 or higher) or **yarn**

To check your Node.js and npm versions, run:

```bash
node -v
npm -v
```

You can download and install the latest version of Node.js from the [official website](https://nodejs.org/).

## Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/your-username/ticket-jet-frontend.git
    cd ticket-jet-frontend
    ```

2. Install dependencies using npm or yarn:

    ```bash
    npm install
    # OR
    yarn install
    ```

3. Create a `.env` file in the root directory for environment variables (if required):

   Example `.env`:
    ```env
    REACT_APP_API_URL=http://localhost:8080/api
    REACT_APP_WS_URL=ws://localhost:8080/ws
    ```

4. Run the application in development mode:

    ```bash
    npm run dev
    # OR
    yarn dev
    ```

   This will start the app at [http://localhost:3000](http://localhost:3000).

## Project Structure

```bash
src/
  ├── components/
  │   ├── PieChart.jsx          # Displays pie charts for customer and vendor data
  │   ├── SessionDetails.jsx    # Displays session summary and tickets remaining
  │   ├── TicketChart.jsx       # Displays bar charts for ticket data
  │   ├── VenCustForm.jsx       # Form to add vendors and customers
  │   ├── VenCustTable.jsx      # Table to display and manage vendors and customers
  ├── hooks/
  │   ├── useWebSocketStore.js  # WebSocket custom hook for real-time data
  │   └── useVendorCustomerStore.js # Custom hook for managing vendor/customer data
  ├── App.jsx                   # Main component to render the app
  └── index.jsx                 # Entry point of the app
```

## Usage

### WebSocket Integration

The app relies on WebSocket to display real-time updates for ticket data. Ensure that your WebSocket server is up and running, and connected to the correct backend service.

- **WebSocket URL**: Define the WebSocket URL in `.env` file (e.g., `REACT_APP_WS_URL`).
- The app listens to events from the backend, updating charts and session details accordingly.

### Adding Vendors and Customers

1. Use the **VenCustForm** component to add vendors and customers.
2. Fill in the form and hit **Add Vendor** or **Add Customer**.
3. View the added vendors/customers in the **VenCustTable** component.
4. Remove vendors/customers by clicking the **Remove** button next to their name.

### Viewing Ticket Data

- **PieChart** component displays pie charts for:
    - **Vendor Tickets Released**
    - **Customer Tickets Bought**

- **TicketChart** component shows bar charts for:
    - **Vendor Tickets Released**
    - **Customer Tickets Bought**

### Session Details

- The **SessionDetails** component provides real-time session information, including total tickets, ticket release rates, and remaining tickets.

## Troubleshooting

### WebSocket Not Connecting

- **Check WebSocket URL**: Ensure the WebSocket URL (`REACT_APP_WS_URL`) is correct in the `.env` file.
- **Backend WebSocket Server**: Verify that the WebSocket server on the backend is running and accepting connections.

### Charts Not Updating

- **Check WebSocket Events**: Ensure the backend is emitting events for ticket updates. You should see logs in the browser’s console when data is received.
- **State Management**: Verify that the data from WebSocket is being correctly saved in the `useWebSocketStore`.

### Data Not Displaying in Tables

- **Ensure Data is Available**: If no vendors/customers are listed, ensure that data has been added via the `VenCustForm` component.
- **State Sync**: Verify the state of `vendorCustomerData` in `useVendorCustomerStore` is being updated correctly.

### General Performance Issues

- **Development Mode**: Running in development mode (`npm run dev`) can sometimes cause performance issues. Build the app for production to improve performance:

    ```bash
    npm run build
    ```

  This will create an optimized version of the app in the `dist` folder.

### Cross-Browser Compatibility

- Test the app on multiple browsers to ensure compatibility. The app should work well on modern browsers such as Chrome, Firefox, and Edge.

## Contributing

If you would like to contribute to the development of this project, please follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Commit your changes (`git commit -am 'Add new feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Create a new Pull Request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
