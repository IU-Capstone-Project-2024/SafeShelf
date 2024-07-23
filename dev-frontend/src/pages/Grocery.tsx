import {FC} from "react";
import HeaderPage from "./HeaderPage";
import GroceryContainer from "./GroceryComponent";
import '../styles/dietStyles.css'
import {useNavigate} from "react-router-dom";


const Grocery: FC<{ setMenuVisible: (visible: boolean) => void }> = ({setMenuVisible}) => {
    const navigate = useNavigate();

    const addNavigate = () => {
        navigate('/grocery-scanner/*');
    }

    return (
        <>
            <HeaderPage setMenuVisible={setMenuVisible}/>
            <div className='pageContainer'>
                <h1>Grocery List</h1>
                <GroceryContainer/>
                <button className='add' onClick={() => addNavigate()}>Add groceries</button>
            </div>
        </>
    )
};

export default Grocery;