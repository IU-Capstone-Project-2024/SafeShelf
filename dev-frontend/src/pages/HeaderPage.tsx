import React, {FC} from "react";
import '../styles/header.css'
import {Navigate, useNavigate} from "react-router-dom";

const HeaderPage: FC<{ setMenuVisible: (visible: boolean) => void }> = ({setMenuVisible}) => {
    const navigate = useNavigate();
    const menuOpening = () => {
        navigate('/menu');
    }
    return (
        <div className='header'>
            {/*<button onClick={() => setMenuVisible(true)} className="menuOpen">*/}
            <button onClick={menuOpening} className="menuOpen">
                <svg width="30" height="21" viewBox="0 0 27 18" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path
                        d="M1.5 18H25.5C26.325 18 27 17.325 27 16.5C27 15.675 26.325 15 25.5 15H1.5C0.675 15 0 15.675 0 16.5C0 17.325 0.675 18 1.5 18ZM1.5 10.5H25.5C26.325 10.5 27 9.825 27 9C27 8.175 26.325 7.5 25.5 7.5H1.5C0.675 7.5 0 8.175 0 9C0 9.825 0.675 10.5 1.5 10.5ZM0 1.5C0 2.325 0.675 3 1.5 3H25.5C26.325 3 27 2.325 27 1.5C27 0.675 26.325 0 25.5 0H1.5C0.675 0 0 0.675 0 1.5Z"
                        fill="white"/>
                </svg>
            </button>
        </div>
    )
};


export default HeaderPage;