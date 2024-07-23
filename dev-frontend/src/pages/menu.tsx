import {FC} from "react";
import React from 'react';
import {Link} from "react-router-dom";
import '../styles/menuStyles.css'

const Menu: FC<{ setMenuVisible: (visible: boolean) => void }> = ({setMenuVisible}) => {
    return (
        <>
            <div className='menuPage'>
                <h1 className='h1'>Main Menu</h1>
                <div><Link to="/diet" onClick={() => setMenuVisible(false)}>Diet</Link></div>
                <nav></nav>
                <div><Link to="/grocery" onClick={() => setMenuVisible(false)}>Grocery list</Link></div>
                <nav></nav>
                <div><Link to="/profile" onClick={() => setMenuVisible(false)}>Profile</Link></div>
            </div>
        </>
    );
}

export default Menu;