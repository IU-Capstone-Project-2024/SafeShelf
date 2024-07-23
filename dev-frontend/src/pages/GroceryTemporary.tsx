import React, {FC, useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import HeaderPage from "./HeaderPage";
import '../styles/groceryTemporary.css'
import {useIndex} from "./IndicesHook";
import {useDate} from "./DatesHook";
import {useWeight} from "./WeightHook";
import {GroceryContainerProps, newGroceries, sendingQR} from "../interfaces/grocery-interfaces";


const CardGroceryComponent: React.FC<GroceryContainerProps> = ({groceries}) => {
    const {indicesArray, setArray} = useIndex();
    const {datesDict, setDict} = useDate();
    const {weightDict, setWeight} = useWeight();


    const [groceryDates, setGroceryDates] = useState(groceries.map(g => g.date));
    const [crossedItems, setCrossedItems] = useState<number[]>([]);

    const clickedComponent = (index: number) => {
        // HERE WILL SAVE THE CURRENT CLICKED ID TO DELETE
        const currentIndexes = [...indicesArray];
        if (currentIndexes.includes(index)) {
            currentIndexes.splice(currentIndexes.indexOf(index), 1);
        } else {
            currentIndexes.push(index);
        }
        setArray(currentIndexes);

        // Array of crossed items to handle
        setCrossedItems(prevItems => {
            if (prevItems.includes(index)) {
                return prevItems.filter(item => item !== index);
            } else {
                return [...prevItems, index];
            }
        });
    }

    const changedDateComponent = (event: React.ChangeEvent<HTMLInputElement>, index: number) => {
        // HERE WILL SAVE THE CURRENT CLICKED ID TO DELETE
        datesDict[index] = event.target.value;
        setDict(datesDict);
    }

    const changedWeightComponent = (event: React.ChangeEvent<HTMLInputElement>, index: number) => {
        // HERE WILL SAVE THE CURRENT CLICKED ID TO DELETE
        weightDict[index] = event.target.value;
        setWeight(weightDict);
    }

    const isClicked = (index: number) => {
        return crossedItems.includes(index);
    };

    return (
        <>
            {groceries.map((grocery, index) => (
                <div key={index} className={`grocery-display ${isClicked(grocery.id) ? 'clicked' : ''}`}>
                    <div className="groceryHeader"><span
                        style={{textDecoration: crossedItems.includes(index) ? 'line-through' : 'none'}}>{grocery.name} </span>
                        <div className="clickable-icon" onClick={() => clickedComponent(grocery.id)}>
                            <svg width="20" height="23" viewBox="0 0 11 14" fill="none"
                                 xmlns="http://www.w3.org/2000/svg">
                                <path
                                    d="M0.785714 12.4444C0.785714 12.857 0.951275 13.2527 1.24597 13.5444C1.54067 13.8361 1.94037 14 2.35714 14H8.64286C9.05963 14 9.45932 13.8361 9.75402 13.5444C10.0487 13.2527 10.2143 12.857 10.2143 12.4444V3.11111H0.785714V12.4444ZM2.35714 4.66667H8.64286V12.4444H2.35714V4.66667ZM8.25 0.777778L7.46429 0H3.53571L2.75 0.777778H0V2.33333H11V0.777778H8.25Z"
                                    fill="#FF5A5A"/>
                            </svg>
                        </div>
                    </div>
                    <div
                        className="grocery-position">Weight:
                        <input type="number"
                               id="weight-${grocery.weight}"
                               className="expiration-date"
                               defaultValue={grocery.weight}
                               value={groceryDates[grocery.id]}
                               onChange={(event) => changedWeightComponent(event, grocery.id)}
                               style={{
                                   border: grocery.weight ? 'none' : '2px solid red'
                               }}
                        />
                    </div>
                    <div className="grocery-position">Kcal: <b>{grocery.kcal}</b></div>
                    <div className="grocery-position">Fats: <b>{grocery.fats}</b></div>
                    <div className="grocery-position">Carbohydrates: <b>{grocery.carbohydrates}</b></div>
                    <div className="grocery-position">Proteins: <b>{grocery.proteins}</b></div>
                    <div className="grocery-position">Expiration: <input
                        type="date"
                        className="expiration-date"
                        id="date-${grocery.id}"
                        defaultValue={grocery.date}
                        value={groceryDates[grocery.id]}
                        onChange={(event) => changedDateComponent(event, grocery.id)}
                    /></div>
                </div>
            ))}
        </>
    );
};

const GroceryTemporary: FC<{ setMenuVisible: (visible: boolean) => void }> = ({setMenuVisible}) => {
    // const {groceryData, setGroceryData} = useQRResponse();
    const [groceries, setGroceries] = useState<GroceryContainerProps["groceries"]>([]);
    // Array of clicked indices to remove
    const {indicesArray, setArray} = useIndex();
    const {datesDict, setDict} = useDate();
    const {weightDict, setWeight} = useWeight();


    const navigate = useNavigate();

    useEffect(() => {
        const fetchData = async () => {
            try {
                // Check endpoint??? IDK but what's happening here XD
                const login = sessionStorage.getItem('userLogin');
                const metaStringProducts = sessionStorage.getItem('qrValue');

                const qrData: sendingQR = {
                    login,
                    metaStringProducts
                }

                // SENDING QR CODE STRING + LOGIN
                const currResponse = await fetch(`/product/get_temp_products`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(qrData),
                });

                const currStatus = currResponse.status;
                if (currStatus === 200) {
                    const data: GroceryContainerProps["groceries"] = await currResponse.json();
                    setGroceries(data);
                } else {
                    throw new Error(currResponse.statusText);
                }

            } catch (error) {
                console.error('Error fetching data:', error);
                // Handle error as needed
            }
        };
        fetchData().then(); // Call the async function immediately
    }, []); // Empty dependency array to run this effect only once on component mount

    const reNavigate = () => {
        navigate('/grocery-scanner/*', {replace: true});
        window.location.reload()
    };

    const reDirect = async () => {
        const products: any = [];

        for (let i = 0; i < groceries.length; i++) {
            if (i in datesDict) {
                groceries[i].date = datesDict[i];
            }
        }

        for (let i = 0; i < groceries.length; i++) {
            if (i in weightDict) {
                groceries[i].weight = Number(weightDict[i]);
            }
        }

        for (let i = 0; i < groceries.length; i++) {
            if (!indicesArray.includes(groceries[i].id)) {
                products.push(groceries[i]);
            }
        }


        try {
            for (let i = 0; i < products.length; i++) {
                if (Number(products[i].weight) === 0) {
                    throw new Error('Weight cannot be empty! Fill all fields');
                }
            }

            let login = sessionStorage.getItem('userLogin');
            if (!login) {
                login = ''
            }
            //TODO: HERE CHANGING groceries INTO THE NEW ARRAY PROVIDED FROM STATES
            // const products = groceries;

            const sendingGrocData: newGroceries = {
                login,
                products
            }

            console.log(sendingGrocData.products);


            const currResponse = await fetch(`/product/save`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(sendingGrocData),
            });

            const currStatus = currResponse.status;
            if (currStatus === 200) {
                navigate('/grocery/*', {replace: true});
                window.location.reload()
            } else {
                throw new Error("ERROR")
            }

        } catch (err) {
            alert(err);
        }
    };

    return (
        <>
            <HeaderPage setMenuVisible={setMenuVisible}/>
            <div className="qr-page">
                <div className="qr-heading">
                    <div onClick={reNavigate}>
                        <svg width="20" height="17" viewBox="0 0 14 11" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <path
                                d="M6.20676 10.6948C6.01923 10.8822 5.76492 10.9875 5.49976 10.9875C5.23459 10.9875 4.98028 10.8822 4.79276 10.6948L0.292756 6.19476C0.105285 6.00723 -3.05176e-05 5.75293 -3.05176e-05 5.48776C-3.05176e-05 5.2226 0.105285 4.96829 0.292756 4.78076L4.79276 0.280763C4.98136 0.0986042 5.23396 -0.00218964 5.49616 8.86917e-05C5.75835 0.00236702 6.00917 0.107536 6.19457 0.292944C6.37998 0.478352 6.48515 0.729165 6.48743 0.991362C6.48971 1.25356 6.38891 1.50616 6.20676 1.69476L3.49976 4.48776L12.9998 4.48776C13.265 4.48776 13.5193 4.59312 13.7069 4.78066C13.8944 4.96819 13.9998 5.22255 13.9998 5.48776C13.9998 5.75298 13.8944 6.00733 13.7069 6.19487C13.5193 6.38241 13.265 6.48776 12.9998 6.48776L3.49976 6.48776L6.20676 9.28076C6.39423 9.46829 6.49954 9.7226 6.49954 9.98776C6.49954 10.2529 6.39423 10.5072 6.20676 10.6948Z"
                                fill="#4D544B"
                            />
                        </svg>
                    </div>
                    <div>Save groceries</div>
                </div>
                <div className="groceryCards">
                    <CardGroceryComponent groceries={groceries}/>
                </div>
                <button className="savebutn" onClick={reDirect}>Save</button>
            </div>
        </>
    );
};

export default GroceryTemporary;
