import React, {useEffect, useState} from 'react';
import LoginPage from "./pages/LoginPage";
import {Navigate, Route, Routes} from "react-router-dom";
import Diet from "./pages/Diet";
import Grocery from "./pages/Grocery";
import Profile from "./pages/Profile";
import DietDetails from "./pages/DietDetails";
import GroceryDetails from "./pages/GroceryDetails";
import QRScanPage from "./pages/QRScanPage";
import Menu from "./pages/menu";
import SignPage from "./pages/SignPage";
import {useResponse} from "./pages/ResponseContext";
import GroceryTemporary from "./pages/GroceryTemporary";


const App: React.FC = () => {
    const [menuVisible, setMenuVisible] = useState(true);
    const {response, setResponse} = useResponse();
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    const handleLogin = (loggedIn: boolean) => {
        setIsLoggedIn(loggedIn);
    };

    return (
        <div>
            <Routes>
                {/* Default route handling */}
                <Route path="/" element={
                    isLoggedIn ? <Navigate to="/menu"/> : <LoginPage onChange={handleLogin} setResponse={setResponse}/>
                }/>

                {/* Route to menu */}
                <Route path="/sign" element={<SignPage/>}></Route>
                <Route path="/menu" element={<Menu setMenuVisible={setMenuVisible}/>}/>
                <Route path="/diet/*" element={<Diet setMenuVisible={setMenuVisible}/>}></Route>
                <Route path="/grocery/*" element={<Grocery setMenuVisible={setMenuVisible}/>}></Route>
                <Route path="/profile" element={<Profile setMenuVisible={setMenuVisible}/>}></Route>
                <Route path="/details" element={<DietDetails setMenuVisible={setMenuVisible}/>}/>
                <Route path="/grocery-details" element={<GroceryDetails setMenuVisible={setMenuVisible}/>}/>
                <Route path="/grocery-scanner/*" element={<QRScanPage setMenuVisible={setMenuVisible}/>}/>
                <Route path="/grocery-temporary" element={<GroceryTemporary setMenuVisible={setMenuVisible}/>}/>

            </Routes>
        </div>
    )
        ;
}

export default App;
