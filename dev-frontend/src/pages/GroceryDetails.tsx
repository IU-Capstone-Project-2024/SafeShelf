import React, {FC, useState} from "react";
import HeaderPage from "./HeaderPage";
import {useLocation, useNavigate} from "react-router-dom";
import '../styles/details.css'
import {updating, deleting} from "../interfaces/grocery-interfaces";


const GroceryDetails: FC<{ setMenuVisible: (visible: boolean) => void }> = ({setMenuVisible}) => {
    const location = useLocation();
    const navigate = useNavigate();

    const {id, name, weight, kcal, proteins, fats, carbohydrates, date} = location.state as {
        id: number;
        name: string;
        weight: number;
        kcal: number;
        proteins: number;
        fats: number;
        carbohydrates: number;
        date: string;
    };
    const [currDate, setCurrDate] = useState(date);

    // Функция для обновления состояния выбранной даты
    const handleDateChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const pressedDate = (document.getElementById('date') as HTMLInputElement).value;
        setCurrDate(pressedDate);
    };

    const reNavigate = () => {
        navigate('/grocery/*');
        window.location.reload();
    }

    const deleteCurrent = async (userProductID: number) => {
        try {
            let login = sessionStorage.getItem('userLogin');
            if (!login) {
                login = ""
            }
            const dateToDelete: deleting = {
                login,
                userProductID,
            }

            // console.log(dateToDelete)
            const currResponse = await fetch(`/product/delete`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(dateToDelete),
            });

            const currStatus = currResponse.status;

            if (currStatus == 200) {
                navigate('/grocery/*', {replace: true});
                window.location.reload();
            } else {
                throw new Error("error while fetching data");
            }
        } catch (e) {
            console.warn(e)
        }
    }

    const saveCurrent = async (productID: number) => {
        try {
            let login = sessionStorage.getItem('userLogin');
            let date = currDate;

            if (!login) {
                login = ""
            }

            if (!date) {
                date = ""
            }

            const dateToSend: updating = {
                login,
                productID,
                date
            }

            const currResponse = await fetch(`/product/update`, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(dateToSend),
            });

            const currStatus = currResponse.status;
            if (currStatus == 200) {
                navigate('/grocery/*', {replace: true});
                window.location.reload();
            } else {
                throw new Error("error while fetching data");
            }
        } catch (e) {
            console.warn(e)
        }
    }

    return (
        <>
            <HeaderPage setMenuVisible={setMenuVisible}/>
            <div className='details-page'>
                <div className="heading">
                    <div onClick={reNavigate}>
                        <svg width="20" height="17" viewBox="0 0 14 11" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <path
                                d="M6.20676 10.6948C6.01923 10.8822 5.76492 10.9875 5.49976 10.9875C5.23459 10.9875 4.98028 10.8822 4.79276 10.6948L0.292756 6.19476C0.105285 6.00723 -3.05176e-05 5.75293 -3.05176e-05 5.48776C-3.05176e-05 5.2226 0.105285 4.96829 0.292756 4.78076L4.79276 0.280763C4.98136 0.0986042 5.23396 -0.00218964 5.49616 8.86917e-05C5.75835 0.00236702 6.00917 0.107536 6.19457 0.292944C6.37998 0.478352 6.48515 0.729165 6.48743 0.991362C6.48971 1.25356 6.38891 1.50616 6.20676 1.69476L3.49976 4.48776L12.9998 4.48776C13.265 4.48776 13.5193 4.59312 13.7069 4.78066C13.8944 4.96819 13.9998 5.22255 13.9998 5.48776C13.9998 5.75298 13.8944 6.00733 13.7069 6.19487C13.5193 6.38241 13.265 6.48776 12.9998 6.48776L3.49976 6.48776L6.20676 9.28076C6.39423 9.46829 6.49954 9.7226 6.49954 9.98776C6.49954 10.2529 6.39423 10.5072 6.20676 10.6948Z"
                                fill="#4D544B"/>
                        </svg>

                    </div>
                    <div className='dish-name'>{name}</div>
                </div>
                <p>Ingredients:</p>
                <ul className='list-decoration'>
                    <li>Weight: <b>{weight}</b></li>
                    <li>Kcal: <b>{kcal}</b></li>
                    <li>Proteins: <b>{proteins}</b></li>
                    <li>Carbohydrates: <b> {carbohydrates}</b></li>
                </ul>

                <input
                    type="date"
                    className='date-picker'
                    id="date"
                    value={currDate}
                    onInput={handleDateChange}
                />
            </div>
            <div className='buttons-del'>
                <button className='fixed-button delete' onClick={() => deleteCurrent(id)}>Delete</button>
                <button className='fixed-button save' onClick={() => saveCurrent(id)}>Save</button>
            </div>
        </>
    )
};

export default GroceryDetails;