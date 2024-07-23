import {FC, useEffect, useState} from "react";
import HeaderPage from "./HeaderPage";
import '../styles/dietStyles.css'
import CardContainer from "./dietComponent";
import {DietInterface} from "../interfaces/diet-interfaces"

const Diet: FC<{ setMenuVisible: (visible: boolean) => void }> = ({ setMenuVisible }) => {
    const [loading, setLoading] = useState(false); // Loading state
    const [activeButton, setActiveButton] = useState('breakfast'); // state to manage active button
    const [dishes, setDishes] = useState<DietInterface["dishes"]>([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                setLoading(true); // Set loading to true before starting fetch
                const login = sessionStorage.getItem('userLogin');
                const currResponse = await fetch(`/dish/${login}`);
                const currStatus = currResponse.status;
                if (currStatus === 200) {
                    const data: DietInterface["dishes"] = await currResponse.json();
                    setDishes(data);
                } else {
                    console.error(currResponse.statusText);
                }
            } catch (err) {
                console.warn(err);
            } finally {
                setLoading(false); // Set loading to false after fetch is complete
            }
        }
        fetchData();
    }, []);

    const regeneration = async (state: string, stateName: string) => {
        try {
            setLoading(true); // Set loading to true before starting regeneration
            const login = sessionStorage.getItem('userLogin');
            const currResponse = await fetch(`/dish/${stateName}/${login}`, {
                method: 'POST'
            });
            const currStatus = currResponse.status;
            if (currStatus === 200) {
                window.location.reload();
            } else {
                console.error(currResponse.statusText);
            }
        } catch (e) {
            console.warn(e);
        } finally {
            setLoading(false); // Set loading to false after regeneration is complete
        }
    }

    /* Here the received request from the backend will be processed, which will subsequently be thrown out in the form of a card component */
    const renderComponent = () => {
        switch (activeButton) {
            case 'breakfast':
                return (
                    <>
                        <CardContainer currentState='B' dishes={dishes} />
                        <button className='regenerate' onClick={() => regeneration('B', 'breakfast')}>Regenerate</button>
                    </>
                );
            case 'lunch':
                return (
                    <>
                        <CardContainer currentState='L' dishes={dishes} />
                        <button className='regenerate' onClick={() => regeneration('L', 'lunch')}>Regenerate</button>
                    </>
                );
            case 'dinner':
                return (
                    <>
                        <CardContainer currentState='D' dishes={dishes} />
                        <button className='regenerate' onClick={() => regeneration('D', 'dinner')}>Regenerate</button>
                    </>
                );
            default:
                return null;
        }
    };

    return (
        <div>
            <HeaderPage setMenuVisible={setMenuVisible} />
            <div className='pageContainer'>
                <h1>Diet</h1>
                <div className="button-container">
                    <button
                        className={activeButton === 'breakfast' ? 'active' : 'button'}
                        onClick={() => setActiveButton('breakfast')}>
                        Breakfast
                    </button>
                    <button
                        className={activeButton === 'lunch' ? 'active' : 'button'}
                        onClick={() => setActiveButton('lunch')}>
                        Lunch
                    </button>
                    <button
                        className={activeButton === 'dinner' ? 'active' : 'button'}
                        onClick={() => setActiveButton('dinner')}>
                        Dinner
                    </button>
                </div>
                {loading ? (
                    <span className="loader"></span> // Show spinner while loading
                ) : (
                    <div className="display-component">
                        {renderComponent()}
                    </div>
                )}
            </div>
        </div>
    );
};

export default Diet;