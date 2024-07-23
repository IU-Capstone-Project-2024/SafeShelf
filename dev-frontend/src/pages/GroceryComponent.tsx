import React, {useEffect, useState} from 'react';
import '../styles/dietStyles.css'
import {useNavigate} from 'react-router-dom';
import {GroceryContainerProps} from "../interfaces/grocery-interfaces";

// CardContainer component
const CardContainer = () => {
    const navigate = useNavigate();
    const [groceries, setGroceries] = useState<GroceryContainerProps["groceries"]>([]);

    const action = (id: number,
                    name: string, weight: number,
                    kcal: number,
                    proteins: number,
                    fats: number,
                    carbohydrates: number,
                    date: string) => {
        navigate('/grocery-details', {
            state: {id, name, weight, kcal, proteins, fats, carbohydrates, date}
        });
    }

    // FETCHING CURRENT DATA FOR IT
    useEffect(() => {
        const fetchData = async () => {
            try {
                const login = sessionStorage.getItem('userLogin');

                // TODO: FIX ENDPOINTS
                const currResponse = await fetch(`/product/${login}`)
                const currStatus = currResponse.status;
                if (currStatus === 200) {
                    const data: GroceryContainerProps["groceries"] = await currResponse.json();
                    setGroceries(data);
                } else {
                    console.error(currResponse.statusText);
                }

            } catch {

            }
        }

        fetchData().then();
    }, []);

    return (
        <div>
            {groceries.map((currentDict, index) => (
                <div key={index} className="card-display"
                     onClick={() => action(currentDict.id, currentDict.name, currentDict.weight, currentDict.kcal,
                         currentDict.proteins, currentDict.fats, currentDict.carbohydrates, currentDict.date)}>
                    <h2>{currentDict.name}</h2>
                    <svg width="30" height="21" viewBox="0 0 20 11" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path
                            d="M5.45 10.9L4.05 9.5L7.075 6.45H0V4.45H7.075L4.05 1.4L5.45 0L10.9 5.45L5.45 10.9ZM11 10.45V8.45H20V10.45H11ZM11 2.45V0.45H20V2.45H11ZM14 6.45V4.45H20V6.45H14Z"
                            fill="#666666"/>
                    </svg>
                </div>
            ))}
        </div>
    );
};

export default CardContainer;