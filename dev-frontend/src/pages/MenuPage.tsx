import React, {FC, useState} from "react";
import Menu from "./menu";
import {Route, Routes} from "react-router-dom";
import Diet from "./Diet";
import Grocery from "./Grocery";
import Profile from "./Profile";
import DietDetails from "./DietDetails";
import GroceryDetails from "./GroceryDetails";
import QRScanPage from "./QRScanPage";
import GroceryTemporary from "./GroceryTemporary";


const MenuPage: FC = () => {
    const [menuVisible, setMenuVisible] = useState(true);

    return (
        <div className={menuVisible ? 'menuVisible' : ''}>
            {menuVisible ? (
                <Menu setMenuVisible={setMenuVisible}/>
            ) : (
                <Routes>
                    <Route path="/diet/*" element={<Diet setMenuVisible={setMenuVisible}/>}></Route>
                    <Route path="/grocery/*" element={<Grocery setMenuVisible={setMenuVisible}/>}></Route>
                    <Route path="/profile" element={<Profile setMenuVisible={setMenuVisible}/>}></Route>
                    <Route path="/details" element={<DietDetails setMenuVisible={setMenuVisible}/>}/>
                    <Route path="/grocery-details" element={<GroceryDetails setMenuVisible={setMenuVisible}/>}/>
                    <Route path="/grocery-scanner/*" element={<QRScanPage setMenuVisible={setMenuVisible}/>}/>
                    <Route path="/grocery-temporary" element={<GroceryTemporary setMenuVisible={setMenuVisible}/>}/>
                </Routes>
            )}
        </div>
    )
}

export default MenuPage;